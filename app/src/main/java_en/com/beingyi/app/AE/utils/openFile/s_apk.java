package com.beingyi.app.AE.utils.openFile;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.content.Context;
import android.view.View;
import java.io.File;
import com.beingyi.app.AE.dialog.view_apk;
import com.beingyi.app.AE.dialog.view_apk_property;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.APKUtils;

public class s_apk extends s_base
{

    String Path;
    File file;

    public s_apk(Context mContext, int window, String mPath, View view,boolean mIsInZip)
    {
        super(mContext, window, view,mIsInZip);
        this.Path = mPath;
        this.file = new File(Path);

        if(APKUtils.isValid(context,Path)) {
            new view_apk(context, adapter, window, file.getAbsolutePath());
        }else{
            ToastUtils.show(BASE128.decode("T5IU/FJDlygRo3FbC3G/nA=="));
        }

    }

}
