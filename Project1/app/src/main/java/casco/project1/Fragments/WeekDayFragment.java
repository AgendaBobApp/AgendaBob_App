package casco.project1.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.dragselectrecyclerview.DragSelectRecyclerView;
import com.afollestad.dragselectrecyclerview.DragSelectRecyclerViewAdapter;
import com.afollestad.materialcab.MaterialCab;

import casco.project1.Adapters.DragSelectRecyclerAdapter;
import casco.project1.Interfaces.ClickListener;
import casco.project1.Interfaces.Communicator;
import casco.project1.R;


public class WeekDayFragment extends Fragment
        implements View.OnClickListener, DragSelectRecyclerViewAdapter.SelectionListener
{
    TextView tvPollDay;
    public String pollDay;
    Button btnBack;
    DragSelectRecyclerView dsrvTimes;
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
    public void setPollName(String day){
        pollDay = day;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week_day, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm = (Communicator) getActivity();
        listener = (ClickListener) getActivity();
        tvPollDay = (TextView) getActivity().findViewById(R.id.tvWeekDay);
        dsraAdapter = new DragSelectRecyclerAdapter((ClickListener) getActivity());
        dsraAdapter.load();
        dsraAdapter.setSelectionListener(this);
        dsraAdapter.restoreInstanceState(savedInstanceState);

        dsrvTimes = (DragSelectRecyclerView) getActivity().findViewById(R.id.dsrvDays);
        dsrvTimes.setAdapter(dsraAdapter);
        dsrvTimes.setLayoutManager(new LinearLayoutManager(getActivity()));


        cab = MaterialCab.restoreState(
                savedInstanceState, (AppCompatActivity) getActivity(), (MaterialCab.Callback) getActivity()
        );

        btnBack = (Button) getActivity().findViewById(R.id.btnPart1);
        btnBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDragSelectionChanged(int i) {

    }
}
