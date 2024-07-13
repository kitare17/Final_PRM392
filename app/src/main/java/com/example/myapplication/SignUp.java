package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.database.UserInfoRepository;
import com.example.myapplication.model.UserInfo;

import org.json.JSONObject;

public class SignUp extends AppCompatActivity {

    private EditText fullNameInput, passwordInput, emailInput;
    private Button btnSignUp;
    private ImageView buttonBack;
    private UserInfoRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        fullNameInput = findViewById(R.id.fullNameInputSignUp);
        passwordInput = findViewById(R.id.passwordInputSignUp);
        emailInput = findViewById(R.id.emailInputSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        buttonBack = findViewById(R.id.buttonBack);

        userRepository = new UserInfoRepository(this);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        String fullName = fullNameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();

        if (fullName.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(SignUp.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userRepository.checkEmailExists(email)) {
            Toast.makeText(SignUp.this, "Email already exists", Toast.LENGTH_SHORT).show();
        } else {
            UserInfo userInfo = new UserInfo();
            userInfo.setFullname(fullName);
            userInfo.setEmail(email);
            userInfo.setPassword(password);
            userInfo.setAvatar("https://cellphones.com.vn/sforum/wp-content/uploads/2023/10/avatar-trang-4.jpg");

            userRepository.registerClassic(userInfo);
            finish();
        }
    }
}