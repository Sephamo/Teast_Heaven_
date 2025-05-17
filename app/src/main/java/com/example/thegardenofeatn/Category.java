package com.example.thegardenofeatn;

public class Category {
    private String name;
    private int image;


    // constractor
    public Category (int image, String name){
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
