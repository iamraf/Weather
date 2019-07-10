package com.github.h01d.weather.ui.settings;

/*
    Copyright 2019 Raf
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.github.h01d.weather.R;
import com.github.h01d.weather.ui.tutorial.TutorialActivity;

public class SettingsFragment extends PreferenceFragmentCompat
{
    public SettingsFragment()
    {

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        Preference tutorial = findPreference("pref_tutorial");
        if(tutorial != null)
        {
            tutorial.setOnPreferenceClickListener(preference ->
            {
                startActivity(new Intent(getContext(), TutorialActivity.class));
                return true;
            });
        }

        Preference about = findPreference("pref_about");
        if(about != null)
        {
            about.setOnPreferenceClickListener(preference ->
            {
                new AlertDialog.Builder(getContext())
                        .setTitle("WeatherApp")
                        .setMessage("Open source weather application with a unique style.\n\nWeather icons by Erikflowers.\nPowered by Dark Sky.")
                        .setNegativeButton("Close", null)
                        .show();
                return true;
            });
        }
    }
}
