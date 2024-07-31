package com.example.harjoitusty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter that combines municipalitys yearly data for municipalityViewHolder (municipality_view)
 */
public class MunicipalityListAdapter extends RecyclerView.Adapter<MunicipalityViewHolder>{
    private ArrayList<MunicipalityData> data;
    private ArrayList<MunicipalityDataEmployment> data2;
    private ArrayList<MunicipalityDataEmployment> data3;

    public MunicipalityListAdapter(AllData allData) {
        this.data = allData.getPopulationData();
        this.data2 = allData.getEmploymentData();
        this.data3 = allData.getEmploymentRateData();
    }

    @NonNull
    @Override
    public MunicipalityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MunicipalityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.municipality_view, parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull MunicipalityViewHolder holder, int position) {
        holder.textYear.setText(String.valueOf(data.get(position).getYear()));
        holder.textPopulation.setText("Väkiluku " + String.valueOf(data.get(position).getPopulation()));
        holder.textPopulationChange.setText("Väestönlisäys " + String.valueOf(data.get(position).getPopulationChange()));
        for (MunicipalityDataEmployment d: data2) {
            if(d.getYear() == data.get(position).getYear()){
                holder.textSelfSufficiency.setText("Työpaikkaomavaraisuus % " + String.valueOf(d.getPercentage()));
                break;
            }
        }
        for (MunicipalityDataEmployment d: data3) {
            if(d.getYear() == data.get(position).getYear()){
                holder.textRate.setText("Työllisyysaste % " + String.valueOf(d.getPercentage()));
                break;
            }
        }
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
}
