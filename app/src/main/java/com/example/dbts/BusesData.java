package com.example.dbts;


import java.util.Arrays;
import java.util.List;

public class BusesData {
    private String CampusToOriginTime;
    private String OriginToCampusTime;
    private double Latitude;
    private double Longitude;
    private int Route;
    private List<String> Stations;
    private List<String> Stations_Time;

    public BusesData() {
    }

    @Override
    public String toString() {
        return "BusesData{" +
                "CampusToOriginTime='" + CampusToOriginTime + '\'' +
                ", OriginToCampusTime='" + OriginToCampusTime + '\'' +
                ", Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                ", Route=" + Route +
                ", Stations=" + Stations +
                ", Stations_Time=" + Stations_Time +
                '}';
    }

    public String getCampusToOriginTime() {
        return CampusToOriginTime;
    }

    public void setCampusToOriginTime(String campusToOriginTime) {
        CampusToOriginTime = campusToOriginTime;
    }

    public String getOriginToCampusTime() {
        return OriginToCampusTime;
    }

    public void setOriginToCampusTime(String originToCampusTime) {
        OriginToCampusTime = originToCampusTime;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public int getRoute() {
        return Route;
    }

    public void setRoute(int route) {
        Route = route;
    }

    public List<String> getStations() {
        return Stations;
    }

    public void setStations(List<String> stations) {
        Stations = stations;
    }

    public List<String> getStations_Time() {
        return Stations_Time;
    }

    public void setStations_Time(List<String> stations_Time) {
        Stations_Time = stations_Time;
    }

    public BusesData(String campusToOriginTime, String originToCampusTime, double latitude, double longitude, int route, List<String> stations, List<String> stations_Time) {
        CampusToOriginTime = campusToOriginTime;
        OriginToCampusTime = originToCampusTime;
        Latitude = latitude;
        Longitude = longitude;
        Route = route;
        Stations = stations;
        Stations_Time = stations_Time;
    }
}