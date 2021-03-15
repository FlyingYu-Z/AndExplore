package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;

import com.beingyi.app.AE.interfaces.GetSavePathCallBack;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.utils.ApkStringEncryptor;
import com.beingyi.app.AE.ApkTask.baseTask;
import com.beingyi.app.AE.utils.DexStringEncryptor;
import com.beingyi.app.AE.utils.FileUtils;
import com.beingyi.app.AE.ApkTask.InjectLogPrinter.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class addLogPrinter extends baseDialog {
    public addLogPrinter(Context mContext, int mWindow, String mPath) {
        super(mContext, mWindow, mPath);








        final File outFile = new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + "_log.apk");

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




    public void start(final String outPath) {


        final AlertProgress progres = new AlertProgress(context);
        new Thread() {
            @Override
            public void run() {
                progres.setLabel(MyApplication.getInstance().getString("5d459d550ae3aed4e3b5423d542b8d88"));
                progres.show();
                try {

                    InjectLogPrinterTask intectLogPrinterTask=new InjectLogPrinterTask(Path,outPath, new baseTask.DealCallBack() {
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
                    intectLogPrinterTask.start();

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
