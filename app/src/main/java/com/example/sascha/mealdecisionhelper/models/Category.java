package com.example.sascha.mealdecisionhelper.models;

import android.graphics.Color;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sascha on 10.04.17.
 */

public class Category implements Serializable, Comparable<Category> {
    private static final long serialVersionUID = -29238982928391L;
    private String name;
    private int color;
    private ArrayList<Ingredient> ingredients;

    public Category(String name){
        this.name = name;
        ingredients = new ArrayList<>();
    }

    public Category(String name, ArrayList<Ingredient> ingredients){
        this.ingredients = ingredients;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int compareTo(@NonNull Category o) {
        return this.name.compareTo(o.getName());
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}