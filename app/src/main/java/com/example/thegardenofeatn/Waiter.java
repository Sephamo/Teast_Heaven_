package com.example.thegardenofeatn;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Waiter extends AppCompatActivity {

    private TableManager tableManager;
    private final String[] tableNumbers = {"1", "2", "3", "4", "5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_waiter);


        // Init Firebase and TableManager
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("tables");
        tableManager = new TableManager(this, databaseRef, tableNumbers);

        // Delegate logic to manager
        tableManager.initializeTables();
        tableManager.setupTableClickListeners();
        tableManager.setupFirebaseListeners();

        // Bottom navigation
        setupBottomNavigation();

    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.tables);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.order_placement) {
                    startActivity(new Intent(Waiter.this, Order_Activity.class));
                    return true;
                } else if (itemId == R.id.active_orders) {
                    startActivity(new Intent(Waiter.this, Order_Status.class));
                    return true;
                } else if (itemId == R.id.oder_status_update) {
                    startActivity(new Intent(Waiter.this, Order_Status_Update.class));
                    return true;
                }

                return false;
            }
        });
    }
}