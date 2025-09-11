package com.example.listcity.service;

import android.util.Log;

import java.util.ArrayList;

public class CityService implements ICityService {
    public ArrayList<String> cityList;

    public CityService(ArrayList<String> dataList) {
        this.cityList = dataList;
    }
    @Override
    public void addCity(String city) {
        if(city != null) {
            this.cityList.add(city);
        }
    }

    // Ensure that we delete the first one
    @Override
    public boolean deleteCity(Integer position) {
        Log.d("CityService", "deleteCity called with pos=" + position);
        if (cityList != null && position >= 0 && position < cityList.size()) {
            cityList.remove((int) position);
            return true;
        }
        return false;
    }

    @Override
    public boolean editCity(Integer position, String city) { return Boolean.TRUE; }
}
