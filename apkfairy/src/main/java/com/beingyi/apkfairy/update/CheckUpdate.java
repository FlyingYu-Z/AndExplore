package com.beingyi.apkfairy.update;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;
import com.beingyi.apkfairy.utils.Conf;
import com.beingyi.apkfairy.utils.CrashPrint;
import com.beingyi.apkfairy.utils.SelfInfo;
import com.beingyi.apkfairy.utils.alert;
import com.beingyi.apkfairy.utils.init;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.xutils.BuildConfig;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class CheckUpdate
{
    Context context;
    String server_url=Conf.getServer(context)+"resource/update/update_apkjingling.xml";
    private final String TAG = this.getClass().getName();
    private final int UPDATA_NONEED = 0;
    private final int UPDATA_CLIENT = 1;
    private final int GET_UNDATAINFO_ERROR = 2;
    private final int SDCARD_NOMOUNTED = 3;
    private final int DOWN_ERROR = 4;
    private UpdateInfo info;
    private String localVersion;
    


    public CheckUpdate(Context context)
    {

        this.context = context;


        try
        {
            localVersion = SelfInfo.versionName(context);
            CheckVersionTask cv = new CheckVersionTask();
            new Thread(cv).start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }





    }

    
    public class CheckVersionTask implements Runnable
    {
        InputStream is;
        public void run()
        {
            try
            {
                URL url = new URL(server_url);
                HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET"); 
                int responseCode = conn.getResponseCode(); 
                if (responseCode == 200)
                { 
                    is = conn.getInputStream(); 
                } 
                info = UpdateInfoParser.getUpdateInfo(is);

                String local=localVersion;
                String webversion=info.getVersion();
                if (info.getVersion().equals(localVersion))
                {


                    Log.i(TAG, "版本号相同");

                    Message msg = new Message();
                    msg.what = UPDATA_NONEED;
                    handler.sendMessage(msg);


                }
                else
                {



                    Log.i(TAG, "版本号不相同 ");


                    Message msg = new Message();
                    msg.what = UPDATA_CLIENT;
                    handler.sendMessage(msg);


                }
            }
            catch (Exception e)
            {



                Message msg = new Message();
                msg.what = GET_UNDATAINFO_ERROR;
                handler.sendMessage(msg);
                e.printStackTrace();





            }
        }
    }




    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
// TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what)
            {



                case UPDATA_NONEED:
                    //Toast.makeText(context, "已是最新版本", Toast.LENGTH_SHORT).show();
                    break;







                case UPDATA_CLIENT:
                    showUpdataDialog();
                    break;



                case GET_UNDATAINFO_ERROR:
                    //Toast.makeText(context, "网络连接失败", 1).show(); 

                    break;








                case DOWN_ERROR:
                    Toast.makeText(context, "下载失败", 1).show(); 

                    break;
            }
        }
    };





    protected void showUpdataDialog()
    {




        AlertDialog dialog = new AlertDialog.Builder(context)

            .setTitle("版本更新提示")
            .setMessage(info.getDescription())
            .setCancelable(false)
            .setNeutralButton("浏览器更新", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
					Uri uri = Uri.parse(Conf.getServer(context)+info.getUrl());
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					context.startActivity(intent);
				}
			})
            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                    if(!BuildConfig.DEBUG){
                        //System.exit(0);
                        
                    }
                }
            })
            .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                    downLoadApk();
                }
            }).create();
        dialog.show();
        
        
        
        
        
/**
        MaterialDialog.Builder build=new MaterialDialog.Builder(context)
            .title("版本更新")
            .content(info.getDescription())
            .positiveText("确定")
            .negativeText("取消")
            .widgetColor(Color.BLUE)
            .cancelable(false)
            .canceledOnTouchOutside(false)
            .titleGravity(GravityEnum.CENTER)
            .checkBoxPrompt("不再提醒", false, new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b)
                {
                    if (b)
                    {
                    }
                    else
                    {
                    }
                }
            })
            .onAny(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                {

                    if (which == DialogAction.POSITIVE)
                    {
                        downLoadApk();
                    }
                    else if (which == DialogAction.NEGATIVE)
                    {

                    }

                }});

        build.show();

        **/
        
        
    }


    protected void downLoadApk()
    {  
        final ProgressDialog pd;
        pd = new  ProgressDialog(context);  
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("正在下载最新版本");
        
        pd.show();  
        new Thread(){  
            @Override  
            public void run()
            {  
            
            Looper.prepare();
            
                try
                {  
                    File file = downAPK(Conf.getServer(context)+info.getUrl(), pd);
                    Apkfile=file;
                    sleep(1000);  

                }
                catch (Exception e)
                {  
                    Message msg = new Message();  
                    msg.what = DOWN_ERROR;  
                    handler.sendMessage(msg);
                    new CrashPrint(context,e+"");
                    e.printStackTrace();  
                }  
                
                
                
                Looper.loop();
                
            }}.start();  
    }  
    
    
    File Apkfile;
    
    public File downAPK(String url,final ProgressDialog pd){
        
        File file=new File(init.app_dir+"file/apkfairy.apk");
        RequestParams requestParams = new RequestParams(url);
        requestParams.setSaveFilePath(file.getAbsolutePath());
        requestParams.setAutoRename(true);
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {

                @Override
                public void onSuccess(File result) {
                    //Log.i(tag, "下载成功");
                    pd.dismiss();
                    installApk(Apkfile);
                    
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    //Log.i(tag, "下载失败");
                    pd.dismiss();
                    new alert(context,"下载失败");
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    new alert(context,"下载失败");
                }

                @Override
                public void onFinished() {
                    //Log.i(tag, "结束下载");
                    pd.dismiss();
                }

                @Override
                public void onWaiting() {
                    // 网络请求开始的时候调用
                    //Log.i(tag, "等待下载");
                }

                @Override
                public void onStarted() {
                    // 下载的时候不断回调的方法
                    //Log.i(tag, "开始下载");
                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {
                    // 当前的下载进度和文件总大小
                    //Log.i(tag, "正在下载中......");
                    pd.setMax((int)total);
                    pd.setProgress((int)current);
                }
            });
            
        return file;
    }
    
    

    protected void installApk(File file)
    {  
        Intent intent = new Intent();  
        intent.setAction(Intent.ACTION_VIEW);  
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");  
        context.startActivity(intent);  



    }



}
