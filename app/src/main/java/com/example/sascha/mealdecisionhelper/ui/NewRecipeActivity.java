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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sascha.mealdecisionhelper.R;
import com.example.sascha.mealdecisionhelper.adapter.IngredientsAdapter;
import com.example.sascha.mealdecisionhelper.adapter.RecipeAdapter;
import com.example.sascha.mealdecisionhelper.models.Category;
import com.example.sascha.mealdecisionhelper.models.Ingredient;
import com.example.sascha.mealdecisionhelper.models.Recipe;
import com.example.sascha.mealdecisionhelper.models.Shop;
import com.example.sascha.mealdecisionhelper.utils.Utilities;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NewRecipeActivity extends AppCompatActivity {
    private ArrayList<Recipe> recipes;
    private Recipe thisRecipe;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Category> categories;
    public static final String TAG = NewRecipeActivity.class.getSimpleName();
    private int prior;
    @InjectView(R.id.ingredientsList) ListView mListView;
    @InjectView(R.id.emptyTextView) TextView mEmptyTextView;
    @InjectView(R.id.nameRecipe) EditText mNameRecipeEditText;
    @InjectView(R.id.newPriorSeekBar) SeekBar mNewPriorSeekbar;
    @InjectView(R.id.newPriorCount) TextView mNewPriorCount;
    private IngredientsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        Log.d(TAG, "New Recipe UI starts running!");
        ButterKnife.inject(this);


        recipes = Utilities.loadRecipies(this);
        if (recipes == null){
            Log.d(TAG,"Recipes sind null sollte nicht passieren");
        }
        thisRecipe = new Recipe();
        recipes.add(thisRecipe);

        Utilities.saveRecipe(this,recipes);

        ingredients = thisRecipe.getIngredients();

         adapter = new IngredientsAdapter(this, ingredients);
        mListView.setAdapter(adapter);

        final Context c = this;
        mListView.setEmptyView(mEmptyTextView);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                Log.d(TAG,"long clicked pos: " + pos);
                ingredients.remove(pos);
                adapter.notifyDataSetChanged();
                return true;
            }

        });
        mNewPriorCount.setText("Prior"+String.valueOf(prior));
        mNewPriorSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub

               prior = progress;
                mNewPriorCount.setText("Prior: "+String.valueOf(prior));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int newIngredientIndex = prefs.getInt("newIngredientIndex",-1);
        int newIngredientCategoryIndex = prefs.getInt("newIngredientCategoryIndex",-1);
        if (newIngredientIndex==-1 || newIngredientCategoryIndex==-1){
            Log.d(TAG,"PutInt hatte nicht funktioniert");
        }else{
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("newIngredientIndex",-1);
            editor.putInt("newIngredientCategoryIndex",-1);
            editor.commit();
        }
        ArrayList<Shop> shops = Utilities.loadShops(this);
        String currentShopName = prefs.getString("currentShop","");
        Shop currentShop = null;
        for(Shop s: shops){
            if (currentShopName.equals(s.getName())){
                currentShop = s;
            }
        }
        recipes = Utilities.loadRecipies(this);
        thisRecipe = recipes.get(recipes.size()-1);
        ingredients = thisRecipe.getIngredients();
        adapter = new IngredientsAdapter(this, ingredients);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thisRecipe.setPrior(prior);
        thisRecipe.setName(mNameRecipeEditText.getText().toString());
        Utilities.saveRecipe(this, recipes);    }


    @OnClick(R.id.newRecipeOkButton)
    public void saveNewRecipe(View view) {
        thisRecipe.setPrior(prior);
        thisRecipe.setName(mNameRecipeEditText.getText().toString());
        Utilities.saveRecipe(this, recipes);
        finish();
    }

    @OnClick(R.id.newRecipeAddIngredient)
    public void startIngredientActivity(View view) {
        Intent intent = new Intent(this, ChooseCategoryActivity.class);
        intent.putExtra("recipeIndex",recipes.size()-1);
        startActivity(intent);
    }
}
