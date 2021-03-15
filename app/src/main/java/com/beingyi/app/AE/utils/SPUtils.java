package com.beingyi.app.AE.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.beingyi.app.AE.application.MyApplication;

public class SPUtils
{

    public static void putString(String name,String key,String value){
        Context context= MyApplication.getContext();
        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        Editor editor=sp.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getString(String name,String key){
        Context context= MyApplication.getContext();
        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        return sp.getString(key,"");
    }



    public static void putBoolean(String name,String key,boolean value){
        Context context= MyApplication.getContext();
        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        Editor editor=sp.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static boolean getBoolean(String name,String key){
        Context context= MyApplication.getContext();
        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }



    public static void putInt(String name,String key,int value){
        Context context= MyApplication.getContext();
        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        Editor editor=sp.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public static int getInt(String name,String key){
        Context context= MyApplication.getContext();
        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        return sp.getInt(key,0);
    }


    public static void putLong(String name,String key,long value){
        Context context= MyApplication.getContext();
        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        Editor editor=sp.edit();
        editor.putLong(key,value);
        editor.commit();
    }

    public static long getLong(String name,String key){
        Context context= MyApplication.getContext();
        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        return sp.getLong(key,0);
    }


    public static void clear(String name) {
        Context context= MyApplication.getContext();
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}

