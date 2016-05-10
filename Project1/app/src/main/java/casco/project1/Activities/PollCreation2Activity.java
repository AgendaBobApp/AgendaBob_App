package casco.project1.Activities;

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

import com.afollestad.dragselectrecyclerview.DragSelectRecyclerView;
import com.afollestad.dragselectrecyclerview.DragSelectRecyclerViewAdapter;
import com.afollestad.materialcab.MaterialCab;

import casco.project1.Adapters.DragSelectRecyclerAdapterDays;
import casco.project1.Interfaces.ClickListener;
import casco.project1.R;
import casco.project1.dataBackend.Constants;
import casco.project1.dataBackend.Poll;
import casco.project1.dataBackend.User;

public class PollCreation2Activity
        extends AppCompatActivity
        implements ClickListener, MaterialCab.Callback,
        View.OnClickListener, DragSelectRecyclerViewAdapter.SelectionListener
{
    User currentUser;
    Poll newPoll;

    TextView tvPollName;
    String pollName;
    String pollDescription;
<<<<<<< HEAD
    String timeStart;
    String timeEnd;
    Button btnBack;
    Button btnNext;
    DragSelectRecyclerView dsrvDays;
    DragSelectRecyclerAdapterDays dsraAdapter;
=======
    int startTime;
    int endTime;
    Button btnBack;
    Button btnNext;
    DragSelectRecyclerView dsrvDays;
    DragSelectRecyclerAdapter dsraAdapter;
    Integer[] selectedItems;
>>>>>>> 250b708afe3d51aa686680b623ac2e7e3da3220e
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
        setContentView(R.layout.activity_poll_creation2);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            currentUser = Constants.loadUser(bundle);
            newPoll = (Poll) bundle.getSerializable(Constants.PollBundleKey);
        }
        else if (savedInstanceState != null)
        {
            pollName = savedInstanceState.getString(Constants.PollNameBundleKey);
            pollDescription = savedInstanceState.getString(Constants.PollDescBundleKey);
            startTime = savedInstanceState.getInt("Starting Time");
            endTime = savedInstanceState.getInt("Ending Time");
            currentUser = (User) savedInstanceState.getSerializable(Constants.UserBundleKey);
            newPoll = (Poll) savedInstanceState.getSerializable(Constants.PollBundleKey);

        }

        tvPollName = (TextView) findViewById(R.id.tvPollName2);
        dsraAdapter = new DragSelectRecyclerAdapterDays(this);
        dsraAdapter.load();
        dsraAdapter.setSelectionListener(this);
        dsraAdapter.restoreInstanceState(savedInstanceState);

        dsrvDays = (DragSelectRecyclerView) findViewById(R.id.dsrvDays);
        dsrvDays.setAdapter(dsraAdapter);
        dsrvDays.setLayoutManager(new LinearLayoutManager(this));
        cab = MaterialCab.restoreState(
                savedInstanceState, this, this);

        btnBack = (Button) findViewById(R.id.btnPart1);
        btnNext = (Button) findViewById(R.id.btnPart3);
        btnBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
<<<<<<< HEAD
//        pollName = bundle.getString(Constants.PollNameBundleKey);
//        pollDescription = bundle.getString(Constants.PollDescBundleKey);
        timeStart = bundle.getString(Constants.PollStartTimeBundleKey);
        timeEnd = bundle.getString(Constants.PollEndTimeBundleKey);
        tvPollName.setText(newPoll.getTitle());
=======
        pollName = bundle.getString(Constants.PollNameBundleKey);
        pollDescription = bundle.getString(Constants.PollDescBundleKey);
        startTime = bundle.getInt("Starting Time");
        endTime = bundle.getInt("Ending Time");
        tvPollName.setText(pollName);
>>>>>>> 250b708afe3d51aa686680b623ac2e7e3da3220e
        btnNext.setEnabled(false);

        System.out.println("Activity 2: " + startTime);
        System.out.println("Activity 2: " + endTime);
    }

    public DragSelectRecyclerView getDragSelectRecyclerView(){
        return dsrvDays;
    }
    public DragSelectRecyclerAdapterDays getDragSelectRecyclerAdapter(){
        return dsraAdapter;
    }
    @Override
    public void onDragSelectionChanged(int i) {
        Log.i("STEFAN", "NUM ITEMS OR ITEM SELECTED?: " + i);
        if(i > 0 && btnNext != null){
            btnNext.setEnabled(true);
        }
        else if(i < 1 && btnNext != null){
            btnNext.setEnabled(false);
        }

        selectedItems = dsraAdapter.getSelectedIndices();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.PollNameBundleKey, pollName);
        outState.putString(Constants.PollDescBundleKey, pollDescription);
        outState.putSerializable(Constants.UserBundleKey, currentUser);
<<<<<<< HEAD
        outState.putSerializable(Constants.PollBundleKey, newPoll);
=======
        outState.putInt("Starting Time", startTime);
        outState.putInt("Ending Time", endTime);
>>>>>>> 250b708afe3d51aa686680b623ac2e7e3da3220e
        if (dsraAdapter != null) {
            dsraAdapter.saveInstanceState(outState);
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
        startTime = savedInstanceState.getInt("Starting Time");
        endTime = savedInstanceState.getInt("Ending Time");
        currentUser = (User) savedInstanceState.getSerializable(Constants.UserBundleKey);
        newPoll = (Poll) savedInstanceState.getSerializable(Constants.PollBundleKey);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnPart1:
                Log.i("STEFAN", "Clicked Back");
                this.finish();
                break;
            case R.id.btnPart3:
                Log.i("STEFAN", "Clicked Next");
                Intent intent2 = new Intent(this, PollCreation3Activity.class);
                Bundle b2 = new Bundle();
                b2.putSerializable(Constants.UserBundleKey, currentUser);
                b2.putSerializable(Constants.PollBundleKey, newPoll);
                intent2.putExtras(b2);
<<<<<<< HEAD
//                intent2.putExtra(Constants.PollNameBundleKey, pollName);
//                intent2.putExtra(Constants.PollDescBundleKey, pollDescription);
                intent2.putExtra(Constants.PollStartTimeBundleKey, timeStart);
                intent2.putExtra(Constants.PollEndTimeBundleKey, timeEnd);
=======
                intent2.putExtra(Constants.PollNameBundleKey, pollName);
                intent2.putExtra(Constants.PollDescBundleKey, pollDescription);
                intent2.putExtra("Starting Time", startTime);
                intent2.putExtra("Ending Time", endTime);
                intent2.putExtra("DayList", selectedItems);
>>>>>>> 250b708afe3d51aa686680b623ac2e7e3da3220e

                startActivity(intent2);
                break;
            case R.id.dsrvDays:
                Log.i("STEFAN", "Selected Recycler");

                break;
            default:
                Log.e("Error","WTF just happened?!");
                break;
        }
    }
    @Override
    public void onClick(int index) {

        getDragSelectRecyclerAdapter().toggleSelected(index);

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
        if(item.getItemId() == R.id.btnPart1)
        {
            return false;
        }
        else if(item.getItemId() == R.id.btnPart3)
        {
            StringBuilder sb = new StringBuilder();
            int traverse = 0;
            for(Integer index: getDragSelectRecyclerAdapter().getSelectedIndices())
            {
                if(traverse > 0){
                    sb.append(", ");
                }
                sb.append(getDragSelectRecyclerAdapter().getItem(index));
                traverse++;
            }
            getDragSelectRecyclerAdapter().clearSelected();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCabFinished(MaterialCab materialCab) {
        getDragSelectRecyclerAdapter().clearSelected();
        return true;
    }

}
