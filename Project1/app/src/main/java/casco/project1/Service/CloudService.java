package casco.project1.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.util.List;

import casco.project1.dataBackend.LocalDataStore;

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
}
