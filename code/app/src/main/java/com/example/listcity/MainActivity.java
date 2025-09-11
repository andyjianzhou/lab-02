package com.example.listcity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listcity.service.CityService;
import com.example.listcity.service.ICityService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    private int selectedCityPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        Button editButton = (Button) findViewById(R.id.editButton);

        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));


        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        ICityService cityService = new CityService(dataList);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedCityPos = position;
            Log.d("MainActivity", "Clicked: " + selectedCityPos);
            // insert city service here
        });

        deleteButton.setOnClickListener(v -> {
            if (selectedCityPos != -1) {
//                dataList.remove(selectedCityPos);
                cityService.deleteCity(selectedCityPos);
                cityAdapter.notifyDataSetChanged();
                selectedCityPos = -1; // reset after delete
            }
        });
    }
}