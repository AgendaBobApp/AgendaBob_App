package casco.project1.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialcab.MaterialCab;

import casco.project1.Adapters.DragSelectRecyclerAdapter;
import casco.project1.Fragments.CreatePoll1;
import casco.project1.Fragments.CreatePoll2;
import casco.project1.Interfaces.ClickListener;
import casco.project1.Interfaces.Communicator;
import casco.project1.R;
import casco.project1.dataBackend.Constants;
import casco.project1.dataBackend.Poll;
import casco.project1.dataBackend.User;

public class PollCreationActivity extends AppCompatActivity
        implements View.OnClickListener
{
    User currentUser;
    TextView tvPollTitle;
    EditText tePollTitle;
    TextView tvPollDescription;
    EditText tePollDescription;
    Button btnNext;
    Spinner spinner1;
    Spinner spinner2;
    public String pollName;
    public String pollDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_creation);
        tvPollTitle = (TextView) findViewById(R.id.tvPollTitle);
        tePollTitle = (EditText) findViewById(R.id.tePollTitle);
        tePollTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                Log.i("STEFAN", "MY TEXT IS \"" + tePollTitle.getText().toString()+ "\"");
                if (tePollTitle.getText().length() > 0) {
                    btnNext.setEnabled(true);
                    pollName = tePollTitle.getText().toString();
                    pollDescription = tePollDescription.getText().toString();
                }
            }
        });
        tvPollDescription = (TextView) findViewById(R.id.tvPollDescription);
        tePollDescription = (EditText) findViewById(R.id.teDescription);
        btnNext = (Button) findViewById(R.id.btnPart2);
        btnNext.setOnClickListener(this);
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null)
            currentUser = Constants.loadUser(bundle);
        else if (savedInstanceState != null)
        {
            pollName = savedInstanceState.getString(Constants.PollNameBundleKey);
            pollDescription = savedInstanceState.getString(Constants.PollDescBundleKey);
            currentUser = (User) savedInstanceState.getSerializable(Constants.UserBundleKey);
        }
        pollName = bundle.getString(Constants.PollNameBundleKey,"");
        pollDescription = bundle.getString(Constants.PollDescBundleKey,"");
        btnNext.setEnabled(false);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.times_half_hour, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.times_half_hour, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i("STEFAN", "MY TEXT IS \"" + tePollTitle.getText().toString() + "\"");
        if (tePollTitle.getText().length() > 0) {
            btnNext.setEnabled(true);
            Log.i("STEFAN", "ENABLED WHEN \"" + tePollTitle.getText().toString() + "\"");
        }else {
            btnNext.setEnabled(false);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pollName = savedInstanceState.getString(Constants.PollNameBundleKey);
        pollDescription = savedInstanceState.getString(Constants.PollDescBundleKey);
        currentUser = (User) savedInstanceState.getSerializable(Constants.UserBundleKey);
        Log.i("STEFAN", "MY TEXT IS \"" + tePollTitle.getText().toString() + "\"");
        if (tePollTitle.getText().length() > 0) {
            btnNext.setEnabled(true);
            Log.i("STEFAN", "ENABLED WHEN \"" + tePollTitle.getText().toString() + "\"");
        }else {
            btnNext.setEnabled(false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        outState.putString(Constants.PollNameBundleKey, pollName);
        outState.putString(Constants.PollDescBundleKey, pollDescription);
        outState.putSerializable(Constants.UserBundleKey, currentUser);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, PollCreation2Activity.class);
        Bundle b = new Bundle();
        b.putSerializable(Constants.UserBundleKey, currentUser);
        intent.putExtras(b);
        intent.putExtra(Constants.PollNameBundleKey, pollName);
        intent.putExtra(Constants.PollDescBundleKey, pollDescription);

        startActivity(intent);
    }
}
