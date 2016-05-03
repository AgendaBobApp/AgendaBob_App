package casco.project1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import casco.project1.dataBackend.Poll;
import casco.project1.dataBackend.TestPopulator;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lvPolls;
    List<Poll> polls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TestPopulator data = new TestPopulator();
        polls = data.polls;
        lvPolls = (ListView) findViewById(R.id.lv_polls);
        lvPolls.setAdapter(new PollAdapter(this, data));
        lvPolls.setOnItemClickListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings_intent = new Intent(this, DisplayPreferences.class);
            startActivity(settings_intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent= new Intent(this, PollSetupActivity.class);
//        String[] pollName = getResources().getStringArray(R.array.test_polls);
//        String[] pollCreator = getResources().getStringArray(R.array.test_participants);
        //

        intent.putExtra("PollName", polls.get(position).getTitle());
        intent.putExtra("PollCreator", polls.get(position).getCreator().getName());
        startActivity(intent);
    }
}
