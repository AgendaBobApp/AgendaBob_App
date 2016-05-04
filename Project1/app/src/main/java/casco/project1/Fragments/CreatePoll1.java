package casco.project1.Fragments;


import android.app.Fragment;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import casco.project1.Interfaces.Communicator;
import casco.project1.R;


public class CreatePoll1 extends Fragment implements View.OnClickListener {
    Communicator comm;
    TextView tvPollTitle;
    EditText tePollTitle;
    TextView tvPollDescription;
    EditText tePollDescription;
    Button btnNext;

    public void setCommunicator(Communicator c)
    {
        comm = c;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_poll1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm = (Communicator) getActivity();
        btnNext = (Button) getActivity().findViewById(R.id.btnPart2);
        btnNext.setOnClickListener(this);
        tvPollTitle = (TextView) getActivity().findViewById(R.id.tvPollTitle);
        tePollTitle = (EditText) getActivity().findViewById(R.id.tePollTitle);
        tvPollDescription = (TextView) getActivity().findViewById(R.id.tvPollDescription);
        tePollDescription = (EditText) getActivity().findViewById(R.id.teDescription);
        if (btnNext  == null){
            Log.i("STEFAN", "FAILED TO LOAD BUTTON");
        }
    }

    @Override
    public void onClick(View v) {
        tvPollDescription.setText("Clicked");
        Log.i("STEFAN", "Clicked");
        comm.switchToPart2();
    }

}
