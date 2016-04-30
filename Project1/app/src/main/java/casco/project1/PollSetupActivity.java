package casco.project1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PollSetupActivity extends Activity implements AdapterView.OnItemClickListener{
    TextView tvPollName;
    TextView tvPollCreator;
    TextView tvPollParticipants;
    ListView lvParticipants;
    Button btnRespond;
    Button btnViewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_setup);
        Intent intent = getIntent();
        String pollName = intent.getStringExtra("PollName");

        tvPollName = (TextView) findViewById(R.id.tv_poll_name);

        tvPollName.setText(pollName);
        tvPollCreator = (TextView) findViewById(R.id.tv_poll_creator);
        tvPollParticipants = (TextView) findViewById(R.id.tv_list_description);

        btnRespond = (Button) findViewById(R.id.btn_respond);
        btnViewResults = (Button) findViewById(R.id.btn_view_results);

        lvParticipants = (ListView) findViewById(R.id.lv_participants);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,R.array.test_participants, android.R.layout.simple_list_item_1);
        lvParticipants.setAdapter(adapter);
        lvParticipants.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String[] desc = getResources().getStringArray(R.array.test_participants);
        Toast.makeText(this, desc[position], Toast.LENGTH_SHORT);
    }
}
