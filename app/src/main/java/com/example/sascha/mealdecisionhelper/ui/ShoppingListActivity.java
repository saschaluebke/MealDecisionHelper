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

    }
    @Override
    protected void onResume(){
        super.onResume();
        ingredients = Utilities.loadShoppingList(this);
        final ArrayList<Ingredient> specialIngredients = Utilities.loadSpecialIngredients(this);
        ingredients.addAll(specialIngredients);
        adapter = new IngredientsAdapter(this, ingredients);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ingredient ingredient = ingredients.get(position);
                if(ingredient.getSelected()){
                    ingredient.setSelected(false);
                }else{
                    ingredient.setSelected(true);
                }
                Utilities.saveSpecialIngredients(c,specialIngredients);
                Utilities.saveShoppingList(c,ingredients);
                adapter.notifyDataSetChanged();
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                Log.d(TAG,"long clicked pos: " + pos);
                Ingredient remove = null;
                for(Ingredient i : specialIngredients){
                    if(i.getName().equals(ingredients.get(pos).getName())){
                        remove = i;
                    }
                }
                if (remove != null){
                    specialIngredients.remove(remove);
                    Utilities.saveSpecialIngredients(c,specialIngredients);
                }
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
        ArrayList<Ingredient> specialIngredients = Utilities.loadSpecialIngredients(this);
        ingredients = new ArrayList<>();
        for(Category c : categories){
            //search special Ingredients

            for(Ingredient i : specialIngredients){
                for(Ingredient inCategory : c.getIngredients()){
                    if(inCategory.getName().equals(i.getName())){
                        ingredients.add(i);
                    }
                }
            }

            //search ingredients from recipes
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

    @OnClick(R.id.shoppingAddIngredient)
    public void addIngredient(View view) {
        Intent intent = new Intent(this, ChooseCategoryActivity.class);
        intent.putExtra("shoppingIndex",1);
        startActivity(intent);
    }

}
