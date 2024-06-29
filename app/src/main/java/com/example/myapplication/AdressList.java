package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.widget.LinearLayout.LayoutParams;

import com.example.myapplication.database.AddressRepository;
import com.example.myapplication.model.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdressList extends AppCompatActivity {

    private AddressRepository addressRepository;

    RadioGroup group;

    AppCompatButton buttonSaveAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adress_list);
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
        ImageView addButton = findViewById(R.id.buttonAddAddress);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddAddress.class);
            startActivity(intent);
        });

        group = (RadioGroup) findViewById(R.id.addressListRadio);
        group.setGravity(Gravity.TOP | Gravity.LEFT);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String selectedOption = radioButton.getText().toString();


                Scanner scanner = new Scanner(selectedOption);

                String address = scanner.nextLine();
                String phone = scanner.nextLine();
                Toast.makeText(getApplicationContext(), "address "+address+" phone "+phone, Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences("Address", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("address", address);

                editor.putString("phone", phone);
                editor.apply();

            }
        });
        buttonSaveAddress = findViewById(R.id.buttonSaveAddress);
        buttonSaveAddress.setOnClickListener(v -> {
            finish();
        });
    }

    public List<Address> getListAddress(int userId){
        addressRepository = new AddressRepository(this);
        List<Address> listAddress = addressRepository.listAllAddress(userId);
        return listAddress;
    }

    @Override
    protected void onResume() {
        group.removeAllViews();
        List<Address> list = getListAddress(1);
        for (Address address : list) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(address.getId());
            radioButton.setText((address.getAddress()+"\n"+address.getPhone()));

            // Thiết lập margin cho mỗi RadioButton
            RadioGroup.LayoutParams buttonParams = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
            );
            buttonParams.setMargins(0, 0, 0, 20); // margin (left, top, right, bottom)
            radioButton.setLayoutParams(buttonParams);

            group.addView(radioButton);
        }
        super.onResume();
    }
}