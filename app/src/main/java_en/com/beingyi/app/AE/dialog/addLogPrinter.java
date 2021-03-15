package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
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








        final File outFile = new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + BASE128.decode("lTfOxwZ45LVCJGw528CfuQ=="));

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
                progres.setLabel(BASE128.decode("yqSHNZwQrWvKNgNtTwQbUA=="));
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
                            activity.showToast(BASE128.decode("N6UQguM7QYb6UGhjX3jEIw=="));
                            adapter.refresh();
                            adapter.setItemHighLight(outPath);
                        }
                    });

                } catch (Exception e) {
                    activity.showMessage(context, BASE128.decode("5x3zP/7tNw0g1zTyNGUeyQ=="), e.toString());
                }

                progres.dismiss();
            }
        }.start();


    }







}
