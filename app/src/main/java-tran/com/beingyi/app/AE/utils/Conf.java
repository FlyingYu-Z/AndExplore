package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;

import java.util.Locale;

public class Conf {


    Context context;

    public Conf(Context context) {

        this.context = context;
    }


    public static String defDir = FileUtils.getSDPath();


    public native static String getBase128Key(Context context);


    public String getAppColor() {
        String result = SPUtils.getString(context, "conf", "AppColor");
        if (!result.equals("")) {
            return result;
        }
        return "#29B188";
    }


    public void setAppColor(String color) {
        SPUtils.putString(context, "conf", "AppColor", color);
    }



    public String getLanguage() {
        String result = SPUtils.getString(context, "conf", "Language");
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
        SPUtils.putString(context, "conf", "Language", value);
    }


    public boolean isChinese(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }


    static {
        System.loadLibrary("BY");//#
    }



}
