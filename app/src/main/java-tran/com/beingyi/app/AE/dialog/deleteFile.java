package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
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

            .setMessage(MyApplication.getInstance().getString("8f9ca4a791743dbbd71b9561d7d053c4")+"\n" + new File(path))
            .setCancelable(false)
            .setNegativeButton(MyApplication.getInstance().getString("625fb26b4b3340f7872b411f401e754c"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }})

            .setPositiveButton(MyApplication.getInstance().getString("38cf16f2204ffab8a6e0187070558721"), new DialogInterface.OnClickListener() {
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
                        progres.setLabel(MyApplication.getInstance().getString("823b2914ac0fdefd24e101ba861c7273") + file.getAbsolutePath());
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
                        activity.showMessage(context, MyApplication.getInstance().getString("7030ff64701a938becbc5aa67ddb86e8"), e.toString());
                    }

                }


                activity.showToast(MyApplication.getInstance().getString("922d3edb9a54b4fbdb518a35fee002a3"));
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

            ToastUtils.show(MyApplication.getInstance().getString("0007d170de017dafc266aa03926d7f00"));

            activity.refreshList();
            activity.update();
        }
        catch (Exception e)
        {
            e.printStackTrace(); 
            ToastUtils.show(MyApplication.getInstance().getString("acf0664a54dc58d9d0377bb56e162092"));
        }


    }

}
