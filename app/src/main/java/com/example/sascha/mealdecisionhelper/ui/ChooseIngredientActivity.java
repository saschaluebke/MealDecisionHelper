package com.example.sascha.mealdecisionhelper.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sascha.mealdecisionhelper.R;
import com.example.sascha.mealdecisionhelper.adapter.IngredientsAdapter;
import com.example.sascha.mealdecisionhelper.adapter.RecipeAdapter;
import com.example.sascha.mealdecisionhelper.adapter.SimpleIngredientsAdapter;
import com.example.sascha.mealdecisionhelper.models.Category;
import com.example.sascha.mealdecisionhelper.models.Ingredient;
import com.example.sascha.mealdecisionhelper.models.Recipe;
import com.example.sascha.mealdecisionhelper.models.Shop;
import com.example.sascha.mealdecisionhelper.utils.Utilities;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ChooseIngredientActivity extends AppCompatActivity {
    private String mInputText = "";
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Category> categories;
    public static final String TAG = ChooseIngredientActivity.class.getSimpleName();
    private SimpleIngredientsAdapter adapter;
    private SharedPreferences prefs;
    private ArrayList<Recipe> recipes;
    @InjectView(R.id.chooseIngredientList)
    ListView mListView;
    @InjectView(R.id.emptyTextView)
    TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_ingredient);
        Log.d(TAG, "ChooseIngredient Actitivy starts running!");

        ButterKnife.inject(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();

    }

    public void refresh(){
        Bundle extras = getIntent().getExtras();
        final int recipeIndex = extras.getInt("recipeIndex");
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        recipes = Utilities.loadRecipies(this);

        final ArrayList<Shop> shops = Utilities.loadShops(this);
        String currentShopName = prefs.getString("currentShop","");
        Shop currentShop = null;
        for(Shop s: shops){
            if (currentShopName.equals(s.getName())){
                currentShop = s;
            }
        }

        categories = currentShop.getCategories();
        final int categoryIndex;
        if (categories == null) {
            Log.d(TAG, "Fail to load Recipe");
        } else {
//TODO muss passieren also lieber exception

        }
        categoryIndex = extras.getInt("categoryIndex");
        Log.d(TAG, "Category Geladen: "+categories.get(categoryIndex).getName());
        ingredients = categories.get(categoryIndex).getIngredients();
        Collections.sort(ingredients);
        Log.d(TAG,"IngredientChecker: "+ingredients.size());
        //look for new Ingredients
        String newIngredientName = mInputText;
        if(newIngredientName == null||newIngredientName.equals("")){
            Log.d(TAG, "No new Ingredient");
        }else{
            Ingredient newIngredient = new Ingredient();
            newIngredient.setName(newIngredientName);
            Log.d(TAG, "IngredientsChecker1: "+ingredients.size());
            ingredients.add(newIngredient);
            Log.d(TAG, "IngredientsChecker2: "+ingredients.size());
        }
        adapter = new SimpleIngredientsAdapter(this, ingredients);
        final Context c = this;
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mEmptyTextView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Ingredients number " + position);
                Intent intent = new Intent(c, ChooseCharacteristicsActivity.class);
                intent.putExtra("categoryIndex",categoryIndex);
                intent.putExtra("ingredientIndex",position);
                intent.putExtra("recipeIndex",recipeIndex);
                startActivity(intent);
                finish();

            }

        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                int pos, long id) {
                    Log.d(TAG,"long clicked pos: " + pos);
                    Ingredient removeIngredient = ingredients.get(pos);
                    for(Recipe r : recipes){
                        Ingredient remove=null;
                        for (Ingredient i : r.getIngredients()){
                            if (i.getName().equals(removeIngredient.getName())){
                                Log.d(TAG,"Found: " + i.getName());
                                remove = i;
                            }
                        }
                        if (remove != null){
                            Log.d(TAG,"Removed");
                            r.getIngredients().remove(remove);
                            Log.d(TAG,String.valueOf(r.getIngredients().size()));
                        }
                    }
                    ingredients.remove(pos);
                    adapter.notifyDataSetChanged();
                    Utilities.saveShoppingList(c,ingredients);
                    Utilities.saveRecipe(c,recipes);
                    Utilities.saveShop(c,shops);
                    return true;
                }

            });
        //adapter = new SimpleIngredientsAdapter(this, ingredients);
        Log.d(TAG, "IngredientsChecker3: "+ingredients.size());
        Utilities.saveShop(this,shops);
    }

    @OnClick(R.id.newIngredientButton)
    public void startNewRecipeActivity(View view) {
        //Intent intent = new Intent(this, IngredientActivity.class);
        //startActivity(intent);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

// Set up the input
        final EditText input = new EditText(this);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mInputText = input.getText().toString();
                refresh();
            }
        });

        builder.show();

    }
}

