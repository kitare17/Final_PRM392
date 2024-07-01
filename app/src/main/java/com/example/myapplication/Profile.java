package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.database.UserInfoRepository;
import com.example.myapplication.model.UserInfo;
import com.example.myapplication.view_model.UserViewModel;
import com.google.android.material.navigation.NavigationView;


public class Profile extends Fragment {

    AppCompatButton saveBtn;
    EditText fullnameEditText;

    UserViewModel userViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        saveBtn = (AppCompatButton) view.findViewById(R.id.btnSaveProfile);
        fullnameEditText = (EditText) view.findViewById(R.id.fullnameEdit);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String googleId = sharedPreferences.getString("googleId", "googleId");
        String fullname = sharedPreferences.getString("fullname", "fullname");
        fullnameEditText.setText(fullname);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoRepository userInfoRepository = new UserInfoRepository(getContext());
                String fullname = fullnameEditText.getText().toString();
                userInfoRepository.updateProfile(googleId, fullname);
                UserInfo userInfo = new UserInfo();
                userInfo.setFullname(fullname);
                userViewModel.setUserInfo(userInfo);
                sharedPreferences.edit().putString("fullname", fullname).apply();
           Toast.makeText(getContext(),"Your profile is saved",Toast.LENGTH_SHORT).show();

            }
        });


    }


}