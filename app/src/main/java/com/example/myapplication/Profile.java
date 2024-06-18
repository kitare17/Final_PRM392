package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.myapplication.database.UserInfoRepository;


public class Profile extends Fragment {

    AppCompatButton saveBtn;
    EditText fullnameEditText;



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        saveBtn = (AppCompatButton) view.findViewById(R.id.btnSaveProfile);
        fullnameEditText = (EditText) view.findViewById(R.id.fullnameEdit);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String googleId = sharedPreferences.getString("googleId", "googleId");

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Profile updated", Toast.LENGTH_SHORT).show();
                UserInfoRepository userInfoRepository = new UserInfoRepository(getContext());
                String fullname = fullnameEditText.getText().toString();
                userInfoRepository.updateProfile(googleId, fullname);
                sharedPreferences.edit().putString("fullname", fullname).apply();


            }
        });


    }


}