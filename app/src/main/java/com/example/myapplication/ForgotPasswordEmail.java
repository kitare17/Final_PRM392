package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.database.UserInfoRepository;
import com.example.myapplication.model.UserInfo;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgotPasswordEmail extends AppCompatActivity {

    private EditText emailInputForgotPassword;
    private UserInfoRepository userRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password_email);

        emailInputForgotPassword = findViewById(R.id.emailInputForgotPassword);
        userRepository = new UserInfoRepository(this);

        findViewById(R.id.confirmMailForgotPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInputForgotPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ForgotPasswordEmail.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                } else {
                    handleForgotPassword(email);
                }
            }
        });
    }

    private void handleForgotPassword(String email) {
        UserInfo user = userRepository.getUserByEmail(email);
        if (user != null) {
            String newPassword = generateRandomPassword();
            userRepository.updatePassword(user.getEmail(), newPassword);
            sendEmailWithNewPassword(email, newPassword);
            Toast.makeText(ForgotPasswordEmail.this, "A new password has been sent to your email", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(ForgotPasswordEmail.this, Login.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(ForgotPasswordEmail.this, "Email not registered", Toast.LENGTH_SHORT).show();
        }
    }

    private String generateRandomPassword() {
        int length = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }

    private void sendEmailWithNewPassword(final String email, final String newPassword) {
        final String username = "trinhquang96vu@gmail.com";
        final String password = "ojczliwiidcoeeje";

        String to = email;
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);// Put your email
                // id and
                // password here
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Your New Password");
            message.setText("Your new password is: " + newPassword);
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}