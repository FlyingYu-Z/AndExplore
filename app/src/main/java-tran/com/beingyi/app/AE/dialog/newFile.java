package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class newFile extends baseDialog{

    EditText ed_name;
    RadioGroup radioGroup;


    int type=0;

    public newFile(Context mContext, int mWindow, String mPath) {
        super(mContext, mWindow, mPath);

        View view=View.inflate(context, R.layout.view_new_file,null);
        ed_name=view.findViewById(R.id.view_new_file_EditText_name);
        radioGroup=view.findViewById(R.id.view_new_file_RadioGroup);


        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(MyApplication.getInstance().getString("26bb8418786593149c0bf9f8970ab6de"))
                .setView(view)
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

                        try {
                            File file=new File(Path,ed_name.getText().toString());

                            if(type==1){
                                FileUtils.createFile(file.getAbsolutePath());
                            }

                            if(type==2){
                                FileUtils.mkdir(file.getAbsolutePath());
                            }

                            if(type==3){

                                String string="This is a ZipFile!";
                                byte[] buffer =string.getBytes();
                                FileOutputStream fOutputStream = new FileOutputStream(file);
                                ZipOutputStream zoutput = new ZipOutputStream(fOutputStream);
                                ZipEntry zEntry  = new ZipEntry("tip.txt");
                                zoutput.putNextEntry(zEntry);
                                zoutput.write(buffer);
                                zoutput.closeEntry();
                                zoutput.close();

                            }


                            ToastUtils.show(MyApplication.getInstance().getString("33130f5c465d732d68ebdfbb80f4284b"));
                            activity.refreshList();
                            activity.adapters.get(window).setItemHighLight(file.getAbsolutePath());
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.show(MyApplication.getInstance().getString("5fa802bef51f52b1f7bcf35ade3512cf"));
                        }


                    }
                })
                .create();
        dialog.show();


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id=radioGroup.getCheckedRadioButtonId();
                switch (id){
                    case R.id.view_new_file_RadioButton_file:
                        type=1;
                        ed_name.setText(MyApplication.getInstance().getString("e48a715738f009af3b133b2567ed4dae")+".txt");
                        break;

                    case R.id.view_new_file_RadioButton_dir:
                        type=2;
                        ed_name.setText(MyApplication.getInstance().getString("f3a485dffd36dc3bcaa3c70e08f704e5"));
                        break;

                    case R.id.view_new_file_RadioButton_zip:
                        type=3;
                        ed_name.setText(MyApplication.getInstance().getString("e48a715738f009af3b133b2567ed4dae")+".zip");
                        break;

                }
            }
        });


        radioGroup.check(R.id.view_new_file_RadioButton_file);


    }

}
