package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.database.UserInfoRepository;
import com.example.myapplication.model.UserInfo;

public class Login extends AppCompatActivity {

    private EditText emailInputLogin, passwordInputLogin;
    private Button buttonLogin;
    private UserInfoRepository userInfoRepository;
    private TextView fogotPassTextLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        emailInputLogin = findViewById(R.id.emailInputLogin);
        passwordInputLogin = findViewById(R.id.passwordInputLogin);
        buttonLogin = findViewById(R.id.button2);
        fogotPassTextLogin = findViewById(R.id.fogotPassTextLogin);

        userInfoRepository = new UserInfoRepository(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInputLogin.getText().toString().trim();
                String password = passwordInputLogin.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userInfoRepository.login(email, password)) {

                    Toast.makeText(Login.this, "Welcome!!!", Toast.LENGTH_SHORT).show();
                    UserInfo userByIdGoogle =   userInfoRepository.getUserByEmail(email);
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("fullname", userByIdGoogle.getFullname());
                    editor.putString("email", userByIdGoogle.getEmail());
                    editor.putString("avatar", userByIdGoogle.getAvatar());
                    editor.putInt("role", 0);
                    editor.putString("googleId", " ");
                    editor.apply();

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fogotPassTextLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotPasswordEmail.class);
                startActivity(intent);
            }
        });
    }
}