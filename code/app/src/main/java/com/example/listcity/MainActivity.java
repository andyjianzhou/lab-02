package com.example.listcity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listcity.service.CityService;
import com.example.listcity.service.ICityService;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;

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
        Button addButton = (Button) findViewById(R.id.addButton);
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

        addButton.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
            bottomSheetDialog.setContentView(R.layout.bottom_add_city);

            EditText cityInput = bottomSheetDialog.findViewById(R.id.cityInput);
            Button confirmButton = bottomSheetDialog.findViewById(R.id.confirmAddButton);

            if(confirmButton != null) {
                confirmButton.setOnClickListener(view -> {
                    if(cityInput != null) {
                        String newCity = cityInput.getText().toString();
                        cityService.addCity(newCity);
                        cityAdapter.notifyDataSetChanged();
                    }
                    bottomSheetDialog.dismiss();
                });
            }
            bottomSheetDialog.show();
            selectedCityPos = -1;
        });

        deleteButton.setOnClickListener(v -> {
            if (selectedCityPos == -1) { return; }
                cityService.deleteCity(selectedCityPos);
                cityAdapter.notifyDataSetChanged();
                selectedCityPos = -1; // reset after delete
        });

        editButton.setOnClickListener(v -> {
            if(selectedCityPos == -1) { return; }

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
            bottomSheetDialog.setContentView(R.layout.bottom_edit_city_layout);

            EditText cityInput = bottomSheetDialog.findViewById(R.id.cityInput);
            assert cityInput != null;
            cityInput.setText(dataList.get(selectedCityPos));
            Button confirmButton = bottomSheetDialog.findViewById(R.id.confirmEditButton);

            if(confirmButton != null) {
                confirmButton.setOnClickListener(view -> {
                    String newCity = cityInput.getText().toString();
                    cityService.editCity(selectedCityPos, newCity);
                    cityAdapter.notifyDataSetChanged();
                    bottomSheetDialog.dismiss();
                });
            }
            bottomSheetDialog.show();
        });
    }
}