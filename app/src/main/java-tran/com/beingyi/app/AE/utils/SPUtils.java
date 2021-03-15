package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.application.MyApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPUtils
{

    public static void putString(Context context,String name,String key,String value){

        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        Editor editor=sp.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getString(Context context,String name,String key){

        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        return sp.getString(key,"");
    }



    public static void putBoolean(Context context,String name,String key,boolean value){

        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        Editor editor=sp.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static boolean getBoolean(Context context,String name,String key){

        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }



    public static void putInt(Context context,String name,String key,int value){

        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        Editor editor=sp.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public static int getInt(Context context,String name,String key){

        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        return sp.getInt(key,0);
    }


    public static void putLong(Context context,String name,String key,long value){

        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        Editor editor=sp.edit();
        editor.putLong(key,value);
        editor.commit();
    }

    public static long getLong(Context context,String name,String key){

        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        return sp.getLong(key,0);
    }


    public static void clear(Context context,String name) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}

