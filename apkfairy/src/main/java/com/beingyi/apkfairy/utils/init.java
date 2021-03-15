package com.beingyi.apkfairy.utils;



import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import java.io.File;
import org.xutils.BuildConfig;

public class init
{



    public static String app_dir=Conf.app_dir;


    public init(Context context)
    {


        try
        {



            mkdir(app_dir);
            mkdir(app_dir + "file");
            



        }
        catch (Exception e)
        {

        }






    }




    public void mkdir(String path)
    {


        try
        {
            if (!new File(path).exists())
            {

                new File(path).mkdirs();
            }


        }
        catch (Exception e)
        {


        }
    }






    public void firstRun(Context context)
    {

        if (SPUtils.getBoolean(context, "conf", "isFirstRun"))
        {

            SPUtils.putBoolean(context, "conf", "isFirstRun",true);
        }


    }





    public static void protect(final Context context)
    {
        new Thread(){
            @Override
            public void run()
            {
                Looper.prepare();

                int sign=SelfInfo.getSign(context);

                Looper.loop();
            }

        }.start();

    }



}
