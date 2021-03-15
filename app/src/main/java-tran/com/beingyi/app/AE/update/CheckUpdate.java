package com.beingyi.app.AE.update;

import com.beingyi.app.AE.application.MyApplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;

import com.beingyi.app.AE.dialog.alert;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.APKUtils;
import com.beingyi.app.AE.utils.Conf;
import com.beingyi.app.AE.utils.FileUtils;
import com.beingyi.app.AE.utils.SelfInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

public class CheckUpdate {
    Context context;
    Conf conf;
    boolean isTip;
    String server_url;
    private final String TAG = this.getClass().getName();
    private final int UPDATA_NONEED = 0;
    private final int UPDATA_CLIENT = 1;
    private final int GET_UNDATAINFO_ERROR = 2;
    private final int SDCARD_NOMOUNTED = 3;
    private final int DOWN_ERROR = 4;
    private UpdateInfo info;
    private String localVersion;


    public CheckUpdate(Context context, boolean isTip) {

        this.context = context;
        this.isTip = isTip;
        conf = new Conf(context);
        server_url = "http://idc.beingyi.cn/download/update_ae.xml";

        try {
            localVersion = SelfInfo.versionName(context);
            CheckVersionTask cv = new CheckVersionTask();
            new Thread(cv).start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public class CheckVersionTask implements Runnable {

        final AlertProgress progres = new AlertProgress(context);

        InputStream is;

        public void run() {
            progres.setLabel(MyApplication.getInstance().getString("f013ea9dcba3f5ca1278aa850931fec8"));
            progres.setNoProgress();
            if (isTip) {
                progres.show();
            }

            try {
                URL url = new URL(server_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    is = conn.getInputStream();
                }
                info = UpdateInfoParser.getUpdateInfo(is);

                String local = localVersion;
                String webversion = info.getVersion();
                if (info.getVersion().equals(localVersion)) {
                    Message msg = new Message();
                    msg.what = UPDATA_NONEED;
                    handler.sendMessage(msg);
                } else {
                    Message msg = new Message();
                    msg.what = UPDATA_CLIENT;
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {

                Message msg = new Message();
                msg.what = GET_UNDATAINFO_ERROR;
                handler.sendMessage(msg);
                e.printStackTrace();

            }

            progres.dismiss();

        }


    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
// TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {


                case UPDATA_NONEED:
                    if (isTip) {
                        ToastUtils.show(MyApplication.getInstance().getString("b29f9b4ce2e7017fd04deb774ff458b7"));
                    }
                    break;

                case UPDATA_CLIENT:
                    showUpdataDialog();
                    break;

                case GET_UNDATAINFO_ERROR:
                    if (isTip) {
                        ToastUtils.show(MyApplication.getInstance().getString("81d76b746b2e66b2a20d5597b3f4e2f6"));
                    }
                    break;

                case DOWN_ERROR:
                    ToastUtils.show(MyApplication.getInstance().getString("65e200d30c86a93c46dcafaa151028f5"));

                    break;
            }
        }
    };


    protected void showUpdataDialog() {


        AlertDialog dialog = new AlertDialog.Builder(context)

                .setTitle(MyApplication.getInstance().getString("912bb5b4aa83ecf2dc53c55569ef7b19"))
                .setMessage(info.getDescription())
                .setCancelable(false)

                .setNegativeButton(MyApplication.getInstance().getString("625fb26b4b3340f7872b411f401e754c"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //System.exit(0);

                    }
                })
                .setNeutralButton(MyApplication.getInstance().getString("19f73692db525fce11f5bfaad151c54a"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Uri uri = Uri.parse(info.getUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                        new CheckUpdate(context, true);
                    }
                })
                .setPositiveButton(MyApplication.getInstance().getString("2541ca4876321ef3b85216c0c5bdecbe"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        downLoadApk();
                    }
                }).create();
        dialog.show();


    }


    protected void downLoadApk() {
        final ProgressDialog pd;
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage(MyApplication.getInstance().getString("511102212d637f9f27c502a0b779afbd"));

        pd.show();
        downAPK(info.getUrl(), pd);
    }


    public void downAPK(String url, final ProgressDialog pd) {

        final File file = new File(FileUtils.getSDPath() + "/AE/file/update.apk");
        RequestParams requestParams = new RequestParams(url);
        requestParams.setSaveFilePath(file.getAbsolutePath());
        requestParams.setAutoRename(true);
        requestParams.setProxy(Proxy.NO_PROXY);
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {

            @Override
            public void onSuccess(File result) {
                pd.dismiss();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }


                APKUtils.installAPK(context, file.getAbsolutePath());


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                new alert(context, MyApplication.getInstance().getString("65e200d30c86a93c46dcafaa151028f5") + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                new alert(context, MyApplication.getInstance().getString("65e200d30c86a93c46dcafaa151028f5"));
            }

            @Override
            public void onFinished() {
                pd.dismiss();
            }

            @Override
            public void onWaiting() {
            }

            @Override
            public void onStarted() {
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                // 当前的下载进度和文件总大小
                //Log.i(tag, MyApplication.getInstance().getString("886850e974f01e2dcad66ac88f52b276"));
                pd.setMax((int) total);
                pd.setProgress((int) current);
            }
        });

    }


}
