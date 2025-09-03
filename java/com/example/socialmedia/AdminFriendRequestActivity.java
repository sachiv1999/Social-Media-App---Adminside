package com.example.socialmedia;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdminFriendRequestActivity extends AppCompatActivity {
    private ListView friendRequestListView;
    private List<FriendRequests> requestList;
    private FriendRequestAdapter adapter;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_friend_request);
        friendRequestListView = findViewById(R.id.friendRequestListView);
        requestList = new ArrayList<>();
        adapter = new FriendRequestAdapter(this, requestList);
        friendRequestListView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String adminUID = currentUser.getUid();
            Log.d("AdminUID", "Admin UID: " + adminUID);
            //Toast.makeText(this,adminUID,Toast.LENGTH_SHORT).show();
            fetchFriendRequests();


            // request.auth.uid == "DenZMAhYMrcgLsdm2TElcCELxvh2

        } else {
            Toast.makeText(this, "Access Denied. Admin Only.", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void fetchFriendRequests() {
        db.collection("FriendRequests")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    requestList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        String from = doc.getString("from");
                        String to = doc.getString("to");
                        String status = doc.getString("status");

                        requestList.add(new FriendRequests(from, to, status));
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreError", "Error loading requests", e);
                    Toast.makeText(AdminFriendRequestActivity.this, "Error loading requests: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }
}