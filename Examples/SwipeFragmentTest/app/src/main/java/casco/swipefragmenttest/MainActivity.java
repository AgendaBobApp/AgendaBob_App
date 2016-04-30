package casco.swipefragmenttest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnTouchListener {
    FragmentManager manager;
    GestureDetector gestureDetector;
    RelativeLayout rlLayout;
    FragmentA fragA;
    FragmentB fragB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getFragmentManager();
        gestureDetector = new GestureDetector(this, new MyGestutreDetector());
        rlLayout = (RelativeLayout) findViewById(R.id.rlMainActivity);
        rlLayout.setOnTouchListener(this);
        loadFragmentA();
    }
    public void loadFragmentA(){
        FragmentTransaction trans = manager.beginTransaction();
        if (fragA == null){
            fragA = new FragmentA();
            trans.add(R.id.group, fragA, "fragmentA");
        }
        if (fragB != null){
            trans.detach(fragB);
        }
        trans.attach(fragA);
        trans.commit();
    }
    public void loadFragmentB(){
        FragmentTransaction trans = manager.beginTransaction();
        if (fragB == null){
            fragB = new FragmentB();
            trans.add(R.id.group, fragB, "fragmentB");
        }
        if (fragA != null){
            trans.detach(fragA);
        }
        trans.attach(fragB);
        trans.commit();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {



        return gestureDetector.onTouchEvent(event);
    }
    public class MyGestutreDetector extends GestureDetector.SimpleOnGestureListener{
        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_MAX_OFF_PATH = 250;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;

        // needed to work period DO NOT REMOVE
        @Override
        public boolean onDown(MotionEvent e) {
            //Toast.makeText(MainActivity.this, "down event", Toast.LENGTH_SHORT).show();
            /*AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("event down");
            builder.setMessage("this is neat");
            AlertDialog dialog = builder.create();
            dialog.show();*/


            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // in case it is more of an up/down motion
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                    return false;
                }

                // detect swipe left
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(MainActivity.this, "Left Swipe", Toast.LENGTH_SHORT).show();
                    loadFragmentB();
                }
                // detect swipe right
                else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(MainActivity.this, "Right Swipe", Toast.LENGTH_SHORT).show();
                    loadFragmentA();
                }
                // catch if something interesting happens (will is curious)
                else {
                    Toast.makeText(MainActivity.this, "WTF happened?", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("onFling try/catch Exception");
                builder.setMessage("You Blew it UP! You Blew it UP");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            return false;
        }
    }
}
