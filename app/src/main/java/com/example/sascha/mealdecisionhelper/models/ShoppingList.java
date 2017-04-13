package com.example.sascha.mealdecisionhelper.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sascha on 09.04.17.
 */

public class ShoppingList implements Serializable {
    private static final long serialVersionUID = -29238982928391L;
    private ArrayList<Ingredient> ingredients;

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
