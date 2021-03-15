package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import com.beingyi.app.AE.R;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.AEUtils;
import com.beingyi.app.AE.utils.FileClip;
import com.beingyi.app.AE.activity.MainActivity;
import com.beingyi.app.AE.utils.ZipTree;

import java.io.File;

public class inzipLongClick
{
    

    Context context;
    MainActivity activity;
    AEUtils utils;
    ZipTree zipTree;
    int window;
    String path;
    String entry;


    public inzipLongClick(Context mContext,View view, int windows,String mPath,String mEntry)
    {

        this.context = mContext;
        this.activity=(MainActivity)context;
        this.utils = new AEUtils(context);
        this.window = windows;
        this.zipTree=activity.adapters.get(window).zipTree;
        this.path = mPath;
        this.entry=mEntry;
        
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.file_in_zip, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item)
                {
                    int id=item.getItemId();
                    switch (id)
                    {
                        case R.id.action_inzip_copy:
                            FileClip clip=activity.fileClip;
                            FileClip.zipTree=zipTree;
                            clip.putInZipClip(path,entry);
                            
                            ToastUtils.show(BASE128.decode("3w81FrYW83NWoAF6uR0tHCz7M2IpRNQoCPYzZX1Aji0="));
                            break;

                        case R.id.action_inzip_delete:

                            final AlertProgress progres = new AlertProgress(context);
                            new Thread() {
                                @Override
                                public void run() {
                                    progres.show();
                                    progres.setNoProgress();
                                    progres.setLabel(BASE128.decode("yqSHNZwQrWvKNgNtTwQbUA=="));

                                    try {
                                        zipTree.remove(entry);
                                        zipTree.saveFile();
                                    } catch (Exception e) {
                                        activity.showMessage(context, BASE128.decode("fAjveAViGPIFMEu+iuqspA=="), e.toString());
                                    }

                                    activity.showToast(BASE128.decode("Uw3uylYPd7PqxMxmdY6saw=="));
                                    activity.refreshList();
                                    progres.dismiss();
                                }
                            }.start();

                            break;

                        case R.id.action_inzip_rename:

                            break;

                        case R.id.action_inzip_details:

                            break;


                    }

                    return true;
                }
            });
        
        
        
    }
    
    
    
    
}
