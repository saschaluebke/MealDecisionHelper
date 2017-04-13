package com.example.sascha.mealdecisionhelper.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.sascha.mealdecisionhelper.R;
import com.example.sascha.mealdecisionhelper.adapter.RecipeAdapter;
import com.example.sascha.mealdecisionhelper.adapter.RecyclerListAdapter;
import com.example.sascha.mealdecisionhelper.helper.OnStartDragListener;
import com.example.sascha.mealdecisionhelper.helper.SimpleItemTouchHelperCallback;
import com.example.sascha.mealdecisionhelper.models.Category;
import com.example.sascha.mealdecisionhelper.models.Ingredient;
import com.example.sascha.mealdecisionhelper.models.Recipe;
import com.example.sascha.mealdecisionhelper.models.Shop;
import com.example.sascha.mealdecisionhelper.utils.Utilities;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class OptionsActivity extends AppCompatActivity implements OnStartDragListener {
    private ArrayList<Shop> shops;
    private ArrayList<Category> shopCat;
    private ItemTouchHelper mItemTouchHelper;
    private Shop currentShop;
    private RecyclerListAdapter adapter;
    public static final String TAG = OptionsActivity.class.getSimpleName();
    Context c;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    @InjectView(R.id.optionsCategoryList)
    RecyclerView mListView;
    @InjectView(R.id.ShopButton1)
    Button shopButton1;
    @InjectView(R.id.ShopButton2)
    Button shopButton2;
    @InjectView(R.id.ShopButton3)
    Button shopButton3;
    @InjectView(R.id.ShopButton4)
    Button shopButton4;
    @InjectView(R.id.ShopButton5)
    Button shopButton5;
    @InjectView(R.id.ShopButton6)
    Button shopButton6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        ButterKnife.inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        c = this;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        shops = Utilities.loadShops(this);

        if (shops == null) {
            Log.d(TAG, "Fail to load Categories");
        }
        Log.d(TAG, "Categories: "+shops.size());

        shopButton1.setText(shops.get(0).getName());
        shopButton2.setText(shops.get(1).getName());
        shopButton3.setText(shops.get(2).getName());
        shopButton4.setText(shops.get(3).getName());
        shopButton5.setText(shops.get(4).getName());
        shopButton6.setText(shops.get(5).getName());

        shopCat = null;
        String currentShopName = prefs.getString("currentShop","");
        int index =0;
        for(Shop p : shops){

            if (currentShopName.equals(p.getName())){
                currentShop = p;
                shopCat = p.getCategories();
                break;
            }
            index++;
        }
        if (index == 0){
            shopButton1.setTextColor(Color.GREEN);
        }else if (index == 1){
            shopButton2.setTextColor(Color.GREEN);
        }else if (index == 2){
            shopButton3.setTextColor(Color.GREEN);
        }else if (index == 3) {
            shopButton4.setTextColor(Color.GREEN);
        }
        else if (index == 4) {
        shopButton5.setTextColor(Color.GREEN);
    }else if (index == 5) {
        shopButton6.setTextColor(Color.GREEN);
    }else{
            Log.d(TAG,"Shouldnt happen index wrong: "+index);
        }
        adapter = new RecyclerListAdapter(this, this,shopCat);
        mListView.setAdapter(adapter);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mListView);


        String newCategoryName = prefs.getString("newCategory","");

        if (newCategoryName==null || newCategoryName==""){
            Log.d(TAG,"new Category not existend");
        }else{
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("newCategory","");
            editor.commit();
            Category newCategory = new Category(newCategoryName);
            for(Shop p:shops){
                p.getCategories().add(newCategory);
            }

            Utilities.saveShop(this,shops);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utilities.saveShop(this,shops);
    }

    @OnClick(R.id.optionsNewCategoryButton)
    public void startNewRecipeActivity(View view) {
        Intent intent = new Intent(this, NewCategoryActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.optionsRemoveCategory)
    public void removeLastCategory(View view) {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(c.getString(R.string.remove_last_category))
                .setMessage(c.getString(R.string.remove_last_category_message))
                .setPositiveButton(c.getString(R.string.yes), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Category removedCategory = null;
                        if(shopCat.size()>0){
                            removedCategory = shopCat.get(shopCat.size()-1);

                            ArrayList<Recipe> recipes = Utilities.loadRecipies(c);
                            for(Recipe r : recipes){
                                for(Ingredient inCategory : removedCategory.getIngredients()){
                                    ArrayList<Ingredient> removeIngredients=new ArrayList<>();
                                    for(Ingredient i : r.getIngredients()){
                                        if (inCategory.getName().equals(i.getName())){
                                            removeIngredients.add(i);
                                        }
                                    }
                                    ArrayList<Ingredient> ingredients = r.getIngredients();
                                    ingredients.removeAll(removeIngredients);
                                }
                            }
                            Utilities.saveRecipe(c,recipes);
                            for(Shop s : shops){
                                s.getCategories().remove(removedCategory);
                            }
                            adapter.notifyDataSetChanged();
                            Utilities.saveShop(c,shops);
                        }

                    }

                })
                .setNegativeButton(c.getString(R.string.no), null)
                .show();

    }

    @OnClick(R.id.ShopButton1)
    public void setShopButton1(View view) {
        Shop s = shops.get(0);
        adapter = new RecyclerListAdapter(this, this,s.getCategories());
        mListView.setAdapter(adapter);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mListView);

        shopButton1.setTextColor(Color.GREEN);
        shopButton2.setTextColor(Color.WHITE);
        shopButton3.setTextColor(Color.WHITE);
        shopButton4.setTextColor(Color.WHITE);
        shopButton5.setTextColor(Color.WHITE);
        shopButton6.setTextColor(Color.WHITE);
        editor.putString("currentShop",shops.get(0).getName());
        editor.commit();
    }

    @OnClick(R.id.ShopButton2)
    public void setShopButton2(View view) {
        Shop s = shops.get(1);
        adapter = new RecyclerListAdapter(this, this,s.getCategories());
        mListView.setAdapter(adapter);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mListView);
        shopButton1.setTextColor(Color.WHITE);
        shopButton2.setTextColor(Color.GREEN);
        shopButton3.setTextColor(Color.WHITE);
        shopButton4.setTextColor(Color.WHITE);
        shopButton5.setTextColor(Color.WHITE);
        shopButton6.setTextColor(Color.WHITE);
        editor.putString("currentShop",shops.get(1).getName());
        editor.commit();
    }

    @OnClick(R.id.ShopButton3)
    public void setShopButton3(View view) {
        Shop s = shops.get(2);
        adapter = new RecyclerListAdapter(this, this,s.getCategories());
        mListView.setAdapter(adapter);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mListView);
        shopButton1.setTextColor(Color.WHITE);
        shopButton2.setTextColor(Color.WHITE);
        shopButton3.setTextColor(Color.GREEN);
        shopButton4.setTextColor(Color.WHITE);
        shopButton5.setTextColor(Color.WHITE);
        shopButton6.setTextColor(Color.WHITE);
        editor.putString("currentShop",shops.get(2).getName());
        editor.commit();
    }

    @OnClick(R.id.ShopButton4)
    public void setShopButton4(View view) {
        Shop s = shops.get(3);
        adapter = new RecyclerListAdapter(this, this,s.getCategories());
        mListView.setAdapter(adapter);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mListView);
        shopButton1.setTextColor(Color.WHITE);
        shopButton2.setTextColor(Color.WHITE);
        shopButton3.setTextColor(Color.WHITE);
        shopButton4.setTextColor(Color.GREEN);
        shopButton5.setTextColor(Color.WHITE);
        shopButton6.setTextColor(Color.WHITE);
        editor.putString("currentShop",shops.get(3).getName());
        editor.commit();
    }

    @OnClick(R.id.ShopButton5)
    public void setShopButton5(View view) {
        Shop s = shops.get(4);
        adapter = new RecyclerListAdapter(this, this,s.getCategories());
        mListView.setAdapter(adapter);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mListView);
        shopButton1.setTextColor(Color.WHITE);
        shopButton2.setTextColor(Color.WHITE);
        shopButton3.setTextColor(Color.WHITE);
        shopButton4.setTextColor(Color.WHITE);
        shopButton5.setTextColor(Color.GREEN);
        shopButton6.setTextColor(Color.WHITE);
        editor.putString("currentShop",shops.get(4).getName());
        editor.commit();
    }

    @OnClick(R.id.ShopButton6)
    public void setShopButton6(View view) {
        Shop s = shops.get(5);
        adapter = new RecyclerListAdapter(this, this,s.getCategories());
        mListView.setAdapter(adapter);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mListView);
        shopButton1.setTextColor(Color.WHITE);
        shopButton2.setTextColor(Color.WHITE);
        shopButton3.setTextColor(Color.WHITE);
        shopButton4.setTextColor(Color.WHITE);
        shopButton5.setTextColor(Color.WHITE);
        shopButton6.setTextColor(Color.GREEN);
        editor.putString("currentShop",shops.get(5).getName());
        editor.commit();
    }

    @OnClick(R.id.resetButton)
    public void reset(View view) {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Reset!")
                .setMessage(c.getString(R.string.reset))
                .setPositiveButton(c.getString(R.string.yes), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putString("reset","yes");
                        editor.commit();
                        finish();
                        System.exit(0);
                    }

                })
                .setNegativeButton(c.getString(R.string.no), null)
                .show();
    }

}
