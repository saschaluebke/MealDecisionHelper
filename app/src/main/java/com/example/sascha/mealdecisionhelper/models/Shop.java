package com.example.sascha.mealdecisionhelper.models;

import android.content.Context;

import com.example.sascha.mealdecisionhelper.utils.Utilities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sascha on 13.04.17.
 */

public class Shop implements Serializable {
    private static final long serialVersionUID = -29238982928391L;
    private String name;
    private ArrayList<Category> categories;

    public Shop(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
