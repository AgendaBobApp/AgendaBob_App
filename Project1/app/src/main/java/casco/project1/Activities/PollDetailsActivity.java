package casco.project1.Activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import casco.project1.Adapters.ResponseAdapter;
import casco.project1.R;
import casco.project1.Service.CloudService;
import casco.project1.dataBackend.Constants;
import casco.project1.dataBackend.LocalDataStore;
import casco.project1.dataBackend.Poll;
import casco.project1.dataBackend.User;

public class PollDetailsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, ServiceConnection {
    TextView tvPollName;
    TextView tvPollCreator;
    TextView tvPollResponses;
    ListView lvResponses;
    Button btnRespond;
    Button btnViewResults;
    Button btnRemovePoll;
    TextView tvTest;
    User currentUser;
    Poll poll;

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

        poll = (Poll) intent.getSerializableExtra(Constants.PollBundleKey);

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
        btnRemovePoll = (Button) findViewById(R.id.btn_remove_poll);

        lvResponses = (ListView) findViewById(R.id.lv_responses);
        lvResponses.setAdapter(new ResponseAdapter(this, poll.getResponses()));
        lvResponses.setOnItemClickListener(this);

        Context app = getApplicationContext();
        Intent serviceIntent = new Intent(app, CloudService.class);
        app.startService(serviceIntent);

        for (String day: poll.getBaseTime().getDayList()) {
            Log.d("CHANG", "Day: " + day);
            for (String time: poll.getBaseTime().getDayTimesList(day)) {
                Log.d("CHANG", time);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // get a reference to the service, for receiving messages
        Context app = getApplicationContext();
        Intent intent = new Intent(app, CloudService.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // get a reference to the service, for receiving messages
        Context app = getApplicationContext();
        Intent intent = new Intent(app, CloudService.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (service != null) {
            unbindService(this);
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (service != null) {
//            unbindService(this);
//        }
//    }

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

    public void deletePoll(View view) {
        LocalDataStore populator = new LocalDataStore();
        populator.removePoll(getApplicationContext(), poll);

        service.removePolls();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Constants.ClearBackstackFlags);
        startActivity(intent);
        finish();
    }

    private CloudService service;

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        service = ((CloudService.CloudServiceBinder) binder).getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        if (service != null)
            service.setListener(null);
        service = null;
    }
}
