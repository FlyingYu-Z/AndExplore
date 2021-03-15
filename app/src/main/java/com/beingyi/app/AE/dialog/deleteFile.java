package com.beingyi.app.AE.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import com.beingyi.app.AE.activity.MainActivity;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.AEUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.beingyi.app.AE.utils.FileUtils;

public class deleteFile
{


    Context context;
    MainActivity activity;
    AEUtils utils;
    int window;
    String path;
    public deleteFile(Context mContext, int windows, String mPath)
    {
        this.context = mContext;
        this.activity = (MainActivity)context;
        this.utils = new AEUtils(context);
        this.window = windows;
        this.path = mPath;


        AlertDialog dialog = new AlertDialog.Builder(context)

            .setMessage("你确定要删除此文件？"+"\n" + new File(path))
            .setCancelable(false)
            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }})

            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();

                    if (new File(path).isFile())
                    {
                        delFile();
                    }
                    else
                    {
                        delFiles();
                    }



                }})
            .create();
        dialog.show();



    }



    public void delFiles()
    {

        listfile(path);

        final AlertProgress progres=new AlertProgress(context);
        new Thread(){
            @Override
            public void run()
            {
                progres.show();
                listfile(path);
                for (File file : fileList)
                {
                    try
                    {
                        progres.setLabel("正在删除\n" + file.getAbsolutePath());
                        if (file.isFile())
                        {
                            FileUtils.delSingleFile(file.getAbsolutePath());
                        }
                        else
                        {
                            FileUtils.delFolder(file.getAbsolutePath());
                        }

                    }
                    catch (Exception e)
                    {
                        activity.showMessage(context, "错误", e.toString());
                    }

                }


                activity.showToast("删除完成");
                activity.refreshList();
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



    public void delFile()
    {

        try
        {
            File file = new File(path);
            file.delete();

            ToastUtils.show("删除成功");

            activity.refreshList();
            activity.update();
        }
        catch (Exception e)
        {
            e.printStackTrace(); 
            ToastUtils.show("删除失败");
        }


    }

}
