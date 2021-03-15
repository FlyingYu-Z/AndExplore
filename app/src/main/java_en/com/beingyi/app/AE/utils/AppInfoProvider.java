package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
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
    private PackageManager packageManager;

    public AppInfoProvider(Context context)
    {

        this.context = context;
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

            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
            {
                appInfo.appType=BASE128.decode("n2yzeqWEobaM835C1wsl5A==");
                appList.add(appInfo);//如果非系统应用，则添加至appList
            }else{
                continue;
            }

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


            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)
            {
                appInfo.appType=BASE128.decode("AXckhmhIY/Uotl4C1YI7cA==");
                appList.add(appInfo);
            }else {
                continue;
            }

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


        }



        return appList;

    }
    
    
    



}
