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
                .setTitle(BASE128.decode("cc2A/8+qAduta+ns9u4FHQ=="))
                .setView(view)
                .setCancelable(true)
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

                        try {
                            File file=new File(Path,ed_name.getText().toString());

                            if(type==1){
                                FileUtils.createFile(file.getAbsolutePath());
                            }

                            if(type==2){
                                FileUtils.mkdir(file.getAbsolutePath());
                            }

                            if(type==3){

                                String string=BASE128.decode("fy38rmaDPqCTgISoWJtp76fmyDSMT1T3q4rwegMn61s=");
                                byte[] buffer =string.getBytes();
                                FileOutputStream fOutputStream = new FileOutputStream(file);
                                ZipOutputStream zoutput = new ZipOutputStream(fOutputStream);
                                ZipEntry zEntry  = new ZipEntry(BASE128.decode("XfA6Ql3UhuRHUyh7avDUpA=="));
                                zoutput.putNextEntry(zEntry);
                                zoutput.write(buffer);
                                zoutput.closeEntry();
                                zoutput.close();

                            }


                            ToastUtils.show(BASE128.decode("BRpDmTbfsno+a4PlCM3Giw=="));
                            activity.refreshList();
                            activity.adapters.get(window).setItemHighLight(file.getAbsolutePath());
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.show(BASE128.decode("6ssXUeEJ0+08+kjdAVmUeQ=="));
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
                        ed_name.setText(BASE128.decode("PwGN2N8VuSysR4go6/oZnw==")+BASE128.decode("nEGUUN2/mO6P/1vLVClFcg=="));
                        break;

                    case R.id.view_new_file_RadioButton_dir:
                        type=2;
                        ed_name.setText(BASE128.decode("2qV4oOxSZrB458KUnlF67w=="));
                        break;

                    case R.id.view_new_file_RadioButton_zip:
                        type=3;
                        ed_name.setText(BASE128.decode("PwGN2N8VuSysR4go6/oZnw==")+BASE128.decode("Hdbe0DwbK39Q7L01OqkqQw=="));
                        break;

                }
            }
        });


        radioGroup.check(R.id.view_new_file_RadioButton_file);


    }

}
