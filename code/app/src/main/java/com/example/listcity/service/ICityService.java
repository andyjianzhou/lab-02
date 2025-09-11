package com.example.listcity.service;

public interface ICityService {
    void addCity(String city);

    boolean deleteCity(Integer position);

    boolean editCity(Integer position, String city);
}
