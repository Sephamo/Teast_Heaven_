package com.example.thegardenofeatn;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class PizzaMenuFragment extends Fragment {


    private RecyclerView recyclerView;
    private MenuItemAdapter adapter;
    private List<MenuItemModel> menuItemList;
    private String mParam1;
    private String mParam2;
    private Button btnAddnewItem;
    private RecyclerView recyclerMenuItems;

    public PizzaMenuFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu_item, container, false);
        btnAddnewItem = view.findViewById(R.id.btnAddNewItem);
        recyclerMenuItems = view.findViewById(R.id.recyclerMenuItems);
        recyclerView = view.findViewById(R.id.recyclerMenuItems);

        // Initialize the menu item list with some sample data
        menuItemList = new ArrayList<>();
        menuItemList.add(new MenuItemModel("Pesta Pizza", 175.00, "Pizza with mushrooms ", R.drawable.pestopizza));
        menuItemList.add(new MenuItemModel("Mushroom Pizza", 200.00, "Pizza with pepperoni", R.drawable.mushroom));
        menuItemList.add(new MenuItemModel("Barbecue Pizza", 125.00, "Pizza with BBQ sauce  ", R.drawable.beefpizza));


        // Set up the RecyclerView with a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MenuItemAdapter(menuItemList);
        recyclerView.setAdapter(adapter);


        btnAddnewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog();
            }
        });

        return view;
    }
    private void showAddItemDialog() {
        // Show a dialog for adding a new menu item
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Base_Theme_Manager_Dashboard);
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_edit_item, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.etItemName);
        EditText etPrice = dialogView.findViewById(R.id.etItemPrice);
        EditText etDescription = dialogView.findViewById(R.id.etItemDescription);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = etName.getText().toString().trim();
            String price = etPrice.getText().toString().trim();
            String description = etDescription.getText().toString().trim();

            if (name.isEmpty() || price.isEmpty() || description.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a new MenuItemModel and add it to the list
            MenuItemModel newItem = new MenuItemModel(name, Double.parseDouble(price), description, R.drawable.ic_launcher_background);
            menuItemList.add(newItem);

            // Notify the adapter to refresh the RecyclerView
            adapter.notifyItemInserted(menuItemList.size() - 1);

            Toast.makeText(getContext(), "Item added: " + name, Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}