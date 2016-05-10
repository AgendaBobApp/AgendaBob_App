package casco.project1.Activities;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import casco.project1.R;
import casco.project1.dataBackend.Constants;
import casco.project1.dataBackend.Poll;
import casco.project1.dataBackend.User;

public class PollCreationActivity extends AppCompatActivity
        implements View.OnClickListener
{
    User currentUser;
    TextView tvTest;
    TextView tvPollTitle;
    EditText tePollTitle;
    TextView tvPollDescription;
    EditText tePollDescription;
    Button btnNext;
    Spinner spinner1;
    Spinner spinner2;
    public String pollName;
    public String pollDescription;
    public int startTime;
    public int endTime;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
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
        tvTest = (TextView) findViewById(R.id.tvTest);
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            currentUser = Constants.loadUser(bundle);
            tvTest.setText(currentUser.getName());
        }
        else if (savedInstanceState != null)
        {
            pollName = savedInstanceState.getString(Constants.PollNameBundleKey);
            pollDescription = savedInstanceState.getString(Constants.PollDescBundleKey);
            currentUser = (User) savedInstanceState.getSerializable(Constants.UserBundleKey);
            tvTest.setText(currentUser.getName());
        }
        else{
            tvTest.setText("USER IS NULL");
        }

        btnNext.setEnabled(false);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        setSpinners();
    }
    private void setSpinners(){
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.times_half_hour, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        int spinnerPosition = adapter1.getPosition(Constants.TimesHalfHour[Constants.DefaultStartTimesHalfHour]);
        spinner1.setSelection(spinnerPosition);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.times_half_hour, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinnerPosition = adapter2.getPosition(Constants.TimesHalfHour[Constants.DefaultEndTimesHalfHour]);
        spinner2.setSelection(spinnerPosition);
        int spinnerPosition2 = adapter2.getPosition(Constants.TimesHalfHour[Constants.DefaultEndTimesHalfHour]);
        spinner2.setSelection(spinnerPosition2);
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
        outState.putInt("Starting Time", startTime);
        outState.putInt("Ending Time", endTime);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onClick(View v) {
        if(spinner1.getSelectedItemPosition() >= spinner2.getSelectedItemPosition()){
            Snackbar snackbar = Snackbar.make(
                    v, "The start time needs to be later than the end time.",
                    Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else {
            Intent intent = new Intent(this, PollCreation2Activity.class);
            Bundle b = new Bundle();

            Poll newPoll = new Poll();
            newPoll.setTitle(pollName);
            newPoll.setDescription(pollDescription);
            newPoll.setCreator(currentUser);
//        intent.putExtra(Constants.PollNameBundleKey, pollName);
//        intent.putExtra(Constants.PollDescBundleKey, pollDescription);
            intent.putExtra(Constants.PollStartTimeBundleKey, spinner1.getSelectedItem().toString());
            intent.putExtra(Constants.PollEndTimeBundleKey, spinner2.getSelectedItem().toString());
            b.putSerializable(Constants.PollBundleKey, newPoll);
            b.putSerializable(Constants.UserBundleKey, currentUser);
            intent.putExtras(b);

            startActivity(intent);
        }
    }
}
