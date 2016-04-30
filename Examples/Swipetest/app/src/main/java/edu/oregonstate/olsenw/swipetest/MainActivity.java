package edu.oregonstate.olsenw.swipetest;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private GestureDetector gestureDetector;
    private View.OnTouchListener gestureListener;

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.textview);

        // Gesture detection
        gestureDetector = new GestureDetector(this, new MyGestutreDetector());
        gestureListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };

        // attach the gesture listener to relevant views
        text.setOnTouchListener(gestureListener);
    }

    class MyGestutreDetector extends GestureDetector.SimpleOnGestureListener {
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
                }
                // detect swipe right
                else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(MainActivity.this, "Right Swipe", Toast.LENGTH_SHORT).show();
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
