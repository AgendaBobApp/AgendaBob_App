package casco.project1.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import casco.project1.dataBackend.LocalDataStore;
import casco.project1.dataBackend.Poll;

public class CloudService extends Service {
    public CloudService() {
    }

    LocalDataStore populator;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return new CloudServiceBinder();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        populator = new LocalDataStore();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updatePolls();
            }
        }, 1, 900000);

        return START_STICKY;
    }

    public class CloudServiceBinder extends Binder {
        public CloudService getService() {
            return CloudService.this;
        }
    }

    public interface Callback {

    }

    private Callback callback;

    public void setListener(Callback callback) {
        this.callback = callback;
    }

    private class PollRemover extends Thread {

        public void run() {
            List<String> polls = populator.removedPolls(getApplicationContext());
            for (String poll:polls) {
                Log.d("MELLO", "Poll: " + poll);

                // Todo: Send these to the cloud.

                File dir = getFilesDir();
                File file = new File(dir, poll + ".remove");
                file.delete();
            }
        }

        public void cleanup() {

        }
    }

    public void removePolls() {
        PollRemover remover = new PollRemover();
        remover.start();
    }

    private class PollUploader extends Thread {

        public void run() {
            List<Poll> polls = populator.newPolls(getApplicationContext());
            for (Poll poll:polls) {
                Log.d("CHANG", "New Poll: " + poll.getLongCode());

                // Todo: send these to the cloud

                Context c = getApplicationContext();
                String baseFileName = poll.getLongCode();
                populator.renamePoll(c, baseFileName + ".new", baseFileName + ".poll");
            }
        }

        public void cleanup() {

        }
    }

    public void uploadPolls() {
        PollUploader uploader = new PollUploader();
        uploader.start();
    }

    private class PollUpdater extends Thread {
         public void run() {
             // Todo: get updates from the cloud

             // Todo: save downloaded polls to the data store

             Log.d("CHANG", "I UPDATED STUFF!");
         }

        public void cleanup() {

        }
    }

    public void updatePolls() {
        PollUpdater updater = new PollUpdater();
        updater.start();
    }
}
