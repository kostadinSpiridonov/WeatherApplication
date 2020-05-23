package com.example.wheatherapplication.services;

import com.example.wheatherapplication.models.OpenWeatherMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherService {
    private static final String API_KEY = "bbd7048f108bc1151b7a043488e4cdd4";
    private static final String API_LINK = "https://api.openweathermap.org/data/2.5/weather";

    public OpenWeatherMap getData(String city){
        String json = null;
        HttpURLConnection connection = null;
        try
        {
            URL url = this.getCityWeatherUrl(city);
            connection = (HttpURLConnection)url.openConnection();

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;

                while((line = r.readLine()) != null) {
                    sb.append(line);
                }

                json = sb.toString();
            }
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if(connection != null) {
                connection.disconnect();
            }
        }

        return this.deserialize(json);
    }

    private OpenWeatherMap deserialize(String s){
        Gson gson = new Gson();
        Type mType = new TypeToken<OpenWeatherMap>(){}.getType();
        return gson.fromJson(s, mType);
    }

    private URL getCityWeatherUrl(String city) throws MalformedURLException {
        StringBuilder url = new StringBuilder(API_LINK);
        url.append(String.format("?q=%s&APPID=%s&units=metric", city, API_KEY));
        return new URL(url.toString());
    }
}
