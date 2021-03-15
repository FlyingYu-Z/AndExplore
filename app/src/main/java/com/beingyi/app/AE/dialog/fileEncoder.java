package com.beingyi.app.AE.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.interfaces.GetSavePathCallBack;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.FileEntry;
import com.beingyi.app.AE.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class fileEncoder extends baseDialog{

    EditText ed_name;
    RadioGroup radioGroup;


    public fileEncoder(Context mContext, int mWindow, String mPath) {
        super(mContext, mWindow, mPath);


        final EditText ed_pass = new EditText(context);
        ed_pass.setHint("请输入密码");
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("文件加解密")
                .setView(ed_pass)
                .setCancelable(true)
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("解密", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(ed_pass.getText().toString().equals("")){
                            ToastUtils.show("密码不能为空");
                            return;
                        }

                        File outFile=new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + "_de."+FileUtils.getSuffix(Path));

                        new getSavePath(context, window, outFile.getAbsolutePath(), new GetSavePathCallBack(){
                            @Override
                            public void onSuccess(String filePath)
                            {
                                start(false,ed_pass.getText().toString(),filePath);
                            }
                            @Override
                            public void onCancel()
                            {
                            }
                        });

                    }
                })
                .setPositiveButton("加密", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(ed_pass.getText().toString().equals("")){
                            ToastUtils.show("密码不能为空");
                            return;
                        }

                        File outFile=new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + "_en."+FileUtils.getSuffix(Path));

                        new getSavePath(context, window, outFile.getAbsolutePath(), new GetSavePathCallBack(){
                            @Override
                            public void onSuccess(String filePath)
                            {
                                start(true,ed_pass.getText().toString(),filePath);
                            }
                            @Override
                            public void onCancel()
                            {
                            }
                        });

                    }
                })
                .create();
        dialog.show();




    }


    public void start(boolean isEncrypt,String pass,String outPath){


        final AlertProgress progres=new AlertProgress(context);
        new Thread(){
            @Override
            public void run()
            {
                progres.setLabel("处理中");
                progres.setNoProgress();
                progres.show();
                try
                {
                    if(isEncrypt){
                        FileEntry.encrypt(Path,outPath,pass);
                    }else{
                        FileEntry.decrypt(Path,outPath,pass);
                    }

                    adapter.setItemHighLight(outPath);
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run()
                        {
                            activity.showToast("操作完成");
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


}
