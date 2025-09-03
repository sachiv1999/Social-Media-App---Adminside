package com.example.socialmedia;

import android.app.ProgressDialog;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddsActivity extends AppCompatActivity {

    EditText advertiserName, companyName, companyAddress, adName;


    Button uploadBtn;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adds);

        advertiserName = findViewById(R.id.name);
        companyName = findViewById(R.id.companyName);
        companyAddress = findViewById(R.id.companyAddress);
        adName = findViewById(R.id.adName);
        //payment = findViewById(R.id.payment);
        uploadBtn = findViewById(R.id.uploadBtn);


        firestore = FirebaseFirestore.getInstance();

        uploadBtn.setOnClickListener(v -> uploadAdDetails());
    }

    private void uploadAdDetails() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Ad...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String adId = UUID.randomUUID().toString();

        Map<String, Object> adData = new HashMap<>();
        adData.put("advertiserName", advertiserName.getText().toString().trim());
        adData.put("companyName", companyName.getText().toString().trim());
        adData.put("companyAddress", companyAddress.getText().toString().trim());
        adData.put("adName", adName.getText().toString().trim());

//        try {
//            adData.put("payment", Integer.parseInt(payment.getText().toString().trim()));
//        } catch (NumberFormatException e) {
//            progressDialog.dismiss();
//            Toast.makeText(this, "Invalid payment amount", Toast.LENGTH_SHORT).show();
//            return;
//        }


        firestore.collection("Advertisements")
                .document(adId)
                .set(adData)
                .addOnSuccessListener(unused -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Ad uploaded successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // Optionally close the activity
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Firestore upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


}
