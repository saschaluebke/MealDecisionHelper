package com.example.sascha.mealdecisionhelper.models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sascha on 06.04.17.
 */

public class Ingredient implements Serializable, Comparable<Ingredient> {
    private static final long serialVersionUID = -29238982928391L;
    static final AtomicLong NEXT_ID = new AtomicLong(0);
    final long id = NEXT_ID.getAndIncrement();
    private String name;
    private String unit;
    private float characteristics;

    public Ingredient(){

    }

    public Ingredient(String name){
        this.setName(name);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(float characteristics) {
        this.characteristics = characteristics;
    }

    @Override
    public int compareTo(@NonNull Ingredient o) {
        return this.name.compareTo(o.getName());
    }


}
