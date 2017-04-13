package com.example.sascha.mealdecisionhelper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.sascha.mealdecisionhelper.models.Category;
import com.example.sascha.mealdecisionhelper.models.Ingredient;
import com.example.sascha.mealdecisionhelper.models.Recipe;
import com.example.sascha.mealdecisionhelper.models.Shop;
import com.example.sascha.mealdecisionhelper.ui.OptionsActivity;
import com.example.sascha.mealdecisionhelper.ui.RecipeActivity;
import com.example.sascha.mealdecisionhelper.ui.ShoppingListActivity;
import com.example.sascha.mealdecisionhelper.utils.Utilities;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Shop> shops;
    public static final String TAG = MainActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        Log.d(TAG, "Main UI code is running!");

        //Just for debugging
        //shops = new ArrayList<>();
        //Utilities.saveShop(this,shops);

        String reset = prefs.getString("reset","yes");
        shops = Utilities.loadShops(this);
        if (shops==null || shops.size()<4|| reset==null || reset.equals("yes")){
            shops = new ArrayList<>();
            shops.add(new Shop("Aldi"));
            shops.add(new Shop("Rewe"));
            shops.add(new Shop("Edeka"));
            shops.add(new Shop("Kaufhof"));
            shops.add(new Shop("Netto"));
            shops.add(new Shop("Kaufland"));

            for(Shop s : shops) {
                ArrayList<Category> categories = new ArrayList<>();
                final Context c = this;
                Ingredient potato = new Ingredient(c.getString(R.string.potato));
                Ingredient springOnion = new Ingredient(c.getString(R.string.spring_onion));
                Ingredient onion = new Ingredient(c.getString(R.string.onion));
                Ingredient garlic = new Ingredient(c.getString(R.string.garlic));
                Ingredient salad = new Ingredient(c.getString(R.string.salad));
                Ingredient tomato = new Ingredient(c.getString(R.string.tomato));
                Ingredient sauerkraut = new Ingredient(c.getString(R.string.sauerkraut));
                Ingredient cucumber = new Ingredient(c.getString(R.string.cucumber));
                Ingredient spinach = new Ingredient(c.getString(R.string.spinach));
                Ingredient broccoli = new Ingredient(c.getString(R.string.broccoli));
                Ingredient napa = new Ingredient(c.getString(R.string.napa_cabbage));
                Ingredient zuc = new Ingredient(c.getString(R.string.zucchini));
                Ingredient radish = new Ingredient(c.getString(R.string.radish));
                Ingredient peppers = new Ingredient(c.getString(R.string.peppers));
                Ingredient rice = new Ingredient(c.getString(R.string.rice));
                Ingredient carrot = new Ingredient(c.getString(R.string.carrot));
                ArrayList<Ingredient> ingredients = new ArrayList<>();
                ingredients.add(potato);
                ingredients.add(springOnion);
                ingredients.add(new Ingredient(c.getString(R.string.mushroom)));
                ingredients.add(carrot);
                ingredients.add(onion);
                ingredients.add(garlic);
                ingredients.add(salad);
                ingredients.add(tomato);
                ingredients.add(sauerkraut);
                ingredients.add(cucumber);
                ingredients.add(spinach);
                ingredients.add(broccoli);
                ingredients.add(napa);
                ingredients.add(zuc);
                ingredients.add(radish);
                ingredients.add(peppers);
                ingredients.add(rice);
                Category vegetables = new Category(c.getString(R.string.category_vegetables),ingredients);


                ArrayList<Ingredient> ingFruits = new ArrayList<>();
                ingFruits.add( new Ingredient(c.getString(R.string.banana)));
                ingFruits.add( new Ingredient(c.getString(R.string.apple)));
                ingFruits.add( new Ingredient(c.getString(R.string.kiwi)));
                ingFruits.add( new Ingredient(c.getString(R.string.grape)));
                Category fruits = new Category(c.getString(R.string.category_fruits),ingFruits);


                ArrayList<Ingredient> ingSweets = new ArrayList<>();
                ingSweets.add( new Ingredient(c.getString(R.string.cookie)));
                Category sweets = new Category(c.getString(R.string.category_sweets),ingSweets);


                ArrayList<Ingredient> ingMeat = new ArrayList<>();
                Ingredient hash =  new Ingredient(c.getString(R.string.hash));
                ingMeat.add( new Ingredient(c.getString(R.string.tuna)));
                ingMeat.add(hash);
                ingMeat.add( new Ingredient(c.getString(R.string.steak)));
                ingMeat.add( new Ingredient(c.getString(R.string.sausage)));
                Category meat = new Category(c.getString(R.string.category_meat),ingMeat);


                ArrayList<Ingredient> ingFrozen = new ArrayList<>();
                ingFrozen.add( new Ingredient(c.getString(R.string.frozen_berries)));
                ingFrozen.add( new Ingredient(c.getString(R.string.ice_cream)));
                ingFrozen.add( new Ingredient(c.getString(R.string.frozen_salmon)));
                Category frozen = new Category(c.getString(R.string.pickled_ware),ingFrozen);


                ArrayList<Ingredient> ingDairy = new ArrayList<>();
                Ingredient parmesan = new Ingredient(c.getString(R.string.parmesan));
                ingDairy.add( new Ingredient(c.getString(R.string.milk)));
                ingDairy.add( new Ingredient(c.getString(R.string.mozzarella)));
                ingDairy.add( parmesan);
                ingDairy.add( new Ingredient(c.getString(R.string.pizza_cheese)));
                Category dairy = new Category(c.getString(R.string.frozen_food),ingDairy);


                ArrayList<Ingredient> ingBakery = new ArrayList<>();
                ingBakery.add( new Ingredient(c.getString(R.string.flour)));
                ingBakery.add( new Ingredient(c.getString(R.string.egg)));
                ingBakery.add( new Ingredient(c.getString(R.string.bun)));
                ingBakery.add( new Ingredient(c.getString(R.string.bread)));
                Category backery = new Category(c.getString(R.string.bakery_products),ingBakery);


                ArrayList<Ingredient> ingPasta = new ArrayList<>();
                Ingredient spagetti = new Ingredient(c.getString(R.string.spaghetti));
                ingBakery.add( spagetti);
                ingBakery.add( new Ingredient(c.getString(R.string.lasagne_plates)));
                Category pasta = new Category(c.getString(R.string.pasta),ingPasta);


                ArrayList<Ingredient> ingPickled = new ArrayList<>();
                Ingredient tomatos = new Ingredient(c.getString(R.string.sieved_tomatos));
                ingPickled.add( new Ingredient(c.getString(R.string.pickled_cucumber)));
                ingPickled.add(tomatos );
                Category pickled = new Category(c.getString(R.string.pickled_ware),ingPickled);


                ArrayList<Ingredient> ingOther = new ArrayList<>();
                ingOther.add( new Ingredient(c.getString(R.string.toilet_paper)));
                Category other = new Category(c.getString(R.string.category_other),ingOther);


                if (s.getName().equals("Aldi")){
                    categories.add(backery);
                    categories.add(sweets);
                    categories.add(fruits);
                    categories.add(pasta);
                    categories.add(vegetables);
                    categories.add(meat);
                    categories.add(other);
                    categories.add(frozen);
                    categories.add(dairy);
                    categories.add(pickled);
                }else if (s.getName().equals("Rewe")){
                    categories.add(fruits);
                    categories.add(vegetables);
                    categories.add(dairy);
                    categories.add(meat);
                    categories.add(backery);
                    categories.add(pasta);
                    categories.add(pickled);
                    categories.add(other);
                    categories.add(sweets);
                    categories.add(frozen);
                }else if (s.getName().equals("Edeka")){
                    categories.add(fruits);
                    categories.add(vegetables);
                    categories.add(dairy);
                    categories.add(meat);
                    categories.add(backery);
                    categories.add(pasta);
                    categories.add(pickled);
                    categories.add(other);
                    categories.add(sweets);
                    categories.add(frozen);
                } else if (s.getName().equals("Netto")){
                    categories.add(fruits);
                    categories.add(vegetables);
                    categories.add(dairy);
                    categories.add(meat);
                    categories.add(backery);
                    categories.add(pasta);
                    categories.add(pickled);
                    categories.add(other);
                    categories.add(sweets);
                    categories.add(frozen);
                }else{
                    categories.add(fruits);
                    categories.add(vegetables);
                    categories.add(dairy);
                    categories.add(meat);
                    categories.add(backery);
                    categories.add(pasta);
                    categories.add(pickled);
                    categories.add(other);
                    categories.add(sweets);
                    categories.add(frozen);
                }

                s.setCategories(categories);
                Utilities.saveShop(this,shops);

                Recipe defaultRecipe = new Recipe();
                defaultRecipe.setName("Spaghetti Bolognese");
                defaultRecipe.setPrior(50);
                defaultRecipe.setSelected(true);
                ArrayList<Ingredient> ingBolognese = defaultRecipe.getIngredients();
                ingBolognese.add(hash);
                ingBolognese.add(carrot);
                ingBolognese.add(tomatos);
                ingBolognese.add(spagetti);
                ingBolognese.add(parmesan);
                ingBolognese.add(onion);
                ingBolognese.add(garlic);
                ArrayList<Recipe> defaultRecipeList = new ArrayList<>();
                defaultRecipeList.add(defaultRecipe);
                Utilities.saveRecipe(this,defaultRecipeList);

            }
            ArrayList<Ingredient> shoppingList = new ArrayList<>();
            Utilities.saveShoppingList(this, shoppingList);
            editor.putString("currentShop","Aldi");
            editor.putString("reset","no");
            editor.commit();
        }

        //Setting recipe data when no recipe is in it!
        ArrayList<Recipe> recipeList = Utilities.loadRecipies(this);
        if (recipeList == null){
            ArrayList<Recipe> newRecipeList = new ArrayList<>();
            Utilities.saveRecipe(this,newRecipeList);
        }
    }

    @OnClick(R.id.rezeptButton)
    public void startDailyActivity(View view) {
        Intent intent = new Intent(this, RecipeActivity.class);
        //intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast()); -> Aus File Daten lesen
        startActivity(intent);
    }

    @OnClick(R.id.shoppingButton)
    public void startShoppingListActivity(View view) {
        Intent intent = new Intent(this, ShoppingListActivity.class);
        //intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast()); -> Aus File Daten lesen
        startActivity(intent);
    }

    @OnClick(R.id.optionButton)
    public void startOptionActivity(View view) {
        Intent intent = new Intent(this, OptionsActivity.class);
        //intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast()); -> Aus File Daten lesen
        startActivity(intent);
    }


}
