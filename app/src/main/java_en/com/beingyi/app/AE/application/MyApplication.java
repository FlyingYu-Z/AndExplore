package com.beingyi.app.AE.application;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.beingyi.app.AE.crash.CrashHandler;
import com.beingyi.app.AE.utils.BYProtectUtils;
import com.beingyi.app.AE.utils.Conf;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.x;

import java.util.Locale;

public class MyApplication extends MultiDexApplication {


    private DbManager.DaoConfig daoConfig;
    public DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }
    private static Context mContext;
    static MyApplication myApp;

    Conf conf;

    @Override
    protected void attachBaseContext(android.content.Context base) {
        super.attachBaseContext(base);

    }

    public void init(){
        conf=new Conf(mContext);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        mContext = getApplicationContext();
        CrashHandler crashHandler = new CrashHandler();
        crashHandler.init(getApplicationContext());


        init();

        x.Ext.init(this);
        x.Ext.setDebug(false);

        daoConfig = new DbManager.DaoConfig()
                .setDbName(AesUtil.decode("qvxE1XUYuGjXfbezRXaLaQ=="))
                .setDbVersion(1)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                    }
                });


    }

    public static Context getContext() {
        return mContext;
    }

    public static MyApplication getInstance() {
        return myApp;
    }

    JSONObject langJson;

    public String getString(String md5) {
        String result = AesUtil.decode("BQGHoM3lqYcsurCRq3PlUw==");
        try {

            if (langJson == null) {
                langJson = new JSONObject(getLanguageTxt());
            }

            result=langJson.getString(md5);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getLanguageTxt() {
        if(conf.getLanguage().equals(AesUtil.decode("UrB6fBHzOG6wNBA0QpiHkA=="))) {
            MyApplication.getInstance().setLanguage(Locale.ENGLISH);
            return BYProtectUtils.readAssetsTxt(AesUtil.decode("V2yUTpxiKJfkLWv+8Kr8O9sQ455GsXrBv0WRPwnn1a4="));
        }else{
            MyApplication.getInstance().setLanguage(Locale.SIMPLIFIED_CHINESE);
            return BYProtectUtils.readAssetsTxt(AesUtil.decode("WQFl3D8KcVBVgLUgCTWCjNsQ455GsXrBv0WRPwnn1a4="));
        }

    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        System.exit(0);
    }

    private String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return AesUtil.decode("BQGHoM3lqYcsurCRq3PlUw==");
    }

    public void setLanguage(Locale locale){
        //Locale.setDefault(locale);
        //设置语言类型
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        // 应用用户选择语言
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        }else{
            configuration.locale = locale;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocales(new LocaleList(locale));
            createConfigurationContext(configuration);
        }else{
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }

    }


}
