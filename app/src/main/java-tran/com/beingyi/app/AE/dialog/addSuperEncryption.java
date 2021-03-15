package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
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


        File outFile=new File(new File(path).getParent(), FileUtils.getPrefix(path) + "_encrypted.apk");

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
                progres.setLabel(MyApplication.getInstance().getString("f013ea9dcba3f5ca1278aa850931fec8"));
                progres.setNoProgress();
                progres.show();
                try
                {

                    long start=System.currentTimeMillis();

                    SuperEncryption superEncryption= new SuperEncryption(path, outPath);
                    superEncryption.start();

                    long end=System.currentTimeMillis();

                    activity.showToast(MyApplication.getInstance().getString("cea5f1a18060919fd93b9ce7aede22f0")+(end-start)/1000+"s");

                    activity.refreshList();
                    adapter.setItemHighLight(outPath);

                }
                catch (final Exception e)
                {
                    activity.showMessage(context,MyApplication.getInstance().getString("7030ff64701a938becbc5aa67ddb86e8"),e.toString());
                }

                progres.dismiss();
            }
        }.start();


    }



}
