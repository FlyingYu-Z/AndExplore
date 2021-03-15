package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.content.Context;

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
        String result = SPUtils.getString(context, AesUtil.decode("m/tqqWaLuEK0TecUvLfICQ=="), AesUtil.decode("IEJCTruefCMk8me+GpsrJw=="));
        if (!result.equals(AesUtil.decode("BQGHoM3lqYcsurCRq3PlUw=="))) {
            return result;
        }
        return AesUtil.decode("qElT95QlrQEKBVolQLVlPw==");
    }


    public void setAppColor(String color) {
        SPUtils.putString(context, AesUtil.decode("m/tqqWaLuEK0TecUvLfICQ=="), AesUtil.decode("IEJCTruefCMk8me+GpsrJw=="), color);
    }



    public String getLanguage() {
        String result = SPUtils.getString(context, AesUtil.decode("m/tqqWaLuEK0TecUvLfICQ=="), AesUtil.decode("zgq0YNRAjXkIuh5kEXzD7A=="));
        if (result.equals(AesUtil.decode("BQGHoM3lqYcsurCRq3PlUw=="))) {

            if(!isChinese(context)){
                return AesUtil.decode("UrB6fBHzOG6wNBA0QpiHkA==");
            }else{
                return AesUtil.decode("xh0GCwKdjIGMESOPd45VTw==");
            }

        }

        return result;
    }


    public void setLanguage(String value) {
        SPUtils.putString(context, AesUtil.decode("m/tqqWaLuEK0TecUvLfICQ=="), AesUtil.decode("zgq0YNRAjXkIuh5kEXzD7A=="), value);
    }


    public boolean isChinese(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith(AesUtil.decode("xh0GCwKdjIGMESOPd45VTw==")))
            return true;
        else
            return false;
    }


    static {
        System.loadLibrary("BY");//#
    }



}
