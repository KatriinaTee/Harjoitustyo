package com.example.harjoitusty;

import android.graphics.drawable.Drawable;

import java.text.DecimalFormat;

/**
 * data model of municipalitys weather condition
 */
public class WeatherData {
    private String name;
    private String main;
    private String description;
    private String temperature;
    private String windSpeed;
    private String iconCode;
    private Drawable icon;

    public WeatherData(String n, String m, String d, String t, String w, String x) {
        name = n;
        main = m;
        description = d;
        temperature = t;
        windSpeed = w;
        iconCode = x;
    }

    // parses a string representation of the data
    public String parseWeatherData(){
        DecimalFormat df = new DecimalFormat("###.##");
        String weatherDataString = getName() + "\n" +
                "Sää nyt: " + getMain() + " (" + getDescription() +")\n" +
                "Lämpötila: " + df.format(Float.parseFloat(getTemperature()) - 273.15)+ " °C\n" +
                "Tuulennopeus: " + getWindSpeed() + " m/s\n";
        return weatherDataString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWindSpeed() {
        return windSpeed;
    }


    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getIconCode() {
        return iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}