package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
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
        String name =BASE128.decode("FzrznU3UuD8KQAiBS9XCeA==");
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
    
    public static String getSignature(Context context)
    {
        try {
            /** ???????????????????????????????????????????????????????????? **/
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            /******* ?????????????????????????????????????????? *******/
            Signature[] signatures = packageInfo.signatures;
            /******* ?????????????????????????????????????????? *******/
            return signatures[0].toCharsString();
            /************** ?????????????????? **************/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    
}
