package com.example.sascha.mealdecisionhelper.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sascha.mealdecisionhelper.R;
import com.example.sascha.mealdecisionhelper.models.Recipe;

import java.util.ArrayList;

/**
 * Created by sascha on 06.04.17.
 */

public class RecipeAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Recipe> recipeList;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipeList) {
        mContext = context;
        this.recipeList = recipeList;
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0; // we aren't going to use this. Tag items for easy reference
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

            if (convertView == null) {
                // brand new
                convertView = LayoutInflater.from(mContext).inflate(R.layout.recipe_list_item, null);
                holder = new ViewHolder();

                holder.recipeLabel = (TextView) convertView.findViewById(R.id.recipeNameLable);
                holder.priorLabel = (TextView) convertView.findViewById(R.id.prior);

                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Recipe recipe = recipeList.get(position);
            holder.recipeLabel.setText(recipe.getName());
            holder.priorLabel.setText(String.valueOf(recipe.getPrior()));
            convertView.setBackgroundColor(0xffaa00);
            if (recipe.isSelected()){
                convertView.setBackgroundColor(Color.GREEN);
            }



        return convertView;
    }

    private static class ViewHolder {
        TextView recipeLabel;
        TextView priorLabel;
    }
}