package com.example.sascha.mealdecisionhelper.models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sascha on 05.04.17.
 */

public class Recipe implements Serializable, Comparable<Recipe> {
    private static final long serialVersionUID = -29238982928391L;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private int prior=0;
    private int count;
    private boolean selected=false;

    public Recipe(){
        this.name = "Name";
        this.ingredients = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public int getPrior() {
        return prior;
    }

    public void setPrior(int prior) {
        this.prior = prior;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    public int compareTo(@NonNull Recipe o) {
        int comparePrior = o.getPrior();
        return comparePrior- this.prior;
    }
}
