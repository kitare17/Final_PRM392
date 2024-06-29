package com.example.myapplication;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout.LayoutParams;

import com.example.myapplication.database.AddressRepository;
import com.example.myapplication.model.Address;

import java.util.ArrayList;
import java.util.List;

public class AdressList extends AppCompatActivity {

    private AddressRepository addressRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adress_list);

        RadioGroup group = (RadioGroup) findViewById(R.id.addressListRadio);
        group.setGravity(Gravity.TOP | Gravity.LEFT);

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


        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String selectedOption = radioButton.getText().toString();
                    Toast.makeText(getApplicationContext(), selectedOption, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public List<Address> getListAddress(int userId){
        addressRepository = new AddressRepository(this);
        List<Address> listAddress = addressRepository.listAllAddress(userId);
        return listAddress;
    }

}