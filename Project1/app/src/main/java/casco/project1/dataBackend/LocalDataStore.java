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
        String fileName = poll.getLongCode() + ".new";
        //String fileName = poll.getShortCode() + ".poll";

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

    public boolean renamePoll(Context c, String oldFile, String newFile) {
        File o = c.getFileStreamPath(oldFile);
        File n = c.getFileStreamPath(newFile);
        if (o.exists()){
            o.renameTo(n);
            return true;
        } else {
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

        File oldFile1 = c.getFileStreamPath(baseFileName + ".poll");
        File oldFile2 = c.getFileStreamPath(baseFileName + ".new");
        File newFile = c.getFileStreamPath(baseFileName + ".remove");

        if(oldFile1.exists()) {
            oldFile1.renameTo(newFile);
        } else if(oldFile2.exists()) {
            oldFile2.renameTo(newFile);
        }

        Log.d("CHANG", "Removed poll with name: " + baseFileName);
    }

    public List<String> removedPolls(Context c) {
        List<String> polls = new ArrayList<String>();
        String[] files = c.fileList();

        if (files.length > 0) {
            for (String file : files) {
                if (file.endsWith(".remove")) {
                        polls.add(file.replace(".remove",""));
                }
            }
        }

        return polls;
    }

    public List<Poll> newPolls(Context c) {
        List<Poll> polls = new ArrayList<Poll>();
        String[] files = c.fileList();
        Poll newPoll = null;

        if (files.length > 0) {
            for (String file : files) {
                if (file.endsWith(".new")) {
                    newPoll = loadPoll(c, file);
                    if (newPoll != null) {
                        polls.add(newPoll);
                    }
                }
            }
        }

        return polls;
    }
}
