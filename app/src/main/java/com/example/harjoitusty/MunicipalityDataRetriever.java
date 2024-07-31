package com.example.harjoitusty;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for retrieving data from different API's:
 * Tilastokeskus, Traficom, Openweather
 */
public class MunicipalityDataRetriever {

    /**
     * get population / population change data from Tilastokeskus
     * @param context
     * @param municipality
     * @return
     */
    public ArrayList<MunicipalityData> getData(Context context, String municipality) {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode areas = null;

        try {
            areas = objectMapper.readTree(new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/fi/StatFin/synt/statfin_synt_pxt_12dy.px"));
            // areas = objectMapper.readTree(new URL("https://statfin.stat.fi/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(areas.toPrettyString());

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        HashMap<String, String> municipalityCodes = new HashMap<>();

        for (JsonNode node : areas.get("variables").get(1).get("values")) {
            values.add(node.asText());
        }
        for (JsonNode node : areas.get("variables").get(1).get("valueTexts")) {
            keys.add(node.asText());
        }
        for (int i = 0; i < keys.size(); i++) {
            municipalityCodes.put(keys.get(i), values.get(i));
        }

        String code = municipalityCodes.get(municipality);

        try {
            URL url = new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/fi/StatFin/synt/statfin_synt_pxt_12dy.px");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JsonNode jsonInputString = objectMapper.readTree(context.getResources().openRawResource(R.raw.query));

            ((ObjectNode) jsonInputString.get("query").get(0).get("selection")).putArray("values").add(code);

            byte[] input = objectMapper.writeValueAsBytes(jsonInputString);
            OutputStream os = con.getOutputStream();
            os.write(input, 0, input.length);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }

            JsonNode municipalityData = objectMapper.readTree(response.toString());

            ArrayList<String> years = new ArrayList<>();
            ArrayList<String> populations = new ArrayList<>();
            ArrayList<String> change = new ArrayList<>();
            ArrayList<String> total = new ArrayList<>();
            ArrayList<MunicipalityData> populationData = new ArrayList<>();

            //System.out.println(municipalityData.toPrettyString());

            for (JsonNode node : municipalityData.get("dimension").get("Vuosi").get("category").get("label")) {
                years.add(node.asText());
            }
            for (JsonNode node : municipalityData.get("value")) {
                populations.add(node.asText());
            }

            for (int i = 0; i < populations.size(); i++) {
                if(i % 2 != 0){
                    change.add(populations.get(i));
                }else{
                    total.add(populations.get(i));
                }
            }

            for(int i = 0; i < years.size(); i++) {
                populationData.add(new MunicipalityData(
                        Integer.valueOf(years.get(i)), Integer.valueOf(change.get(i)), Integer.valueOf(total.get(i))
                        )
                );
            }

            return populationData;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get job self sufficiency data "työpaikkaomavaraisuus" from Tilastokeskus
     * @param context
     * @param municipality
     * @return
     */
    public ArrayList<MunicipalityDataEmployment> getDataEmployment(Context context, String municipality) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode areas = null;

        try {
            areas = objectMapper.readTree(
                    new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/fi/StatFin/tyokay/statfin_tyokay_pxt_125s.px")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(areas.toPrettyString());

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        HashMap<String, String> municipalityCodes = new HashMap<>();

        for (JsonNode node : areas.get("variables").get(1).get("values")) {
            values.add(node.asText());
        }
        for (JsonNode node : areas.get("variables").get(1).get("valueTexts")) {
            keys.add(node.asText());
        }
        for(int i = 0; i < keys.size(); i++) {
            municipalityCodes.put(keys.get(i), values.get(i));
        }

        String code = municipalityCodes.get(municipality);

        try {
            URL url = new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/fi/StatFin/tyokay/statfin_tyokay_pxt_125s.px");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JsonNode jsonInputString = objectMapper.readTree(context.getResources().openRawResource(R.raw.employmentquery));

            ((ObjectNode) jsonInputString.get("query").get(0).get("selection")).putArray("values").add(code);

            byte[] input = objectMapper.writeValueAsBytes(jsonInputString);
            OutputStream os = con.getOutputStream();
            os.write(input, 0, input.length);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }

            JsonNode employmentData = objectMapper.readTree(response.toString());

            ArrayList<String> years = new ArrayList<>();
            ArrayList<String> percentages = new ArrayList<>();
            ArrayList<MunicipalityDataEmployment> empData = new ArrayList<>();

            //System.out.println("EMPLOYMENT DATA");
            //System.out.println(employmentData.toPrettyString());

            for (JsonNode node : employmentData.get("dimension").get("Vuosi").get("category").get("label")) {
                years.add(node.asText());
            }
            for (JsonNode node : employmentData.get("value")) {
                percentages.add(node.asText());
            }
            for(int i = 0; i < years.size(); i++) {
                empData.add(
                        new MunicipalityDataEmployment(Integer.valueOf(years.get(i)), Float.valueOf(percentages.get(i)))
                );
            }

            return empData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get employment rate data "Työllisyysaste" from Tilastokeskus
     * @param context
     * @param municipality
     * @return
     */
    public ArrayList<MunicipalityDataEmployment> getDataEmploymentLvl(Context context, String municipality) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode areas = null;
        try {
            areas = objectMapper.readTree(new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/fi/StatFin/tyokay/statfin_tyokay_pxt_115x.px"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(areas.toPrettyString());

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        HashMap<String, String> municipalityCodes = new HashMap<>();

        for (JsonNode node : areas.get("variables").get(0).get("values")) {
            values.add(node.asText());
        }
        for (JsonNode node : areas.get("variables").get(0).get("valueTexts")) {
            keys.add(node.asText());
        }
        for(int i = 0; i < keys.size(); i++) {
            municipalityCodes.put(keys.get(i), values.get(i));
        }

        String code = municipalityCodes.get(municipality);

        try {
            URL url = new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/fi/StatFin/tyokay/statfin_tyokay_pxt_115x.px");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JsonNode jsonInputString = objectMapper.readTree(context.getResources().openRawResource(R.raw.levelquery));
            ((ObjectNode) jsonInputString.get("query").get(0).get("selection")).putArray("values").add(code);

            byte[] input = objectMapper.writeValueAsBytes(jsonInputString);
            OutputStream os = con.getOutputStream();
            os.write(input, 0, input.length);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }

            JsonNode employmentData = objectMapper.readTree(response.toString());

            ArrayList<String> years = new ArrayList<>();
            ArrayList<String> percentages = new ArrayList<>();
            ArrayList<MunicipalityDataEmployment> empData = new ArrayList<>();

            //System.out.println("EMPLOYMENT LEVEL DATA");
            //System.out.println(employmentData.toPrettyString());

            for (JsonNode node : employmentData.get("dimension").get("Vuosi").get("category").get("label")) {
                years.add(node.asText());
            }
            for (JsonNode node : employmentData.get("value")) {
                percentages.add(node.asText());
            }
            for(int i = 0; i < years.size(); i++) {
                empData.add(
                        new MunicipalityDataEmployment(Integer.valueOf(years.get(i)), Float.valueOf(percentages.get(i)))
                );
            }
            return empData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get weather data from openweather api
     * @param municipality
     * @return
     */
    public WeatherData getWeatherData(String municipality) {
        String API_KEY = "b47c509ced22c7ca33a4c5922122f9f4";
        String CONVERTER_BASE_URL = "https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=5&appid=%s";
        String WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s";

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode areas = null;
            areas = objectMapper.readTree(new URL(String.format(CONVERTER_BASE_URL, municipality, API_KEY)));

            String latitude = areas.get(0).get("lat").toString();
            String longitude = areas.get(0).get("lon").toString();
            JsonNode weatherData;
            weatherData = objectMapper.readTree(new URL(String.format(WEATHER_BASE_URL, latitude, longitude, API_KEY)));

            WeatherData wd = new WeatherData(
                    weatherData.get("name").asText(),
                    weatherData.get("weather").get(0).get("main").asText(),
                    weatherData.get("weather").get(0).get("description").asText(),
                    weatherData.get("main").get("temp").asText(),
                    weatherData.get("wind").get("speed").asText(),
                    weatherData.get("weather").get(0).get("icon").asText()
            );

            String iconUrl = "https://openweathermap.org/img/wn/"+wd.getIconCode()+"@2x.png";
            InputStream is = (InputStream) new URL(iconUrl).getContent();
            Drawable d = Drawable.createFromStream(is, "wdIcon");
            wd.setIcon(d);

            return wd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get electric vehicle data from traficom
     * @param context
     * @param municipality
     * @return
     */
    public ArrayList<TraficomData> getDataTrafi(Context context, String municipality) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode areas = null;
        try {
            areas = objectMapper.readTree(new URL("https://trafi2.stat.fi:443/PXWeb/api/v1/fi/TraFi/Liikennekaytossa_olevat_ajoneuvot/010_kanta_tau_101.px"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(areas.toPrettyString());

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        HashMap<String, String> municipalityCodes = new HashMap<>();

        ArrayList<String> keys2 = new ArrayList<>();
        ArrayList<String> values2 = new ArrayList<>();
        HashMap<String, String> brandCodes = new HashMap<>();

        for (JsonNode node : areas.get("variables").get(0).get("values")) {
            values.add(node.asText());
        }
        for (JsonNode node : areas.get("variables").get(0).get("valueTexts")) {
            keys.add(node.asText());
        }
        for(int i = 0; i < keys.size(); i++) {
            municipalityCodes.put(keys.get(i), values.get(i));
        }

        for (JsonNode node : areas.get("variables").get(1).get("values")) {
            values2.add(node.asText());
        }
        for (JsonNode node : areas.get("variables").get(1).get("valueTexts")) {
            keys2.add(node.asText());
        }
        for(int i = 0; i < keys2.size(); i++) {
            brandCodes.put(keys2.get(i), values2.get(i));
        }

        String code = municipalityCodes.get(municipality);

        try {
            URL url = new URL("https://trafi2.stat.fi:443/PXWeb/api/v1/fi/TraFi/Liikennekaytossa_olevat_ajoneuvot/010_kanta_tau_101.px");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JsonNode jsonInputString = objectMapper.readTree(context.getResources().openRawResource(R.raw.trafiquery));
            ((ObjectNode) jsonInputString.get("query").get(0).get("selection")).putArray("values").add(code);

            byte[] input = objectMapper.writeValueAsBytes(jsonInputString);
            OutputStream os = con.getOutputStream();
            os.write(input, 0, input.length);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }

            JsonNode trafiData = objectMapper.readTree(response.toString());

            ArrayList<String> models = new ArrayList<>();
            ArrayList<String> amounts = new ArrayList<>();
            ArrayList<TraficomData> carData = new ArrayList<>();

            //System.out.println("Traficom DATA");
            //System.out.println(trafiData.toPrettyString());

            for (JsonNode node : trafiData.get("dimension").get("Merkki").get("category").get("label")) {
                models.add(node.asText());
            }
            for (JsonNode node : trafiData.get("value")) {
                amounts.add(node.asText());
            }
            for(int i = 0; i < models.size(); i++) {
                if(amounts.get(i) == null || amounts.get(i) == "null") continue;
                carData.add(new TraficomData(models.get(i), amounts.get(i)));
            }
            return carData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
