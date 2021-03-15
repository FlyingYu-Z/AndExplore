package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.content.Context;

import com.beingyi.app.AE.activity.MainActivity;
import com.beingyi.app.AE.adapter.FilesAdapter;
import com.beingyi.app.AE.interfaces.GetSavePathCallBack;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.utils.AEUtils;
import com.beingyi.app.AE.utils.FileUtils;
import com.beingyi.app.AE.utils.SuperEncryption;
import java.io.File;

public class addSuperEncryption {



    Context context;
    MainActivity activity;
    AEUtils utils;
    int window;
    String path;
    FilesAdapter adapter;


    public addSuperEncryption(Context mContext, int windows, String mPath)
    {
        this.context = mContext;
        this.activity = (MainActivity)context;
        this.utils = new AEUtils(context);
        this.window = windows;
        this.path = mPath;
        this.adapter=activity.adapters.get(window);


        File outFile=new File(new File(path).getParent(), FileUtils.getPrefix(path) + BASE128.decode("w9JoMb3bH3+XMJLoIvFGhw=="));

        new getSavePath(context, window, outFile.getAbsolutePath(), new GetSavePathCallBack(){

            @Override
            public void onSuccess(String filePath)
            {
                start(filePath);
            }

            @Override
            public void onCancel()
            {

            }

        });






    }



    public void start(final String outPath){


        final AlertProgress progres=new AlertProgress(context);
        new Thread(){
            @Override
            public void run()
            {
                progres.setLabel(BASE128.decode("NG6/2bPeC5kiLGc6CtSQOA=="));
                progres.setNoProgress();
                progres.show();
                try
                {

                    long start=System.currentTimeMillis();

                    SuperEncryption superEncryption= new SuperEncryption(path, outPath);
                    superEncryption.start();

                    long end=System.currentTimeMillis();

                    activity.showToast(BASE128.decode("z737JycFkOygKkmq75EASV4iLg+GgDCjvM63K1DMDfI=")+(end-start)/1000+BASE128.decode("b/JZYUlYMTXVIc7V8f+/2Q=="));

                    activity.refreshList();
                    adapter.setItemHighLight(outPath);

                }
                catch (final Exception e)
                {
                    activity.showMessage(context,BASE128.decode("fAjveAViGPIFMEu+iuqspA=="),e.toString());
                }

                progres.dismiss();
            }
        }.start();


    }



}
