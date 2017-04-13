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
import android.widget.Toast;

import com.example.sascha.mealdecisionhelper.MainActivity;
import com.example.sascha.mealdecisionhelper.R;
import com.example.sascha.mealdecisionhelper.adapter.RecipeAdapter;
import com.example.sascha.mealdecisionhelper.models.Recipe;
import com.example.sascha.mealdecisionhelper.utils.Utilities;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class RecipeActivity extends AppCompatActivity {
    private ArrayList<Recipe> recipes;
    public static final String TAG = RecipeActivity.class.getSimpleName();
    @InjectView(android.R.id.list) ListView mListView;
    @InjectView(R.id.emptyTextView) TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Log.d(TAG, "Recipe UI starts running!");

        ButterKnife.inject(this);

    }

    @Override
    public void onResume(){
        super.onResume();
        recipes = Utilities.loadRecipies(this);

        if (recipes == null){
            Log.d(TAG, "Fail to load Recipe");
        }
        Collections.sort(recipes);
        Log.d(TAG, "Recipe UI Load Recipes!");
        final RecipeAdapter adapter = new RecipeAdapter(this, recipes);
        final Context c = this;
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mEmptyTextView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Recipe number "+position);
                Recipe choosenRecipe = recipes.get(position);
                if (choosenRecipe.isSelected()){
                    choosenRecipe.setSelected(false);

                }else{
                    choosenRecipe.setSelected(true);
                }
                adapter.notifyDataSetChanged();
                Utilities.saveRecipe(c, recipes);
            }

        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                Log.d(TAG,"long clicked pos: " + pos);
                Intent intent = new Intent(getBaseContext(), EditRecipeActivity.class);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("recipeIndex",  pos);
                editor.commit();
                startActivity(intent);
                return true;
            }

        });
        adapter.notifyDataSetChanged();

    }

    @OnClick(R.id.newRecipeButton)
    public void startNewRecipeActivity(View view) {
        Intent intent = new Intent(this, NewRecipeActivity.class);
        startActivity(intent);

    }


}
