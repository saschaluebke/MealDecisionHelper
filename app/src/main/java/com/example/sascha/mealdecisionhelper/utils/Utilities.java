package com.example.sascha.mealdecisionhelper.utils;

import android.content.Context;

import com.example.sascha.mealdecisionhelper.models.Recipe;

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

    public static void saveRecipe(Context context, List<Recipe> recipeList){
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

    public static List<Recipe> loadRecipies (Context context) {
        File fileDirectory = context.getFilesDir();
        File file = new File(fileDirectory, "recipe");
        List<Recipe> recipeList = null;
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            recipeList = (List<Recipe>) in.readObject();
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return recipeList;
    }
}
