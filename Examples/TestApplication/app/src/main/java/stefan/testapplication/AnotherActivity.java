package stefan.testapplication;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

public class AnotherActivity extends Activity {

    int index;
    FragmentManager manager;
    FragmentB fragB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);
        manager = getFragmentManager();
        fragB = (FragmentB) manager.findFragmentById(R.id.fragmentB);
        if (fragB != null){
            fragB.changeData(index);
        }
    }

    @Nullable
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }
}
