package casco.project1;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PollSetupActivity extends Activity implements AdapterView.OnItemClickListener{
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_setup);
        list = (ListView) findViewById(R.id.lv_participants);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,R.array.test_participants, android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String[] desc = getResources().getStringArray(R.array.test_participants);
        Toast.makeText(this, desc[position], Toast.LENGTH_SHORT);
    }
}
