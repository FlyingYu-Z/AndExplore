package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.bean.PathInfo;
import com.beingyi.app.AE.utils.APKUtils;
import com.beingyi.app.AE.utils.FileSizeUtils;
import com.beingyi.app.AE.utils.FileUtils;

import java.io.File;

public class view_file_property extends baseDialog {

    AlertDialog dialog;
    LinearLayout linearLayout;

    public view_file_property(Context mContext, int mWindow, String mPath) {
        super(mContext, mWindow, mPath);


        View view = View.inflate(context, R.layout.view_file_property, null);
        linearLayout = view.findViewById(R.id.view_file_property_LinearLayout);

        dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setTitle(MyApplication.getInstance().getString("24d67862f87f439db7ca957aecb77cce"))
                .setCancelable(true)
                .create();
        dialog.show();


        String Name = MyApplication.getInstance().getString("5b47861521d533de5a72ad8fc43a5051") + new File(Path).getName();
        String FullPath = MyApplication.getInstance().getString("461ec75a5a6807bfa903cc2b8e7cae75") + Path;
        String Type = MyApplication.getInstance().getString("e91f5a13bb84c26d9fbf37bcf8efc427") + ((new File(Path).isFile()) ? MyApplication.getInstance().getString("2a0c4740f156a9536049f4610da25400") : MyApplication.getInstance().getString("1f4c1042ed0080cf3526644f92248ddc"));
        String Size = MyApplication.getInstance().getString("19e5b0460cdf9e5605497bb16d145b49") + FileSizeUtils.getAutoFileOrFilesSize(Path);
        String Permissions = null;
        try {
            Permissions = MyApplication.getInstance().getString("b2cb600086bd9fa5ee058efd851d5d90") + FileUtils.getPermissions(new File(Path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String MD5 = "MD5：" + ((new File(Path).isFile()) ? FileUtils.getMd5ByFile(Path) : "null");

        addProperty(Name);
        addProperty(FullPath);
        addProperty(Type);
        addProperty(Size);
        addProperty(Permissions);

        if(new File(Path).isFile()){
            addProperty(MD5);
        }

    }


    public void addProperty(String value) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dialog.findViewById(R.id.view_file_property_CopyTextView1).getLayoutParams());
        TextView tv = new TextView(context);
        tv.setLayoutParams(lp);
        tv.setText(value);
        linearLayout.addView(tv);
    }


}
