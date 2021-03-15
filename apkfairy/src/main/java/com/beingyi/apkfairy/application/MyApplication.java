package com.beingyi.apkfairy.application;

import android.app.Application;
import android.util.Log;
import com.beingyi.apkfairy.crash.CrashHandler;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

public class MyApplication extends Application
{


	private DbManager.DaoConfig daoConfig;
    public DbManager.DaoConfig getDaoConfig()
    {
        return daoConfig;
    }

	@Override
    public void onCreate()
    {
        super.onCreate();

		//CrashHandler crashHandler = new CrashHandler();
        //crashHandler.init(getApplicationContext());

        x.Ext.init(this);
        x.Ext.setDebug(false); // 开启debug会影响性能

        daoConfig = new DbManager.DaoConfig()
            .setDbName("Backup.db")//创建数据库的名称
            .setDbVersion(1)//数据库版本号

            //设置表创建的监听
            .setTableCreateListener(new DbManager.TableCreateListener(){

                @Override
                public void onTableCreated(DbManager db, TableEntity<?> table)
                {
                    Log.i("JAVA", "onTableCreated：" + table.getName());
                }
            })

            //设置数据库更新的监听
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion)
                {

                }
            })
            //设置数据库打开的监听
            .setDbOpenListener(new DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(DbManager db)
                {
                    //开启数据库支持多线程操作，提升性能
                    db.getDatabase().enableWriteAheadLogging();
                }
            });

        DbManager db = x.getDb(daoConfig);



    }



}
