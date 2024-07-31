package com.example.harjoitusty;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Holds municipalitys one year data about population, population change, employment rate and employment sel-sufficienty
 */
public class MunicipalityViewHolder extends RecyclerView.ViewHolder{
    TextView textYear;
    TextView textPopulation;
    TextView textPopulationChange;
    TextView textSelfSufficiency;
    TextView textRate;
    public MunicipalityViewHolder(@NonNull View itemView){
        super(itemView);
        textYear = itemView.findViewById(R.id.textYear);
        textPopulation = itemView.findViewById(R.id.textPopulation);
        textPopulationChange = itemView.findViewById(R.id.textPopulationChange);
        textSelfSufficiency = itemView.findViewById(R.id.textSelfSufficiency);
        textRate = itemView.findViewById(R.id.textRate);
    }
}
