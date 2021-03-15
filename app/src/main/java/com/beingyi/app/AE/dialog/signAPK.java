package com.beingyi.app.AE.dialog;

import android.content.Context;

import com.android.apksig.ApkSigner;
import com.beingyi.app.AE.activity.MainActivity;
import com.beingyi.app.AE.utils.AEUtils;
import com.beingyi.app.AE.utils.Conf;
import com.beingyi.app.AE.utils.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import kellinwood.security.zipsigner.ZipSigner;
import com.beingyi.app.AE.ui.AlertProgress;
import kellinwood.security.zipsigner.ProgressListener;
import kellinwood.security.zipsigner.ProgressEvent;
import kellinwood.security.zipsigner.optional.JksKeyStore;

import com.beingyi.app.AE.interfaces.GetSavePathCallBack;
import com.beingyi.app.AE.adapter.FilesAdapter;
import com.google.common.collect.ImmutableList;

public class signAPK
{


    Context context;
    MainActivity activity;
    AEUtils utils;
    int window;
    String path;
    FilesAdapter adapter;
    Conf conf;
    
    public signAPK(Context mContext, int windows, String mPath)
    {
        this.context = mContext;
        this.activity = (MainActivity)context;
        this.utils = new AEUtils(context);
        this.window = windows;
        this.path = mPath;
        this.adapter=activity.adapters.get(window);
        this.conf=new Conf(context);

        File outFile=new File(new File(path).getParent(), FileUtils.getPrefix(path) + "_signed.apk");
        
        new getSavePath(context, window, outFile.getAbsolutePath(), new GetSavePathCallBack(){

                @Override
                public void onSuccess(String filePath)
                {
                    signAPK(filePath);
                }

                @Override
                public void onCancel()
                {

                }
            
        });
        
        




    }

    
    
    public void signAPK(final String outPath){
        

        final AlertProgress progres=new AlertProgress(context);
        new Thread(){
            @Override
            public void run()
            {
                progres.setLabel("签名中");
                progres.show();
                try
                {


                    if(conf.getUseKey()){


                        String alias=conf.getCertAlias();
                        char[] keyPass=conf.getCertPw().toCharArray();
                        char[] storePass=conf.getKeyStorePw().toCharArray();
                        PrivateKey privateKey;
                        X509Certificate certificate;

                        InputStream i = new FileInputStream(conf.getKeyStorePath());
                        JksKeyStore keyStore=new JksKeyStore();
                        keyStore.load(i, storePass);
                        if (alias.isEmpty())
                            alias = keyStore.aliases().nextElement();
                        PrivateKey prk = (PrivateKey) keyStore.getKey(alias, keyPass);
                        X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);

                        privateKey = prk;
                        certificate = cert;

                        ApkSigner.SignerConfig.Builder builder = new ApkSigner.SignerConfig.Builder("CERT", privateKey, ImmutableList.of(certificate));
                        ApkSigner.SignerConfig signerConfig = builder.build();
                        ApkSigner.Builder builder1 = new ApkSigner.Builder(ImmutableList.of(signerConfig));
                        builder1.setInputApk(new File(path));
                        builder1.setOutputApk(new File(outPath));
                        builder1.setCreatedBy(conf.getCertAlias());
                        builder1.setMinSdkVersion(1);
                        builder1.setV1SigningEnabled(true);
                        builder1.setV2SigningEnabled(true);
                        ApkSigner signer = builder1.build();
                        signer.sign();

                    }else {

                        ZipSigner zipSigner = new ZipSigner();
                        zipSigner.setKeymode("testkey");
                        zipSigner.addProgressListener(new ProgressListener() {

                            @Override
                            public void onProgress(ProgressEvent p1) {
                                p1.setPercentDone(100);
                                progres.setProgress(p1.getPercentDone(), 100);
                            }


                        });
                        zipSigner.signZip(path, outPath);


                    }

                    activity.showToast("签名完成");
                    activity.refreshList();
                    adapter.setItemHighLight(outPath);

                }
                catch (final Exception e)
                {
                    activity.showMessage(context,"错误",e.toString());
                }

                progres.dismiss();
            }
        }.start();
        
        
    }
    
    
    
    

}
