package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
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
        ed_name.setText(new File(Path).getName() + BASE128.decode("Hdbe0DwbK39Q7L01OqkqQw=="));
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(BASE128.decode("SCSXN4rqETREoY19wrmISg=="))
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
                progres.setLabel(BASE128.decode("yqSHNZwQrWvKNgNtTwQbUA=="));
                progres.setNoProgress();
                progres.show();
                try {


                    CompressUtils.compress(Path, outPath, true);

                    activity.showToast(BASE128.decode("Tg5DwAo8plT4l4u1b648nQ=="));
                    activity.refreshList();
                    adapter.setItemHighLight(outPath);

                } catch (final Exception e) {
                    activity.showMessage(context, BASE128.decode("fAjveAViGPIFMEu+iuqspA=="), e.toString());
                }

                progres.dismiss();
            }
        }.start();


    }


}
