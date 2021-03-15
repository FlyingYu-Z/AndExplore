package com.beingyi.apkfairy.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class version
{

    //获取当前程序的版本名
    
    public static String getVersionName(Context context) throws Exception
    {
        //获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        Log.e("TAG", "版本号" + packInfo.versionCode);
        Log.e("TAG", "版本名" + packInfo.versionName);
        return packInfo.versionName;
    }

    /*
     * 获取当前程序的版本号
     */
    public static int getVersionCode(Context context) throws Exception{
        //获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        Log.e("TAG","版本号"+packInfo.versionCode);
        Log.e("TAG","版本名"+packInfo.versionName);
        return packInfo.versionCode;
    }
    
    
    
    
    
}
