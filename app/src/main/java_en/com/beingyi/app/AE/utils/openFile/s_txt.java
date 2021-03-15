package com.beingyi.app.AE.utils.openFile;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
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
        intent.putExtra(BASE128.decode("/h+2Zkr8crYDBzmTRWy5Cg=="), true);
        intent.putExtra(BASE128.decode("cr62CT1jqczIn9Pg6z6GZg=="), file.getAbsolutePath());
        context.startActivity(intent);

    }

}
