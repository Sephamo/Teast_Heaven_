package com.example.thegardenofeatn;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Order_Status_Update extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_status_update);

        // Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.oder_status_update);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.order_placement) {
                    startActivity(new Intent(Order_Status_Update.this, Order_Activity.class));
                    return true;
                } else if (itemId == R.id.active_orders) {
                    startActivity(new Intent(Order_Status_Update.this, Order_Status.class));
                    return true;
                } else if (itemId == R.id.tables) {
                    startActivity(new Intent(Order_Status_Update.this, Waiter.class));
                    return true;
                }

                return false;
            }
        });

    }
}