package com.beingyi.apkfairy.dialog;


import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import com.beingyi.apkfairy.*;
import com.beingyi.apkfairy.activity.*;
import com.beingyi.apkfairy.utils.*;
import java.util.*;

public class AppInfoDialog
{
    Context context;
    List<AppInfo> appinfos;
    AppInfo appinfo;
    ComUtils comUtils;
    Handler handler;
    MainActivity activity;
    String backpath;
    PackageManager packageManager;
    PackageInfo packageInfo;
    View view;
    
    
    public AppInfoDialog(Context context,List<AppInfo> appinfos,AppInfo appinfo,Handler handler,MainActivity activity){
        this.context = context;
        this.appinfos=appinfos;
        this.appinfo = appinfo;
        this.comUtils = new ComUtils(context);
        this.handler = handler;

        this.activity = activity;

        this.packageManager = context.getPackageManager();
        try
        {
            packageInfo=packageManager.getPackageInfo(appinfo.packageName, PackageManager.GET_ACTIVITIES);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            
        }
        

        appinfo.versionCode = packageInfo.versionCode;
        
        
        appinfo.installTime=comUtils.getInstallTime(appinfo.path);

        try
        {
            appinfo.MD5 = FileUtils.getMd5ByFile(appinfo.path);
        }
        catch (Exception e)
        {
            appinfo.MD5="";
        }
        appinfo.sig=comUtils.getAppSign(appinfo.packageName)+"";
        appinfo.signature=comUtils.getAppSignature(appinfo.packageName);
        appinfo.UID=packageInfo.applicationInfo.uid+"";
        
        
        
        String[] permission=comUtils.getAppPremission(appinfo.packageName);
		try{
        appinfo.permission="";
        for(int i=0;i<permission.length;i++){
            appinfo.permission=appinfo.permission+"\n"+permission[i];
        }
        }catch(Exception e){
			
		}
		
		
		
        backpath = comUtils.getBackPath();
        
        LayoutInflater inflater=LayoutInflater.from(context);
        view = inflater.inflate(R.layout.view_appinfo, null);
        
        ((TextView)view.findViewById(R.id.viewappinfoTextView2)).setText("包名："+appinfo.packageName);
        ((TextView)view.findViewById(R.id.viewappinfoTextView3)).setText("版本名："+appinfo.versionName);
        ((TextView)view.findViewById(R.id.viewappinfoTextView4)).setText("版本号："+appinfo.versionCode);
        ((TextView)view.findViewById(R.id.viewappinfoTextView5)).setText("文件大小："+appinfo.size);
        ((TextView)view.findViewById(R.id.viewappinfoTextView6)).setText("安装包路径："+appinfo.path);
        ((TextView)view.findViewById(R.id.viewappinfoTextView7)).setText("数据路径："+appinfo.dataPath);
        ((TextView)view.findViewById(R.id.viewappinfoTextView8)).setText("软件类型："+appinfo.appType);
        ((TextView)view.findViewById(R.id.viewappinfoTextView9)).setText("安装时间："+appinfo.installTime);
        ((TextView)view.findViewById(R.id.viewappinfoTextView10)).setText("加固类型:"+appinfo.jiaguType);
        ((TextView)view.findViewById(R.id.viewappinfoTextView11)).setText("MD5："+appinfo.MD5);
        ((TextView)view.findViewById(R.id.viewappinfoTextView12)).setText("数字签名："+appinfo.sig);
        ((TextView)view.findViewById(R.id.viewappinfoTextView13)).setText("UID："+appinfo.UID);
        ((TextView)view.findViewById(R.id.viewappinfoTextView14)).setText("权限："+appinfo.permission);
        ((TextView)view.findViewById(R.id.viewappinfoTextView15)).setText("签名哈西："+appinfo.signature);
        
        
        
        AlertDialog dialog = new AlertDialog.Builder(context)

            .setTitle(appinfo.appName)
            .setIcon(appinfo.appIcon)
            .setView(view)
            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            })
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            }).create();
        dialog.show();
        
        
        
    }
    
    
    
    
    
}
