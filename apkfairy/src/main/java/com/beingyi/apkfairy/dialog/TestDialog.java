package com.beingyi.apkfairy.dialog;

import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import com.beingyi.apkfairy.*;
import com.beingyi.apkfairy.activity.*;
import com.beingyi.apkfairy.utils.*;
import java.util.*;

public class TestDialog
{
    Context context;
    List<AppInfo> appinfos;
    AppInfo appinfo;
    ComUtils comUtils;
    Handler handler;
    MainActivity activity;
    String backpath;

    View view;


    public TestDialog(Context context,List<AppInfo> appinfos,AppInfo appinfo,Handler handler,MainActivity activity){
        this.context = context;
        this.appinfos=appinfos;
        this.appinfo = appinfo;
        this.comUtils = new ComUtils(context);
        this.handler = handler;

        this.activity = activity;

        backpath = comUtils.getBackPath();

        LayoutInflater inflater=LayoutInflater.from(context);
        view = inflater.inflate(R.layout.view_appinfo, null);


        AlertDialog dialog = new AlertDialog.Builder(context)

            .setTitle("详细信息")
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
