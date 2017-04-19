package com.example.sascha.mealdecisionhelper.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.sascha.mealdecisionhelper.R;

/**
 * Created by sascha on 19.04.17.
 */

public class Parser {

    private Context c = null;
    private Boolean parsable=false;

    public Parser(){

    }

    public Parser(Context c){
        this.c = c;
    }

    public Float characteristicsParser(String input){
        float output = 0;

        input = input.replace(',','.');
        try {
            output = Float.parseFloat(input);
            if (output < 0){
                Toast.makeText(c, input.toString()+" "+c.getString( R.string.error_parser),
                        Toast.LENGTH_LONG).show();
            }else{
                parsable = true;
            }

        }
        catch(NumberFormatException ex) {
            Log.d("Parser","input not correct: "+input);
            if (c!=null){
                Toast.makeText(c, input.toString()+" "+c.getString( R.string.error_parser),
                        Toast.LENGTH_LONG).show();
            }
        }

        return output;
    }

    public Boolean getParsable() {
        return parsable;
    }

    public void setParsable(Boolean parsable) {
        this.parsable = parsable;
    }
}
