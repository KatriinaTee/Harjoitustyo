package com.example.harjoitusty;

import java.util.ArrayList;

/**
 * data model for amount of cars in use per model
 */
public class TraficomData {
    private String carModel;
    private String amountInUse;

    public TraficomData(String carModel, String amountInUse) {
        this.carModel = carModel;
        this.amountInUse = amountInUse;
    }

    // parses a string from list of TraficomData objects
    public static String parseData(ArrayList<TraficomData> data){
        StringBuilder sb = new StringBuilder();
        for (TraficomData d :  data) {
            String formattedStr = String.format("%-23s", d.getCarModel());
            if(d.getAmountInUse() != null && Integer.parseInt(d.getAmountInUse()) > 0){
                sb.append(formattedStr).append(d.getAmountInUse()).append("\n");
            }
        }
        return sb.toString();
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getAmountInUse() {
        return amountInUse;
    }

    public void setAmountInUse(String amountInUse) {
        this.amountInUse = amountInUse;
    }
}
