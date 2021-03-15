package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
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
        ed_pass.setHint(MyApplication.getInstance().getString("e39ffe99e93cb6cc8d5978ed21660740"));
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(MyApplication.getInstance().getString("7a59d8873fce5aa70bc7fb6db91a1f78"))
                .setView(ed_pass)
                .setCancelable(true)
                .setNeutralButton(MyApplication.getInstance().getString("625fb26b4b3340f7872b411f401e754c"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(MyApplication.getInstance().getString("1872008289c5e25292fe34cb024b7d9e"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(ed_pass.getText().toString().equals("")){
                            ToastUtils.show(MyApplication.getInstance().getString("89b5d3d5bfb3510565a32a76316fc6a5"));
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
                .setPositiveButton(MyApplication.getInstance().getString("56563edf23b9d717dc63981b8836fc60"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(ed_pass.getText().toString().equals("")){
                            ToastUtils.show(MyApplication.getInstance().getString("89b5d3d5bfb3510565a32a76316fc6a5"));
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
                progres.setLabel(MyApplication.getInstance().getString("5d459d550ae3aed4e3b5423d542b8d88"));
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
                            activity.showToast(MyApplication.getInstance().getString("04fe5af3f8e4e5693c8edc8259066434"));
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
