package stefan.transactionexample;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getFragmentManager();
    }
    public void addA(View v){
        FragmentA fragA = new FragmentA();
        FragmentTransaction trans = manager.beginTransaction();
        trans.add(R.id.group,fragA,"fragmentA");
        trans.commit();


    }
    public void addB(View v){
        FragmentB fragB = new FragmentB();
        FragmentTransaction trans = manager.beginTransaction();
        trans.add(R.id.group,fragB,"fragmentB");
        trans.commit();

    }
    public void removeA(View v){
        FragmentA fragA = (FragmentA) manager.findFragmentByTag("fragmentA");
        FragmentTransaction trans = manager.beginTransaction();
        if (fragA != null){
            trans.remove(fragA);
            trans.commit();
        }else
        {
            Toast.makeText(this,"Fragment A is not here.", Toast.LENGTH_SHORT);
        }
    }
    public void removeB(View v){
        FragmentB fragB = (FragmentB) manager.findFragmentByTag("fragmentB");
        FragmentTransaction trans = manager.beginTransaction();
        if (fragB != null) {
            trans.remove(fragB);
            trans.commit();
        }else
        {
            Toast.makeText(this,"Fragment B is not here.", Toast.LENGTH_SHORT);
        }
    }
    public void replaceAWtihB(View v){
        FragmentB fragB = new FragmentB();
        FragmentTransaction trans = manager.beginTransaction();
        trans.replace(R.id.group, fragB, "fragmentB");
        trans.commit();

    }
    public void replaceBWtihA(View v){
        FragmentA fragA = new FragmentA();
        FragmentTransaction trans = manager.beginTransaction();
        trans.replace(R.id.group, fragA, "fragmentA");
        trans.commit();
    }
    public void attachA(View v){
        FragmentA fragA = (FragmentA) manager.findFragmentByTag("fragmentA");
        FragmentTransaction trans = manager.beginTransaction();
        if (fragA != null){
            trans.attach(fragA);
            trans.commit();
        }else
        {
            Toast.makeText(this,"Fragment A is not here.", Toast.LENGTH_SHORT);
        }

    }
    public void detachA(View v){
        FragmentA fragA = (FragmentA) manager.findFragmentByTag("fragmentA");
        FragmentTransaction trans = manager.beginTransaction();
        if (fragA != null){
            trans.detach(fragA);
            trans.commit();
        }else
        {
            Toast.makeText(this,"Fragment A is not here.", Toast.LENGTH_SHORT);
        }

    }
}
