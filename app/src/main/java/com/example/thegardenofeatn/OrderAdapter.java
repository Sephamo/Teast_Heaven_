package com.example.thegardenofeatn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context context;
    private List<orderModel> orderList;

    public OrderAdapter(Context context, List<orderModel> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_card, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        orderModel order = orderList.get(position);

        // Set basic info
        holder.tvTableOrder.setText("Table " +
                (order.getTable_number() != null ? order.getTable_number() : "N/A") +
                " - Order #" +
                (order.getOrder_id() != null ? order.getOrder_id() : "N/A"));

        holder.tvTimeOrdered.setText("Time: " +
                (order.getTime() != null ? order.getTime() : "N/A"));

        // Set items
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            StringBuilder itemsText = new StringBuilder();
            for (orderModel.Item item : order.getItems()) {
                itemsText.append(item.getQuantity() != null ? item.getQuantity() : "0")
                        .append("x ")
                        .append(item.getName() != null ? item.getName() : "Unknown Item")
                        .append("\n");
            }
            holder.tvItemsOrdered.setText(itemsText.toString());
        } else {
            holder.tvItemsOrdered.setText("No items in order");
        }

        // Set notes if available
        if (order.getNotes() != null && !order.getNotes().isEmpty()) {
            holder.tvNotes.setText("Note: " + order.getNotes());
            holder.tvNotes.setVisibility(View.VISIBLE);
        } else {
            holder.tvNotes.setVisibility(View.GONE);
        }

        // Status spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                context, R.array.order_status_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinnerStatus.setAdapter(spinnerAdapter);

        // Set current status
        if (order.getStatus() != null) {
            int pos = spinnerAdapter.getPosition(order.getStatus());
            holder.spinnerStatus.setSelection(pos >= 0 ? pos : 0);
        }

        // Update button
        holder.btnUpdate.setOnClickListener(v -> {
            String newStatus = holder.spinnerStatus.getSelectedItem().toString();
            order.setStatus(newStatus);
            // TODO: Update status in Firebase
            Toast.makeText(context, "Status updated", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvTableOrder, tvTimeOrdered, tvItemsOrdered, tvNotes;
        Spinner spinnerStatus;
        Button btnUpdate;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTableOrder = itemView.findViewById(R.id.tvTableOrder);
            tvTimeOrdered = itemView.findViewById(R.id.tvTimeOrdered);
            tvItemsOrdered = itemView.findViewById(R.id.tvItems);
            tvNotes = itemView.findViewById(R.id.tvNotes);
            spinnerStatus = itemView.findViewById(R.id.spinnerStatus);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }
}

