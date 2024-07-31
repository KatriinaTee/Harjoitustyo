package com.example.harjoitusty.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.harjoitusty.AllData;
import com.example.harjoitusty.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentC#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentC extends Fragment {
    private TextView txtWeather;
    private ImageView imgWeather;
    private static final String ARG_PARAM1 = "data";

    private String weatherData;

    public FragmentC() {
    }

    public static FragmentC newInstance(String weatherData) {
        FragmentC fragment = new FragmentC();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, weatherData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            weatherData = getArguments().getString(ARG_PARAM1);
        }
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        txtWeather = view.findViewById(R.id.txtWeather);
        txtWeather.setText(AllData.getInstance().getWeatherDataString());
        imgWeather = view.findViewById(R.id.weatherImage);
        imgWeather.setImageDrawable(AllData.getInstance().getWeatherIcon());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_c, container, false);
    }
}