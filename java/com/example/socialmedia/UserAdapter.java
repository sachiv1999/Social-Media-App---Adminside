package com.example.socialmedia;


import android.annotation.SuppressLint;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;

import java.util.Collections;
import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private final Context context;
    private final List<User> userList;
    public UserAdapter(Context context, List<User> users) {
        super(context, 0, users);
        this.context = context;
        this.userList = users;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        User user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_user, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvEmail = convertView.findViewById(R.id.tvEmail);
        TextView tvBio = convertView.findViewById(R.id.tvBio);
        ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);

        if (user != null) {
            tvName.setText("Name:" + user.name);
            tvEmail.setText("Email:" + user.email);
            tvBio.setText("Bio:" + user.bio);

            btnDelete.setOnClickListener(v -> {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String currentEmail = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getEmail() : "";

                if (!"manavpandya42@gmail.com".equals(currentEmail)) {
                    Toast.makeText(context, "Only admin can delete users", Toast.LENGTH_SHORT).show();
                    return;
                }

                new AlertDialog.Builder(context)
                        .setTitle("Delete User")
                        .setMessage("Are you sure you want to delete " + user.email + "?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            FirebaseFirestore.getInstance().collection("Users")
                                    .whereEqualTo("email", user.email)
                                    .get()
                                    .addOnSuccessListener(query -> {
                                        for (DocumentSnapshot doc : query) {
                                            doc.getReference().delete();
                                        }
                                        FirebaseFunctions.getInstance()
                                                .getHttpsCallable("deleteAuthUser")
                                                .call(Collections.singletonMap("email", user.email))
                                                .addOnSuccessListener(result -> {
                                                    Toast.makeText(context, "Deleted from Firebase Auth", Toast.LENGTH_SHORT).show();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(context, "Auth delete failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                                });

                                        userList.remove(user);
                                        notifyDataSetChanged();
                                        Toast.makeText(context, "Deleted from Firestore", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(context, "Firestore delete failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        })
                        .setNegativeButton("No", null)
                        .show();
            });
        }

        return convertView;
    }
}
