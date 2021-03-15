package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import com.beingyi.app.AE.activity.MainActivity;
import com.beingyi.app.AE.utils.AEUtils;
import com.beingyi.app.AE.utils.FileUtils;
import java.io.File;
import kellinwood.security.zipsigner.ZipSigner;
import com.beingyi.app.AE.ui.AlertProgress;
import kellinwood.security.zipsigner.ProgressListener;
import kellinwood.security.zipsigner.ProgressEvent;
import com.beingyi.app.AE.interfaces.GetSavePathCallBack;
import com.beingyi.app.AE.adapter.FilesAdapter;

public class signAPK
{


    Context context;
    MainActivity activity;
    AEUtils utils;
    int window;
    String path;
    FilesAdapter adapter;
    
    
    public signAPK(Context mContext, int windows, String mPath)
    {
        this.context = mContext;
        this.activity = (MainActivity)context;
        this.utils = new AEUtils(context);
        this.window = windows;
        this.path = mPath;
        this.adapter=activity.adapters.get(window);


        File outFile=new File(new File(path).getParent(), FileUtils.getPrefix(path) + "_signed.apk");
        
        new getSavePath(context, window, outFile.getAbsolutePath(), new GetSavePathCallBack(){

                @Override
                public void onSuccess(String filePath)
                {
                    signAPK(filePath);
                }

                @Override
                public void onCancel()
                {

                }
            
        });
        
        




    }

    
    
    public void signAPK(final String outPath){
        

        final AlertProgress progres=new AlertProgress(context);
        new Thread(){
            @Override
            public void run()
            {
                progres.setLabel(MyApplication.getInstance().getString("f42c2eee3fbced17fcf1385ec33058cd"));
                progres.show();
                try
                {
                    ZipSigner zipSigner=new ZipSigner();
                    zipSigner.setKeymode("testkey");
                    zipSigner.addProgressListener(new ProgressListener(){

                            @Override
                            public void onProgress(ProgressEvent p1)
                            {
                                p1.setPercentDone(100);
                                progres.setProgress(p1.getPercentDone(),100);
                            }


                        });
                    zipSigner.signZip(path,outPath);

                    activity.showToast(MyApplication.getInstance().getString("6061b1a18ac3d781c37629f250794cd9"));
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
