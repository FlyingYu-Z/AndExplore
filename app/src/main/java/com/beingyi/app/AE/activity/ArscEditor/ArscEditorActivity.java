package com.beingyi.app.AE.activity.ArscEditor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.activity.ArscEditor.ArscLib.AndrolibResources;
import com.beingyi.app.AE.activity.ArscEditor.ArscLib.ResDecoder.ARSCCallBack;
import com.beingyi.app.AE.activity.ArscEditor.ArscLib.ResDecoder.data.ResTable;
import com.beingyi.app.AE.activity.ArscEditor.ArscLib.Utils;
import com.beingyi.app.AE.base.BaseActivity;
import com.beingyi.app.AE.dialog.alert;
import com.beingyi.app.AE.ui.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class ArscEditorActivity extends BaseActivity {


    Context context;
    ProgressDialog progress;


    String Path;
    AndrolibResources androlibResources;

    public void init(){
        context=this;
        progress = new ProgressDialog(context);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("加载中");


    }



    public static final int ShowProgress = 1;
    public static final int DismissProgress = 2;
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ShowProgress:
                    progress.show();
                    break;

                case DismissProgress:
                    progress.dismiss();
                    break;


            }

        }
    };


    public void initParseArsc(){
        androlibResources=new AndrolibResources();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arsc_editor);
        init();

        Intent intent=getIntent();
        Path=intent.getStringExtra("Path");

        initParseArsc();
        new Thread(){
            @Override
            public void run(){
                handler.sendEmptyMessage(ShowProgress);
                try {

                    /**
                    ResTable resTable=androlibResources.getResTable(new FileInputStream(Path));
                    resTable.setPackageOriginal("com.kk.vv");
                    androlibResources.decodeARSC(resTable, new ARSCCallBack() {
                        @Override
                        public void back(String config, String type, String key, String value) {

                        }
                    });**/



                    Utils utils = new Utils();
                    utils.openArsc(Path);
                    utils.getResouces("string", "[DEFAULT]");
                    utils.changeResouce("app_name", "很完但快跑");
                    utils.getLib().mARSCDecoder.CloneArsc(new FileOutputStream(Path+"_.arsc"),"c",true);
                    utils.saveArsc(Path,
                            Path+"_out.arsc");

                    new alert(context,androlibResources.mARSCDecoder.getPackageName());

                } catch (Exception e) {
                    e.printStackTrace();
                    new alert(context,getExceptionAllinformation(e));
                }

                handler.sendEmptyMessage(DismissProgress);

            }
        }.start();



    }


    public static String getExceptionAllinformation(Exception ex) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        ex.printStackTrace(pout);
        String ret = new String(out.toByteArray());
        pout.close();
        try {
            out.close();
        } catch (Exception e) {
        }
        return ret;
    }



}
