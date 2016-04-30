package stefan.transactionexample;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentB extends Fragment {


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Fragment B", "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Fragment B", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("Fragment B", "onCreateView");
        return inflater.inflate(R.layout.fragment_b, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Fragment B", "onActivityCreated");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Fragment B", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Fragment B", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Fragment B", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Fragment B", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Fragment B", "onDetach");
    }

}
