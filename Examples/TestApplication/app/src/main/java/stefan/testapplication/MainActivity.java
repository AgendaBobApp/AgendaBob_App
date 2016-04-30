package stefan.testapplication;

import android.app.FragmentManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

public class MainActivity extends Activity implements FragmentA.Communicator {
    FragmentA fragA;
    FragmentB fragB;
    FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getFragmentManager();
        fragA = (FragmentA) manager.findFragmentById(R.id.fragmentA);
        fragA.setCommunicator(this);
    }

    @Override
    public void respond(int index) {
        fragB = (FragmentB) manager.findFragmentById(R.id.fragmentB);
        if (fragB != null && fragB.isVisible()){
            fragB.changeData(index);
        }else{
            Intent anotherActivityIntent = new Intent(this, AnotherActivity.class);
            anotherActivityIntent.putExtra("index", index);
            startActivity(anotherActivityIntent);
        }
    }

}
