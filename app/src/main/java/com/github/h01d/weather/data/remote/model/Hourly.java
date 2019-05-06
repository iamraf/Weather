package com.github.h01d.weather.data.remote.model;

import androidx.annotation.Nullable;

import java.util.List;

public class Hourly
{
    @Nullable
    private String summary;
    @Nullable
    private String icon;
    @Nullable
    private List<Currently> data;

    public Hourly(@Nullable String summary, @Nullable String icon, @Nullable List<Currently> data)
    {
        this.summary = summary;
        this.icon = icon;
        this.data = data;
    }

    @Nullable
    public String getSummary()
    {
        return summary;
    }

    @Nullable
    public String getIcon()
    {
        return icon;
    }

    @Nullable
    public List<Currently> getData()
    {
        return data;
    }
}
