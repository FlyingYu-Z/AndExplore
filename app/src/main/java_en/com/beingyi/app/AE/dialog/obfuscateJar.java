package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
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

        String defaultAndJAR=FileUtils.getSDPath()+BASE128.decode("NiwLZ3bwrtvE2KaB4gewTW+QipKA7lM+8Q3wUS+gaGU=");
        String defaultProFile=FileUtils.getSDPath()+BASE128.decode("FP68M3ih1jB8IamYpE7qpNVUwrEhr6Z82Y3ZhmeUlVc=");

        if(!new File(defaultProFile).exists()){
            BYProtectUtils.copyAssetsFile(BASE128.decode("ovOKy3XtxwbA6zskw56ClA=="),defaultProFile);
        }

        ed_AndJar=view.findViewById(R.id.view_obfuscate_conf_EditText_jar);
        ed_ProFile=view.findViewById(R.id.view_obfuscate_conf_EditText_proFile);

        ed_AndJar.setTag(this.getClass().getSimpleName()+BASE128.decode("eIxS8gGlW4nqWmeQOI8qYw=="));
        ed_ProFile.setTag(this.getClass().getSimpleName()+BASE128.decode("wn5NeaQn+hkPzN8nfyWgmp+WmKEgTc4kZ3IvMtYSjYc="));

        if(ed_AndJar.getText().toString().equals(BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="))){
            ed_AndJar.setText(defaultAndJAR);
        }

        if(ed_ProFile.getText().toString().equals(BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="))){
            ed_ProFile.setText(defaultProFile);
        }


        dialog = new AlertDialog.Builder(context)
                .setTitle(BASE128.decode("koI9dokMnpgxOEfPo4+FcQ=="))
                .setView(view)
                .setCancelable(true)
                .setNegativeButton(BASE128.decode("gba256PHP6LGkSXBwqlv8Q=="), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .setPositiveButton(BASE128.decode("GXe18lf7nDG5Ik/56BTUhw=="), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        androidJar=ed_AndJar.getText().toString();
                        proFile=ed_ProFile.getText().toString();

                        if(!new File(androidJar).exists()){
                            tip();
                            return;
                        }


                        final File outFile = new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + BASE128.decode("+ZBFL3N7AJf1Xexs4BHwQQ=="));

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
                .setMessage(BASE128.decode("PzurrH2pfZuWTz56pLVDoDVatxEv4D3aNvzbwGfkfhd5Qi2FjWKe3WaOFiPr0QMl"))
                .setCancelable(true)
                .setNegativeButton(BASE128.decode("gba256PHP6LGkSXBwqlv8Q=="), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();

                    }
                })
                .setPositiveButton(BASE128.decode("GXe18lf7nDG5Ik/56BTUhw=="), new DialogInterface.OnClickListener() {
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
        pd.setMessage(BASE128.decode("vQ0rLelmI1O26jP/B/U7Kg=="));
        pd.show();

        final File file = new File(androidJar);
        RequestParams requestParams = new RequestParams(BASE128.decode("5GTf+He+LQ6BQJAHujtuezQcsJcnFPEAoxLdda41N75BfAIDPcFLq0ASd9dTI/zg"));
        requestParams.setSaveFilePath(file.getAbsolutePath());
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {

            @Override
            public void onSuccess(File result) {
                new obfuscateJar(context,window,Path);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                new alert(context, BASE128.decode("UrmwU2iLdg0zoUpjy12ayg==") + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                new alert(context, BASE128.decode("UrmwU2iLdg0zoUpjy12ayg=="));
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
                progres.setLabel(BASE128.decode("NG6/2bPeC5kiLGc6CtSQOA=="));
                progres.setNoProgress();
                progres.show();
                try
                {
                    ArrayList<String> args = new ArrayList<>();
                    args.add(BASE128.decode("oz35KZXLE6dZBArEEoxFhA=="));
                    args.add(androidJar);
                    //args.add("-libraryjars");
                    //args.add("sdcard/ProGuard/org.apache.http.legacy.jar");

                    args.add(BASE128.decode("nY47i7Dt3Wk66Kgho2gExg=="));
                    args.add(proFile);
                    args.add(BASE128.decode("QIA1PQItojFynMeKcEr4qA=="));
                    args.add(Path);
                    args.add(BASE128.decode("rG/6Y68G9eItx9twy9XYaA=="));
                    args.add(outPath);

                    String[] arg = args.toArray(new String[args.size()]);
                    ProGuard.main(arg);
                    args.clear();

                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run()
                        {
                            activity.showToast(BASE128.decode("o7cMJIoxm6yu6goDi7THNg=="));
                            adapter.refresh();
                            adapter.setItemHighLight(outPath);
                        }
                    });

                }
                catch (Exception e)
                {
                    activity.showMessage(context, BASE128.decode("5x3zP/7tNw0g1zTyNGUeyQ=="), e.toString());
                }

                progres.dismiss();
            }
        }.start();


    }



}
