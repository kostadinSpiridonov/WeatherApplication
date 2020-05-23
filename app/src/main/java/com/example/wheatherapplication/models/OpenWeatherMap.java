package com.example.wheatherapplication.models;

import com.example.wheatherapplication.common.DateTimeHelper;

import java.util.List;

public class OpenWeatherMap {
    private List<Weather> weather;
    private Main main;
    private Sys sys;
    private String name;

    public List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Sys getSys() {
        return sys;
    }

    public String getName() {
        return name;
    }

    public String getLastUpdate(){
        return DateTimeHelper.getDateNow();
    }
}
