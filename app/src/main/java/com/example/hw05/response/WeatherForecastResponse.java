package com.example.hw05.response;

import java.util.ArrayList;

public class WeatherForecastResponse {

    ArrayList<List> list;

    @Override
    public String toString() {
        return "WeatherForecastResponse{" +
                "list=" + list +
                '}';
    }

    public ArrayList<List> getList() {
        return list;
    }

    public void setList(ArrayList<List> list) {
        this.list = list;
    }

    public class List{

        public String getDt_txt() {
            return dt_txt;
        }

        @Override
        public String toString() {
            return "List{" +
                    "dt_txt='" + dt_txt + '\'' +
                    ", main=" + main +
                    ", weather=" + weather +
                    ", clouds=" + clouds +
                    ", wind=" + wind +
                    '}';
        }

        public void setDt_txt(String dt_txt) {
            this.dt_txt = dt_txt;
        }

        public CurWeatherResponse.Main getMain() {
            return main;
        }

        public void setMain(CurWeatherResponse.Main main) {
            this.main = main;
        }

        public ArrayList<CurWeatherResponse.Weather> getWeather() {
            return weather;
        }

        public void setWeather(ArrayList<CurWeatherResponse.Weather> weather) {
            this.weather = weather;
        }

        public CurWeatherResponse.Clouds getClouds() {
            return clouds;
        }

        public void setClouds(CurWeatherResponse.Clouds clouds) {
            this.clouds = clouds;
        }

        public CurWeatherResponse.Wind getWind() {
            return wind;
        }

        public void setWind(CurWeatherResponse.Wind wind) {
            this.wind = wind;
        }

        String dt_txt;

        CurWeatherResponse.Main main;

        ArrayList<CurWeatherResponse.Weather> weather;

        CurWeatherResponse.Clouds clouds;

        CurWeatherResponse.Wind wind;

    }

}
