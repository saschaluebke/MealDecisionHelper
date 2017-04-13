package com.example.sascha.mealdecisionhelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sascha.mealdecisionhelper.R;
import com.example.sascha.mealdecisionhelper.models.Category;
import com.example.sascha.mealdecisionhelper.models.Ingredient;

import java.util.ArrayList;

/**
 * Created by sascha on 10.04.17.
 */

public class CategoryAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Category> categoriesList;

    public CategoryAdapter(Context context, ArrayList<Category> categoriesList) {
        mContext = context;
        this.categoriesList = categoriesList;
    }

    @Override
    public int getCount() {
        return categoriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoriesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0; // we aren't going to use this. Tag items for easy reference
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryAdapter.ViewHolder holder;

        if (convertView == null) {
            // brand new
            convertView = LayoutInflater.from(mContext).inflate(R.layout.recipe_list_item, null);
            holder = new CategoryAdapter.ViewHolder();

            holder.recipeLabel = (TextView) convertView.findViewById(R.id.recipeNameLable);

            convertView.setTag(holder);
        } else {
            holder = (CategoryAdapter.ViewHolder) convertView.getTag();
        }

        Category category = categoriesList.get(position);

        holder.recipeLabel.setText(category.getName());


        return convertView;
    }

    private static class ViewHolder {
        TextView recipeLabel;
    }
}