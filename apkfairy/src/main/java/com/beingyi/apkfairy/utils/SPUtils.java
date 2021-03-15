package com.beingyi.apkfairy.utils;

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

    public static String getString(Context context,String name,String key,String mdefault){

        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        return sp.getString(key,mdefault);
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
    
    
}
