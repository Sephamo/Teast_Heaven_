package com.example.thegardenofeatn;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OrderManager {
    private static final String TAG = "OrderManager";
    private static final String PREFS_NAME = "OrderPrefs";
    private static final String COUNTER_KEY = "orderCounter";

    private final DatabaseReference ordersRef;
    private final DatabaseReference menuRef;
    private final List<OrderItem> currentOrder;
    private final Context context;
    private final SimpleDateFormat timeFormat;
    private final SimpleDateFormat dateFormat;

    private int tableNumber = 1;
    private String currentOrderId;

    public OrderManager(Context context) {
        this.context = context.getApplicationContext();
        this.ordersRef = FirebaseDatabase.getInstance().getReference("orders");
        this.menuRef = FirebaseDatabase.getInstance().getReference("menu");
        this.currentOrder = new ArrayList<>();
        this.timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void addItemToOrder(String itemCode, String specialInstructions, OrderCallback callback) {
        if (itemCode == null || itemCode.trim().isEmpty()) {
            callback.onFailure("Please enter an item code");
            return;
        }

        menuRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    // 1. Check if item exists in menu
                    boolean itemFound = false;
                    for (DataSnapshot category : dataSnapshot.getChildren()) {
                        if (category.hasChild(itemCode)) {
                            OrderItem menuItem = category.child(itemCode).getValue(OrderItem.class);
                            if (menuItem != null) {
                                // 2. Create new order item
                                OrderItem newItem = new OrderItem();
                                newItem.setCode(menuItem.getCode());
                                newItem.setName(menuItem.getName());
                                newItem.setPrice(menuItem.getPrice());
                                newItem.setSpecialInstructions(specialInstructions);

                                // 3. Add to current order (allow duplicates)
                                synchronized (currentOrder) {
                                    currentOrder.add(newItem);
                                }
                                callback.onSuccess(newItem);
                                itemFound = true;
                                break;
                            }
                        }
                    }
                    if (!itemFound) {
                        callback.onFailure("Item not found: " + itemCode);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error processing item", e);
                    callback.onFailure("Error adding item to order");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                callback.onFailure("Database error: " + error.getMessage());
            }
        });
    }

    public void sendOrderToKitchen(OrderStatusCallback callback) {
        try {
            List<OrderItem> orderCopy;
            synchronized (currentOrder) {
                if (currentOrder.isEmpty()) {
                    callback.onFailure("No items in order");
                    return;
                }
                orderCopy = new ArrayList<>(currentOrder);
            }

            // Get sequential order number (ORD001, ORD002, etc.)
            String orderId = "ORD" + String.format(Locale.US, "%03d", getNextOrderNumber());
            currentOrderId = orderId;

            Map<String, Object> orderData = new HashMap<>();
            orderData.put("items", orderCopy);
            orderData.put("total", calculateOrderTotal(orderCopy));
            orderData.put("items_count", getTotalItemCount(orderCopy));
            orderData.put("table_number", tableNumber);
            orderData.put("status", "pending");
            orderData.put("order_id", orderId);
            orderData.put("timestamp", ServerValue.TIMESTAMP);
            orderData.put("time", timeFormat.format(new Date()));
            orderData.put("date", dateFormat.format(new Date()));

            ordersRef.child(orderId).setValue(orderData)
                    .addOnSuccessListener(aVoid -> {
                        synchronized (currentOrder) {
                            currentOrder.clear();
                        }
                        callback.onSuccess();
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Order submission failed", e);
                        callback.onFailure("Failed to send order. Please try again.");
                    });
        } catch (Exception e) {
            Log.e(TAG, "System error", e);
            callback.onFailure("System error occurred");
        }
    }

    private int getNextOrderNumber() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int counter = prefs.getInt(COUNTER_KEY, 1);
        prefs.edit().putInt(COUNTER_KEY, counter + 1).apply();
        return counter;
    }

    private double calculateOrderTotal(List<OrderItem> items) {
        double total = 0;
        for (OrderItem item : items) {
            if (item != null) {
                total += item.getItemTotal();
            }
        }
        return total;
    }

    private int getTotalItemCount(List<OrderItem> items) {
        int count = 0;
        for (OrderItem item : items) {
            if (item != null) {
                count += item.getQuantity();
            }
        }
        return count;
    }

    public List<OrderItem> getCurrentOrder() {
        synchronized (currentOrder) {
            return new ArrayList<>(currentOrder);
        }
    }

    public String getCurrentOrderId() {
        return currentOrderId;
    }

    public interface OrderCallback {
        void onSuccess(OrderItem item);
        void onFailure(String error);
    }

    public interface OrderStatusCallback {
        void onSuccess();
        void onFailure(String error);
    }
}





