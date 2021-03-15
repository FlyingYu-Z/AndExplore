package com.beingyi.app.AE.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.beingyi.app.AE.ApkTask.ConexistTask.CloneAPK;
import com.beingyi.app.AE.interfaces.GetSavePathCallBack;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.ApkTask.baseTask;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.APKUtils;
import com.beingyi.app.AE.utils.FileUtils;
import java.io.File;



public class makeConexist extends baseDialog {

    String destPkg;

    public makeConexist(Context mContext, int mWindow, String mPath) {
        super(mContext, mWindow, mPath);



        String defaultPkg= APKUtils.getPkgName(context,Path);

        AppCompatEditText ed_pkg=new AppCompatEditText(context);
        ed_pkg.setText(defaultPkg+Math.random() * 5);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("制作共存")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定",null)
                .create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pkg=ed_pkg.getText().toString();
                String[] array=pkg.split(".");
                for (String a:array){
                    if(a.equals("") ||a==null){
                        ToastUtils.show("包名不合法");
                        return;
                    }
                }

                destPkg=pkg;

                final File outFile = new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + "_clone.apk");

                new getSavePath(context, window, outFile.getAbsolutePath(), new GetSavePathCallBack() {

                    @Override
                    public void onSuccess(String filePath) {
                        start(filePath);
                    }

                    @Override
                    public void onCancel() {

                    }

                });


            }
        });


    }


    public void start(final String outPath) {


        final AlertProgress progres = new AlertProgress(context);
        new Thread() {
            @Override
            public void run() {
                progres.setLabel("处理中");
                progres.show();
                try {

                    CloneAPK cloneAPK=new CloneAPK(context,Path,outPath,destPkg,new baseTask.DealCallBack() {
                        @Override
                        public void onStep(String Name) {
                            progres.setTitle(Name);
                        }

                        @Override
                        public void onLabel(String Name) {
                            progres.setLabel(Name);
                        }

                        @Override
                        public void onProgress(int progress, int total) {
                            progres.setProgress(progress, total);
                        }

                        @Override
                        public void onSaveProgress(int progress, int total) {
                            progres.setProgress(progress, total);
                        }
                    });


                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.showToast("操作完成");
                            adapter.refresh();
                            adapter.setItemHighLight(outPath);
                        }
                    });

                } catch (Exception e) {
                    activity.showMessage(context, "错误：", e.toString());
                }

                progres.dismiss();
            }
        }.start();


    }







}
