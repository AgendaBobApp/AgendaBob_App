package casco.project1.Activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
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
        implements Communicator, ClickListener, MaterialCab.Callback
{
    FragmentManager manager;
    Poll newPoll;
    Button btnNext;
    User currentUser;
    TextView test;

    String pollName;
    String pollDescription;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_creation);
        manager = getFragmentManager();
        Bundle bundle = this.getIntent().getExtras();
        loadUser(bundle);
        addPart1();
    }
    public void loadUser(Bundle bundle){
        currentUser = Constants.loadUser(bundle);
        test = (TextView) findViewById(R.id.tvTest);
        test.setText("Logged in as: " + currentUser.getName());
        Log.i("STEFAN", currentUser.getName());
    }
    public void addPart1(){
        Log.i("STEFAN", "addPart1");
        CreatePoll1 fragPart1 = new CreatePoll1();
        fragPart1.setCommunicator(this);
        FragmentTransaction trans = manager.beginTransaction();
        trans.add(R.id.group, fragPart1, "Part1");
        trans.commit();
    }
    public void loadPart1(){
        Log.i("STEFAN", "loadPart1");
        CreatePoll1 fragPart1 = new CreatePoll1();
        Log.i("STEFAN", "SHOULD BE "+fragPart1.newPollName);
        //fragPart1.setCommunicator(this);
        fragPart1.setPollNameAndDescription(pollName, pollDescription);
        FragmentTransaction trans = manager.beginTransaction();
        trans.replace(R.id.group, fragPart1, "Part1");
        trans.commit();

    }
    public void loadPart2(){
        Log.i("STEFAN", "loadPart2");
        CreatePoll2 fragPart2 = new CreatePoll2();
        fragPart2.setPollNameAndDescription(pollName, pollDescription);
        Log.i("STEFAN", "SHOULD BE "+fragPart2.pollName);
        //fragPart2.setCommunicator(this);
        FragmentTransaction trans = manager.beginTransaction();
        trans.replace(R.id.group, fragPart2, "Part2");
        trans.commit();

    }
    public void loadPart3(){
        Log.i("STEFAN", "loadPart3");
        CreatePoll2 fragPart3 = new CreatePoll2();
        //fragPart3.setCommunicator(this);
        FragmentTransaction trans = manager.beginTransaction();
        trans.replace(R.id.group, fragPart3, "Part3");
        trans.commit();
    }
    @Override
    public void switchToPart1() {
        Log.i("STEFAN", "switchToPart1");

        loadPart1();
    }
    @Override
    public void switchToPart2(String newPollName, String newPollDescription) {
        Log.i("STEFAN", "switchToPart2");
        pollName = newPollName;
        pollDescription = newPollDescription;
        loadPart2();
    }

    @Override
    public void switchToPart2() {
        Log.i("STEFAN", "switchToPart2");
        loadPart2();
    }

    @Override
    public void switchToPart3() {
        Log.i("STEFAN", "switchToPart3");
        loadPart3();
    }

    @Override
    public void onClick(int index) {
        CreatePoll2 fragPart2 = (CreatePoll2) manager.findFragmentByTag("Part2");
        if(fragPart2 != null)
        {
            fragPart2.getDragSelectRecyclerAdapter().toggleSelected(index);
        }
    }

    @Override
    public void onLongClick(int index) {
        CreatePoll2 fragPart2 = (CreatePoll2) manager.findFragmentByTag("Part2");
        if(fragPart2 != null)
        {
            fragPart2.getDragSelectRecyclerView().setDragSelectActive(true, index);
        }
    }

    @Override
    public boolean onCabCreated(MaterialCab materialCab, Menu menu) {

        return true;
    }

    @Override
    public boolean onCabItemClicked(MenuItem item) {
        CreatePoll2 fragPart2 = (CreatePoll2) manager.findFragmentByTag("Part2");
        if(fragPart2 == null)
        {
            return false;
        }
        if(item.getItemId() == R.id.btnPart1)
        {
            return false;
        }
        else if(item.getItemId() == R.id.btnPart3)
        {
            StringBuilder sb = new StringBuilder();
            int traverse = 0;
            for(Integer index: fragPart2.getDragSelectRecyclerAdapter().getSelectedIndices())
            {
                if(traverse > 0){
                    sb.append(", ");
                }
                sb.append(fragPart2.getDragSelectRecyclerAdapter().getItem(index));
                traverse++;
            }
            fragPart2.getDragSelectRecyclerAdapter().clearSelected();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCabFinished(MaterialCab materialCab) {
        CreatePoll2 fragPart2 = (CreatePoll2) manager.findFragmentByTag("Part2");
        if(fragPart2 == null)
        {
            return false;
        }
        fragPart2.getDragSelectRecyclerAdapter().clearSelected();
        return true;
    }

}
