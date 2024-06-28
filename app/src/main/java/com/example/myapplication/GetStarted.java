package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.database.UserInfoRepository;
import com.example.myapplication.model.UserInfo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class GetStarted extends AppCompatActivity {
    private AppCompatTextView btnLogin;

    private FirebaseAuth auth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_started);
        auth = FirebaseAuth.getInstance();
        btnLogin = findViewById(R.id.googleButton);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient= GoogleSignIn.getClient(this, gso);
        btnLogin.setOnClickListener(v -> {
            googleSingIn();
        });


    }
    private void googleSingIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 20);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== 20) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    auth.signInWithCredential(credential).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(this, "User Signed In", Toast.LENGTH_SHORT).show();
                            firebaseAuthWithGoogle(account.getIdToken());
                        } else {
                            Toast.makeText(this, "Error" + task1.getException(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = auth.getCurrentUser();

                        String fullname= user.getDisplayName();
                        String email= user.getEmail();
                        String avatar= user.getPhotoUrl().toString();
                        String googleId=user.getUid();


                        UserInfo userInfo=new UserInfo(fullname,email,avatar,0,googleId);
                        UserInfoRepository userInfoRepository=new UserInfoRepository(getApplicationContext());
                        boolean check=userInfoRepository.checkExistIdGoogle(googleId);
                        if (!check){
                            Toast.makeText(this, "Not found "+googleId, Toast.LENGTH_SHORT).show();
                            userInfoRepository.register(userInfo);
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("fullname", fullname);
                            editor.putString("email", email);
                            editor.putString("avatar", avatar);
                            editor.putInt("role", 0);
                            editor.putString("googleId", googleId);
                            editor.apply();
                        }
                        else {

                            Toast.makeText(this, "Welcome!!!", Toast.LENGTH_SHORT).show();
                            UserInfo userByIdGoogle =   userInfoRepository.getUserByIdGoogle(googleId);
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("fullname", userByIdGoogle.getFullname());
                            editor.putString("email", userByIdGoogle.getEmail());
                            editor.putString("avatar", userByIdGoogle.getAvatar());
                            editor.putInt("role", 0);
                            editor.putString("googleId", googleId);
                            editor.apply();
                        }


                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}