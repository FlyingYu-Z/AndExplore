package com.beingyi.app.AE.dialog;

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
                .setTitle("搜索")
                .setView(ed_name)
                .setCancelable(true)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                progres.setLabel("加载中");
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
                            activity.showToast("搜索完成");
                            adapter.refresh();
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
