package com.example.sascha.mealdecisionhelper.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sascha.mealdecisionhelper.R;
import com.example.sascha.mealdecisionhelper.models.Recipe;
import com.example.sascha.mealdecisionhelper.utils.Utilities;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class IngredientActivity extends Activity {
    String modus;
    public static final String TAG = RecipeActivity.class.getSimpleName();
    @InjectView(R.id.ingredientEditText) EditText mIngredientEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ingredient);
        ButterKnife.inject(this);

    }

    @OnClick(R.id.okIngredientButton)
    public void backToNewRecipeActivity(View view) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("newIngredient",mIngredientEditText.getText().toString());
        editor.commit();
        finish();

    }
}
