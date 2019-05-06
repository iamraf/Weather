package com.github.h01d.weather.data.remote.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Forecast
{
    @NonNull
    private String timezone;
    @Nullable
    private Currently currently;
    @Nullable
    private Hourly hourly;
    @Nullable
    private Daily daily;
    @NonNull
    private Flags flags;

    public Forecast(@NonNull String timezone, @Nullable Currently currently, @Nullable Hourly hourly, @Nullable Daily daily, @NonNull Flags flags)
    {
        this.timezone = timezone;
        this.currently = currently;
        this.hourly = hourly;
        this.daily = daily;
        this.flags = flags;
    }

    @NonNull
    public String getTimezone()
    {
        return timezone;
    }

    @Nullable
    public Currently getCurrently()
    {
        return currently;
    }

    @Nullable
    public Hourly getHourly()
    {
        return hourly;
    }

    @Nullable
    public Daily getDaily()
    {
        return daily;
    }

    @NonNull
    public Flags getFlags()
    {
        return flags;
    }
}
