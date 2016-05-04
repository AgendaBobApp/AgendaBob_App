package casco.project1;
import android.preference.PreferenceFragment;
import android.os.Bundle;

public class SecondSettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting2);
    }
}
