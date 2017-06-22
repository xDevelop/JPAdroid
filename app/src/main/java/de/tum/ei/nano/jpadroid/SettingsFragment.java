package de.tum.ei.nano.jpadroid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import static de.tum.ei.nano.jpadroid.MainActivity.getMainContext;

/**
 * Created by ga73sut on 19.05.2017.
 */

public class SettingsFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    OnSettingsChangedListener settingsListener = (OnSettingsChangedListener) MainActivity.getMainContext();

    public interface OnSettingsChangedListener {

        void onSettingsChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getMainContext());
        Preference showPhotonPref = findPreference("switch_show_photon_number");
        Preference initSignalPhotonNumber = findPreference("edit_text_signal_photon_number");
        Preference initIdlerPhotonNumber = findPreference("edit_text_idler_photon_number");
        Preference initQ1 = findPreference("edit_text_q1");
        Preference initPhi1 = findPreference("edit_text_phi1");
        Preference initQ2 = findPreference("edit_text_q2");
        Preference initPhi2 = findPreference("edit_text_phi2");

        if (SP.getBoolean("switch_preference_quantum",true)) {

            showPhotonPref.setEnabled(true);
            initSignalPhotonNumber.setEnabled(true);
            initIdlerPhotonNumber.setEnabled(true);

            initQ1.setEnabled(false);
            initPhi1.setEnabled(false);
            initQ2.setEnabled(false);
            initPhi2.setEnabled(false);


        } else {

            showPhotonPref.setEnabled(false);
            initSignalPhotonNumber.setEnabled(false);
            initIdlerPhotonNumber.setEnabled(false);

            initQ1.setEnabled(true);
            initPhi1.setEnabled(true);
            initQ2.setEnabled(true);
            initPhi2.setEnabled(true);
        }

    }

//    @Override
//    public boolean onPreferenceChange(Preference preference, Object value) {
//
//        return false;
//    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences SP, String key) {

        //SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getMainContext());

        if (key.equals("switch_preference_quantum"))
        {
            // Set summary to be the user-description for the selected value

            //Preference quantumPref = findPreference("switch_show_photon_number");
            Preference showPhotonPref = findPreference("switch_show_photon_number");
            Preference initSignalPhotonNumber = findPreference("edit_text_signal_photon_number");
            Preference initIdlerPhotonNumber = findPreference("edit_text_idler_photon_number");
            Preference initQ1 = findPreference("edit_text_q1");
            Preference initPhi1 = findPreference("edit_text_phi1");
            Preference initQ2 = findPreference("edit_text_q2");
            Preference initPhi2 = findPreference("edit_text_phi2");

            if (SP.getBoolean(key,true)) {

                showPhotonPref.setEnabled(true);
                initSignalPhotonNumber.setEnabled(true);
                initIdlerPhotonNumber.setEnabled(true);

                initQ1.setEnabled(false);
                initPhi1.setEnabled(false);
                initQ2.setEnabled(false);
                initPhi2.setEnabled(false);

            } else {

                initSignalPhotonNumber.setEnabled(false);
                initIdlerPhotonNumber.setEnabled(false);

                initQ1.setEnabled(true);
                initPhi1.setEnabled(true);
                initQ2.setEnabled(true);
                initPhi2.setEnabled(true);

                SharedPreferences.Editor editor = SP.edit();
                editor.putBoolean("switch_show_photon_number", false);
                editor.commit();
                showPhotonPref.setEnabled(false);
            }

        }

        settingsListener.onSettingsChanged();

    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
}
