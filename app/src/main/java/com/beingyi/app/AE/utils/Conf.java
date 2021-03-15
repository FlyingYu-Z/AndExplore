package com.beingyi.app.AE.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.beingyi.app.AE.application.MyApplication;

import java.util.Locale;

public class Conf {


    Context context;

    public Conf(Context context) {

        this.context = context;
    }


    public static String defDir = FileUtils.getSDPath();


    public native static String getBase128Key(Context context);


    public String getAppColor() {
        String result = SPUtils.getString( "conf", "AppColor");
        if (!result.equals("")) {
            return result;
        }
        return "#29B188";
    }


    public void setAppColor(String color) {
        SPUtils.putString( "conf", "AppColor", color);
    }



    public String getLanguage() {
        String result = SPUtils.getString( "conf", "Language");
        if (result.equals("")) {

            if(!isChinese(context)){
                return "en";
            }else{
                return "zh";
            }

        }

        return result;
    }


    public void setLanguage(String value) {
        SPUtils.putString( "conf", "Language", value);
    }


    public boolean isChinese(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }



    public boolean getUseKey(){
        SharedPreferences shp = PreferenceManager.getDefaultSharedPreferences(context);
        return shp.getBoolean("useKey", false);
    }

    public void setKeyStorePath(String value){
        SPUtils.putString("conf","keystore_path",value);
    }
    public String getKeyStorePath(){
        return SPUtils.getString("conf","keystore_path");
    }


    public void setKeyStorePw(String value){
        SPUtils.putString("conf","keystore_keystorePw",value);
    }
    public String getKeyStorePw(){
        return SPUtils.getString("conf","keystore_keystorePw");
    }


    public void setCertAlias(String value){
        SPUtils.putString("conf","keystore_certAlias",value);
    }
    public String getCertAlias(){
        return SPUtils.getString("conf","keystore_certAlias");
    }


    public void setCertPw(String value){
        SPUtils.putString("conf","keystore_certPw",value);
    }
    public String getCertPw(){
        return SPUtils.getString("conf","keystore_certPw");
    }





    static {
        System.loadLibrary("BY");//#
    }



}
