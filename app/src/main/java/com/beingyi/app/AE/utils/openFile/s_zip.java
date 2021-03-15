package com.beingyi.app.AE.utils.openFile;

import android.content.Context;
import android.view.View;
import java.io.File;

public class s_zip extends s_base
{

    String Path;
    File file;

    public s_zip(Context mContext, int window, String mPath, View view,boolean mIsInZip)
    {
        super(mContext, window, view,mIsInZip);
        this.Path = mPath;
        this.file = new File(Path);
        
        openZip(Path);

    }


}
