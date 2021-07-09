package com.example.hw05.response;

import java.util.ArrayList;

public class CurWeatherResponse {

    ArrayList<Weather> weather;
    Main main;
    Coord coord;
    Wind wind;
    Clouds clouds;

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Wind getWind() {
        return wind;
    }


    @Override
    public String toString() {
        return "CurWeatherResponse{" +
                "weather=" + weather +
                ", main=" + main +
                ", coord=" + coord +
                ", wind=" + wind +
                ", clouds=" + clouds +
                '}';
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public class Coord {

        public String getLon() {
            return lon;
        }

        @Override
        public String toString() {
            return "Coord{" +
                    "lon='" + lon + '\'' +
                    ", lat='" + lat + '\'' +
                    '}';
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        String lon, lat;
    }

    public class Clouds{
        String all;

        @Override
        public String toString() {
            return "Clouds{" +
                    "all='" + all + '\'' +
                    '}';
        }

        public String getAll() {
            return all;
        }

        public void setAll(String all) {
            this.all = all;
        }
    }

    public class Main {

        String temp, feels_like, temp_min, temp_max, pressure, humidity;

        public String getTemp() {
            return temp;
        }

        @Override
        public String toString() {
            return "Main{" +
                    "temp='" + temp + '\'' +
                    ", feels_like='" + feels_like + '\'' +
                    ", temp_min='" + temp_min + '\'' +
                    ", temp_max='" + temp_max + '\'' +
                    ", pressure='" + pressure + '\'' +
                    ", humidity='" + humidity + '\'' +
                    '}';
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getFeels_like() {
            return feels_like;
        }

        public void setFeels_like(String feels_like) {
            this.feels_like = feels_like;
        }

        public String getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(String temp_min) {
            this.temp_min = temp_min;
        }

        public String getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(String temp_max) {
            this.temp_max = temp_max;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }
    }

    public class Wind {

        String speed, deg;

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getDeg() {
            return deg;
        }

        @Override
        public String toString() {
            return "Wind{" +
                    "speed='" + speed + '\'' +
                    ", deg='" + deg + '\'' +
                    '}';
        }

        public void setDeg(String deg) {
            this.deg = deg;
        }

    }

    public class Weather {

        private String id;
        private String main;
        private String description;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        @Override
        public String toString() {
            return "Weather{" +
                    "id='" + id + '\'' +
                    ", main='" + main + '\'' +
                    ", description='" + description + '\'' +
                    ", icon='" + icon + '\'' +
                    '}';
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        private String icon;

    }

}






