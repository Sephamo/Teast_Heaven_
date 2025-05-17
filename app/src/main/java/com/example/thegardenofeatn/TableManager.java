package com.example.thegardenofeatn;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.*;

public class TableManager {

    private final Activity activity;
    private final DatabaseReference databaseRef;
    private final String[] tableNumbers;

    public TableManager(Activity activity, DatabaseReference databaseRef, String[] tableNumbers) {
        this.activity = activity;
        this.databaseRef = databaseRef;
        this.tableNumbers = tableNumbers;
    }

    public void initializeTables() {
        for (final String tableNum : tableNumbers) {
            final String tableKey = "table" + tableNum;

            databaseRef.child(tableKey).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        Table newTable = new Table(
                                "Table " + tableNum,
                                "Available",
                                0
                        );
                        databaseRef.child(tableKey).setValue(newTable);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.e("Firebase", "Error checking " + tableKey, error.toException());
                }
            });
        }
    }

    public void setupTableClickListeners() {
        for (int i = 0; i < tableNumbers.length; i++) {
            final String tableNum = tableNumbers[i];
            int layoutId = activity.getResources().getIdentifier(
                    "table" + tableNum + "Layout",
                    "id",
                    activity.getPackageName()
            );

            View layout = activity.findViewById(layoutId);
            if (layout != null) {
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showTableDialog("table" + tableNum);
                    }
                });
            }
        }
    }

    private void showTableDialog(final String tableId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_table, null);
        builder.setView(dialogView);

        final EditText etStatus = dialogView.findViewById(R.id.etStatus);
        final EditText etGuests = dialogView.findViewById(R.id.etGuests);

        builder.setTitle("Edit " + tableId)
                .setPositiveButton("Save", (dialog, id) -> {
                    try {
                        String status = etStatus.getText().toString().trim();
                        String guestsStr = etGuests.getText().toString().trim();

                        if (status.isEmpty() || guestsStr.isEmpty()) {
                            Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        int guests = Integer.parseInt(guestsStr);
                        Table table = new Table(
                                tableId,
                                status,
                                guests
                        );
                        databaseRef.child(tableId).setValue(table);
                    } catch (NumberFormatException e) {
                        Toast.makeText(activity, "Please enter valid number for guests", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null);

        builder.create().show();
    }

    public void setupFirebaseListeners() {
        for (int i = 0; i < tableNumbers.length; i++) {
            final String tableNum = tableNumbers[i];
            final String tableKey = "table" + tableNum;

            databaseRef.child(tableKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Table table = snapshot.getValue(Table.class);
                    if (table != null) {
                        updateTableUI(tableNum, table);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.e("Firebase", "Error loading " + tableKey, error.toException());
                }
            });
        }
    }

    private void updateTableUI(String tableNumber, Table table) {
        try {
            int statusId = activity.getResources().getIdentifier("Table" + tableNumber + "Status", "id", activity.getPackageName());
            int guestsId = activity.getResources().getIdentifier("Table" + tableNumber + "Guests", "id", activity.getPackageName());

            TextView statusView = activity.findViewById(statusId);
            TextView guestsView = activity.findViewById(guestsId);

            if (statusView != null) {
                statusView.setText("Status: " + table.getStatus());
            }
            if (guestsView != null) {
                guestsView.setText("Guests: " + table.getGuests());
            }
        } catch (Exception e) {
            Log.e("UI Update", "Error updating table UI", e);
        }
    }
}

