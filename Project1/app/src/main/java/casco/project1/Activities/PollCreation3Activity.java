package casco.project1.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.dragselectrecyclerview.DragSelectRecyclerView;
import com.afollestad.dragselectrecyclerview.DragSelectRecyclerViewAdapter;
import com.afollestad.materialcab.MaterialCab;

import java.io.FileOutputStream;

import casco.project1.Adapters.DragSelectRecyclerAdapterTimes;
import casco.project1.Interfaces.ClickListener;
import casco.project1.R;
import casco.project1.dataBackend.Constants;
import casco.project1.dataBackend.LocalDataStore;
import casco.project1.dataBackend.Poll;
import casco.project1.dataBackend.Serializier;
import casco.project1.dataBackend.User;

public class PollCreation3Activity
        extends AppCompatActivity
        implements ClickListener, MaterialCab.Callback,
        View.OnClickListener, DragSelectRecyclerViewAdapter.SelectionListener
{
    User currentUser;
    Poll newPoll;
    String pollName;
    String pollDescription;
    String timeStart;
    String timeEnd;

    TextView tvPollName;
    Button btnBack;
    Button btnCreate;
    DragSelectRecyclerView dsrvTimes;
    DragSelectRecyclerAdapterTimes dsraAdapter2;
    MaterialCab cab;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_creation3);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null) {
            currentUser = Constants.loadUser(bundle);
            newPoll = (Poll) bundle.getSerializable(Constants.PollBundleKey);
        }
        else if (savedInstanceState != null)
        {
            pollName = savedInstanceState.getString(Constants.PollNameBundleKey);
            pollDescription = savedInstanceState.getString(Constants.PollDescBundleKey);
            currentUser = (User) savedInstanceState.getSerializable(Constants.UserBundleKey);
            newPoll = (Poll) savedInstanceState.getSerializable(Constants.PollBundleKey);
        }
//        pollName = bundle.getString(Constants.PollNameBundleKey);
//        pollDescription = bundle.getString(Constants.PollDescBundleKey);
        timeStart = bundle.getString(Constants.PollStartTimeBundleKey);
        timeEnd = bundle.getString(Constants.PollEndTimeBundleKey);

        tvPollName = (TextView) findViewById(R.id.tvPollName3);
        dsraAdapter2 = new DragSelectRecyclerAdapterTimes(this);
        if(timeStart != null && timeEnd!=null)
            dsraAdapter2.loadTimes(timeStart, timeEnd);
        else
            dsraAdapter2.loadTimes();
        dsraAdapter2.setSelectionListener(this);
        dsraAdapter2.restoreInstanceState(savedInstanceState);

        dsrvTimes = (DragSelectRecyclerView) findViewById(R.id.dsrvTimes);
        dsrvTimes.setAdapter(dsraAdapter2);
        dsrvTimes.setLayoutManager(new LinearLayoutManager(this));
        cab = MaterialCab.restoreState(savedInstanceState, this, this);
        btnBack = (Button) findViewById(R.id.btnPart2);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnBack.setOnClickListener(this);
        btnCreate.setOnClickListener(this);

        tvPollName.setText(newPoll.getTitle());
        btnCreate.setEnabled(false);
    }

    public DragSelectRecyclerView getDragSelectRecyclerView(){
        return dsrvTimes;
    }
    public DragSelectRecyclerAdapterTimes getDragSelectRecyclerAdapter2(){
        return dsraAdapter2;
    }
    @Override
    public void onDragSelectionChanged(int i) {
        Log.i("STEFAN", "NUM ITEMS OR ITEM SELECTED?: " + i);
        if(i > 0 && btnCreate != null){
            btnCreate.setEnabled(true);
        }
        else if(i < 1 && btnCreate != null){
            btnCreate.setEnabled(false);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.PollNameBundleKey, pollName);
        outState.putString(Constants.PollDescBundleKey, pollDescription);
        outState.putSerializable(Constants.UserBundleKey, currentUser);
        if (dsraAdapter2 != null) {
            dsraAdapter2.saveInstanceState(outState);
        }
        if (cab != null) {
            cab.saveState(outState);
        }
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pollName = savedInstanceState.getString(Constants.PollNameBundleKey);
        pollDescription = savedInstanceState.getString(Constants.PollDescBundleKey);
        currentUser = (User) savedInstanceState.getSerializable(Constants.UserBundleKey);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnPart2:
                Log.i("STEFAN", "Clicked Back");
                this.finish();
                break;
            case R.id.btnCreate:
                Log.i("STEFAN", "Clicked Create");
                Context context = getApplicationContext();
                CharSequence text = "Poll created!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                // Add the selected times to the poll
                newPoll.getBaseTime().addDay("Monday");

                for(Integer index: getDragSelectRecyclerAdapter2().getSelectedIndices())
                {
                    newPoll.getBaseTime().addTimeToDay("Monday", getDragSelectRecyclerAdapter2().getItem(index));
                }
                getDragSelectRecyclerAdapter2().clearSelected();

                LocalDataStore populator = new LocalDataStore();
                populator.savePoll(context, newPoll);

                // Go back to the Home Screen
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

                break;
            case R.id.dsrvTimes:
                Log.i("STEFAN", "Selected Recycler");

                break;
            default:
                Log.e("Error","WTF just happened?!");
                break;
        }
    }
    @Override
    public void onClick(int index) {

        getDragSelectRecyclerAdapter2().toggleSelected(index);

    }

    @Override
    public void onLongClick(int index) {
        getDragSelectRecyclerView().setDragSelectActive(true, index);
    }

    @Override
    public boolean onCabCreated(MaterialCab materialCab, Menu menu) {
        return true;
    }

    @Override
    public boolean onCabItemClicked(MenuItem item) {
        if(item.getItemId() == R.id.btnPart2)
        {
            return false;
        }
        else if(item.getItemId() == R.id.btnCreate)
        {
            StringBuilder sb = new StringBuilder();
            int traverse = 0;
            for(Integer index: getDragSelectRecyclerAdapter2().getSelectedIndices())
            {
                if(traverse > 0){
                    sb.append(", ");
                }
                sb.append(getDragSelectRecyclerAdapter2().getItem(index));
                traverse++;
            }
            Log.i("CHANG", sb.toString());
            getDragSelectRecyclerAdapter2().clearSelected();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCabFinished(MaterialCab materialCab) {
        getDragSelectRecyclerAdapter2().clearSelected();
        return true;
    }
}
