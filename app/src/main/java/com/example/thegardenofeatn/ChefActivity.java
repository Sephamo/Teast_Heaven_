package com.example.thegardenofeatn;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChefActivity extends AppCompatActivity {
    private RecyclerView recyclerOrders;
    private OrderAdapter adapter;
    private List<orderModel> orderList;
    private TextView tvEmptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);

        // Initialize UI
        recyclerOrders = findViewById(R.id.recyclerOrders);
        tvEmptyState = findViewById(R.id.EmptyState); // Make sure this ID exists in your XML
        recyclerOrders.setLayoutManager(new LinearLayoutManager(this));

        orderList = new ArrayList<>();
        adapter = new OrderAdapter(this, orderList);
        recyclerOrders.setAdapter(adapter);

        // Verify Firebase connection
        checkFirebaseConnection();

        fetchOrdersFromFirebase();
    }

    private void checkFirebaseConnection() {
        FirebaseDatabase.getInstance().getReference(".info/connected")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Boolean connected = snapshot.getValue(Boolean.class);
                        Log.d("FIREBASE_STATUS", "Connected: " + connected);
                    }
                    @Override public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("FIREBASE_STATUS", "Connection check failed", error.toException());
                    }
                });
    }

    private void fetchOrdersFromFirebase() {
        Log.d("FIREBASE_PATH", "Checking path: " +
                FirebaseDatabase.getInstance().getReference("orders").toString());
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();
                Log.d("FIREBASE_DATA", "DataSnapshot: " + snapshot.toString());

                if (snapshot.exists()) {
                    for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                        try {
                            orderModel order = orderSnapshot.getValue(orderModel.class);
                            if (order != null) {
                                // Ensure critical fields exist
                                if (order.getOrder_id() == null) {
                                    order.setOrder_id(orderSnapshot.getKey());
                                }
                                if (order.getStatus() == null) {
                                    order.setStatus("pending");
                                }
                                orderList.add(order);
                                Log.d("ORDER_LOADED", "Added order: " + order.getOrder_id());
                            }
                        } catch (Exception e) {
                            Log.e("PARSE_ERROR", "Error parsing order", e);
                        }
                    }

                    if (orderList.isEmpty()) {
                        showEmptyState();
                    } else {
                        runOnUiThread(() -> {
                            // Sort by status
                            Collections.sort(orderList, (o1, o2) -> {
                                List<String> statusOrder = Arrays.asList("pending", "preparing", "ready");
                                return Integer.compare(
                                        statusOrder.indexOf(o1.getStatus().toLowerCase()),
                                        statusOrder.indexOf(o2.getStatus().toLowerCase())
                                );
                            });

                            adapter.notifyDataSetChanged();
                            recyclerOrders.setVisibility(View.VISIBLE);
                            tvEmptyState.setVisibility(View.GONE);
                        });
                    }
                } else {
                    showEmptyState();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FIREBASE_ERROR", "Load cancelled: " + error.getMessage());
                showEmptyState();
            }
        });
    }

    private void showEmptyState() {
        runOnUiThread(() -> {
            recyclerOrders.setVisibility(View.GONE);
            tvEmptyState.setVisibility(View.VISIBLE);
            tvEmptyState.setText("No active orders"); // Ensure this matches your XML TextView ID
        });
    }
}