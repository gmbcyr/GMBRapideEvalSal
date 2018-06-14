package com.gmb.gmbrapideevalsal;

/**
 * Created by cchoudja on 6/2/2015.
 */

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class UserSettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Display the fragment as the main content.
        /*getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PreferencesFragment())
                .commit();*/


        addPreferencesFromResource(R.xml.preferences);

    }
}
