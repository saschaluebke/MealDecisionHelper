package com.example.sascha.mealdecisionhelper.models;

import java.io.Serializable;

/**
 * Created by sascha on 05.04.17.
 */

public class Recipe implements Serializable {
    private static final long serialVersionUID = -29238982928391L;
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
