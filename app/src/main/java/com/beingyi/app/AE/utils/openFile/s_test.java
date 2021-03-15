package com.beingyi.app.AE.utils.openFile;

import android.content.Context;
import android.view.View;
import java.io.File;

public class s_test extends s_base
{

    String Path;
    File file;

    public s_test(Context mContext, int mWindow, String mPath, View view,boolean mIsInZip)
    {
        super(mContext, mWindow, view,mIsInZip);
        this.Path = mPath;
        this.file = new File(Path);



    }

}
