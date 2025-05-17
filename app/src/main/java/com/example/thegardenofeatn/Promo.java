package com.example.thegardenofeatn;

public class Promo {

    private String name;
    private int image;

    public Promo (int image, String name){

        this.image = image;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int getImage(){
        return image;
    }
}
