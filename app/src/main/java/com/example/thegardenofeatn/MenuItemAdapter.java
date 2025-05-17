package com.example.thegardenofeatn;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> {
    private List<MenuItemModel> menuItemList;

    public MenuItemAdapter(List<MenuItemModel> menuItemList) {
        this.menuItemList = menuItemList;
    }

    @NonNull
    @Override
    public MenuItemAdapter.MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);

        return new MenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        MenuItemModel menuItem = menuItemList.get(position);

        // Set the menu item data in the respective views
        holder.tvItemName.setText(menuItem.getName());
        holder.tvItemPrice.setText("R" + menuItem.getPrice());
        holder.tvItemDescription.setText(menuItem.getDescription());

        holder.itemImage.setImageResource(menuItem.getImageResId());

        // You can later implement setting the image dynamically from a URL or resource
        // Example placeholder image

        // Set up Edit button
        holder.btnEdit.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.Base_Theme_Manager_Dashboard);
            View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_add_edit_item, null);
            builder.setView(dialogView);

            EditText etName = dialogView.findViewById(R.id.etItemName);
            EditText etPrice = dialogView.findViewById(R.id.etItemPrice);
            EditText etDescription = dialogView.findViewById(R.id.etItemDescription);

            // Pre-fill existing values
            etName.setText(menuItem.getName());
            etPrice.setText(String.valueOf(menuItem.getPrice()));
            etDescription.setText(menuItem.getDescription());

            builder.setPositiveButton("Save", (dialog, which) -> {
                String newName = etName.getText().toString().trim();
                String newPrice = etPrice.getText().toString().trim();
                String newDesc = etDescription.getText().toString().trim();


                if (newName.isEmpty() || newPrice.isEmpty() || newDesc.isEmpty()) {
                    Toast.makeText(v.getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                menuItem.setName(newName);
                menuItem.setPrice(Double.parseDouble(newPrice));
                menuItem.setDescription(newDesc);
                notifyItemChanged(position);
                Toast.makeText(v.getContext(), "Item updated", Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.create().show();
        });

        // Set up Delete button
        holder.btnDelete.setOnClickListener(v -> {
            // Handle delete logic
            menuItemList.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(v.getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return menuItemList.size();
    }

    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemName, tvItemPrice, tvItemDescription;
        ImageView itemImage;
        Button btnEdit, btnDelete;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
            tvItemDescription = itemView.findViewById(R.id.tvItemDescription);
            itemImage = itemView.findViewById(R.id.itemImage);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
