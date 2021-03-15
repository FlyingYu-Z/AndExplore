package com.beingyi.app.AE.dialog;

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
                .setTitle("重命名")
                .setView(ed_name)
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        try {
                            File file = new File(path);
                            File newFile=new File(file.getParent(), ed_name.getText().toString());
                            file.renameTo(newFile);

                            ToastUtils.show("重命名成功");
                            activity.refreshList();
                            activity.adapters.get(window).setItemHighLight(newFile.getAbsolutePath());
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.show("重命名失败");
                        }


                    }
                })
                .create();
        dialog.show();


    }


}
