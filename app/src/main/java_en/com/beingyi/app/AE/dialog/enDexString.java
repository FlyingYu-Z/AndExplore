package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.interfaces.GetSavePathCallBack;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.ui.SPEditText;
import com.beingyi.app.AE.utils.DexStringEncryptor;
import com.beingyi.app.AE.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class enDexString extends baseDialog {

    AlertDialog dialog;

    public enDexString(Context mContext, int mWindow, String mPath) {
        super(mContext, mWindow, mPath);

        View view=View.inflate(context,R.layout.view_enstr_conf,null);

        String mkeep="android.support\njavax\nandroid.app\ncom.jsdroid.antlr";

        SPEditText ed_keep=view.findViewById(R.id.view_enstr_conf_EditText_keep);
        ed_keep.setHistory(this.getClass().getSimpleName()+BASE128.decode("3/GQU2jRZzLscMkpLysZhw=="));
        ed_keep.setHint(mkeep);

        if(ed_keep.getText().toString().isEmpty()){
            ed_keep.setText(mkeep);
        }

        dialog = new AlertDialog.Builder(context)
                .setTitle(BASE128.decode("v+tw+ZmpaVfEqqgD/haEgH8c4T+lOARBWAhEIe4uC2A="))
                .setView(view)
                .setCancelable(false)
                .setNegativeButton(BASE128.decode("gba256PHP6LGkSXBwqlv8Q=="), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .setPositiveButton(BASE128.decode("GXe18lf7nDG5Ik/56BTUhw=="), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        String keep=ed_keep.getText().toString();

                        final File outFile = new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + BASE128.decode("Mh4w94029/tgUkCmJt+27A=="));

                        new getSavePath(context, window, outFile.getAbsolutePath(), new GetSavePathCallBack() {

                            @Override
                            public void onSuccess(String filePath) {
                                //如果配置不为空
                                if(!keep.equals(BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="))) {
                                    String[] keepArray = keep.split("\n");
                                    List<String> keeps=new ArrayList<>();

                                    for(int i=0;i<keepArray.length;i++){
                                        String k=keepArray[i];
                                        if(k.length()>0) {
                                            k=BASE128.decode("BG0TE+dtfbzeiznki74vFA==")+k.replace(BASE128.decode("slQ5h/9rTzh2GKAKOvqH+w=="),BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="));
                                            keeps.add(k);
                                        }
                                    }
                                    encryptString(filePath,keeps);
                                }else{
                                    encryptString(filePath,null);
                                }
                            }

                            @Override
                            public void onCancel() {

                            }

                        });

                }
                })
                .create();
        dialog.show();

    }




    public void encryptString(final String outPath, List<String> keeps) {


        final AlertProgress progres = new AlertProgress(context);
        new Thread() {
            @Override
            public void run() {
                progres.setLabel(BASE128.decode("yqSHNZwQrWvKNgNtTwQbUA=="));
                progres.show();
                try {


                    DexStringEncryptor dexStringEncryptor=new DexStringEncryptor(Path,keeps, new DexStringEncryptor.EncryptCallBack() {
                        @Override
                        public void onProgress(int progress, int total) {
                            progres.setProgress(progress,total);
                        }

                        @Override
                        public void onClassDefName(String Name) {
                            progres.setLabel(Name);
                        }
                    });
                    dexStringEncryptor.start();

                    byte[] data=dexStringEncryptor.getOutData();
                    FileUtils.saveFile(data,outPath);

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.showToast(BASE128.decode("N6UQguM7QYb6UGhjX3jEIw=="));
                            adapter.refresh();
                            adapter.setItemHighLight(outPath);
                        }
                    });

                } catch (Exception e) {
                    activity.showMessage(context, "错误：", e.toString()+"\n"
                            +e.getStackTrace()[0].getFileName()+"\n"
                            +e.getStackTrace()[0].getMethodName()+"\n"
                            +e.getStackTrace()[0].getLineNumber()+"\n");
                }

                progres.dismiss();
            }
        }.start();


    }




}
