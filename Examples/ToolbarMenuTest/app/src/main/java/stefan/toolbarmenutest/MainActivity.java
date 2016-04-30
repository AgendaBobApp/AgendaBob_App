package stefan.toolbarmenutest;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings){
            Toast.makeText(this,"You hit the toolbar item!", Toast.LENGTH_SHORT);
        }
        if (id == R.id.navigate){
           startActivity(new Intent(this, SubActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }
}
