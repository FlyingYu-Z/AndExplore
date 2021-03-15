package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.ui.SPEditText;
import com.beingyi.app.AE.ui.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class search extends baseDialog {
    public search(Context mContext, int mWindow, String mPath) {
        super(mContext, mWindow, mPath);

        final SPEditText ed_name = new SPEditText(context);
        ed_name.setHistory(this.getClass().getSimpleName()+"search");
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(MyApplication.getInstance().getString("e5f71fc31e7246dd6ccc5539570471b0"))
                .setView(ed_name)
                .setCancelable(true)
                .setNegativeButton(MyApplication.getInstance().getString("625fb26b4b3340f7872b411f401e754c"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .setPositiveButton(MyApplication.getInstance().getString("38cf16f2204ffab8a6e0187070558721"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        keyWord=ed_name.getText().toString();
                        start();
                    }
                })
                .create();
        dialog.show();


    }



    AlertProgress progres;
    String keyWord;
    public void start(){



        progres=new AlertProgress(context);
        new Thread(){
            @Override
            public void run()
            {
                progres.setLabel(MyApplication.getInstance().getString("f013ea9dcba3f5ca1278aa850931fec8"));
                progres.setNoProgress();
                progres.show();
                try
                {
                    listfile(Path);

                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run()
                        {

                            new keyFileList(context,window,Path,fileList);
                            activity.showToast(MyApplication.getInstance().getString("ed0f366522125ce7351de8049927c657"));
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





    List<String> fileList=new ArrayList<>();

    private void listfile(String path)
    {
        File file=new File(path);

        if (file != null)
        {
            progres.setLabel(file.getAbsolutePath());
            if (file.isDirectory())
            {
                File [] files=file.listFiles();
                for (File f : files)
                {
                    listfile(f.getAbsolutePath());
                }
            }

            if(file.getAbsolutePath().contains(keyWord)){
                fileList.add(file.getAbsolutePath());
            }

        }
    }



}
