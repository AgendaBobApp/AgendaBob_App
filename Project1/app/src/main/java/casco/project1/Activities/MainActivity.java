package casco.project1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import casco.project1.DisplayPreferences;
import casco.project1.Adapters.PollAdapter;
import casco.project1.R;
import casco.project1.dataBackend.Constants;
import casco.project1.dataBackend.Poll;
import casco.project1.dataBackend.TestPopulator;
import casco.project1.dataBackend.User;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lvPolls;
    List<Poll> polls;
    TextView test;
    User currentuser;

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
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent pollCreationIntent = new Intent(view.getContext(), PollCreationActivity.class);
                saveUser(pollCreationIntent);
                startActivity(pollCreationIntent);
            }
        });

        TestPopulator data = new TestPopulator();
        polls = data.polls;

        lvPolls = (ListView) findViewById(R.id.lv_polls);
        lvPolls.setAdapter(new PollAdapter(this, data));
        lvPolls.setOnItemClickListener(this);
        Bundle bundle = this.getIntent().getExtras();
        loadUser(bundle);

    }
    public void loadUser(Bundle bundle){
        if (bundle != null) {
            currentuser = (User) bundle.getSerializable(Constants.UserBundleKey);
            Log.i("STEFAN", "User passed from other activity");
        }
        else
        {
            currentuser = new User();
            Log.i("STEFAN", "User RECREATED");
        }
        test = (TextView) findViewById(R.id.tvTest);
        test.setText("Logged in as: "+currentuser.getName());
        Log.i("STEFAN", currentuser.getName());
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
            //saveUser(settings_intent);
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
        saveUser(intent);
        intent.putExtra("PollName", polls.get(position).getTitle());
        intent.putExtra("PollCreator", polls.get(position).getCreator().getName());
        startActivity(intent);
    }
    public void saveUser(Intent intent){
        Bundle b = new Bundle();
        b.putSerializable(Constants.UserBundleKey, currentuser);
        intent.putExtras(b);
    }
}
