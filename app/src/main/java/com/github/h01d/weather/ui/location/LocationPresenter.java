package com.github.h01d.weather.ui.location;

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

import com.github.h01d.weather.data.local.database.DatabaseManager;
import com.github.h01d.weather.data.local.database.entity.Location;
import com.github.h01d.weather.data.local.preference.PreferencesManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LocationPresenter
{
    private LocationView locationView;
    private DatabaseManager databaseManager;

    private CompositeDisposable disposable;

    public LocationPresenter(LocationView locationView, DatabaseManager databaseManager)
    {
        this.locationView = locationView;
        this.databaseManager = databaseManager;

        disposable = new CompositeDisposable();

        getLocations();
    }

    private void getLocations()
    {
        disposable.add(databaseManager.locationDao().getLocations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(locations -> locationView.updateUI(locations)));
    }

    public void deleteLocation(Location location)
    {
        disposable.add(databaseManager.locationDao().delete(location)
                .subscribeOn(Schedulers.io())
                .subscribe());

        if(PreferencesManager.getSavedLocation() != null && PreferencesManager.getSavedLocation().getName().equals(location.getName()))
        {
            PreferencesManager.setSavedLocation(null);
        }
    }

    public void dispose()
    {
        disposable.dispose();
    }
}
