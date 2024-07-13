package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.adapter.MessageAdapter;
import com.example.myapplication.model.Message;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText editTextMessage;
    private Button buttonSend;
    private MessageAdapter adapter;
    private List<Message> messageList = new ArrayList<>();
    private DatabaseReference messagesRef;
    private FirebaseAuth mAuth;
    private String adminId = "cxcq3OdM7EQ2aNyhq6RCYlwkh4y1"; // ID của admin
    private String customerId; // ID của khách hàng
    private Button btnLogout;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recyclerView);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN);
        btnLogout = findViewById(R.id.buttonLogout);
        btnLogout.setOnClickListener(v -> signOut());

//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        customerId = sharedPreferences.getString("googleId", "1");
//        Toast.makeText(this, customerId, Toast.LENGTH_SHORT).show();
        customerId = getIntent().getStringExtra("customerId"); // Lấy ID của khách hàng từ Intent
        Toast.makeText(this, customerId, Toast.LENGTH_SHORT).show();
        // Xác định conversationId để đảm bảo mỗi cuộc trò chuyện là duy nhất
        String conversationId = getConversationId(customerId, adminId);
        messagesRef = FirebaseDatabase.getInstance("https://my-application-88747-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("messages").child("conversations").child(conversationId).child("messages");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(adapter);

        loadMessages();

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = editTextMessage.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    sendMessage(messageText);
                }
            }
        });
    }

    private void sendMessage(String messageText) {
        String senderId = mAuth.getCurrentUser().getUid();
        long timestamp = System.currentTimeMillis();

        Message message = new Message(senderId, adminId, messageText, timestamp);

        // Lưu tin nhắn vào Firebase Realtime Database
        messagesRef.push().setValue(message)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Gửi tin nhắn thành công
                        Toast.makeText(ChatActivity.this, "Gửi tin nhắn thành công" + senderId, Toast.LENGTH_SHORT).show();
                        editTextMessage.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Gửi tin nhắn thất bại
                        Toast.makeText(ChatActivity.this, "Gửi tin nhắn thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadMessages() {
        messagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    messageList.add(message);
                }
                adapter.notifyDataSetChanged();
                // Cuộn xuống tin nhắn mới nhất
                recyclerView.scrollToPosition(messageList.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatActivity.this, "Không thể tải tin nhắn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getConversationId(String customerId, String adminId) {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Sort IDs to ensure consistent conversationId format
        List<String> userIdList = new ArrayList<>();
        userIdList.add(currentUserId);
        userIdList.add(adminId);
        Collections.sort(userIdList);

        // Ensure the customer ID is set to currentUserId if null
        if (customerId == null) {
            customerId = currentUserId;
        }

        return userIdList.get(0) + "_" + userIdList.get(1);
    }


    private void signOut() {
//        mAuth.signOut();
//        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
//            Toast.makeText(ChatActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
//
//            // Xóa thông tin người dùng đã lưu trữ trong SharedPreferences
//            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.remove("fullname");
//            editor.remove("email");
//            editor.remove("avatar");
//            editor.remove("role");
//            editor.remove("googleId");
//            editor.apply();
//
//            // Chuyển về màn hình đăng nhập
//            Intent intent = new Intent(ChatActivity.this, LoginGoogle.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();
//        });
    }
}
