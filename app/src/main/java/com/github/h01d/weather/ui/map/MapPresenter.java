package com.github.h01d.weather.ui.map;

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

import android.database.sqlite.SQLiteConstraintException;

import com.github.h01d.weather.data.local.database.DatabaseManager;
import com.github.h01d.weather.data.local.database.entity.Location;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MapPresenter
{
    private MapView mapView;
    private DatabaseManager databaseManager;

    private CompositeDisposable disposable;

    public MapPresenter(MapView mapView, DatabaseManager databaseManager)
    {
        this.mapView = mapView;
        this.databaseManager = databaseManager;

        disposable = new CompositeDisposable();

        mapView.loadMapFragment();
    }

    public void saveLocation(Location location)
    {
        disposable.add(databaseManager.locationDao().insert(location)
                .subscribeOn(Schedulers.io())
                .subscribe(() -> mapView.locationAdded(true, null), throwable ->
                {
                    if(throwable instanceof SQLiteConstraintException)
                    {
                        mapView.locationAdded(false, "Location already exist");
                    }
                    else
                    {
                        mapView.locationAdded(false, "Location failed to add");
                    }
                }));
    }

    public void dispose()
    {
        disposable.dispose();
    }
}
