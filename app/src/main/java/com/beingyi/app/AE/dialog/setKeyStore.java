package com.beingyi.app.AE.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.interfaces.SelectFileCallBack;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.Conf;
import com.beingyi.app.AE.utils.FileUtils;

import java.io.File;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;


public class setKeyStore {


    Context context;
    Conf conf;

    public setKeyStore(Context mContext) {
        this.context = mContext;
        this.conf=new Conf(context);


        TextView title = new TextView(context);
        title.setPadding(0, 10, 0, 0);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);
        title.setTextColor(Color.BLACK);
        title.setText("设置签名私匙");

        View view = LinearLayout.inflate(context, R.layout.dialog_keyinfo, null);

        EditText ed_path=view.findViewById(R.id.dialog_keyinfo_path);
        EditText ed_keystorePw=view.findViewById(R.id.dialog_keyinfo_keystorePw);
        EditText ed_certAlias=view.findViewById(R.id.dialog_keyinfo_certAlias);
        EditText ed_certPw=view.findViewById(R.id.dialog_keyinfo_certPw);

        ed_path.setText(conf.getKeyStorePath());
        ed_keystorePw.setText(conf.getKeyStorePw());
        ed_certAlias.setText(conf.getCertAlias());
        ed_certPw.setText(conf.getCertPw());

        AlertDialog dialog = new AlertDialog.Builder(context)

                .setCustomTitle(title)
                .setView(view)
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", null)
                .create();
        dialog.show();


        ed_path.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    new selectFile(context, 0, FileUtils.getSDPath() + "/", "jks", new SelectFileCallBack() {
                        @Override
                        public void onSelected(String selectedPath) {
                            ed_path.setText(selectedPath);
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }
            }
        });

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File path=new File(ed_path.getText().toString());
                if(!path.exists()){
                    ToastUtils.show("私匙文件不存在");
                }else{
                    conf.setKeyStorePath(path.getAbsolutePath());
                    conf.setKeyStorePw(ed_keystorePw.getText().toString());
                    conf.setCertAlias(ed_certAlias.getText().toString());
                    conf.setCertPw(ed_certPw.getText().toString());

                    try {

                        KeyStore keyStore = net.fornwall.apksigner.KeyStoreFileManager.loadKeyStore(conf.getKeyStorePath(),null);
                        String alias = conf.getCertAlias();
                        X509Certificate publicKey = (X509Certificate) keyStore.getCertificate(alias);
                        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, conf.getCertPw().toCharArray());

                        ToastUtils.show("设置成功");
                        dialog.dismiss();
                    } catch (Exception e) {
                        new alert(context,e.toString());
                    }


                }

            }
        });


    }


}
