package com.beingyi.app.AE.utils.openFile;

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

                .setMessage("是否要把此模块导入到模块管理？")
                .setCancelable(true)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
