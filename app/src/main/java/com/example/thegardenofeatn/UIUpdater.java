package com.example.thegardenofeatn;

import android.widget.EditText;
import android.widget.TextView;
import java.util.List;
import java.util.Locale;

public class UIUpdater {
    private final EditText editItemCode;
    private final EditText editSpecialNotes;
    private final TextView txtOrderItems;
    private final TextView txtTotal;
    private final TextView txtTableNumber;

    public UIUpdater(Order_Activity activity) {
        // Initialize views with correct IDs from your XML
        editItemCode = activity.findViewById(R.id.txtSelectedItems);
        editSpecialNotes = activity.findViewById(R.id.txtNotes);
        txtOrderItems = activity.findViewById(R.id.txtSelectedItems);
        txtTotal = activity.findViewById(R.id.txtTotal);
        txtTableNumber = activity.findViewById(R.id.txtTableNumber);
    }

    public String getItemCodeInput() {
        return editItemCode.getText().toString().trim();
    }

    public String getSpecialInstructionsInput() {
        return editSpecialNotes.getText().toString().trim();
    }

    public void updateOrderUI(List<OrderItem> items, double total, int itemCount, int tableNumber) {
        txtOrderItems.setText(formatItems(items));
        txtTotal.setText(String.format(Locale.getDefault(), "Total: R%.2f", total));
        txtTableNumber.setText(String.valueOf(tableNumber));
    }

    private String formatItems(List<OrderItem> items) {
        StringBuilder sb = new StringBuilder();
        for (OrderItem item : items) {
            sb.append(item.getName())
                    .append(" x").append(item.getQuantity())
                    .append(" - R").append(String.format(Locale.getDefault(), "%.2f", item.getItemTotal()))
                    .append("\n");

            if (item.getSpecialInstructions() != null && !item.getSpecialInstructions().isEmpty()) {
                sb.append("(").append(item.getSpecialInstructions()).append(")\n\n");
            }
        }
        return sb.toString();
    }

    public void clearInputFields() {
        editItemCode.setText("");
        editSpecialNotes.setText("");
    }
}

