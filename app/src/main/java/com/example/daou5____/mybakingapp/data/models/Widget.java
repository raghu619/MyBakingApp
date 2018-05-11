package com.example.daou5____.mybakingapp.data.models;

import java.util.ArrayList;

public class Widget {
    public String recipeTitle;
    public ArrayList<Ingredients> ingredients;

    public Widget(String recipeTitle, ArrayList<Ingredients> ingredients) {
        this.recipeTitle = recipeTitle;
        this.ingredients = ingredients;
    }
}
