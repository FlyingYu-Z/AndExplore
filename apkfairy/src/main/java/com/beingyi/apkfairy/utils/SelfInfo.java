package com.beingyi.apkfairy.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

public class SelfInfo
{
    
    

    public static int versionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }



    public static String versionName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name ="";
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }
    
    
    public static String apkpath(Context context){
        
        String path=context.getPackageResourcePath();
        return path;
    }
    
    
    
    public static int getSign(Context context){
        

        PackageManager manager;
        PackageInfo packageInfo;
        Signature[] signatures;
        StringBuilder builder;
        String signature;



        PackageManager pm = context.getPackageManager();  
        PackageInfo pi = null;  
        int sig = 0;  
        try {  
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);  
            Signature[] s = pi.signatures;  
            sig = s[0].hashCode();    
        } catch (Exception e1) {  
            sig = 0;  
            e1.printStackTrace();  
        }  
        return sig;
        
    }
    
    
    
}
