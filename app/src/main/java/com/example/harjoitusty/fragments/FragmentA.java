package com.example.harjoitusty.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.harjoitusty.AllData;
import com.example.harjoitusty.MunicipalityData;
import com.example.harjoitusty.MunicipalityDataEmployment;
import com.example.harjoitusty.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FragmentA extends Fragment {
    private TextView txtTrafi;
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    public FragmentA() {
    }

   public static FragmentC newInstance(String param1) {
        FragmentC fragment = new FragmentC();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        txtTrafi = view.findViewById(R.id.trafiView);
        txtTrafi.setText(AllData.getInstance().getTrafiDataString());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_a, container, false);
    }
}