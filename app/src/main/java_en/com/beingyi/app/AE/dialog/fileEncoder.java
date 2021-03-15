package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
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
        ed_pass.setHint(BASE128.decode("jPnkv5rHR/+1aEHM8IbvYA=="));
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(BASE128.decode("lnIliZT+BvKi8tJ95eGCQg=="))
                .setView(ed_pass)
                .setCancelable(true)
                .setNeutralButton(BASE128.decode("gba256PHP6LGkSXBwqlv8Q=="), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(BASE128.decode("P6Jkug23BOrEhAf+jejgsw=="), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(ed_pass.getText().toString().equals(BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="))){
                            ToastUtils.show(BASE128.decode("GQpLgIfyere8G2Xx1sm2WoXbRZa6A90QKICkNklNrDo="));
                            return;
                        }

                        File outFile=new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + BASE128.decode("w8BVQVmOppojVP11SId/YQ==")+FileUtils.getSuffix(Path));

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
                .setPositiveButton(BASE128.decode("tdA+cJnobu3II8xhDw9x6g=="), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(ed_pass.getText().toString().equals(BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="))){
                            ToastUtils.show(BASE128.decode("GQpLgIfyere8G2Xx1sm2WoXbRZa6A90QKICkNklNrDo="));
                            return;
                        }

                        File outFile=new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + BASE128.decode("TyItIfhcvSWs3SbCSyaKfw==")+FileUtils.getSuffix(Path));

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
                progres.setLabel(BASE128.decode("yqSHNZwQrWvKNgNtTwQbUA=="));
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
                            activity.showToast(BASE128.decode("N6UQguM7QYb6UGhjX3jEIw=="));
                            adapter.refresh();
                        }
                    });

                }
                catch (Exception e)
                {
                    activity.showMessage(context, BASE128.decode("5x3zP/7tNw0g1zTyNGUeyQ=="), e.toString());
                }

                progres.dismiss();
            }
        }.start();



    }


}
