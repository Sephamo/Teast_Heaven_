package com.example.thegardenofeatn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Manager extends AppCompatActivity {

    private CardView editMenuButton, reservationsButton, trackSalesButton, staffButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        // Initialize buttons
        editMenuButton = findViewById(R.id.editMenuButton);
        reservationsButton = findViewById(R.id.reservationsButton);
        trackSalesButton = findViewById(R.id.trackSalesButton);
        staffButton = findViewById(R.id.staffButton);

        // Set up footer navigation
        setupFooterNavigation();

        // Set click listeners
        editMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manager.this, manage_menu.class);
                startActivity(intent);
            }
        });

        reservationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manager.this, activity_manage_reservations.class);
                startActivity(intent);
            }
        });

        trackSalesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manager.this, Track_Sales.class);
                startActivity(intent);
            }
        });

        staffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manager.this, staff_activity.class);
                startActivity(intent);
            }
        });
    }

    private void setupFooterNavigation() {
        findViewById(R.id.bottomNavigation).findViewById(R.id.homeFooter).setOnClickListener(v -> {
            // Already on home page, maybe just refresh
        });
     findViewById(R.id.bottomNavigation).findViewById(R.id.reservationsFooter).setOnClickListener(v -> {
         Intent intent = new Intent(Manager.this, activity_manage_reservations.class);
         startActivity(intent);
             }
             );
        findViewById(R.id.bottomNavigation).findViewById(R.id.reportsFooter).setOnClickListener(v -> {
            Intent intent = new Intent(Manager.this, Track_Sales.class);
            startActivity(intent);
        });
        findViewById(R.id.bottomNavigation).findViewById(R.id.staffFooter).setOnClickListener(v -> {
            Intent intent = new Intent(Manager.this, staff_activity.class);
            startActivity(intent);
        });
        findViewById(R.id.bottomNavigation).findViewById(R.id.menuFooter).setOnClickListener(v -> {
            Intent intent = new Intent(Manager.this, manage_menu.class);
            startActivity(intent);
        });





    }
}