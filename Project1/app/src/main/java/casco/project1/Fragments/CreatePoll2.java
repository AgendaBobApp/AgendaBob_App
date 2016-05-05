package casco.project1.Fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.dragselectrecyclerview.DragSelectRecyclerView;
import com.afollestad.dragselectrecyclerview.DragSelectRecyclerViewAdapter;
import com.afollestad.materialcab.MaterialCab;

import org.w3c.dom.Text;

import casco.project1.Adapters.DragSelectRecyclerAdapter;
import casco.project1.Interfaces.ClickListener;
import casco.project1.Interfaces.Communicator;
import casco.project1.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePoll2 extends Fragment
        implements View.OnClickListener, DragSelectRecyclerViewAdapter.SelectionListener
{
    TextView tvPollName;
    String pollName;
    String pollDecription;
    Button btnBack;
    Button btnNext;
    DragSelectRecyclerView dsrvDays;
    public DragSelectRecyclerAdapter dsraAdapter;
    Communicator comm;
    ClickListener listener;
    MaterialCab cab;


    public void setCommunicator(Communicator c)
    {
        comm = c;
    }
    public void setCommunicator(ClickListener l)
    {
        listener = l;
    }
    public void setPollNameAndDescription(String name, String desc){
        pollName = name;
        pollDecription = desc;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_poll2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm = (Communicator) getActivity();
        listener = (ClickListener) getActivity();

        tvPollName = (TextView) getActivity().findViewById(R.id.tvPollName);

        dsraAdapter = new DragSelectRecyclerAdapter((ClickListener) getActivity());
        dsraAdapter.load();
        dsraAdapter.setSelectionListener(this);
        dsraAdapter.restoreInstanceState(savedInstanceState);

        dsrvDays = (DragSelectRecyclerView) getActivity().findViewById(R.id.dsrvDays);
        dsrvDays.setAdapter(dsraAdapter);
        dsrvDays.setLayoutManager(new LinearLayoutManager(getActivity()));


        cab = MaterialCab.restoreState(
                savedInstanceState, (AppCompatActivity) getActivity(), (MaterialCab.Callback) getActivity()
        );

        btnBack = (Button) getActivity().findViewById(R.id.btnPart1);
        btnBack.setOnClickListener(this);
        btnNext = (Button) getActivity().findViewById(R.id.btnPart3);
        btnNext.setOnClickListener(this);
        btnNext.setEnabled(false);
        tvPollName.setText(pollName);
    }

    public DragSelectRecyclerView getDragSelectRecyclerView(){
        return dsrvDays;
    }
    public DragSelectRecyclerAdapter getDragSelectRecyclerAdapter(){
        return dsraAdapter;
    }
    @Override
    public void onDragSelectionChanged(int i) {
        Log.i("STEFAN", "NUM ITEMS OR ITEM SELECTED?: "+i);
        if(i > 0){
            btnNext.setEnabled(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        dsraAdapter.saveInstanceState(outState);
        if (cab != null)
        {
            cab.saveState(outState);
        }

    }

    //  DragSelectRecyclerViewAdapter
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnPart1:
                Log.i("STEFAN", "Clicked Back");
                comm.switchToPart1();
                break;
            case R.id.btnPart3:
                Log.i("STEFAN", "Clicked Next");
                comm.switchToPart3();
                break;
            case R.id.dsrvDays:
                Log.i("STEFAN", "Selected Recycler");

                break;
            default:
                Log.e("Error","WTF just happened?!");
                break;
        }
    }




}
