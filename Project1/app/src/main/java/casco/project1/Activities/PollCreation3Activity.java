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

import casco.project1.Adapters.DragSelectRecyclerAdapter2;
import casco.project1.Interfaces.ClickListener;
import casco.project1.R;
import casco.project1.dataBackend.Constants;
import casco.project1.dataBackend.User;

public class PollCreation3Activity
        extends AppCompatActivity
        implements ClickListener, MaterialCab.Callback,
        View.OnClickListener, DragSelectRecyclerViewAdapter.SelectionListener
{
    User currentUser;

    TextView tvPollName;
    String pollName;
    String pollDescription;
    Button btnBack;
    Button btnCreate;
    DragSelectRecyclerView dsrvTimes;
    DragSelectRecyclerAdapter2 dsraAdapter2;
    MaterialCab cab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_creation3);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null)
            currentUser = Constants.loadUser(bundle);
        else if (savedInstanceState != null)
        {
            pollName = savedInstanceState.getString(Constants.PollNameBundleKey);
            pollDescription = savedInstanceState.getString(Constants.PollDescBundleKey);
            currentUser = (User) savedInstanceState.getSerializable(Constants.UserBundleKey);
        }

        tvPollName = (TextView) findViewById(R.id.tvPollName3);
        dsraAdapter2 = new DragSelectRecyclerAdapter2(this);
        dsraAdapter2.load();
        dsraAdapter2.setSelectionListener(this);
        dsraAdapter2.restoreInstanceState(savedInstanceState);

        dsrvTimes = (DragSelectRecyclerView) findViewById(R.id.dsrvTimes);
        dsrvTimes.setAdapter(dsraAdapter2);
        dsrvTimes.setLayoutManager(new LinearLayoutManager(this));
        cab = MaterialCab.restoreState(
                savedInstanceState, this, this);

        btnBack = (Button) findViewById(R.id.btnPart2);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnBack.setOnClickListener(this);
        btnCreate.setOnClickListener(this);
        pollName = bundle.getString(Constants.PollNameBundleKey);
        pollDescription = bundle.getString(Constants.PollDescBundleKey);
        tvPollName.setText(pollName);
        btnCreate.setEnabled(false);

    }

    public DragSelectRecyclerView getDragSelectRecyclerView(){
        return dsrvTimes;
    }
    public DragSelectRecyclerAdapter2 getDragSelectRecyclerAdapter2(){
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
                /*Intent intent1 = new Intent(this, PollCreation2Activity.class);
                Bundle b1 = new Bundle();
                b1.putSerializable(Constants.UserBundleKey, currentUser);
                intent1.putExtras(b1);
                intent1.putExtra(Constants.PollNameBundleKey, pollName);
                intent1.putExtra(Constants.PollDescBundleKey, pollDescription);

                startActivity(intent1);*/
                this.finish();
                break;
            case R.id.btnCreate:
                Log.i("STEFAN", "Clicked Create");
                /*Intent intent2 = new Intent(this, PollCreation3Activity.class);
                Bundle b2 = new Bundle();
                b2.putSerializable(Constants.UserBundleKey, currentUser);
                intent2.putExtras(b2);
                intent2.putExtra(Constants.PollNameBundleKey, pollName);
                intent2.putExtra(Constants.PollDescBundleKey, pollDescription);

                startActivity(intent2);*/
                Context context = getApplicationContext();
                CharSequence text = "Poll (not really) created!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
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
