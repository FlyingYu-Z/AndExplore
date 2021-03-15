package com.beingyi.app.AE.application;

import com.beingyi.app.AE.application.MyApplication;
import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

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
                .setDbName("AppData")
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
        String result = "";
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
        if(conf.getLanguage().equals("en")) {
            return BYProtectUtils.readAssetsTxt("language/English.txt");
        }else{
            return BYProtectUtils.readAssetsTxt("language/Chinese.txt");
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
        return "";
    }


}


