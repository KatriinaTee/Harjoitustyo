package com.example.harjoitusty;

/**
 * data model for municipalitys employment self-sufficienty and rate
 */
public class MunicipalityDataEmployment {
    private float percentage;
    private int year;

    public MunicipalityDataEmployment( int year, float percentage) {
        this.percentage = percentage;
        this.year = year;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
