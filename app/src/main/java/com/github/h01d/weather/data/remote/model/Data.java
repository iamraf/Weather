package com.github.h01d.weather.data.remote.model;

import androidx.annotation.Nullable;

public class Data
{
    private long time;
    @Nullable
    private String summary;
    @Nullable
    private String icon;
    private long sunriseTime;
    private long sunsetTime;
    private double moonPhase;
    private double precipIntensity;
    private double precipIntensityMax;
    private long precipIntensityMaxTime;
    private double precipProbability;
    @Nullable
    private String precipType;
    private double temperatureHigh;
    private long temperatureHighTime;
    private double temperatureLow;
    private long temperatureLowTime;
    private double apparentTemperatureHigh;
    private long apparentTemperatureHighTime;
    private double apparentTemperatureLow;
    private long apparentTemperatureLowTime;
    private double dewPoint;
    private double humidity;
    private double pressure;
    private double windSpeed;
    private double windGust;
    private long windGustTime;
    private int windBearing;
    private double cloudCover;
    private int uvIndex;
    private long uvIndexTime;
    private double visibility;
    private double ozone;

    public Data(long time, @Nullable String summary, @Nullable String icon, long sunriseTime, long sunsetTime, double moonPhase, double precipIntensity, double precipIntensityMax, long precipIntensityMaxTime, double precipProbability, @Nullable String precipType, double temperatureHigh, long temperatureHighTime, double temperatureLow, long temperatureLowTime, double apparentTemperatureHigh, long apparentTemperatureHighTime, double apparentTemperatureLow, long apparentTemperatureLowTime, double dewPoint, double humidity, double pressure, double windSpeed, double windGust, long windGustTime, int windBearing, double cloudCover, int uvIndex, long uvIndexTime, double visibility, double ozone)
    {
        this.time = time;
        this.summary = summary;
        this.icon = icon;
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
        this.moonPhase = moonPhase;
        this.precipIntensity = precipIntensity;
        this.precipIntensityMax = precipIntensityMax;
        this.precipIntensityMaxTime = precipIntensityMaxTime;
        this.precipProbability = precipProbability;
        this.precipType = precipType;
        this.temperatureHigh = temperatureHigh;
        this.temperatureHighTime = temperatureHighTime;
        this.temperatureLow = temperatureLow;
        this.temperatureLowTime = temperatureLowTime;
        this.apparentTemperatureHigh = apparentTemperatureHigh;
        this.apparentTemperatureHighTime = apparentTemperatureHighTime;
        this.apparentTemperatureLow = apparentTemperatureLow;
        this.apparentTemperatureLowTime = apparentTemperatureLowTime;
        this.dewPoint = dewPoint;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windGust = windGust;
        this.windGustTime = windGustTime;
        this.windBearing = windBearing;
        this.cloudCover = cloudCover;
        this.uvIndex = uvIndex;
        this.uvIndexTime = uvIndexTime;
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

    public long getSunriseTime()
    {
        return sunriseTime;
    }

    public long getSunsetTime()
    {
        return sunsetTime;
    }

    public double getMoonPhase()
    {
        return moonPhase;
    }

    public double getPrecipIntensity()
    {
        return precipIntensity;
    }

    public double getPrecipIntensityMax()
    {
        return precipIntensityMax;
    }

    public long getPrecipIntensityMaxTime()
    {
        return precipIntensityMaxTime;
    }

    public double getPrecipProbability()
    {
        return precipProbability;
    }

    @Nullable
    public String getPrecipType()
    {
        return precipType;
    }

    public double getTemperatureHigh()
    {
        return temperatureHigh;
    }

    public long getTemperatureHighTime()
    {
        return temperatureHighTime;
    }

    public double getTemperatureLow()
    {
        return temperatureLow;
    }

    public long getTemperatureLowTime()
    {
        return temperatureLowTime;
    }

    public double getApparentTemperatureHigh()
    {
        return apparentTemperatureHigh;
    }

    public long getApparentTemperatureHighTime()
    {
        return apparentTemperatureHighTime;
    }

    public double getApparentTemperatureLow()
    {
        return apparentTemperatureLow;
    }

    public long getApparentTemperatureLowTime()
    {
        return apparentTemperatureLowTime;
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

    public long getWindGustTime()
    {
        return windGustTime;
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

    public long getUvIndexTime()
    {
        return uvIndexTime;
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
