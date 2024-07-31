package com.example.harjoitusty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import com.example.harjoitusty.fragments.FragmentC;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private EditText editTextLocation;
    private TextView txtHistory;
    private TextView txtNotification;
    private String history = "Hakuhistoria\n\n";
    private String notification;
    private MunicipalityDataRetriever mr = new MunicipalityDataRetriever();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLocation = findViewById(R.id.editLocation);
        txtHistory = findViewById(R.id.txtHistory);
        txtNotification = findViewById(R.id.txtNotification);
        txtHistory.setText(history);
        txtNotification.setText(notification);
    }

    // find button click. Calls MunicipalityDataRetriever to get data
    // from multiple API's and after changes view to tab activity
   public void onFindBtnClick(View view) {
        Context context = this;
        String location = editTextLocation.getText().toString();
        ExecutorService service = Executors.newSingleThreadExecutor();
        AllData allData = AllData.getInstance();

        service.execute(new Runnable() {
            @Override
            public void run() {
                history += "\n" + location;
                notification = "Haetaan dataa " + location;
                txtNotification.setText("Haetaan dataa " + location);
                txtHistory.setText(history);

                WeatherData weatherData = mr.getWeatherData(location);
                ArrayList<MunicipalityData> populationData = mr.getData(context, location);
                ArrayList<MunicipalityDataEmployment> employmentData = mr.getDataEmployment(context, location);
                ArrayList<MunicipalityDataEmployment> employmentRateData = mr.getDataEmploymentLvl(context, location);
                ArrayList<TraficomData> traficomData = mr.getDataTrafi(context, location);

                System.out.println(populationData);
                System.out.println(weatherData);
                System.out.println(traficomData);

                if (populationData == null || traficomData == null){
                    notification = "Ei dataa kunnalle " + location;
                    txtNotification.setText(notification);
                    return;
                }

                notification = "";
                txtNotification.setText(notification);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // set Population data
                        allData.setPopulationData(populationData);
                        // set Weather data
                        allData.setWeatherDataString(weatherData.parseWeatherData());
                        allData.setWeatherIcon(weatherData.getIcon());
                        // set Employment self sufficiency data
                        allData.setEmploymentData(employmentData);
                        // set Employment rate data
                        allData.setEmploymentRateData(employmentRateData);
                        // set Electric vehicle data
                        allData.setTrafiDataString(TraficomData.parseData(traficomData));
                    }
                });

                Intent intent = new Intent(view.getContext(), TabActivity.class);
                startActivity(intent);
            }
        });
   }
}