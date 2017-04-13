package com.example.sascha.mealdecisionhelper.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sascha.mealdecisionhelper.R;
import com.example.sascha.mealdecisionhelper.utils.Utilities;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NewCategoryActivity extends AppCompatActivity {
    public static final String TAG = NewCategoryActivity.class.getSimpleName();

    @InjectView(R.id.newCategoryEditText)
    EditText mCategoryEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.newCategoryOKButton)
    public void saveNewRecipe(View view) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("newCategory",mCategoryEditText.getText().toString());
        editor.commit();
        finish();
    }

}
