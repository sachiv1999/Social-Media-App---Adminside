package com.example.socialmedia;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        ImageButton menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });

        ImageButton i1 = findViewById(R.id.i1);
        ImageButton i2 = findViewById(R.id.i2);
        ImageButton i3 = findViewById(R.id.i3);
        ImageButton i5 = findViewById(R.id.i5);
       // ImageButton i6 = findViewById(R.id.i6);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AdminPostListActivity.class);
                startActivity(intent);
            }
        });

        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AdminFriendRequestActivity.class);
                startActivity(intent);
            }
        });

        i5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddsActivity.class);
                startActivity(intent);
            }
        });

//        i6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, ShowAdsActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void showPopupMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(HomeActivity.this, anchor);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.i1) {
                    startActivity(new Intent(HomeActivity.this, AdminActivity.class));
                } else if (id == R.id.i2) {
                    startActivity(new Intent(HomeActivity.this, AdminPostListActivity.class));
                } else if (id == R.id.i3) {
                    startActivity(new Intent(HomeActivity.this, AdminFriendRequestActivity.class));
                }
//                else if (id == R.id.i4) {
//                    startActivity(new Intent(HomeActivity.this, FlowersActivity.class));
//                }
                else if (id == R.id.i5) {
                    startActivity(new Intent(HomeActivity.this, AddsActivity.class));
                }
//                else if (id == R.id.i6) {
//                   startActivity(new Intent(HomeActivity.this, ShowAdsActivity.class));
//                }

                return true;
            }
        });

        popupMenu.show();
    }


}