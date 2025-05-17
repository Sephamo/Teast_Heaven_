package com.example.thegardenofeatn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class MenuAdapter extends BaseAdapter {
    private Context context;
    private List<Menu_Item> menuItems;

    public MenuAdapter(Context context, List<Menu_Item> menuItems){
        this.context = context;
        this.menuItems = menuItems;
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.menu_items, parent, false);
        }

        Menu_Item menuItem = menuItems.get(position);

        ImageView pesto_pizza = view.findViewById(R.id.pesto_pizza);
        TextView textPrice = view.findViewById(R.id.textPrice);
        TextView textTime = view.findViewById(R.id.textTime);

        pesto_pizza.setImageResource(menuItem.getImageResId());
        textPrice.setText(menuItem.getPrice());
        textTime.setText(menuItem.getName());

        return view;
    }
}
