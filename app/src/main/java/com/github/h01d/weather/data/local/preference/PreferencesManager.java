package com.github.h01d.weather.data.local.preference;

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

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.github.h01d.weather.data.local.database.entity.Location;
import com.google.gson.Gson;

public class PreferencesManager
{
    private static SharedPreferences instance = null;

    private PreferencesManager()
    {

    }

    public static void init(Context context)
    {
        if(instance == null)
        {
            instance = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public static void setSavedLocation(Location location)
    {
        SharedPreferences.Editor prefsEditor = instance.edit();
        prefsEditor.putString("pref_location", location != null ? new Gson().toJson(location) : "");
        prefsEditor.apply();
    }

    public static Location getSavedLocation()
    {
        return instance.getString("pref_location", "").isEmpty() ? null : new Gson().fromJson(instance.getString("pref_location", ""), Location.class);
    }

    public static String getLanguage()
    {
        return instance.getString("pref_language", "en");
    }

    public static String getUnits()
    {
        return instance.getString("pref_units", "auto");
    }

    public static void setFirstTime()
    {
        SharedPreferences.Editor prefsEditor = instance.edit();
        prefsEditor.putBoolean("pref_first", false);
        prefsEditor.apply();
    }
    public static boolean getFirstTime()
    {
        return instance.getBoolean("pref_first", true);
    }
}
