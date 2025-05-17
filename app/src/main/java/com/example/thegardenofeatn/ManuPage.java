package com.example.thegardenofeatn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.thegardenofeatn.MenuAdapter;
import com.example.thegardenofeatn.Menu_Item;
import com.example.thegardenofeatn.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ManuPage extends AppCompatActivity {
    private GridView gridPizza;
    private GridView gridPizza2;

    private GridView gridBurgers;
    private GridView gridBurger2;

    private GridView gridBeef;
    private GridView gridBeef2;

    private GridView gridChicken;
    private GridView gridChicken2;

    private GridView gridDesert;
    private GridView gridDesert2;


    private GridView gridDrinks;
    private GridView gridDrinks2;

    private MenuAdapter menuAdapter;
    private List<Menu_Item> pizzaList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_page);

        // Pizza1 starts here//
        gridPizza = findViewById(R.id.gridPizza);

        pizzaList = new ArrayList<>();
        pizzaList.add(new Menu_Item(R.drawable.beefpizza, "R89.00", "Beef Pizza"));
        pizzaList.add(new Menu_Item(R.drawable.chicken_pizza, "R90.00", "Chicken Pizza"));
        pizzaList.add(new Menu_Item(R.drawable.mushroom, "R120.00", "Mushroom Pizza"));

        MenuAdapter menuAdapter = new MenuAdapter(this, pizzaList);
        gridPizza.setAdapter(menuAdapter);

        //pizza2
        gridPizza2 = findViewById(R.id.gridPizz2);

        List<Menu_Item> pizzaList2 = new ArrayList<>();
        pizzaList2.add(new Menu_Item(R.drawable.pestopizza, "R100.00", "Pesto Pizza"));
        pizzaList2.add(new Menu_Item(R.drawable.paperoni, "R130.00", "Pepperoni"));
        pizzaList2.add(new Menu_Item(R.drawable.magherita, "R90.00", "Magherita"));

        MenuAdapter menuAdapter2 = new MenuAdapter(this, pizzaList2);
        gridPizza2.setAdapter(menuAdapter2);




// Burgers
        gridBurgers = findViewById(R.id.gridBurgers);

        List<Menu_Item> burgerList = new ArrayList<>();
        burgerList.add(new Menu_Item(R.drawable.chickenburger, "R75", "Chicken Burger"));
        burgerList.add(new Menu_Item(R.drawable.chillyburger, "R55", "Chilli Burger"));
        burgerList.add(new Menu_Item(R.drawable.doublecheesy, "R81", "Double Cheese Burger"));

        MenuAdapter burgerAdapter = new MenuAdapter(this, burgerList);
        gridBurgers.setAdapter(burgerAdapter);

        //burger2

        gridBurger2 = findViewById(R.id.gridBurger2);
        List<Menu_Item> burgerList2 = new ArrayList<>();
        burgerList2.add(new Menu_Item(R.drawable.doublepatty, "R45", "Double Patty Burger"));
        burgerList2.add(new Menu_Item(R.drawable.groundbeef, "R25", "Mince Burger"));
        burgerList2.add(new Menu_Item(R.drawable.ribburger, "R97", "Rib Burger"));

        MenuAdapter burgerAdapter2 = new MenuAdapter(this, burgerList2);
        gridBurger2.setAdapter(burgerAdapter2);

// Beef
        gridBeef = findViewById(R.id.gridBeef);

        List<Menu_Item> beefList = new ArrayList<>();
        beefList.add(new Menu_Item(R.drawable.biltong, "R110", "Biltong Beef"));
        beefList.add(new Menu_Item(R.drawable.mince, "R120", "Mince Beef"));
        beefList.add(new Menu_Item(R.drawable.ribs, "R100", "Ribs Beef"));

        MenuAdapter beefAdapter = new MenuAdapter(this, beefList);
        gridBeef.setAdapter(beefAdapter);

    //Beef 2

        gridBeef2 = findViewById(R.id.gridBeef2);
        List<Menu_Item> beefList2 = new ArrayList<>();
        beefList2.add(new Menu_Item(R.drawable.stake, "R90", "Steak Beef"));
        beefList2.add(new Menu_Item(R.drawable.beefstew, "R150", "Stew Beef"));
        beefList2.add(new Menu_Item(R.drawable.wors, "R210", "Wors Beef"));

        MenuAdapter beefAdapter2 = new MenuAdapter(this, beefList2);
        gridBeef2.setAdapter(beefAdapter2);


