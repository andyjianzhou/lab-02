package com.example.listcity.service;

public interface ICityService {
    void addCity(String city);

    void deleteCity(Integer position);

    void editCity(Integer position, String newCity);
}
