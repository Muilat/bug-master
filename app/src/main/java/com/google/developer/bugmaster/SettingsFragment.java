package com.google.developer.bugmaster;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
//    Preference minMagnitude = findPreference(getString(R.string.settings_min_magnitude_key));
//            bindPreferenceSummaryToValue(minMagnitude);
//
//            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
//            bindPreferenceSummaryToValue(orderBy);
        }

//        @Override
//        public boolean onPreferenceChange(Preference preference, Object value) {
//            String stringValue = value.toString();
//            if (preference instanceof ListPreference) {
//                ListPreference listPreference = (ListPreference) preference;
//                int prefIndex = listPreference.findIndexOfValue(stringValue);
//                if (prefIndex >= 0) {
//                    CharSequence[] labels = listPreference.getEntries();
//                    preference.setSummary(labels[prefIndex]);
//                }
//            } else {
//                preference.setSummary(stringValue);
//            }
//            return true;
//        }
}
