package com.example.socialmedia;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {
    private final Context context;
    private final List<Post> postList;
    public PostAdapter(Context context, List<Post> postList) {
        super(context, 0, postList);
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Post post = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.row_post, parent, false);
        }

        TextView tvUserId = convertView.findViewById(R.id.tvUserId);
        TextView tvPostId = convertView.findViewById(R.id.tvPostId);
        TextView tvContent = convertView.findViewById(R.id.tvContent);
        TextView tvImageDescription = convertView.findViewById(R.id.tvImageDescription);
        TextView tvLikeCount = convertView.findViewById(R.id.tvLikeCount);
        ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);


        tvUserId.setText("User ID: " + post.getUserId());
        tvPostId.setText("Post ID: " + post.getPostId());
        tvContent.setText("Content: " + post.getContent());
        tvImageDescription.setText("Image Desc: " + post.getImageDescription());
        tvLikeCount.setText("Likes: " + post.getLikeCount());

        btnDelete.setOnClickListener(v -> {
            String currentEmail = FirebaseAuth.getInstance().getCurrentUser() != null
                    ? FirebaseAuth.getInstance().getCurrentUser().getEmail() : "";

            if (!"manavpandya42@gmail.com".equals(currentEmail)) {
                Toast.makeText(context, "Only admin can delete posts", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(context)
                    .setTitle("Delete Post")
                    .setMessage("Are you sure you want to delete this post?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        FirebaseFirestore.getInstance()
                                .collection("Posts")
                                .document(post.getPostId())
                                .delete()
                                .addOnSuccessListener(unused -> {
                                    postList.remove(post);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Post deleted", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(context, "Failed to delete: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                                );
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
        return convertView;
    }
}