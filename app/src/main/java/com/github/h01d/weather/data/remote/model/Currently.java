package com.github.h01d.weather.data.remote.model;

import androidx.annotation.Nullable;

public class Currently
{
    private long time;
    @Nullable
    private String summary;
    @Nullable
    private String icon;
    private int nearestStormDistance;
    private int nearestStormBearing;
    private double precipIntensity;
    private double precipProbability;
    private double temperature;
    private double apparentTemperature;
    private double dewPoint;
    private double humidity;
    private double pressure;
    private double windSpeed;
    private double windGust;
    private int windBearing;
    private double cloudCover;
    private int uvIndex;
    private double visibility;
    private double ozone;

    public Currently(long time, @Nullable String summary, @Nullable String icon, int nearestStormDistance, int nearestStormBearing, double precipIntensity, double precipProbability, double temperature, double apparentTemperature, double dewPoint, double humidity, double pressure, double windSpeed, double windGust, int windBearing, double cloudCover, int uvIndex, double visibility, double ozone)
    {
        this.time = time;
        this.summary = summary;
        this.icon = icon;
        this.nearestStormDistance = nearestStormDistance;
        this.nearestStormBearing = nearestStormBearing;
        this.precipIntensity = precipIntensity;
        this.precipProbability = precipProbability;
        this.temperature = temperature;
        this.apparentTemperature = apparentTemperature;
        this.dewPoint = dewPoint;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windGust = windGust;
        this.windBearing = windBearing;
        this.cloudCover = cloudCover;
        this.uvIndex = uvIndex;
        this.visibility = visibility;
        this.ozone = ozone;
    }

    public long getTime()
    {
        return time;
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

    public int getNearestStormDistance()
    {
        return nearestStormDistance;
    }

    public int getNearestStormBearing()
    {
        return nearestStormBearing;
    }

    public double getPrecipIntensity()
    {
        return precipIntensity;
    }

    public double getPrecipProbability()
    {
        return precipProbability;
    }

    public double getTemperature()
    {
        return temperature;
    }

    public double getApparentTemperature()
    {
        return apparentTemperature;
    }

    public double getDewPoint()
    {
        return dewPoint;
    }

    public double getHumidity()
    {
        return humidity;
    }

    public double getPressure()
    {
        return pressure;
    }

    public double getWindSpeed()
    {
        return windSpeed;
    }

    public double getWindGust()
    {
        return windGust;
    }

    public int getWindBearing()
    {
        return windBearing;
    }

    public double getCloudCover()
    {
        return cloudCover;
    }

    public int getUvIndex()
    {
        return uvIndex;
    }

    public double getVisibility()
    {
        return visibility;
    }

    public double getOzone()
    {
        return ozone;
    }
}
