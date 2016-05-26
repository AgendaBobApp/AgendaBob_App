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

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import casco.project1.Adapters.DragSelectRecyclerAdapterTimes;
import casco.project1.Interfaces.ClickListener;
import casco.project1.R;
import casco.project1.dataBackend.Constants;
import casco.project1.dataBackend.LocalDataStore;
import casco.project1.dataBackend.Poll;
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
    List<String> pollDays;
    Integer currentDay;
    Dictionary<String, Set<String>> selectedTimes;

    TextView tvPollDay;
    Button btnBack;
    Button btnCreate;
    Button btnCreatePrev;
    Button btnCreateNext;
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
        pollDays = bundle.getStringArrayList(Constants.PollDaysBundleKey);
        selectedTimes = new Hashtable<>();

        tvPollDay = (TextView) findViewById(R.id.tvPollDay);
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

        // Set current day view
        tvPollDay.setText(pollDays.get(0));
        currentDay = 0;
        btnCreate.setEnabled(false);

        // Prev/Next day buttons
        btnCreatePrev = (Button) findViewById(R.id.btnCreatePrev);
        btnCreatePrev.setVisibility(View.INVISIBLE);
        btnCreateNext = (Button) findViewById(R.id.btnCreateNext);
        btnCreateNext.setText(pollDays.get(1));
        btnCreatePrev.setOnClickListener(this);
        btnCreateNext.setOnClickListener(this);
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

    public void saveTimes(String day) {
        selectedTimes.put(day, new TreeSet<String>());
        Integer[] times = dsraAdapter2.getSelectedIndices();
        for (Integer i: times) {
            selectedTimes.get(day).add(dsraAdapter2.getItem(i));
        }
    }

    public void changeDay(String direction) {
        // Save selection as list
        saveTimes(pollDays.get(currentDay));

        // Clear list selections
        dsraAdapter2.clearSelected();
        dsrvTimes.scrollToPosition(0);

        // Tick over current day
        if (direction == "next") {
            currentDay++;
        } else if (direction == "prev") {
            currentDay--;
        }

        // Update main text
        tvPollDay.setText(pollDays.get(currentDay));

        // If last day, hide next button
        if (currentDay == pollDays.size() - 1) {
            btnCreateNext.setVisibility(View.INVISIBLE);
        } else {
            // Otherwise, un-hide it and set new text
            if (btnCreateNext.getVisibility() == View.INVISIBLE) {
                btnCreateNext.setVisibility(View.VISIBLE);
            }
            btnCreateNext.setText(pollDays.get(currentDay + 1));
        }

        // If first day, hide prev button
        if (currentDay == 0) {
            btnCreatePrev.setVisibility(View.INVISIBLE);
        } else {
            // Otherwise, un-hide it and set new text
            if (btnCreatePrev.getVisibility() == View.INVISIBLE) {
                btnCreatePrev.setVisibility(View.VISIBLE);
            }
            btnCreatePrev.setText(pollDays.get(currentDay - 1));
        }
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

                // Save the current day
                saveTimes(pollDays.get(currentDay));

                // Add the selected times to the poll
                for (String day: pollDays) {
                    if (selectedTimes.get(day) == null) {
                        Log.d("CHANG", "No times selected for day: " + day);
                        newPoll.getBaseTime().addDayTimes(day, new TreeSet<String>());
                    } else {
                        Log.d("CHANG", "Times selected for day: " + day);
                        newPoll.getBaseTime().addDayTimes(day, selectedTimes.get(day));
                    }
                }

                LocalDataStore populator = new LocalDataStore();
                populator.savePoll(context, newPoll);

                // Go back to the Home Screen
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Constants.ClearBackstackFlags);
                startActivity(intent);
                finish();

                break;
            case R.id.dsrvTimes:
                Log.i("STEFAN", "Selected Recycler");

                break;
            case R.id.btnCreatePrev:
                changeDay("prev");
                break;
            case R.id.btnCreateNext:
                changeDay("next");
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
