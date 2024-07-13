package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.database.UserInfoRepository;
import com.example.myapplication.model.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerListActivity extends AppCompatActivity {

    private ListView listViewCustomers;
    private List<String> senderIds;
    private FirebaseAuth mAuth;
    private String adminId = "cxcq3OdM7EQ2aNyhq6RCYlwkh4y1"; // Replace with your admin ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        listViewCustomers = findViewById(R.id.listViewCustomers);
        mAuth = FirebaseAuth.getInstance();

        // Initialize list view
        senderIds = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, senderIds);
        listViewCustomers.setAdapter(adapter);

        // Load sender IDs that sent messages to admin
        loadsenderIds();

        // Handle item click in list view
        listViewCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = senderIds.get(position);
                UserInfoRepository userInfoRepository=new UserInfoRepository(getApplicationContext());
                UserInfo userByIdGoogle =   userInfoRepository.getUserByName(name);
                openChatActivity(userByIdGoogle.getGoogleId());
            }
        });
    }

    private void loadsenderIds() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance("https://my-application-88747-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        DatabaseReference conversationsRef = rootRef.child("messages").child("conversations");

        conversationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                senderIds.clear();
                for (DataSnapshot conversationSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot messageSnapshot : conversationSnapshot.child("messages").getChildren()) {
                        String senderId = messageSnapshot.child("senderId").getValue(String.class);
                        //query
                        UserInfoRepository userInfoRepository=new UserInfoRepository(getApplicationContext());

                        UserInfo userByIdGoogle =   userInfoRepository.getUserByIdGoogle(senderId);
                        if (senderId != null && !senderIds.contains(userByIdGoogle.getFullname()) && !senderId.equals(adminId)) {

                            senderIds.add(userByIdGoogle.getFullname());
                        }
                    }
                }
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) listViewCustomers.getAdapter();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CustomerListActivity.this, "Không thể tải danh sách sender IDs", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void openChatActivity(String customerId) {
        Intent intent = new Intent(CustomerListActivity.this, ChatAdmin.class);
        intent.putExtra("customerId", customerId);
        startActivity(intent);
    }
}
