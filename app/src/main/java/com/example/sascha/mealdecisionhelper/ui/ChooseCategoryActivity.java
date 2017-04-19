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
import com.example.sascha.mealdecisionhelper.adapter.CategoryAdapter;
import com.example.sascha.mealdecisionhelper.adapter.IngredientsAdapter;
import com.example.sascha.mealdecisionhelper.adapter.RecipeAdapter;
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

public class ChooseCategoryActivity extends AppCompatActivity {
    ArrayList<Category> categories;
    Context c;
    private CategoryAdapter adapter;
    public static final String TAG = ChooseCategoryActivity.class.getSimpleName();
    ArrayList<Shop> shops;
    @InjectView(R.id.chooseCategoryList)
    ListView mListView;
    @InjectView(R.id.emptyTextView)
    TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);
        ButterKnife.inject(this);
        c = getApplicationContext();


        adapter = new CategoryAdapter(this, categories);

    }

    @Override
    public void onResume() {
        super.onResume();

        //Look for new Category
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String newCategoryName = prefs.getString("newCategory","");
        SharedPreferences.Editor editor = prefs.edit();

        if (newCategoryName==null || newCategoryName==""){
            Log.d(TAG,"new Category not existend");
        }else{
            editor.putString("newCategory","");
            editor.commit();
            Category newCategory = new Category(newCategoryName);
            categories.add(newCategory);
            Utilities.saveShop(this,shops);
            adapter.notifyDataSetChanged();
        }

        Bundle extras = getIntent().getExtras();
        final int recipeIndex = extras.getInt("recipeIndex",-1);
        final int shoppingIndex = extras.getInt("shoppingIndex",-1);
        if(shoppingIndex==1) {
            editor.putInt("shoppingIndex", 1);
            editor.commit();
        }
        Log.d(TAG,"ShoppingIndex= "+shoppingIndex);
        shops = Utilities.loadShops(this);
        String currentShopName = prefs.getString("currentShop","");
        Shop currentShop=null;
        for(Shop s : shops){
            if (s.getName().equals(currentShopName)){
                currentShop=s;
            }
        }

        categories = currentShop.getCategories();

        if (categories == null) {
            Log.d(TAG, "Fail to load Recipe");
        }
       // Collections.sort(categories);
        Log.d(TAG, "Recipe UI Load Recipes!");
        final CategoryAdapter adapter = new CategoryAdapter(this, categories);
        final Context c = this;
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mEmptyTextView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Recipe number " + position);
                Intent intent = new Intent(c, ChooseIngredientActivity.class);
                intent.putExtra("categoryIndex",position);
                intent.putExtra("recipeIndex",recipeIndex);
                startActivity(intent);
                finish();
            }

        });
    }
    @OnClick(R.id.newCategoryButton)
    public void startNewRecipeActivity(View view) {
        Intent intent = new Intent(this, NewCategoryActivity.class);
        startActivity(intent);

    }

}