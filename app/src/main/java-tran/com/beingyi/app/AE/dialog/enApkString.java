package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.interfaces.GetSavePathCallBack;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.ui.SPEditText;
import com.beingyi.app.AE.utils.ApkStringEncryptor;
import com.beingyi.app.AE.utils.DexStringEncryptor;
import com.beingyi.app.AE.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class enApkString extends baseDialog {

    AlertDialog dialog;

    public enApkString(Context mContext, int mWindow, String mPath) {
        super(mContext, mWindow, mPath);

        View view=View.inflate(context,R.layout.view_enstr_conf,null);

        String mkeep="android.support\njavax\nandroid.app\ncom.jsdroid.antlr";

        SPEditText ed_keep=view.findViewById(R.id.view_enstr_conf_EditText_keep);
        ed_keep.setHistory(this.getClass().getSimpleName()+"keep");
        ed_keep.setHint(mkeep);

        if(ed_keep.getText().toString().isEmpty()){
            ed_keep.setText(mkeep);
        }

        dialog = new AlertDialog.Builder(context)
                .setTitle(MyApplication.getInstance().getString("658358ab37e9fc41a4524e41026ff4dc"))
                .setView(view)
                .setCancelable(false)
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

                        String keep=ed_keep.getText().toString();

                        final File outFile = new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + "_enStr.apk");

                        new getSavePath(context, window, outFile.getAbsolutePath(), new GetSavePathCallBack() {

                            @Override
                            public void onSuccess(String filePath) {
                                //如果配置不为空
                                if(!keep.equals("")) {
                                    String[] keepArray = keep.split("\n");
                                    List<String> keeps=new ArrayList<>();

                                    for(int i=0;i<keepArray.length;i++){
                                        String k=keepArray[i];
                                        if(k.length()>0) {
                                            k="L"+k.replace(".","/");
                                            keeps.add(k);
                                        }
                                    }
                                    encryptString(filePath,keeps);
                                }else{
                                    encryptString(filePath,null);
                                }
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




    public void encryptString(final String outPath, List<String> keeps) {


        final AlertProgress progres = new AlertProgress(context);
        new Thread() {
            @Override
            public void run() {
                progres.setLabel(MyApplication.getInstance().getString("5d459d550ae3aed4e3b5423d542b8d88"));
                progres.show();
                try {

                    ApkStringEncryptor apkStringEncryptor=new ApkStringEncryptor(Path, outPath, keeps, new DexStringEncryptor.EncryptCallBack() {
                        @Override
                        public void onProgress(int progress, int total) {
                            progres.setProgress(progress, total);
                        }

                        @Override
                        public void onClassDefName(String Name) {
                            progres.setLabel(Name);
                        }

                    }, new ApkStringEncryptor.UICallBack() {
                        @Override
                        public void onStep(String Name) {
                            progres.setTitle(Name);
                        }

                        @Override
                        public void onSaveProgress(int progress, int total) {
                            progres.setProgress(progress, total);
                        }
                    });

                    apkStringEncryptor.start();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.showToast(MyApplication.getInstance().getString("04fe5af3f8e4e5693c8edc8259066434"));
                            adapter.refresh();
                            adapter.setItemHighLight(outPath);
                        }
                    });

                } catch (Exception e) {
                    activity.showMessage(context, MyApplication.getInstance().getString("22af5f9e0c3f5aaba469cb807298386e"), e.toString());
                }

                progres.dismiss();
            }
        }.start();


    }




}
