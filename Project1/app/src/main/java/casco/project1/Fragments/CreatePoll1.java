package casco.project1.Fragments;


import android.app.Fragment;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
    public String newPollName;
    public String newPollDescription;

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
    public void setPollNameAndDescription(String name, String desc){
        newPollName = name;
        newPollDescription = desc;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm = (Communicator) getActivity();
        btnNext = (Button) getActivity().findViewById(R.id.btnPart2);
        btnNext.setOnClickListener(this);
        btnNext.setEnabled(false);
        tvPollTitle = (TextView) getActivity().findViewById(R.id.tvPollTitle);
        tePollTitle = (EditText) getActivity().findViewById(R.id.tePollTitle);
        tePollTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(tePollTitle.getText().toString() != "") {
                    btnNext.setEnabled(true);
                }
            }
        });
        tvPollDescription = (TextView) getActivity().findViewById(R.id.tvPollDescription);
        tePollDescription = (EditText) getActivity().findViewById(R.id.teDescription);
        if (btnNext  == null){
            Log.i("STEFAN", "FAILED TO LOAD BUTTON");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        tePollTitle.setText(newPollName);
        tePollDescription.setText(newPollDescription);
    }

    @Override
    public void onClick(View v) {
        tvPollDescription.setText("Clicked");
        Log.i("STEFAN", "Clicked");
        newPollName = tePollTitle.getText().toString();
        newPollDescription = tePollDescription.getText().toString();
        comm.switchToPart2(newPollName, newPollDescription);
    }

}
