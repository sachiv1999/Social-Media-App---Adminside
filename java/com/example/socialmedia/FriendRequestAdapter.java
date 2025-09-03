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

public class FriendRequestAdapter  extends ArrayAdapter<FriendRequests> {
    private Context context;
    private List<FriendRequests> requestList ;
    public FriendRequestAdapter(Context context, List<FriendRequests> requests) {
        super(context, 0, requests);
        this.context = context;
        this.requestList = requests;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        FriendRequests request = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_friend_request, parent, false);
        }

        TextView tvFrom = convertView.findViewById(R.id.tvFrom);
        TextView tvTo = convertView.findViewById(R.id.tvTo);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);
        ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);

        assert request != null;
        tvFrom.setText("From: " + request.from);
        tvTo.setText("To: " + request.to);
        tvStatus.setText("Status: " + request.status);
        
        btnDelete.setOnClickListener(v -> {
            String currentEmail = FirebaseAuth.getInstance().getCurrentUser() != null
                    ? FirebaseAuth.getInstance().getCurrentUser().getEmail() : "";

            if (!"manavpandya42@gmail.com".equals(currentEmail)) {
                Toast.makeText(context, "Only admin can delete friend requests", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(context)
                    .setTitle("Delete Friend Request")
                    .setMessage("Are you sure you want to delete this request?\n\nFrom: " + request.from + "\nTo: " + request.to)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes, Delete", (dialog, which) -> {
                        // Ensure your FriendRequest model has request.requestId set correctly
                        FirebaseFirestore.getInstance()
                                .collection("FriendRequests")
                                .document(FriendRequests.getFrom())
                                .delete()
                                //.get()
                                .addOnSuccessListener(unused -> {
                                    requestList.remove(request);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Friend request deleted", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(context, " Delete failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                                );
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
        });


        return convertView;
    }
}
