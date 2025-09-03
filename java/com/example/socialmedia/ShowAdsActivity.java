package com.example.socialmedia;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowAdsActivity extends AppCompatActivity {

    private ListView listViewAds;
    private AdvertisementAdapter adsAdapter;
    private List<Advertisement> adList = new ArrayList<>();
    AdvertisementAdapter adapter = new AdvertisementAdapter(this, adList);

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ads);

        listViewAds = findViewById(R.id.listAds);

        adsAdapter = new AdvertisementAdapter(this, adList);
        listViewAds.setAdapter(adsAdapter);

        firestore = FirebaseFirestore.getInstance();

        loadAds();
    }

    private void loadAds() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Ads...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        firestore.collection("Advertisements")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    adList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Advertisement ad = doc.toObject(Advertisement.class);
                        ad.setAdId(doc.getId()); // set ID for delete button
                        adList.add(ad);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }


}
