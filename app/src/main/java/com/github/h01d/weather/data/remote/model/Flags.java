package com.github.h01d.weather.data.remote.model;

import androidx.annotation.NonNull;

public class Flags
{
    @NonNull
    private String units;

    public Flags(@NonNull String units)
    {
        this.units = units;
    }

    @NonNull
    public String getUnits()
    {
        return units;
    }
}
