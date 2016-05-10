package casco.project1.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import casco.project1.DisplayPreferences;
import casco.project1.Adapters.PollAdapter;
import casco.project1.R;
import casco.project1.dataBackend.Constants;
import casco.project1.dataBackend.Poll;
import casco.project1.dataBackend.Response;
import casco.project1.dataBackend.Serializier;
import casco.project1.dataBackend.TestPopulator;
import casco.project1.dataBackend.TimeSet;
import casco.project1.dataBackend.User;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    CoordinatorLayout coordinatorLayout;
    ListView lvPolls;
    List<Poll> polls;
    TextView test;
    User currentUser;
    int RC_SIGN_IN = 888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(view.getContext(), PollCreationActivity.class);
                Bundle b = new Bundle();
                b.putSerializable(Constants.UserBundleKey, currentUser);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        TestPopulator data = new TestPopulator();
        polls = data.polls;

        lvPolls = (ListView) findViewById(R.id.lv_polls);
        lvPolls.setAdapter(new PollAdapter(this, data));
        lvPolls.setOnItemClickListener(this);
        Bundle bundle = this.getIntent().getExtras();
        //loadUser(bundle);

        GoogleApiClient.OnConnectionFailedListener listen = new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {

            }
        };
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, listen /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        int RC_SIGN_IN = 888;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

        try {
            poll_serialization();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void loadUser(Bundle bundle){
        currentUser = Constants.loadUser(bundle);
        //test = (TextView) findViewById(R.id.tvTest);
        //test.setText("Logged in as: " + currentUser.getName());
        Log.i("STEFAN", currentUser.getName());
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
        Intent intent= new Intent(this, PollDetailsActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(Constants.UserBundleKey, currentUser);
        intent.putExtras(b);
        intent.putExtra("PollName", polls.get(position).getTitle());
        intent.putExtra("PollCreator", polls.get(position).getCreator().getName());

        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        //Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            test = (TextView) findViewById(R.id.tvTest);
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            test.setText("Logged in as: " + personName);

            currentUser = new User(personName);
            if(currentUser == null) {
                Snackbar snackbar = Snackbar.make(
                        coordinatorLayout, "Logged in as "+personName+", but User class is NULL",
                        Snackbar.LENGTH_LONG);
                snackbar.show();
            }else{
                Snackbar snackbar = Snackbar.make(
                        coordinatorLayout, "Logged in as "+personName+".  Welcome!",
                        Snackbar.LENGTH_LONG);
                snackbar.show();
            }

            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //updateUI(true);
        } else {
            test = (TextView) findViewById(R.id.tvTest);
            test.setText("Please login with Google Login");
        }
    }



    public void poll_serialization() throws Exception {
        int c = 753;

        User u = new User("Mary Poppins");

        String t = "Penguins";

        String d = "supercalifragilisticexpialidocious";

        TimeSet ts = new TimeSet();

        List<Response> r = new ArrayList<Response>();

        Poll poll = new Poll(c, u, t, d, ts, r);

        // serialize file
        String fileName = "poll.poll";
        FileOutputStream fos;
        try {
            fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            Serializier.serialize(fos, poll);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // deserialize file
        Poll depoll = null;
        FileInputStream fis;
        try {
            fis = openFileInput(fileName);
            depoll = (Poll) Serializier.deserialize(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("SERIAL TEST", "code: " + depoll.getShortCode());
        Log.i("SERIAL TEST", "user: " + depoll.getCreator().getName());
        Log.i("SERIAL TEST", "title: " + depoll.getTitle());
        Log.i("SERIAL TEST", "desc: " + depoll.getDescription());
    }
}

