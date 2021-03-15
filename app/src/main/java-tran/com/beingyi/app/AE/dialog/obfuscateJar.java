package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.interfaces.GetSavePathCallBack;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.ui.SPEditText;
import com.beingyi.app.AE.utils.APKUtils;
import com.beingyi.app.AE.utils.BYProtectUtils;
import com.beingyi.app.AE.utils.FileUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import proguard.ProGuard;

public class obfuscateJar extends baseDialog {

    AlertDialog dialog;

    SPEditText ed_AndJar;
    SPEditText ed_ProFile;


    String androidJar;
    String proFile;


    public obfuscateJar(Context mContext, int mWindow, String mPath) {
        super(mContext, mWindow, mPath);

        View view=View.inflate(context,R.layout.view_obfuscate_conf,null);

        String defaultAndJAR=FileUtils.getSDPath()+"/AE/obfuscate/android.jar";
        String defaultProFile=FileUtils.getSDPath()+"/AE/obfuscate/proguard_cfg1.pro";

        if(!new File(defaultProFile).exists()){
            BYProtectUtils.copyAssetsFile("ProGuard.Pro",defaultProFile);
        }

        ed_AndJar=view.findViewById(R.id.view_obfuscate_conf_EditText_jar);
        ed_ProFile=view.findViewById(R.id.view_obfuscate_conf_EditText_proFile);

        ed_AndJar.setTag(this.getClass().getSimpleName()+"android.jar");
        ed_ProFile.setTag(this.getClass().getSimpleName()+"proguard_cfg1.pro");

        if(ed_AndJar.getText().toString().equals("")){
            ed_AndJar.setText(defaultAndJAR);
        }

        if(ed_ProFile.getText().toString().equals("")){
            ed_ProFile.setText(defaultProFile);
        }


        dialog = new AlertDialog.Builder(context)
                .setTitle(MyApplication.getInstance().getString("e5cf04f650453808979eaeced0276937"))
                .setView(view)
                .setCancelable(true)
                .setNegativeButton(MyApplication.getInstance().getString("625fb26b4b3340f7872b411f401e754c"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .setPositiveButton(MyApplication.getInstance().getString("38cf16f2204ffab8a6e0187070558721"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        androidJar=ed_AndJar.getText().toString();
                        proFile=ed_ProFile.getText().toString();

                        if(!new File(androidJar).exists()){
                            tip();
                            return;
                        }


                        final File outFile = new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + "_obfuscated.jar");

                        new getSavePath(context, window, outFile.getAbsolutePath(), new GetSavePathCallBack() {

                            @Override
                            public void onSuccess(String filePath) {
                                obfuscateJar(filePath);
                            }

                            @Override
                            public void onCancel() {

                            }

                        });

                    }
                })
                .create();
        dialog.show();



    }



    public void tip(){

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setMessage(MyApplication.getInstance().getString("3d650f78422971815e5e22282c90b242"))
                .setCancelable(true)
                .setNegativeButton(MyApplication.getInstance().getString("625fb26b4b3340f7872b411f401e754c"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();

                    }
                })
                .setPositiveButton(MyApplication.getInstance().getString("38cf16f2204ffab8a6e0187070558721"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        downoadAndJar();
                    }
                })
                .create();
        alertDialog.show();



    }



    public void downoadAndJar(){

        final ProgressDialog pd;
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage(MyApplication.getInstance().getString("e4090eb70c2d57fd6f825907ea168fc0"));
        pd.show();

        final File file = new File(androidJar);
        RequestParams requestParams = new RequestParams("http://47.52.35.71/Farmer/android.jar");
        requestParams.setSaveFilePath(file.getAbsolutePath());
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {

            @Override
            public void onSuccess(File result) {
                new obfuscateJar(context,window,Path);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                new alert(context, MyApplication.getInstance().getString("65e200d30c86a93c46dcafaa151028f5") + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                new alert(context, MyApplication.getInstance().getString("65e200d30c86a93c46dcafaa151028f5"));
            }

            @Override
            public void onFinished() {
                pd.dismiss();
            }

            @Override
            public void onWaiting() {
            }

            @Override
            public void onStarted() {
            }
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                pd.setMax((int) total);
                pd.setProgress((int) current);
            }
        });

    }


    public void obfuscateJar(final String outPath){


        final AlertProgress progres=new AlertProgress(context);
        new Thread(){
            @Override
            public void run()
            {
                progres.setLabel(MyApplication.getInstance().getString("f013ea9dcba3f5ca1278aa850931fec8"));
                progres.setNoProgress();
                progres.show();
                try
                {
                    ArrayList<String> args = new ArrayList<>();
                    args.add("-libraryjars");
                    args.add(androidJar);
                    //args.add("-libraryjars");
                    //args.add("sdcard/ProGuard/org.apache.http.legacy.jar");

                    args.add("-include");
                    args.add(proFile);
                    args.add("-injars");
                    args.add(Path);
                    args.add("-outjars");
                    args.add(outPath);

                    String[] arg = args.toArray(new String[args.size()]);
                    ProGuard.main(arg);
                    args.clear();

                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run()
                        {
                            activity.showToast(MyApplication.getInstance().getString("27eddfe070792903f3c9268ecf3b3e01"));
                            adapter.refresh();
                            adapter.setItemHighLight(outPath);
                        }
                    });

                }
                catch (Exception e)
                {
                    activity.showMessage(context, MyApplication.getInstance().getString("22af5f9e0c3f5aaba469cb807298386e"), e.toString());
                }

                progres.dismiss();
            }
        }.start();


    }



}
