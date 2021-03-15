package com.beingyi.app.AE.utils.openFile;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
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

                .setMessage(BASE128.decode("pQpdB3+sr885cJX50f9teCH24S+XkSjoWwubgEok292ZYJRBSdqjmUSbo6qFsGhc"))
                .setCancelable(true)
                .setNegativeButton(BASE128.decode("gba256PHP6LGkSXBwqlv8Q=="), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(BASE128.decode("GXe18lf7nDG5Ik/56BTUhw=="), new DialogInterface.OnClickListener() {
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
