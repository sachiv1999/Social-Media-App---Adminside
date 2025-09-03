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

public class AdvertisementAdapter extends ArrayAdapter<Advertisement> {
    private final Context context;
    private final List<Advertisement> adList;

    public AdvertisementAdapter(Context context, List<Advertisement> adList) {
        super(context, 0, adList);
        this.context = context;
        this.adList = adList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Advertisement ad = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_ad, parent, false);
        }

        TextView tvAdvertiserName = convertView.findViewById(R.id.tvAdvertiserName);
        TextView tvCompanyName = convertView.findViewById(R.id.tvCompanyname);
        TextView tvCompanyAddress = convertView.findViewById(R.id.tvCompanyadd);
        TextView tvAdName = convertView.findViewById(R.id.tvAdName);
        //TextView tvPayment = convertView.findViewById(R.id.tvPayment);
        ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);

        if (ad != null) {
            tvAdvertiserName.setText("Advertiser: " + ad.getAdvertiserName());
            tvCompanyName.setText("Company: " + ad.getCompanyName());
            tvCompanyAddress.setText("Address: " + ad.getCompanyAddress());
            tvAdName.setText("Ad: " + ad.getAdName());
            //tvPayment.setText("Payment: $" + ad.getPayment());
        }

        btnDelete.setOnClickListener(v -> {
            String currentEmail = FirebaseAuth.getInstance().getCurrentUser() != null
                    ? FirebaseAuth.getInstance().getCurrentUser().getEmail() : "";

            if (!"manavpandya42@gmail.com".equals(currentEmail)) {
                Toast.makeText(context, "Only admin can delete ads", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(context)
                    .setTitle("Delete Advertisement")
                    .setMessage("Are you sure you want to delete this ad?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        FirebaseFirestore.getInstance()
                                .collection("Advertisements")
                                .document(ad.getAdName())
                                .delete()
                                .addOnSuccessListener(unused -> {
                                    adList.remove(ad);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Ad deleted", Toast.LENGTH_SHORT).show();
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
