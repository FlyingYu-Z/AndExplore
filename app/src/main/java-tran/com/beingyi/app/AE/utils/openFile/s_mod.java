package com.beingyi.app.AE.utils.openFile;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import java.io.File;

public class s_mod extends s_base {


    String Path;
    File file;

    public s_mod(Context mContext, int mWindow, String mPath, View view, boolean mIsInZip)
    {
        super(mContext, mWindow, view,mIsInZip);
        this.Path = mPath;
        this.file = new File(Path);



        AlertDialog dialog = new AlertDialog.Builder(context)

                .setMessage(MyApplication.getInstance().getString("4bb45ec3f86ff96d724672b088c3643a"))
                .setCancelable(true)
                .setNegativeButton(MyApplication.getInstance().getString("625fb26b4b3340f7872b411f401e754c"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(MyApplication.getInstance().getString("38cf16f2204ffab8a6e0187070558721"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();



    }


}
