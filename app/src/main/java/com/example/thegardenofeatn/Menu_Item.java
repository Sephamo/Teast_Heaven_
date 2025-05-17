package com.example.thegardenofeatn;

public class Menu_Item {
    private int imageResId;
    private String price;
    private String name;

    public Menu_Item(int imageResId, String price, String time) {
        this.imageResId = imageResId;
        this.price = price;
        this.name = time;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}

