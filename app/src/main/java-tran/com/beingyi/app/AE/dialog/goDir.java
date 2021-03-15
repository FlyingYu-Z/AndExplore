package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.beingyi.app.AE.ui.ToastUtils;

import java.io.File;

public class goDir extends baseDialog {

    public goDir(Context mContext, int mWindow, String mPath) {
        super(mContext, mWindow, mPath);

        final EditText ed_name = new EditText(context);
        ed_name.setText(Path);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(MyApplication.getInstance().getString("2ee547f0d6d17daa1b5edcc7644816fd"))
                .setView(ed_name)
                .setCancelable(false)
                .setNegativeButton(MyApplication.getInstance().getString("625fb26b4b3340f7872b411f401e754c"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNeutralButton(MyApplication.getInstance().getString("79d3abe929f67f0644a78bf32adb3a89"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        utils.setClipBoardText(ed_name.getText().toString());
                        ToastUtils.show(MyApplication.getInstance().getString("18e21437b5321d845e5d0d13a5efb40c"));
                    }
                })

                .setPositiveButton(MyApplication.getInstance().getString("38cf16f2204ffab8a6e0187070558721"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        File file = new File(ed_name.getText().toString());
                        if (!file.exists()) {
                            return;
                        }

                        if (file.isFile()) {
                            adapter.getFiles(file.getParent());
                            adapter.setItemHighLight(file.getAbsolutePath());
                        } else {
                            adapter.getFiles(file.getAbsolutePath());
                        }

                        dialog.dismiss();

                    }
                })
                .create();
        dialog.show();


    }

}
