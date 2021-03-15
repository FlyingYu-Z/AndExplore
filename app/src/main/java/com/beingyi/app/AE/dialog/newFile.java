package com.beingyi.app.AE.dialog;

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
                .setTitle("新建")
                .setView(view)
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


                            ToastUtils.show("操作成功");
                            activity.refreshList();
                            activity.adapters.get(window).setItemHighLight(file.getAbsolutePath());
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.show("操作失败");
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
                        ed_name.setText("新建文件"+".txt");
                        break;

                    case R.id.view_new_file_RadioButton_dir:
                        type=2;
                        ed_name.setText("新建文件夹");
                        break;

                    case R.id.view_new_file_RadioButton_zip:
                        type=3;
                        ed_name.setText("新建文件"+".zip");
                        break;

                }
            }
        });


        radioGroup.check(R.id.view_new_file_RadioButton_file);


    }

}
