package com.beingyi.app.AE.dialog;

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
                .setTitle("属性")
                .setCancelable(true)
                .create();
        dialog.show();


        String Name = "名称：" + new File(Path).getName();
        String FullPath = "路径：" + Path;
        String Type = "类型：" + ((new File(Path).isFile()) ? "文件" : "文件夹");
        String Size = "大小：" + FileSizeUtils.getAutoFileOrFilesSize(Path);
        String Permissions = null;
        try {
            Permissions = "权限：" + FileUtils.getPermissions(new File(Path));
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
