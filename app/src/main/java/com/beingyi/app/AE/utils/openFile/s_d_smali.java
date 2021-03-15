package com.beingyi.app.AE.utils.openFile;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import com.beingyi.app.AE.R;
import com.beingyi.app.AE.dialog.getSavePath;
import com.beingyi.app.AE.interfaces.GetSavePathCallBack;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.utils.FileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.writer.builder.DexBuilder;
import org.jf.dexlib2.writer.io.MemoryDataStore;
import org.jf.smali.Smali;
import org.jf.smali.SmaliOptions;

public class s_d_smali extends s_base
{

    String Path;
    File file;

    public s_d_smali(Context mContext, int mWindow, String mPath, View view,boolean mIsInZip)
    {
        super(mContext, mWindow, view,mIsInZip);
        this.Path = mPath;
        this.file = new File(Path);



        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.dir_smali, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item)
                {
                    int id=item.getItemId();
                    switch (id)
                    {
                        case R.id.action_dir_smali_entey:
                            adapter.getFiles(file.getAbsolutePath());
                            break;


                        case R.id.action_dir_smali_toDex:

                            final File outFile = new File(new File(Path).getAbsolutePath() + ".dex");

                            new getSavePath(context, window, outFile.getAbsolutePath(), new GetSavePathCallBack(){

                                    @Override
                                    public void onSuccess(String filePath)
                                    {

                                        smalis2Dex(outFile.getAbsolutePath());
                                    }

                                    @Override
                                    public void onCancel()
                                    {

                                    }

                                });

                            break;
                    }

                    return true;
                }
            });




    }





    public void smalis2Dex(final String outPath)
    {

        final AlertProgress progres=new AlertProgress(context);
        new Thread(){
            @Override
            public void run()
            {
                progres.setLabel("加载中");
                progres.show();
                try
                {
                    listfile(Path);

                    DexBuilder dexBuilder = new DexBuilder(Opcodes.getDefault());
                    dexBuilder.setIgnoreMethodAndFieldError(true);

                    for (int i=0;i < fileList.size();i++)
                    {
                        if (fileList.get(i).isFile())
                        {
                            progres.setProgress(i, fileList.size());
                            ClassDef classDef=Smali.assembleSmaliFile(FileUtils.readFile(fileList.get(i).getAbsolutePath()) , dexBuilder, new SmaliOptions());
                        }
                    }


                    MemoryDataStore memoryDataStore = new MemoryDataStore();
                    dexBuilder.writeTo(memoryDataStore);
                    byte[] result = Arrays.copyOf(memoryDataStore.getBufferData(), memoryDataStore.getSize());
                    FileUtils.saveFile(result, outPath);

                    activity.runOnUiThread(new Runnable(){
                            @Override
                            public void run()
                            {
                                activity.showToast("回编译完成");
                                adapter.refresh();
                                adapter.setItemHighLight(outPath);
                            }
                        });

                }
                catch (Exception e)
                {
                    activity.showMessage(context, "错误：", e.toString());
                }

                progres.dismiss();
            }
        }.start();








    }






    List<File> fileList=new ArrayList<>();

    private void listfile(String path)
    {
        File file=new File(path);

        if (file != null)
        {  
            if (file.isDirectory())
            {  
                fileList.add(file);  
                File [] files=file.listFiles();  
                for (File f : files)
                {
                    listfile(f.getAbsolutePath());  
                }  
            }
            else
            {  
                fileList.add(file);  
            }  
        }  
    }  






}
