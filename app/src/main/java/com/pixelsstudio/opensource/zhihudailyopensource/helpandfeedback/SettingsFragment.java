package com.pixelsstudio.opensource.zhihudailyopensource.helpandfeedback;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.pixelsstudio.opensource.zhihudailyopensource.R;

/**
 * Created by WongChen on 2017/3/21.
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference version = findPreference("version");
        version.setSummary(getVersion());
    }

    public String getVersion() {
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
            String version = "V"+info.versionName;
            return  version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
