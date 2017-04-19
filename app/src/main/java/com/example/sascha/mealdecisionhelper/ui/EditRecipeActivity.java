package com.example.sascha.mealdecisionhelper.ui;

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

public class EditRecipeActivity extends AppCompatActivity {
    private ArrayList<Recipe> recipes;
    private ArrayList<Category> categories;
    private Recipe thisRecipe;
    private ArrayList<Ingredient> ingredients;
    private int index = 0;
    private Intent intent;
    public static final String TAG = RecipeActivity.class.getSimpleName();
    private int prior;
    private IngredientsAdapter adapter;
    @InjectView(R.id.editIngredientsList) ListView mListView;
    @InjectView(R.id.editEmptyTextView) TextView mEmptyTextView;
    @InjectView(R.id.editNameRecipe) EditText mNameRecipeEditText;
    @InjectView(R.id.editPriorSeekBar) SeekBar mNewPriorSeekbar;
    @InjectView(R.id.editPriorCount) TextView mNewPriorCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        Log.d(TAG, "New Recipe UI starts running!");
        ButterKnife.inject(this);
        intent = getIntent();
        recipes = Utilities.loadRecipies(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (recipes == null){
            Log.d(TAG,"Recipes sind null sollte nicht passieren");
        }else{
            index = prefs.getInt("recipeIndex",-1);
            if (index < 0 || index > recipes.size()-1){
                Log.d(TAG,"Index not right "+index);
            }else{
                Log.d(TAG,"Angekommener Index: "+index);
                thisRecipe = recipes.get(index);
            }
        }
        prior = thisRecipe.getPrior();
        ingredients = thisRecipe.getIngredients();
        adapter = new IngredientsAdapter(this, ingredients);
        mListView.setAdapter(adapter);

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
        Log.d(TAG,"Recipe name: "+thisRecipe.getName());
        mNameRecipeEditText.setText(thisRecipe.getName());
        mNewPriorCount.setText("Prior: "+String.valueOf(prior));

        mNewPriorSeekbar.post(new Runnable() {
            @Override
            public void run() {
                mNewPriorSeekbar.setProgress(prior);
            }
        });
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
        recipes = Utilities.loadRecipies(this);
        thisRecipe = recipes.get(index);
        ingredients = thisRecipe.getIngredients();
        Log.d(TAG,thisRecipe.getName()+": "+thisRecipe.getIngredients().size());
        adapter = new IngredientsAdapter(this, ingredients);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(this, RecipeActivity.class);
        thisRecipe.setPrior(prior);
        Log.d(TAG, "Und die prior ist: "+thisRecipe.getPrior());
        thisRecipe.setName(mNameRecipeEditText.getText().toString());
        Utilities.saveRecipe(this, recipes);

    }

    @OnClick(R.id.removeEditRecipeButton)
    public void removeButtonClick(View view) {
        recipes.remove(index);
        Utilities.saveRecipe(this, recipes);
        finish();
    }

    @OnClick(R.id.editAddButton)
    public void startIngredientActivity(View view) {
        Intent intent = new Intent(this, ChooseCategoryActivity.class);
        intent.putExtra("recipeIndex",index);
        startActivity(intent);
    }
}
