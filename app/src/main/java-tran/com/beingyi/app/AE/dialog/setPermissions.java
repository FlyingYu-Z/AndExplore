package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.bean.PathInfo;
import com.beingyi.app.AE.utils.FileUtils;

import java.io.File;

public class setPermissions extends baseDialog {

    AlertDialog dialog;
    View view;

    public void init(){

    }

    public setPermissions(Context mContext, int mWindow, String mPath) {
        super(mContext, mWindow, mPath);



        view=View.inflate(context, R.layout.view_permissions, null);

        dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setPositiveButton(MyApplication.getInstance().getString("38cf16f2204ffab8a6e0187070558721"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        setPermissions();
                    }
                })
                .setNegativeButton(MyApplication.getInstance().getString("625fb26b4b3340f7872b411f401e754c"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .setCancelable(true)
                .create();
        dialog.show();


        int perms = 0;
        try {
            perms = FileUtils.getPermissions(new File(Path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPermBit(perms, 8, R.id.view_permissions_ckOwnRead);
        setPermBit(perms, 7, R.id.view_permissions_ckOwnWrite);
        setPermBit(perms, 6, R.id.view_permissions_ckOwnExec);
        setPermBit(perms, 5, R.id.view_permissions_ckGrpRead);
        setPermBit(perms, 4, R.id.view_permissions_ckGrpWrite);
        setPermBit(perms, 3, R.id.view_permissions_ckGrpExec);
        setPermBit(perms, 2, R.id.view_permissions_ckOthRead);
        setPermBit(perms, 1, R.id.view_permissions_ckOthWrite);
        setPermBit(perms, 0, R.id.view_permissions_ckOthExec);


    }








    public void setPermissions() {
        dialog.dismiss();
        int perms =
                getPermBit(8, R.id.view_permissions_ckOwnRead) | getPermBit(7, R.id.view_permissions_ckOwnWrite)
                        | getPermBit(6, R.id.view_permissions_ckOwnExec) | getPermBit(5, R.id.view_permissions_ckGrpRead)
                        | getPermBit(4, R.id.view_permissions_ckGrpWrite) | getPermBit(3, R.id.view_permissions_ckGrpExec)
                        | getPermBit(2, R.id.view_permissions_ckOthRead) | getPermBit(1, R.id.view_permissions_ckOthWrite)
                        | getPermBit(0, R.id.view_permissions_ckOthExec);

        try {
            FileUtils.chmod(new File(Path), perms);
            String permissions=Integer.toString(perms, 8);

        } catch (Exception e) {
            activity.showMessage(context,"Set Permission Exception",e.getMessage());
        }
    }





    private void setPermBit(int perms, int bit, int id) {
        CheckBox ck = (CheckBox) view.findViewById(id);
        ck.setChecked(((perms >> bit) & 1) == 1);
    }

    private int getPermBit(int bit, int id) {
        CheckBox ck = (CheckBox) view.findViewById(id);
        int ret = (ck.isChecked()) ? (1 << bit) : 0;
        return ret;
    }





}
