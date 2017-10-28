package com.example.cansu.havadurumu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeatherFort implements Serializable{

    private City city = new City();

    private int cod;

    private ArrayList<WeatherList> list;



    public int getCod() {
        return cod;
    }

    public City getCity() {
        return city;
    }


    public static class City implements Serializable{

        private String name;
        private WeatherInfo.Coordinates coord;
        private String country;

        public WeatherInfo.Coordinates getCoord() {
            return coord;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry( String country ) {
            this.country = country;
        }


        public void setName( String name ) {
            this.name = name;
        }



    }

    public ArrayList<WeatherList> getList() {
        return list;
    }

    public static class WeatherList implements Serializable{
        private long dt;
        private Temp temp;
        private double pressure;
        private int humidity;
        private double speed;
        private double deg;
        private double rain;
        private double snow;

        private List<WeatherInfo.Weather> weather = new ArrayList<>();

        public List<WeatherInfo.Weather> getWeather() {
            return weather;
        }

        public long getDt() {
            return dt;
        }

        public Temp getTemp() {
            return temp;
        }

        public double getSpeed() {
            return speed;
        }

        public double getPressure() {
            return pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public double getDeg() {
            return deg;
        }

        public double getRain() {
            return rain;
        }

        public double getSnow() {
            return snow;
        }
    }

    public static class Temp implements Serializable{
        private double day;
        private double min;
        private double max;

        public int getMax() {
            return (int) max;
        }

        public int getMin() {
            return (int) min;
        }

        public double getDay() {
            return (double) day;
        }


        public void setDay( double aDouble ) {
        }

        public void setMin( double aDouble ) {
        }

        public void setMax( double aDouble ) {
        }
    }

}