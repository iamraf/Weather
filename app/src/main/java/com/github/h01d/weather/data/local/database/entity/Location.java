package com.github.h01d.weather.data.local.database.entity;

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

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "locations_table")
public class Location
{
    @PrimaryKey
    @NonNull
    private String name;
    @NonNull
    private String country;
    private double latitude;
    private double longitude;

    public Location(@NonNull String name, @NonNull String country, double latitude, double longitude)
    {
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    public String getName()
    {
        return name;
    }

    @NonNull
    public String getCountry()
    {
        return country;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }
}
