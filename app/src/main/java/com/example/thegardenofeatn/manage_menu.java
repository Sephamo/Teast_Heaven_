package com.example.thegardenofeatn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class manage_menu extends AppCompatActivity {
    TextView categoryPizza, categoryDrinks, categoryBurger, categoryChicken, categoryBeef, categoryDesert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_menu);
        loadFragment(new PizzaMenuFragment());
        categoryPizza = findViewById(R.id.btnPizza);
        categoryDrinks = findViewById(R.id.btnDrinks);
        categoryChicken = findViewById(R.id.btnChicken);
        categoryDesert = findViewById(R.id.btnDesert);
        categoryBeef = findViewById(R.id.btnBeef);
        categoryBurger = findViewById(R.id.btnBurger);


        categoryPizza.setOnClickListener(v -> loadFragment(new PizzaMenuFragment()));
        categoryBurger.setOnClickListener(v -> loadFragment(new BurgerMenuFragment()));
        categoryDrinks.setOnClickListener(v -> loadFragment(new DrinksMenuFragment()));
        categoryDesert.setOnClickListener(v -> loadFragment(new DessertMenuFragment()));
        categoryChicken.setOnClickListener(v -> loadFragment(new ChickenMenuFragment()));
        categoryBeef.setOnClickListener(v -> loadFragment(new BeefMenuFragment()));

        setupFooterNavigation();

    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
    private void setupFooterNavigation(){
        findViewById(R.id.bottomNavigation).findViewById(R.id.menuFooter).setOnClickListener(v -> {
            // Already on home page, maybe just refresh
        });
        findViewById(R.id.bottomNavigation).findViewById(R.id.reservationsFooter).setOnClickListener(v -> {
            Intent intent = new Intent(manage_menu.this, activity_manage_reservations.class);
            startActivity(intent);
        });
        findViewById(R.id.bottomNavigation).findViewById(R.id.reportsFooter).setOnClickListener(v -> {
            Intent intent = new Intent(manage_menu.this, Track_Sales.class);
            startActivity(intent);
        });
        findViewById(R.id.bottomNavigation).findViewById(R.id.staffFooter).setOnClickListener(v -> {
            Intent intent = new Intent(manage_menu.this, staff_activity.class);
            startActivity(intent);
        });

    }

}