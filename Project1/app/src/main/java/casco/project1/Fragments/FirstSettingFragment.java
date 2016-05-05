package casco.project1.Fragments;

import android.preference.PreferenceFragment;
import android.os.Bundle;

import casco.project1.R;

public class FirstSettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting1);
    }
}
