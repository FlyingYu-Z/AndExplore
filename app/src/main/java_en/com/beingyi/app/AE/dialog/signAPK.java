package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
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


        File outFile=new File(new File(path).getParent(), FileUtils.getPrefix(path) + BASE128.decode("1SDDAJrajG+DsErQWflq6g=="));
        
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
                progres.setLabel(BASE128.decode("nu2B5AsOGPnvC+cW+36IHA=="));
                progres.show();
                try
                {
                    ZipSigner zipSigner=new ZipSigner();
                    zipSigner.setKeymode(BASE128.decode("MfHsGpZf2f2Et476hEBDuQ=="));
                    zipSigner.addProgressListener(new ProgressListener(){

                            @Override
                            public void onProgress(ProgressEvent p1)
                            {
                                p1.setPercentDone(100);
                                progres.setProgress(p1.getPercentDone(),100);
                            }


                        });
                    zipSigner.signZip(path,outPath);

                    activity.showToast(BASE128.decode("yv2nBsxvnNxj8oSSEFLSMw=="));
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
