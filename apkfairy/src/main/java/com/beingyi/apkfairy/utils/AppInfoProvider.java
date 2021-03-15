package com.beingyi.apkfairy.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppInfoProvider
{
    private Context context;
    ComUtils comUtils;
    private PackageManager packageManager;

    public AppInfoProvider(Context context)
    {

        this.context = context;
        comUtils = new ComUtils(context);
        this.packageManager = context.getPackageManager();
	}





    
    public List<AppInfo> getUserApps()
    {


        List<AppInfo> appList = new ArrayList<AppInfo>();
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);

        for (int i=0;i < packages.size();i++)
        { 
            PackageInfo packageInfo = packages.get(i); 
            AppInfo appInfo =new AppInfo(); 
            appInfo.appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            appInfo.packageName = packageInfo.packageName; 
            appInfo.versionName = packageInfo.versionName; 
            appInfo.appIcon = packageInfo.applicationInfo.loadIcon(packageManager);
            try
            {
                appInfo.path = packageManager.getApplicationInfo(appInfo.packageName, 0).sourceDir;
                appInfo.dataPath = packageManager.getApplicationInfo(appInfo.packageName, 0).dataDir;
            }
            catch (PackageManager.NameNotFoundException e)
            {
            }
            appInfo.size=FileSizeUtils.getAutoFileOrFilesSize(appInfo.path);
            appInfo.isBacked = isBacked(appInfo.appName + "_" + appInfo.versionName + ".apk");
            
            
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
            {
                appInfo.appType="用户软件";
                appList.add(appInfo);//如果非系统应用，则添加至appList
            }

        }



        return appList;

    }
    


    public List<AppInfo> getSystemApps()
    {


        List<AppInfo> appList = new ArrayList<AppInfo>();
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);

        for (int i=0;i < packages.size();i++)
        { 
            PackageInfo packageInfo = packages.get(i); 
            AppInfo appInfo =new AppInfo(); 
            appInfo.appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            appInfo.packageName = packageInfo.packageName; 
            appInfo.versionName = packageInfo.versionName; 
            appInfo.appIcon = packageInfo.applicationInfo.loadIcon(packageManager);
            try
            {
                appInfo.path = packageManager.getApplicationInfo(appInfo.packageName, 0).sourceDir;
                appInfo.dataPath = packageManager.getApplicationInfo(appInfo.packageName, 0).dataDir;
            }
            catch (PackageManager.NameNotFoundException e)
            {
            }
            appInfo.size=FileSizeUtils.getAutoFileOrFilesSize(appInfo.path);
            appInfo.isBacked = isBacked(appInfo.appName + "_" + appInfo.versionName + ".apk");

            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)
            {
                appInfo.appType="系统软件";
                appList.add(appInfo);
            }


        }



        return appList;

    }
    
    
    




    //判断是否备份
    public boolean isBacked(String appName)
    {
        boolean isbacked=false;



        File file=new File(comUtils.getBackPath() + appName);

        if (file.exists())
        {
            isbacked = true;
        }
        else
        {
            isbacked = false;
        }


        return isbacked;
    }





}
