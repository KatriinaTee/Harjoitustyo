package com.example.harjoitusty.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.harjoitusty.AllData;
import com.example.harjoitusty.MunicipalityListAdapter;
import com.example.harjoitusty.R;

public class FragmentB extends Fragment {
    private static final String ARG_PARAM1 = "data";
    private String populationDataString;

    public FragmentB() {
    }

    public static FragmentB newInstance(String populationDataString) {
        FragmentB fragment = new FragmentB();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, populationDataString);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            populationDataString = getArguments().getString(ARG_PARAM1);
        }
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rvMunicipalityView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MunicipalityListAdapter mla = new MunicipalityListAdapter(AllData.getInstance());
        recyclerView.setAdapter(mla);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_b, container, false);
    }
}