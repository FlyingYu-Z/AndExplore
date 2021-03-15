package com.beingyi.app.AE.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.CompressUtils;

import java.io.File;

public class zipFiles extends baseDialog {


    public zipFiles(Context mContext, int mWindow, String mPath) {
        super(mContext, mWindow, mPath);


        final EditText ed_name = new EditText(context);
        ed_name.setText(new File(Path).getName() + ".zip");
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("压缩")
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


                        File file = new File(Path);
                        File newFile = new File(file.getParent(), ed_name.getText().toString());
                        start(newFile.getAbsolutePath());

                    }
                })
                .create();
        dialog.show();


    }


    public void start(String outPath) {


        final AlertProgress progres = new AlertProgress(context);
        new Thread() {
            @Override
            public void run() {
                progres.setLabel("处理中");
                progres.setNoProgress();
                progres.show();
                try {


                    CompressUtils.compress(Path, outPath, true);

                    activity.showToast("压缩完成");
                    activity.refreshList();
                    adapter.setItemHighLight(outPath);

                } catch (final Exception e) {
                    activity.showMessage(context, "错误", e.toString());
                }

                progres.dismiss();
            }
        }.start();


    }


}
