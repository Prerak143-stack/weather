package com.example.patel.weatherone;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CurrentWeather {
    private String locationLabel;
    private String icon;
    private long time;
    private double temperature;
    private double humidity;
    private double precipChance;
    private String summary;
    private String timeZone;

    public CurrentWeather() {
    }

    public CurrentWeather(String locationLabel, String icon, long time, double temperature, double humidity, double precipChance, String summary, String timeZone) {
        this.locationLabel = locationLabel;
        this.icon = icon;
        this.time = time;
        this.temperature = temperature;
        this.humidity = humidity;
        this.precipChance = precipChance;
        this.summary = summary;
        this.timeZone = timeZone;
    }

    public String getFormattedTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(getTimeZone()));

        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    public String getLocationLabel() {
        return locationLabel;
    }

    public void setLocationLabel(String locationLabel) {
        this.locationLabel = locationLabel;
    }



    public int getIcon(){
        //clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
        switch (icon)
        {
            case "clear-day":
                return R.drawable.clear_day;
            case "clear-night":
                return R.drawable.clear_night;
            case "rain":
                return R.drawable.rain;
            case "snow":
                return R.drawable.snow;
            case "sleet":
                return R.drawable.sleet;
            case "wind":
                return R.drawable.wind;
            case "fog":
                return R.drawable.fog;
            case "cloudy":
                return R.drawable.cloudy;
            case "partly-cloudy-day":
                return R.drawable.partly_cloudy;
            case "partly-cloudy-night":
                return R.drawable.partly_cloudy;
            default:
                return R.drawable.partly_cloudy;
        }
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTemperature() {
        return String.format("%.0f",temperature);
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPrecipChance() {
        return precipChance;
    }

    public void setPrecipChance(double precipChance) {
        this.precipChance = precipChance;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
