package com.example.harjoitusty;

/**
 * data model for municipalitys yearly population and pupulation change
 */
public class MunicipalityData {
    private int year;
    private int population;
    private int populationChange;

    public MunicipalityData(int y, int p, int pc) {
        year = y;
        population = p;
        populationChange = pc;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getPopulation() {
        return population;
    }
    public void setPopulation(int population) {
        this.population = population;
    }

    public int getPopulationChange() {
        return populationChange;
    }

    public void setPopulationChange(int populationChange) {
        this.populationChange = populationChange;
    }
}