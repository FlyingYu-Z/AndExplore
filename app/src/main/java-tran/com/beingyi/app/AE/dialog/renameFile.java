package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.beingyi.app.AE.activity.MainActivity;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.AEUtils;
import com.beingyi.app.AE.utils.FileUtils;

import java.io.File;

import android.widget.EditText;

public class renameFile {


    Context context;
    MainActivity activity;
    AEUtils utils;
    int window;
    String path;

    public renameFile(Context mContext, int mWindow, String mPath) {
        this.context = mContext;
        this.activity = (MainActivity) context;
        this.utils = new AEUtils(context);
        this.window = mWindow;
        this.path = mPath;

        final EditText ed_name = new EditText(context);
        ed_name.setText(new File(path).getName());
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(MyApplication.getInstance().getString("c8ce4b36cb6a0ff587974a738f32facf"))
                .setView(ed_name)
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

                        try {
                            File file = new File(path);
                            File newFile=new File(file.getParent(), ed_name.getText().toString());
                            file.renameTo(newFile);

                            ToastUtils.show(MyApplication.getInstance().getString("dce6a364c0d665aa9a0345000c15de2b"));
                            activity.refreshList();
                            activity.adapters.get(window).setItemHighLight(newFile.getAbsolutePath());
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.show(MyApplication.getInstance().getString("37ba51a4c6bc20db6944d111c9e1bdaa"));
                        }


                    }
                })
                .create();
        dialog.show();


    }


}
