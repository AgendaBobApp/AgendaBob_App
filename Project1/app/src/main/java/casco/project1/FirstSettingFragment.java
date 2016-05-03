package casco.project1;

import android.preference.PreferenceFragment;
import android.os.Bundle;

public class FirstSettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting1);
    }
}
