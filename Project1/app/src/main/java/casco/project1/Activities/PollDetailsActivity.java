package casco.project1.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import casco.project1.Adapters.ResponseAdapter;
import casco.project1.R;
import casco.project1.dataBackend.Constants;
import casco.project1.dataBackend.User;

public class PollDetailsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    TextView tvPollName;
    TextView tvPollCreator;
    TextView tvPollResponses;
    ListView lvResponses;
    Button btnRespond;
    Button btnViewResults;
    TextView tvTest;
    User currentUser;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_details);
        Intent intent = getIntent();
        String pollName = intent.getStringExtra("PollName");
        String pollCreator = intent.getStringExtra("PollCreator");
        tvTest = (TextView) findViewById(R.id.tvTest);
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            currentUser = Constants.loadUser(bundle);
            tvTest.setText(currentUser.getName());
        }
        else if (savedInstanceState != null)
        {
            currentUser = (User) savedInstanceState.getSerializable(Constants.UserBundleKey);
            tvTest.setText(currentUser.getName());
        }


        tvPollName = (TextView) findViewById(R.id.tv_poll_name);
        tvPollName.setText(pollName);
        tvPollCreator = (TextView) findViewById(R.id.tv_poll_creator);
        tvPollCreator.setText(pollCreator);
        tvPollResponses = (TextView) findViewById(R.id.tv_list_description);

        btnRespond = (Button) findViewById(R.id.btn_respond);
        btnViewResults = (Button) findViewById(R.id.btn_view_results);

        lvResponses = (ListView) findViewById(R.id.lv_responses);
        lvResponses.setAdapter(new ResponseAdapter(this, currentUser));
        lvResponses.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String[] desc = getResources().getStringArray(R.array.test_participants);
        Toast.makeText(this, desc[position], Toast.LENGTH_SHORT);
    }

    public void viewResults(View view) {
        Intent intent = new Intent(this, PollResultsActivity.class);
        startActivity(intent);
    }
    public void viewRespond(View view) {
        Intent intent = new Intent(this, PollRespondActivity.class);
        startActivity(intent);
    }
}
