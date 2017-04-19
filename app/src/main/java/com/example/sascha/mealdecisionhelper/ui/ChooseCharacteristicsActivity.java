package com.example.sascha.mealdecisionhelper.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sascha.mealdecisionhelper.R;
import com.example.sascha.mealdecisionhelper.adapter.IngredientsAdapter;
import com.example.sascha.mealdecisionhelper.models.Category;
import com.example.sascha.mealdecisionhelper.models.Ingredient;
import com.example.sascha.mealdecisionhelper.models.Recipe;
import com.example.sascha.mealdecisionhelper.models.Shop;
import com.example.sascha.mealdecisionhelper.models.ShoppingList;
import com.example.sascha.mealdecisionhelper.utils.Parser;
import com.example.sascha.mealdecisionhelper.utils.Utilities;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ChooseCharacteristicsActivity extends AppCompatActivity {
    private int recipeIndex;
    private int categoryIndex;
    private int ingredientIndex;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Category> categories;
    private Ingredient thisIngredient;
    private SharedPreferences prefs;
    public static final String TAG = ChooseCharacteristicsActivity.class.getSimpleName();
    @InjectView(R.id.chooseCaracteristicsC)
    EditText mCaraEditText;
    @InjectView(R.id.unitSpinner)
    Spinner mUnitSpinner;
    @InjectView(R.id.chooseCaracteristicsName) TextView mIngredientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosse_characteristics);
        Log.d(TAG, "ChooseCharacterisics Actitivy starts running!");
        ButterKnife.inject(this);

        mUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                thisIngredient.setUnit(parent.getSelectedItem().toString());
                Log.d(TAG,"Selected Item: "+parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                thisIngredient.setUnit(parent.getSelectedItem().toString());
                Log.d(TAG,"Selected Item: "+parent.getSelectedItem().toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle extras = getIntent().getExtras();
        ingredientIndex = extras.getInt("ingredientIndex");
        recipeIndex = extras.getInt("recipeIndex");

        categoryIndex = 0;

        prefs = PreferenceManager.getDefaultSharedPreferences(this);


        ArrayList<Shop> shops = Utilities.loadShops(this);
        String currentShopName = prefs.getString("currentShop","");
        Shop currentShop = null;
        for(Shop s: shops){
            if (currentShopName.equals(s.getName())){
                currentShop = s;
            }
        }

        categories = currentShop.getCategories();

        if (categories == null) {
            Log.d(TAG, "Fail to load Recipe");
        } else {

            categoryIndex= extras.getInt("categoryIndex");
            Log.d(TAG, "Category UI loads: "+categoryIndex);
            ingredients = categories.get(categoryIndex).getIngredients();
            thisIngredient = ingredients.get(ingredientIndex);

        }

        mIngredientName.setText(thisIngredient.getName());
    }

    @OnClick(R.id.chooseCaracteristicsOKButton)
    public void startNewRecipeActivity(View view) {
        String input = mCaraEditText.getText().toString();
        Boolean isNumber = false;
        Parser parser = new Parser(this);
        Float output = parser.characteristicsParser(input);
        if (!parser.getParsable()){

        }else{
            final int shoppingIndex = prefs.getInt("shoppingIndex",-1);
            SharedPreferences.Editor editor = prefs.edit();
            Ingredient newIngredient = new Ingredient(thisIngredient.getName());
            newIngredient.setCharacteristics(output);
            newIngredient.setUnit(thisIngredient.getUnit());
            Log.d(TAG, "ShoppingIndex get = "+shoppingIndex);
            if (shoppingIndex == 1){
                ArrayList<Ingredient> specialIngredients = Utilities.loadSpecialIngredients(this);
                specialIngredients.add(newIngredient);
                Utilities.saveSpecialIngredients(this,specialIngredients);
                editor.putInt("shoppingIndex",0);
                editor.commit();
                finish();
            }else{
                ArrayList<Recipe> recipes = Utilities.loadRecipies(this);
                Recipe thisRecipe = recipes.get(recipeIndex);

                Ingredient sameIngredient = null;
                for(Ingredient i : thisRecipe.getIngredients()){
                    if(newIngredient.getName().equals(i.getName())){
                        sameIngredient = i;
                        sameIngredient.setCharacteristics(sameIngredient.getCharacteristics()+newIngredient.getCharacteristics());
                    }
                }
                if(sameIngredient==null){
                    thisRecipe.getIngredients().add(newIngredient);
                }
                Utilities.saveRecipe(this,recipes);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

                editor.putInt("newIngredientCategoryIndex",categoryIndex);
                editor.putInt("newIngredientIndex",ingredientIndex);
                editor.putInt("recipeIndex",recipeIndex);
                editor.commit();
                finish();
        }




        }


    }
}
