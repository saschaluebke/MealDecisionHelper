package com.example.sascha.mealdecisionhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.sascha.mealdecisionhelper.ui.ShoppingActivity;
import com.example.sascha.mealdecisionhelper.ui.RecipeActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Log.d(TAG, "Main UI code is running!");
    }

    @OnClick(R.id.rezeptButton)
    public void startDailyActivity(View view) {
        Intent intent = new Intent(this, RecipeActivity.class);
        //intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast()); -> Aus File Daten lesen
        startActivity(intent);
    }

    @OnClick(R.id.buyButton)
    public void startKaufActivity(View view) {
        Intent intent = new Intent(this, ShoppingActivity.class);
        //intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast()); -> Aus File Daten lesen
        startActivity(intent);
    }
}
