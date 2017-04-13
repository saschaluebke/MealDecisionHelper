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
    public static final String TAG = RecipeActivity.class.getSimpleName();
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
        ingredients = thisRecipe.getIngredients();
        adapter = new IngredientsAdapter(this, ingredients);
        mListView.setAdapter(adapter);

       /* Intent intent = getIntent();
        String newIngredientName = intent.getStringExtra("newIngredient");
        if (newIngredientName==null || newIngredientName.equals("")){

        }else{
            Log.d(TAG,"New ingredient: "+newIngredientName);
            Ingredient newIngredient = new Ingredient();
            newIngredient.setName(newIngredientName);
            ingredients.add(newIngredient);
            adapter.notifyDataSetChanged();
        }
*/

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
            ArrayList<Shop> shops = Utilities.loadShops(this);
            String currentShopName = prefs.getString("currentShop","");
            Shop currentShop = null;
            for(Shop s: shops){
                if (currentShopName.equals(s.getName())){
                    currentShop = s;
                }
            }

            categories = currentShop.getCategories();

            Ingredient newIngredient = categories.get(newIngredientCategoryIndex).getIngredients().get(newIngredientIndex);

            Ingredient sameIngredient=null;
            for(Ingredient i : ingredients){
                if (i.getName().equals(newIngredient.getName())){
                    sameIngredient = i;
                }
            }
            if(sameIngredient==null){
                ingredients.add(newIngredient);
            }else{
                sameIngredient.setCharacteristics(newIngredient.getCharacteristics()+sameIngredient.getCharacteristics());
            }

            Log.d(TAG,newIngredient.getName()+"/: "+newIngredient.toString());
            adapter.notifyDataSetChanged();
            Utilities.saveRecipe(this,recipes);

        }
    }


    @OnClick(R.id.newRecipeOkButton)
    public void saveNewRecipe(View view) {
        Intent intent = new Intent(this, RecipeActivity.class);
        thisRecipe.setPrior(prior);
        thisRecipe.setName(mNameRecipeEditText.getText().toString());
        recipes.add(thisRecipe);
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
