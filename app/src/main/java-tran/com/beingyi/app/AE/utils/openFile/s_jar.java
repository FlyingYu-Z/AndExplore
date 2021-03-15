package com.beingyi.app.AE.utils.openFile;

import com.beingyi.app.AE.application.MyApplication;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import com.beingyi.app.AE.R;
import com.beingyi.app.AE.dialog.getSavePath;
import com.beingyi.app.AE.dialog.obfuscateJar;
import com.beingyi.app.AE.interfaces.GetSavePathCallBack;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.utils.FileUtils;
import java.io.File;
import java.util.ArrayList;
import proguard.ProGuard;

public class s_jar extends s_base
{
    String Path;
    File file;

    public s_jar(Context mContext, int mWindow, String mPath, View view,boolean mIsInZip)
    {
        super(mContext, mWindow, view,mIsInZip);
        this.Path = mPath;
        this.file = new File(Path);



        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.file_jar, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item)
                {
                    int id=item.getItemId();
                    final File outFile;
                    switch (id)
                    {
                        case R.id.action_jar_view:
                            openZip(file.getAbsolutePath());
                            break;

                        case R.id.action_jar_toDex:

                            outFile=new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + ".dex");

                            new getSavePath(context, window, outFile.getAbsolutePath(), new GetSavePathCallBack(){

                                    @Override
                                    public void onSuccess(String filePath)
                                    {
                                        jar2dex(filePath);
                                    }

                                    @Override
                                    public void onCancel()
                                    {

                                    }

                                });
                                
                                
                            break;

                        case R.id.action_jar_obfuse:
                            new obfuscateJar(context,window,Path);
                            break;

                    }

                    return true;
                }
            });

        
        
    }


    
    public void jar2dex(final String outPath){
        
        

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
                    com.android.dx.command.Main.main(new String []{"--dex","--output="+outPath,file.getAbsolutePath()});

                    adapter.setItemHighLight(outPath);
                    activity.runOnUiThread(new Runnable(){
                            @Override
                            public void run()
                            {
                                activity.showToast(MyApplication.getInstance().getString("6bfe16584f867f8128f075b6f43e8a8c"));
                                adapter.refresh();
                            }
                        });

                }
                catch (Exception e)
                {
                    activity.showMessage(context, MyApplication.getInstance().getString("22af5f9e0c3f5aaba469cb807298386e"), e.toString());
                }

                progres.dismiss();
            }
        }.start();

        
        
    }
    
    
    
    
    
}
