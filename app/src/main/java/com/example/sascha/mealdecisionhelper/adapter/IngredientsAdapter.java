package com.example.sascha.mealdecisionhelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sascha.mealdecisionhelper.R;
import com.example.sascha.mealdecisionhelper.models.Ingredient;
import com.example.sascha.mealdecisionhelper.models.Recipe;

import java.util.ArrayList;

/**
 * Created by sascha on 06.04.17.
 */

public class IngredientsAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Ingredient> ingredientsList;

    public IngredientsAdapter(Context context, ArrayList<Ingredient> ingredientsList) {
        mContext = context;
        this.ingredientsList = ingredientsList;
    }

    @Override
    public int getCount() {
        return ingredientsList.size();
    }

    @Override
    public Object getItem(int position) {
        return ingredientsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0; // we aren't going to use this. Tag items for easy reference
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IngredientsAdapter.ViewHolder holder;

        if (convertView == null) {
            // brand new
            convertView = LayoutInflater.from(mContext).inflate(R.layout.ingredient_list_item, null);
            holder = new IngredientsAdapter.ViewHolder();

            holder.ingredientLabel = (TextView) convertView.findViewById(R.id.ingredientNameLabel);
            holder.unitLabel = (TextView) convertView.findViewById(R.id.unit);
            holder.charaLabel = (TextView) convertView.findViewById(R.id.characterisitc);
            convertView.setTag(holder);
        }
        else {
            holder = (IngredientsAdapter.ViewHolder) convertView.getTag();
        }

        Ingredient ingredient = ingredientsList.get(position);

        holder.ingredientLabel.setText(ingredient.getName());

        float chara = ingredient.getCharacteristics();
        if (chara<=0.0){
            holder.unitLabel.setText("");
            holder.charaLabel.setText("");
        }else{
            holder.unitLabel.setText(ingredient.getUnit());
            holder.charaLabel.setText(String.valueOf(ingredient.getCharacteristics()));
        }




        return convertView;
    }

    private static class ViewHolder {
        TextView ingredientLabel;
        TextView unitLabel;
        TextView charaLabel;
    }
}