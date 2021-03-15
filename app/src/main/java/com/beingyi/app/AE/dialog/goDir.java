package com.beingyi.app.AE.dialog;

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
                .setTitle("转到")
                .setView(ed_name)
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNeutralButton("复制", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        utils.setClipBoardText(ed_name.getText().toString());
                        ToastUtils.show("已复制到剪切板");
                    }
                })

                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
