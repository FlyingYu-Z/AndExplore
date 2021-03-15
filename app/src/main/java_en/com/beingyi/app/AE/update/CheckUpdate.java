package com.beingyi.app.AE.update;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;

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
        server_url = BASE128.decode("NNSl0ud9+7dr4lvoe5hG8VOMre9huHR0iUGZEbKFZcGS2NVR/LxJvuN+1Am6dqAS");

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
            progres.setLabel(BASE128.decode("NG6/2bPeC5kiLGc6CtSQOA=="));
            progres.setNoProgress();
            if (isTip) {
                progres.show();
            }

            try {
                URL url = new URL(server_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod(BASE128.decode("IGMx+RZmnx1ajBSxChI4cg=="));
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
                        ToastUtils.show(BASE128.decode("R8OTLtN2Ab64Gu7WNbgTGVNIF8CcpuYSLZzUvEw4Cu8="));
                    }
                    break;

                case UPDATA_CLIENT:
                    showUpdataDialog();
                    break;

                case GET_UNDATAINFO_ERROR:
                    if (isTip) {
                        ToastUtils.show(BASE128.decode("syFMcYROU9h/OQwt1xhkI+O8qc2Y/gPGYuT7uWo12lM="));
                    }
                    break;

                case DOWN_ERROR:
                    ToastUtils.show(BASE128.decode("UrmwU2iLdg0zoUpjy12ayg=="));

                    break;
            }
        }
    };


    protected void showUpdataDialog() {


        AlertDialog dialog = new AlertDialog.Builder(context)

                .setTitle(BASE128.decode("HnDJ8DjH/GBoO7RB6qyMe6zYjm8x2N5EZhCT8f3Qrbs="))
                .setMessage(info.getDescription())
                .setCancelable(false)

                .setNegativeButton(BASE128.decode("gba256PHP6LGkSXBwqlv8Q=="), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //System.exit(0);

                    }
                })
                .setNeutralButton(BASE128.decode("xKJgTXXoMiZpXWo8Nv5mdjfmbQV2r3SEGS6rYmZToEU="), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Uri uri = Uri.parse(info.getUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                        new CheckUpdate(context, true);
                    }
                })
                .setPositiveButton(BASE128.decode("7+JMYePLDhGAshkXl6sWNQ=="), new DialogInterface.OnClickListener() {
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
        pd.setMessage(BASE128.decode("EMowPui+4heJaK1G2KfxFF9xO+SU4DJKFEQPk82D7YI="));

        pd.show();
        downAPK(info.getUrl(), pd);
    }


    public void downAPK(String url, final ProgressDialog pd) {

        final File file = new File(FileUtils.getSDPath() + BASE128.decode("3Gm4sLvVeMFHcZOZ16vByzhu0YfxlpdiHB8W5Agfu6w="));
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
                new alert(context, BASE128.decode("UrmwU2iLdg0zoUpjy12ayg==") + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                new alert(context, BASE128.decode("UrmwU2iLdg0zoUpjy12ayg=="));
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
                //Log.i(tag, "正在下载中......");
                pd.setMax((int) total);
                pd.setProgress((int) current);
            }
        });

    }


}
