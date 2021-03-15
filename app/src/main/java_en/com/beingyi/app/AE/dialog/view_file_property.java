package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
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
                .setTitle(BASE128.decode("UtGmZtwTu2JBq8ZhDcPu3A=="))
                .setCancelable(true)
                .create();
        dialog.show();


        String Name = BASE128.decode("g4GnqrbyujAPw/Zg8XUjiA==") + new File(Path).getName();
        String FullPath = BASE128.decode("Yt8a34+XkaZS83RhHrIjCw==") + Path;
        String Type = BASE128.decode("kZWDL34COJ2AbsdVOP37EQ==") + ((new File(Path).isFile()) ? BASE128.decode("MbiqnvwGho411IFIAOK1uQ==") : BASE128.decode("tBFmEHVAeJVv6kmJL/08ow=="));
        String Size = BASE128.decode("lNHsjT4YEO8zlObH2o4VPA==") + FileSizeUtils.getAutoFileOrFilesSize(Path);
        String Permissions = null;
        try {
            Permissions = BASE128.decode("+3BSdPLx16laGdsVJ9mWcg==") + FileUtils.getPermissions(new File(Path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String MD5 = BASE128.decode("xxfCsjpKGhJM+8Nd7zokng==") + ((new File(Path).isFile()) ? FileUtils.getMd5ByFile(Path) : BASE128.decode("MkL3TD7r8k/Kt9VpVvxuaw=="));

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
