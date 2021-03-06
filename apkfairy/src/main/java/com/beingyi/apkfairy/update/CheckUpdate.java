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


                    Log.i(TAG, "???????????????");

                    Message msg = new Message();
                    msg.what = UPDATA_NONEED;
                    handler.sendMessage(msg);


                }
                else
                {



                    Log.i(TAG, "?????????????????? ");


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
                    //Toast.makeText(context, "??????????????????", Toast.LENGTH_SHORT).show();
                    break;







                case UPDATA_CLIENT:
                    showUpdataDialog();
                    break;



                case GET_UNDATAINFO_ERROR:
                    //Toast.makeText(context, "??????????????????", 1).show(); 

                    break;








                case DOWN_ERROR:
                    Toast.makeText(context, "????????????", 1).show(); 

                    break;
            }
        }
    };





    protected void showUpdataDialog()
    {




        AlertDialog dialog = new AlertDialog.Builder(context)

            .setTitle("??????????????????")
            .setMessage(info.getDescription())
            .setCancelable(false)
            .setNeutralButton("???????????????", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
					Uri uri = Uri.parse(Conf.getServer(context)+info.getUrl());
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					context.startActivity(intent);
				}
			})
            .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                    if(!BuildConfig.DEBUG){
                        //System.exit(0);
                        
                    }
                }
            })
            .setPositiveButton("????????????", new DialogInterface.OnClickListener() {
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
            .title("????????????")
            .content(info.getDescription())
            .positiveText("??????")
            .negativeText("??????")
            .widgetColor(Color.BLUE)
            .cancelable(false)
            .canceledOnTouchOutside(false)
            .titleGravity(GravityEnum.CENTER)
            .checkBoxPrompt("????????????", false, new CompoundButton.OnCheckedChangeListener(){
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
        pd.setMessage("????????????????????????");
        
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
                    //Log.i(tag, "????????????");
                    pd.dismiss();
                    installApk(Apkfile);
                    
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    //Log.i(tag, "????????????");
                    pd.dismiss();
                    new alert(context,"????????????");
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    new alert(context,"????????????");
                }

                @Override
                public void onFinished() {
                    //Log.i(tag, "????????????");
                    pd.dismiss();
                }

                @Override
                public void onWaiting() {
                    // ?????????????????????????????????
                    //Log.i(tag, "????????????");
                }

                @Override
                public void onStarted() {
                    // ????????????????????????????????????
                    //Log.i(tag, "????????????");
                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {
                    // ???????????????????????????????????????
                    //Log.i(tag, "???????????????......");
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
