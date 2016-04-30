package stefan.transactionexample;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentA extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Fragment A", "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Fragment A", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("Fragment A", "onCreateView");
        return inflater.inflate(R.layout.fragment_a, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Fragment A", "onActivityCreated");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Fragment A", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Fragment A", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Fragment A", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Fragment A", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Fragment A", "onDetach");
    }
}
