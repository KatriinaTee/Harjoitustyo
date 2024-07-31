package com.example.harjoitusty;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Singleton class that holds data about municipality
 */
public class AllData {
    private static AllData instance;
    private String trafiDataString;
    private String weatherDataString;
    private Drawable weatherIcon;
    private ArrayList<MunicipalityData> populationData = new ArrayList<>();
    private ArrayList<MunicipalityDataEmployment> employmentData = new ArrayList<>();
    private ArrayList<MunicipalityDataEmployment> employmentRateData = new ArrayList<>();

    public static AllData getInstance() {
        if(instance == null) {
            instance = new AllData();
        }
        return instance;
    }

    public ArrayList<MunicipalityData> getPopulationData() {
        return populationData;
    }
    public void setPopulationData(ArrayList<MunicipalityData> populationData) {
        this.populationData = populationData;
    }

    public String getWeatherDataString() {
        return weatherDataString;
    }
    public void setWeatherDataString(String weatherDataString) {
        this.weatherDataString = weatherDataString;
    }

    public ArrayList<MunicipalityDataEmployment> getEmploymentData() {
        return employmentData;
    }
    public void setEmploymentData(ArrayList<MunicipalityDataEmployment> employmentData) {
        this.employmentData = employmentData;
    }

    public ArrayList<MunicipalityDataEmployment> getEmploymentRateData() {
        return employmentRateData;
    }
    public void setEmploymentRateData(ArrayList<MunicipalityDataEmployment> employmentLvlData) {
        this.employmentRateData = employmentLvlData;
    }

    public Drawable getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(Drawable weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getTrafiDataString() {
        return trafiDataString;
    }

    public void setTrafiDataString(String trafiDataString) {
        this.trafiDataString = trafiDataString;
    }
}
