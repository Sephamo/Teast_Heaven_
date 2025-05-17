package com.example.thegardenofeatn;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Order_Activity extends AppCompatActivity {
    private OrderManager orderManager;
    private EditText editTableNumber, editItemCode, editSpecialNotes;
    private TextView txtOrderItems, txtTotal;
    private int currentTableNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Initialize views
        editTableNumber = findViewById(R.id.txtTableNumber);
        editItemCode = findViewById(R.id.txtSelectedItems);
        editSpecialNotes = findViewById(R.id.txtNotes);
        txtOrderItems = findViewById(R.id.txtSelectedItems);
        txtTotal = findViewById(R.id.txtTotal);
        Button btnAddToOrder = findViewById(R.id.btnAddToOrder);
        Button btnSendToKitchen = findViewById(R.id.btnSendToKitchen);

        // Initialize OrderManager
        orderManager = new OrderManager(getApplicationContext());

        // Setup listeners
        btnAddToOrder.setOnClickListener(v -> addItemToOrder());
        btnSendToKitchen.setOnClickListener(v -> sendOrderToKitchen());

        setupNavigation();
    }

    private void addItemToOrder() {
        try {
            // Update table number from input
            String tableText = editTableNumber.getText().toString().trim();
            if (!tableText.isEmpty()) {
                currentTableNumber = Integer.parseInt(tableText);
                orderManager.setTableNumber(currentTableNumber);
            }

            String itemCode = editItemCode.getText().toString().trim();
            String specialNotes = editSpecialNotes.getText().toString().trim();

            if (itemCode.isEmpty()) {
                Toast.makeText(this, "Please enter item code", Toast.LENGTH_SHORT).show();
                return;
            }

            orderManager.addItemToOrder(itemCode, specialNotes, new OrderManager.OrderCallback() {
                @Override
                public void onSuccess(OrderItem item) {
                    runOnUiThread(() -> {
                        updateOrderUI();
                        editItemCode.setText("");
                        editItemCode.requestFocus();
                    });
                }

                @Override
                public void onFailure(String error) {
                    runOnUiThread(() -> {
                        Toast.makeText(Order_Activity.this, error, Toast.LENGTH_LONG).show();
                        editItemCode.requestFocus();
                    });
                }
            });
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid table number", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error adding item", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateOrderUI() {
        StringBuilder sb = new StringBuilder();
        double total = 0;

        for (OrderItem item : orderManager.getCurrentOrder()) {
            sb.append(item.getName())
                    .append(" x").append(item.getQuantity())
                    .append(" - R").append(String.format("%.2f", item.getItemTotal()));

            if (item.getSpecialInstructions() != null && !item.getSpecialInstructions().isEmpty()) {
                sb.append("\n(").append(item.getSpecialInstructions()).append(")");
            }
            sb.append("\n\n");
            total += item.getItemTotal();
        }

        txtOrderItems.setText(sb.toString());
        txtTotal.setText(String.format("Total: R%.2f", total));
        editTableNumber.setText(String.valueOf(currentTableNumber));
    }

    private void sendOrderToKitchen() {
        orderManager.sendOrderToKitchen(new OrderManager.OrderStatusCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    Toast.makeText(Order_Activity.this,
                            "Order " + orderManager.getCurrentOrderId() + " sent!",
                            Toast.LENGTH_SHORT).show();
                    resetUI();
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() ->
                        Toast.makeText(Order_Activity.this, error, Toast.LENGTH_LONG).show()
                );
            }
        });
    }

    private void resetUI() {
        txtOrderItems.setText("");
        txtTotal.setText("Total: R0.00");
        editItemCode.setText("");
        editSpecialNotes.setText("");
        // Keep table number
    }

    private void setupNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setSelectedItemId(R.id.order_placement);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.tables) {
                startActivity(new Intent(this, Waiter.class));
            } else if (itemId == R.id.active_orders) {
                startActivity(new Intent(this, Order_Status.class));
            } else if (itemId == R.id.oder_status_update) {
                startActivity(new Intent(this, Order_Status_Update.class));
            }
            overridePendingTransition(0, 0);
            return true;
        });
    }
}






