package com.example.sascha.mealdecisionhelper.utils;

import android.content.Context;

import com.example.sascha.mealdecisionhelper.models.Category;
import com.example.sascha.mealdecisionhelper.models.Ingredient;
import com.example.sascha.mealdecisionhelper.models.Recipe;
import com.example.sascha.mealdecisionhelper.models.Shop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sascha on 05.04.17.
 */

public class Utilities {

    public static void saveShop(Context context, ArrayList<Shop> shops){

        File fileDirectory = context.getFilesDir();
        File fileToWrite = new File(fileDirectory, "shops");

        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileToWrite));
            out.writeObject(shops);
            out.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Shop> loadShops (Context context) {
        File fileDirectory = context.getFilesDir();
        File file = new File(fileDirectory, "shops");
        ArrayList<Shop> shops = new ArrayList<>();
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            shops = (ArrayList<Shop>) in.readObject();
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return shops;
    }
/*
    public static void saveCategories(Context context, ArrayList<Category> categoryList){

        File fileDirectory = context.getFilesDir();
        File fileToWrite = new File(fileDirectory, "categories");

        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileToWrite));
            out.writeObject(categoryList);
            out.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Category> loadCategories (Context context) {
        File fileDirectory = context.getFilesDir();
        File file = new File(fileDirectory, "categories");
        ArrayList<Category> categoryList = new ArrayList<>();
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            categoryList = (ArrayList<Category>) in.readObject();
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return categoryList;
    }
*/
    public static void saveShoppingList(Context context, ArrayList<Ingredient> shoppingList){

        File fileDirectory = context.getFilesDir();
        File fileToWrite = new File(fileDirectory, "shoppingList");

        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileToWrite));
            out.writeObject(shoppingList);
            out.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Ingredient> loadShoppingList (Context context) {
        File fileDirectory = context.getFilesDir();
        File file = new File(fileDirectory, "shoppingList");
        ArrayList<Ingredient> shoppingList = new ArrayList<>();
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            shoppingList = (ArrayList<Ingredient>) in.readObject();
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return shoppingList;
    }

    public static void saveRecipe(Context context, ArrayList<Recipe> recipeList){

        File fileDirectory = context.getFilesDir();
        File fileToWrite = new File(fileDirectory, "recipe");

        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileToWrite));
            out.writeObject(recipeList);
            out.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Recipe> loadRecipies (Context context) {
        File fileDirectory = context.getFilesDir();
        File file = new File(fileDirectory, "recipe");
        ArrayList<Recipe> recipeList = null;
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            recipeList = (ArrayList<Recipe>) in.readObject();
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return recipeList;
    }
}
