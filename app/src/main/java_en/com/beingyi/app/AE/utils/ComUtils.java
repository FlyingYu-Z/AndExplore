package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.content.pm.ActivityInfo;

public class ComUtils
{

    Context context;

    public ComUtils(Context context)
    {

        this.context = context;

    }



    public void setBackPath()
    {



    }

    public String getBackPath()
    {

        String backpath;

        boolean ishaspath=SPUtils.getBoolean(context, BASE128.decode("W2pJPq03yxukoKDvS7KV3w=="), BASE128.decode("ov1z9zg4vIGfQFV7PYUzlw=="));

        if (ishaspath)
        {
            backpath = SPUtils.getString(context, BASE128.decode("W2pJPq03yxukoKDvS7KV3w=="), BASE128.decode("Qquyq8nPzVdqLz8GFE0bug=="));
        }
        else
        {
            backpath = FileUtils.getSDPath() + BASE128.decode("S+2XzMaNh1DR7lUqzssIHQ==");

        }
        return backpath;
    }


    public static ArrayList getActivities(Context ctx)
    {
        ArrayList result = new ArrayList();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.setPackage(ctx.getPackageName());
        for (ResolveInfo info : ctx.getPackageManager().queryIntentActivities(intent, 0))
        {
            result.add(info.activityInfo.name);

        }
        return result;
    }


    /*
     * 获取程序的Activity
     */
    public ArrayList getAppActivity(String packname)
    {
        ArrayList result = new ArrayList();
        
        PackageManager pm=context.getPackageManager();

        try
        {
            PackageInfo packinfo = pm.getPackageInfo(packname, PackageManager.GET_ACTIVITIES);
            //获取到所有的Activity
            for (ActivityInfo s:packinfo.activities)
            {
                result.add(s.name);
            }

            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        return null;
    }





    /*
     * 获取程序的权限
     */
    public String[] getAppPremission(String packname)
    {
        PackageManager pm=context.getPackageManager();

        try
        {
            PackageInfo packinfo = pm.getPackageInfo(packname, PackageManager.GET_PERMISSIONS);
            //获取到所有的权限
            return packinfo.requestedPermissions;

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        return null;
    }





    /*
     * 获取程序的签名哈西
     */
    public String getAppSignature(String packname)
    {
        PackageManager pm=context.getPackageManager();
        try
        {
            PackageInfo packinfo = pm.getPackageInfo(packname, PackageManager.GET_SIGNATURES);
            //获取到所有的权限
            return packinfo.signatures[0].toCharsString();

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        return null;
    }



    //获取数字签名
    public int getAppSign(String packname)
    {


        PackageManager pm = context.getPackageManager();  
        PackageInfo pi = null;  
        int sig = 0;  
        try
        {  
            pi = pm.getPackageInfo(packname, PackageManager.GET_SIGNATURES);  
            Signature[] s = pi.signatures;  
            sig = s[0].hashCode();    
        }
        catch (Exception e1)
        {  
            sig = 0;  
            e1.printStackTrace();  
        }  
        return sig;

    }


    public String getInstallTime(String path)
    {

        File file=new File(path);
        if (file.exists())
        {
            System.out.println(BASE128.decode("YueBVCZtSdVsXH4uTnGdaQ=="));
        }
        long time=file.lastModified();
        SimpleDateFormat formatter = new  
            SimpleDateFormat(BASE128.decode("YWbIUHTuDJ67rPtCbvij4rVYA8qtZwjRwMdzcj0ahF8="));  
        String result=formatter.format(time);
        return result;
    }



}
