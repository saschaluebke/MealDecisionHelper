package com.example.sascha.mealdecisionhelper.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sascha.mealdecisionhelper.R;
import com.example.sascha.mealdecisionhelper.models.Recipe;

public class RecipeActivity extends AppCompatActivity {

    private Recipe[] recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

    }
}