// Chicken
        gridChicken = findViewById(R.id.gridChicken);

        List<Menu_Item> chickenList = new ArrayList<>();
        chickenList.add(new Menu_Item(R.drawable.wings, "R200", "Wings Chicken"));
        chickenList.add(new Menu_Item(R.drawable.mealchicken, "R120", "Meal Chicken"));
        chickenList.add(new Menu_Item(R.drawable.fullchicken, "R190", "Full Chicken"));

        MenuAdapter chickenAdapter = new MenuAdapter(this, chickenList);
        gridChicken.setAdapter(chickenAdapter);

//Chicken 2

        gridChicken2 = findViewById(R.id.gridChicken2);
        List<Menu_Item> chickenList2 = new ArrayList<>();
        chickenList2.add(new Menu_Item(R.drawable.deepfried, "R170", "Fried Chicken"));
        chickenList2.add(new Menu_Item(R.drawable.bakedchicken, "R290", "Cooked Chicken"));
        chickenList2.add(new Menu_Item(R.drawable.chickenwrap, "R280", "Chicken Salad"));

        MenuAdapter chickenAdapter2 = new MenuAdapter(this, chickenList2);
        gridChicken2.setAdapter(chickenAdapter2);


// Dessert
        gridDesert = findViewById(R.id.gridDesert);

        List<Menu_Item> desertList = new ArrayList<>();
        desertList.add(new Menu_Item(R.drawable.bananacake, "R100", "Banana Dessert"));
        desertList.add(new Menu_Item(R.drawable.icecream, "R120", "Ice Cream Dessert"));
        desertList.add(new Menu_Item(R.drawable.milkshake, "R190", "Milk Shake"));

        MenuAdapter desertAdapter = new MenuAdapter(this, desertList);
        gridDesert.setAdapter(desertAdapter);

//Desert2

       gridDesert2 = findViewById(R.id.gridDesert2);

       List<Menu_Item> desertList2 = new ArrayList<>();
        desertList2.add(new Menu_Item(R.drawable.oreo_shake, "R170", "Oreo Dessert"));
        desertList2.add(new Menu_Item(R.drawable.crapes, "R290", "Raisin Dessert"));
        desertList2.add(new Menu_Item(R.drawable.tiramisu, "R280", "tiramisu"));

        MenuAdapter desertAdapter2 = new MenuAdapter(this, desertList2);
        gridDesert2.setAdapter(desertAdapter2);


// Drinks
        gridDrinks = findViewById(R.id.gridDrinks);

        List<Menu_Item> drinkList = new ArrayList<>();
        drinkList.add(new Menu_Item(R.drawable.coke, "R25", "Coke"));
        drinkList.add(new Menu_Item(R.drawable.shots, "R32", "Fire Cocktail"));
        drinkList.add(new Menu_Item(R.drawable.fruit_juice, "R10", "Juice"));

        MenuAdapter drinkAdapter = new MenuAdapter(this, drinkList);
        gridDrinks.setAdapter(drinkAdapter);

//drink2
        gridDrinks2 = findViewById(R.id.gridDrinks2);
        List<Menu_Item> drinkList2 = new ArrayList<>();

        drinkList2.add(new Menu_Item(R.drawable.mojito, "R70", "majito"));
        drinkList2.add(new Menu_Item(R.drawable.cocktail, "R90", "Red Cocktail"));
        drinkList2.add(new Menu_Item(R.drawable.strawberry, "R90", "Stawberry"));

        MenuAdapter drinkAdapter2 = new MenuAdapter(this, drinkList2);
        gridDrinks2.setAdapter(drinkAdapter2);

// Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(ManuPage.this, CustomerActivity.class));
                    return true;
                } else if (itemId == R.id.nav_reservation) {
                    startActivity(new Intent(ManuPage.this, ReservationActivity.class));
                    return true;
                } else if (itemId == R.id.nav_review) {
                    startActivity(new Intent(ManuPage.this, Feedback.class));
                    return true;
                }

                return false;
            }
        });


        // category landing page

        String category = getIntent().getStringExtra("category");
        if (category != null) {
            View targetView = null;

            switch (category.toLowerCase()){
                case "pizza":
                    targetView = findViewById(R.id.gridPizza);
                    break;
                    case "burgers":
                    targetView = findViewById(R.id.gridBurgers);
                    break;
                    case "beef":
                    targetView = findViewById(R.id.gridBeef);
                    break;
                    case "chicken":
                    targetView = findViewById(R.id.gridChicken);
                    break;
                    case "deserts":
                    targetView = findViewById(R.id.gridDesert);
                    break;
                    case "drinks":
                        targetView = findViewById(R.id.gridDrinks);
                        break;
            }

            if (targetView != null) {
                targetView.getParent().requestChildFocus(targetView, targetView);
            }

        }


    }

}