package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
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
                .setTitle(BASE128.decode("IAiXSAeCvxKLs5ymqToIrw=="))
                .setView(ed_name)
                .setCancelable(false)
                .setNegativeButton(BASE128.decode("gba256PHP6LGkSXBwqlv8Q=="), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNeutralButton(BASE128.decode("RLZhXkgzAZFnbuWJPjwm9g=="), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        utils.setClipBoardText(ed_name.getText().toString());
                        ToastUtils.show(BASE128.decode("d5gItZ0gk7w4hq6NhVNBryHRdB4X/ObrY5RPNppjD/0="));
                    }
                })

                .setPositiveButton(BASE128.decode("GXe18lf7nDG5Ik/56BTUhw=="), new DialogInterface.OnClickListener() {
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
