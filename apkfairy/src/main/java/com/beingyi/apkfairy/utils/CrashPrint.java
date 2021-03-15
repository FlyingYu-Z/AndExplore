package com.beingyi.apkfairy.utils;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.beingyi.apkfairy.BuildConfig;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrashPrint
{
    ExecutorService cachedThreadPool=Executors.newCachedThreadPool();

    String str;
    Context context;

    final Handler handler;




    public CrashPrint(final Context context,final String str){

        this.str=str;
        this.context=context;


        handler = new Handler(){
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 0:

                        break;

                    case 1:
                        if(BuildConfig.DEBUG){
                            new alert(context,str);
                            cachedThreadPool.shutdown();

                        }

                        break;


                    case 3:
                        break;





                }
            }
        };




        cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {

                    handler.sendEmptyMessage(1);

                }


            });
    }



}

