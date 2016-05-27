package casco.project1.dataBackend;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by crazy on 5/12/2016.
 */
public class LocalDataStore {
    public boolean savePoll(Context c, Poll poll) {
        // Using filename for now; should use unique ID later
        String fileName = poll.getTitle() + ".new";
        //String fileName = poll.getShortCode() + ".poll";
        poll.setLongCode(poll.getTitle());

        FileOutputStream fos;
        try {
            fos = c.openFileOutput(fileName, Context.MODE_PRIVATE);
            Serializier.serialize(fos, poll);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Poll loadPoll(Context c, String fileName) {
        Poll newPoll = null;
        FileInputStream fis;

        try {
            fis = c.openFileInput(fileName);
            newPoll = (Poll) Serializier.deserialize(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newPoll;
    }

    public List<Poll> loadAllPolls(Context c) {
        List<Poll> polls = new ArrayList<Poll>();
        String[] files = c.fileList();
        Poll newPoll = null;

        if (files.length > 0) {
            for (String file : files) {
                if (file.endsWith(".poll") || file.endsWith(".new")) {
                    newPoll = loadPoll(c, file);
                    if (newPoll != null) {
                        polls.add(newPoll);
                    }
                }
            }
        }

        return polls;
    }

    public void removePoll(Context c, Poll p) {
        String baseFileName = p.getLongCode();

        File oldFile = c.getFileStreamPath(baseFileName + ".poll");
        File newFile = c.getFileStreamPath(baseFileName + ".remove");
        oldFile.renameTo(newFile);
        Log.d("CHANG", "Removed poll with name: " + baseFileName);
    }
}
