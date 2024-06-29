package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.model.Commune;
import com.example.myapplication.model.District;
import com.example.myapplication.model.Province;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class AddAddress extends AppCompatActivity {


    Spinner provinceSpinner;
    Spinner districtSpinner;
    Spinner communeSpinner;
    EditText addressDetail;
    EditText phoneText;
    AppCompatButton saveButton;

    List<Province> provinces;
    List<District> districts;
    List<Commune> communes;
    String idProvince = "";
    String idDistrict = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_address);
        provinceSpinner = findViewById(R.id.provinceSpinner);
        districtSpinner = findViewById(R.id.districtSpinner);
        communeSpinner = findViewById(R.id.communeSpinner);
        addressDetail = findViewById(R.id.addressDetail);
        phoneText = findViewById(R.id.phoneDetail);

        loadProvince();


        // provinceSpinner
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // This method is called when an item is selected
                String selectedItem = (String) parent.getItemAtPosition(position);

                for (Province province : provinces) {
                    if (province.getName().equals(selectedItem)) {
                        idProvince = province.getIdProvince();
                        break;
                    }
                }
                loadDistrict(idProvince);
                Toast.makeText(getApplicationContext(), "Selected: " + selectedItem + idProvince, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // This method is called when an item is selected
                String selectedItem = (String) parent.getItemAtPosition(position);

                for (District district : districts) {
                    if (district.getName().equals(selectedItem)) {
                        idDistrict = district.getIdDistrict();
                        break;
                    }
                }
                loadCommune(idDistrict);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        saveButton = findViewById(R.id.saveAddress);

        saveButton.setOnClickListener(v -> {
            String address = provinceSpinner.getSelectedItem().toString() + ", " + districtSpinner.getSelectedItem().toString() +
                    ", " + communeSpinner.getSelectedItem().toString() + ", " + addressDetail.getText().toString();
            String phone = phoneText.getText().toString();
            Toast.makeText(getApplicationContext(), address + "\n" + phone, Toast.LENGTH_SHORT).show();
        });


    }

    private void loadProvince() {
        String url = "https://api-tinh-thanh-by-kitare17.vercel.app/province";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        provinces = Arrays.asList(gson.fromJson(response, Province[].class));
                        String[] choices = new String[provinces.size()];
                        System.out.println(provinces.size());
                        for (int i = 0; i < provinces.size(); i++) {
                            choices[i] = provinces.get(i).getName();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                getApplicationContext(), // Context of your activity
                                android.R.layout.simple_spinner_item, // Default spinner item layout
                                choices // Your String array of choices
                        );
                        provinceSpinner.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddAddress.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void loadDistrict(String idProvince) {
        String url = "https://api-tinh-thanh-by-kitare17.vercel.app/district?idProvince=" + idProvince;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        districts = Arrays.asList(gson.fromJson(response, District[].class));
                        String[] choices = new String[districts.size()];
                        System.out.println(districts.size());
                        for (int i = 0; i < districts.size(); i++) {
                            choices[i] = districts.get(i).getName();

                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                getApplicationContext(), // Context of your activity
                                android.R.layout.simple_spinner_item, // Default spinner item layout
                                choices // Your String array of choices
                        );
                        districtSpinner.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddAddress.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void loadCommune(String idDistrict) {
        Toast.makeText(getApplicationContext(), "idDistrict: " + idDistrict, Toast.LENGTH_SHORT).show();

        String url = "https://api-tinh-thanh-by-kitare17.vercel.app/commune?idDistrict=" + idDistrict;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        communes = Arrays.asList(gson.fromJson(response, Commune[].class));
                        String[] choices = new String[communes.size()];

                        for (int i = 0; i < communes.size(); i++) {
                            choices[i] = communes.get(i).getName();

                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                getApplicationContext(), // Context of your activity
                                android.R.layout.simple_spinner_item, // Default spinner item layout
                                choices // Your String array of choices
                        );
                        communeSpinner.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddAddress.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }

}