package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
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
                .setTitle(MyApplication.getInstance().getString("6612548ad85b6a83b6f57b2dc1f27549"))
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
                progres.setLabel(MyApplication.getInstance().getString("5d459d550ae3aed4e3b5423d542b8d88"));
                progres.setNoProgress();
                progres.show();
                try {


                    CompressUtils.compress(Path, outPath, true);

                    activity.showToast(MyApplication.getInstance().getString("36430ddcfa66a9dee4aa65f9e9f020c5"));
                    activity.refreshList();
                    adapter.setItemHighLight(outPath);

                } catch (final Exception e) {
                    activity.showMessage(context, MyApplication.getInstance().getString("7030ff64701a938becbc5aa67ddb86e8"), e.toString());
                }

                progres.dismiss();
            }
        }.start();


    }


}
