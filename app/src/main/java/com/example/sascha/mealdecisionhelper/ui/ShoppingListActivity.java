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
import android.widget.ListView;
import android.widget.TextView;

import com.example.sascha.mealdecisionhelper.R;
import com.example.sascha.mealdecisionhelper.adapter.IngredientsAdapter;
import com.example.sascha.mealdecisionhelper.models.Category;
import com.example.sascha.mealdecisionhelper.models.Ingredient;
import com.example.sascha.mealdecisionhelper.models.Recipe;
import com.example.sascha.mealdecisionhelper.models.Shop;
import com.example.sascha.mealdecisionhelper.utils.Utilities;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ShoppingListActivity extends AppCompatActivity {
    private ArrayList<Recipe> recipes;
    private ArrayList<Category> categories;
    private ArrayList<Ingredient> ingredients;
    private IngredientsAdapter adapter;
    private Context c=this;
    public static final String TAG = ShoppingListActivity.class.getSimpleName();
    @InjectView(R.id.shoppingList)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        ButterKnife.inject(this);
        ingredients = Utilities.loadShoppingList(this);
        adapter = new IngredientsAdapter(this, ingredients);
        mListView.setAdapter(adapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                Log.d(TAG,"long clicked pos: " + pos);
                ingredients.remove(pos);
                adapter.notifyDataSetChanged();
                Utilities.saveShoppingList(c,ingredients);
                return true;
            }

        });
    }

    @OnClick(R.id.createNewShoppingList)
    public void startNewRecipeActivity(View view) {
        recipes = Utilities.loadRecipies(this);
        if (recipes == null){
            Log.d(TAG, "Fail to load Recipe");
        }
        Log.d(TAG, "Shopping list UI Load Recipes!");

        ArrayList<Shop> shops = Utilities.loadShops(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String currentShopName = prefs.getString("currentShop","");
        Shop currentShop=null;
        for (Shop s : shops){
            if (currentShopName.equals(s.getName())){
                currentShop = s;
            }
        }

        categories = currentShop.getCategories();
        if (categories == null){
            Log.d(TAG, "Fail to load Recipe");
        }

        ingredients = new ArrayList<>();
        for(Category c : categories){
            for(Recipe r : recipes){
                if (r.isSelected()){
                    for (Ingredient i : r.getIngredients()){
                        Log.d(TAG, "Categorie: "+c.getName()+"/Rezept: "+r.getName()+"/Ing: "+i.getName());
                        for(int ii=0; ii<c.getIngredients().size();ii++){
                            Log.d(TAG,c.getIngredients().get(ii).toString()+"/"+i.toString());
                            Log.d(TAG,c.getIngredients().get(ii).getName()+"/"+i.getName());

                        }
                        for (Ingredient inCategory : c.getIngredients()){
                            if (inCategory.getName().equals(i.getName())){

                                Ingredient sameNamedIngredient = null;
                                for(Ingredient inList : ingredients){
                                    if(inList.getName().equals(i.getName())){
                                        sameNamedIngredient=inList;
                                    }
                                }
                                if (sameNamedIngredient == null){
                                    Ingredient newListIngredient = new Ingredient();
                                    newListIngredient.setName(i.getName());
                                    newListIngredient.setUnit(i.getUnit());
                                    newListIngredient.setCharacteristics(i.getCharacteristics());
                                    ingredients.add(newListIngredient);
                                }else{
                                    sameNamedIngredient.setCharacteristics(sameNamedIngredient.getCharacteristics()+i.getCharacteristics());
                                }

                            }
                        }
                    }
                }
            }
        }

        adapter = new IngredientsAdapter(this, ingredients);
        mListView.setAdapter(adapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                Log.d(TAG,"long clicked pos: " + pos);
                ingredients.remove(pos);
                adapter.notifyDataSetChanged();
                Utilities.saveShoppingList(c,ingredients);
                return true;
            }

        });
        Utilities.saveShoppingList(this,ingredients);

    }
}
