
package com.example.thegardenofeatn;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CustomerActivity extends AppCompatActivity {

    RecyclerView categoryRecyclerView;
    CategoryAdapter adapter;
    ArrayList<Category> categoryList;
    int spacingInPixels;

    RecyclerView promosRecyclerView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);


        //category slide
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // categories
        categoryList = new ArrayList<>();
        categoryList.add(new Category(R.drawable.pizza, "Pizza"));
        categoryList.add(new Category(R.drawable.burger, "Burgers"));
        categoryList.add(new Category(R.drawable.beef, "Beef"));
        categoryList.add(new Category(R.drawable.chicken, "Chicken"));
        categoryList.add(new Category(R.drawable.deserts, "Dessert"));
        categoryList.add(new Category(R.drawable.drinks, "drinks"));

        adapter = new CategoryAdapter(categoryList, new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(Category category) {
                Intent intent = new Intent(CustomerActivity.this, ManuPage.class);
                intent.putExtra("category", category.getName());
                startActivity(intent);
                Toast.makeText(CustomerActivity.this, "Clicked: " + category.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        categoryRecyclerView.setAdapter(adapter);
        spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_spacing);
        categoryRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        //promotions slide
        promosRecyclerView = findViewById(R.id.promosRecyclerView);
        promosRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        //promotions
        List<Promo> promotionList = new ArrayList<>();
        promotionList.add(new Promo(R.drawable.promotions, "new"));
        promotionList.add(new Promo(R.drawable.promo, "new"));
        promotionList.add(new Promo(R.drawable.promotions, "new"));
        promotionList.add(new Promo(R.drawable.promo, "new"));
        promotionList.add(new Promo(R.drawable.promotions, "new"));

        PromoAdapter promoAdapter = new PromoAdapter(promotionList, new PromoAdapter.OnPromoClickListener() {
            @Override
            public void onPromoClick(Promo promo) {
                Toast.makeText(CustomerActivity.this, "Clicked " + promo.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        promosRecyclerView.setAdapter(promoAdapter);

        // link from see al to a menu page

        TextView seeAll = findViewById(R.id.see_all);
        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, ManuPage.class);
                startActivity(intent);
            }
        });

        // Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_menu) {
                    startActivity(new Intent(CustomerActivity.this, ManuPage.class));
                    return true;
                } else if (itemId == R.id.nav_reservation) {
                    startActivity(new Intent(CustomerActivity.this, ReservationActivity.class));
                    return true;
                } else if (itemId == R.id.nav_review) {
                    startActivity(new Intent(CustomerActivity.this, Feedback.class));
                    return true;
                }

                return false;
            }
        });

    }


}
