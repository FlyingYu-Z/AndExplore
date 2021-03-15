package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
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
                .setTitle(BASE128.decode("uSSgg4ntY+kpfLtqY4bqPw=="))
                .setView(ed_name)
                .setCancelable(false)
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

                        try {
                            File file = new File(path);
                            File newFile=new File(file.getParent(), ed_name.getText().toString());
                            file.renameTo(newFile);

                            ToastUtils.show(BASE128.decode("S8uC1AmUykgwyxb7/GRZCA=="));
                            activity.refreshList();
                            activity.adapters.get(window).setItemHighLight(newFile.getAbsolutePath());
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.show(BASE128.decode("pMyFpLEQhmJVbSVU+BSELQ=="));
                        }


                    }
                })
                .create();
        dialog.show();


    }


}
