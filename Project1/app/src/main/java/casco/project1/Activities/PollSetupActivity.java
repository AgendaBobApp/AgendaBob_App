package casco.project1.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import casco.project1.Adapters.ParticipantAdapter;
import casco.project1.R;
import casco.project1.dataBackend.Constants;
import casco.project1.dataBackend.User;

public class PollSetupActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    TextView tvPollName;
    TextView tvPollCreator;
    TextView tvPollParticipants;
    ListView lvParticipants;
    Button btnRespond;
    Button btnViewResults;
    TextView test;
    User currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_setup);
        Intent intent = getIntent();
        String pollName = intent.getStringExtra("PollName");
        String pollCreator = intent.getStringExtra("PollCreator");
        Bundle bundle = this.getIntent().getExtras();
        loadUser(bundle);

        tvPollName = (TextView) findViewById(R.id.tv_poll_name);
        tvPollName.setText(pollName);
        tvPollCreator = (TextView) findViewById(R.id.tv_poll_creator);
        tvPollCreator.setText(pollCreator);
        tvPollParticipants = (TextView) findViewById(R.id.tv_list_description);

        btnRespond = (Button) findViewById(R.id.btn_respond);
        btnViewResults = (Button) findViewById(R.id.btn_view_results);

        lvParticipants = (ListView) findViewById(R.id.lv_participants);
        lvParticipants.setAdapter(new ParticipantAdapter(this));
        lvParticipants.setOnItemClickListener(this);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String[] desc = getResources().getStringArray(R.array.test_participants);
        Toast.makeText(this, desc[position], Toast.LENGTH_SHORT);
    }
}
