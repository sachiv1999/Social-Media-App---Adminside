package com.example.socialmedia;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminPostListActivity extends AppCompatActivity {
    private ListView listView;
    private PostAdapter adapter;
    private List<Post> postList;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_post_list);
        listView = findViewById(R.id.listViewPosts);
        postList = new ArrayList<>();
        adapter = new PostAdapter(this, postList);
        listView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String adminUID = currentUser.getUid();
            Log.d("AdminUID", "Admin UID: " + adminUID);
            //Toast.makeText(this,adminUID,Toast.LENGTH_SHORT).show();
            fetchPosts();

            // request.auth.uid == "DenZMAhYMrcgLsdm2TElcCELxvh2

        } else {
            Toast.makeText(this, "Access Denied. Admin Only.", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

//    private void fetchPosts() {
//        db.collection("Posts")
//                .get()
//                .addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                postList.clear();
//                for (QueryDocumentSnapshot doc : task.getResult()) {
//                    Post post = doc.toObject(Post.class);
//                    postList.add(post);
//                }
//                adapter.notifyDataSetChanged();
//            } else {
//                Log.e("AdminPostList", "Error getting posts", task.getException());
//            }
//        });
//    }

    private void fetchPosts() {
        db.collection("Posts")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    postList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        String postId = doc.getId(); // Assuming postId is the document ID
                        String userId = doc.getString("userId");
                        String content = doc.getString("content");
                        String imageDescription = doc.getString("imageDescription");
                        List<String> likedBy = (List<String>) doc.get("likedBy");
                        long likeCount = doc.contains("likeCount") ? doc.getLong("likeCount") : 0;

                        Post post = new Post(postId, userId, content, imageDescription, likedBy, likeCount);
                        postList.add(post);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to fetch posts: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

}