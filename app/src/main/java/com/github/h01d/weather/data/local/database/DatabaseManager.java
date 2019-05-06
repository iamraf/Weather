package com.github.h01d.weather.data.local.database;

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

import android.app.Application;

import com.github.h01d.weather.data.local.database.dao.LocationDao;
import com.github.h01d.weather.data.local.database.entity.Location;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Location.class, version = 1, exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase
{
    public abstract LocationDao locationDao();

    private static DatabaseManager instance = null;

    public static synchronized DatabaseManager getInstance(Application application)
    {
        if(instance == null)
        {
            synchronized(DatabaseManager.class)
            {
                instance = Room.databaseBuilder(application, DatabaseManager.class, "location_database")
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }

        return instance;
    }
}
