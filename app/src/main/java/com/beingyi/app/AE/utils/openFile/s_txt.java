package com.beingyi.app.AE.utils.openFile;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.beingyi.app.AE.activity.TextEditor;
import java.io.File;

public class s_txt extends s_base
{

    String Path;
    File file;

    public s_txt(Context mContext, int window, String mPath, View view,boolean mIsInZip)
    {
        super(mContext, window, view,mIsInZip);
        this.Path = mPath;
        this.file = new File(Path);


        Intent intent=new Intent(context, TextEditor.class);
        intent.putExtra("isFile", true);
        intent.putExtra("Path", file.getAbsolutePath());
        context.startActivity(intent);

    }

}
