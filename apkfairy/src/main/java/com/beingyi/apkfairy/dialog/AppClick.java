package com.beingyi.apkfairy.dialog;

import android.content.*;
import android.content.pm.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.support.v7.app.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.beingyi.apkfairy.*;
import com.beingyi.apkfairy.activity.*;
import com.beingyi.apkfairy.utils.*;
import java.io.*;
import java.util.*;
import org.xutils.common.util.*;

public class AppClick implements OnClickListener
{


    Context context;
    List<AppInfo> appinfos;
    AppInfo appinfo;
    ComUtils comUtils;
    Handler handler;
    MainActivity activity;
    String backpath;

    View view;


    public AppClick(Context context, List<AppInfo> appinfos, AppInfo appinfo, Handler handler, MainActivity activity)
    {

        this.context = context;
        this.appinfos = appinfos;
        this.appinfo = appinfo;
        this.comUtils = new ComUtils(context);
        this.handler = handler;

        this.activity = activity;

        backpath = comUtils.getBackPath();



        LayoutInflater inflater=LayoutInflater.from(context);
        view = inflater.inflate(R.layout.view_app_menu, null);

        ((Button)view.findViewById(R.id.viewappmenuButton1)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.viewappmenuButton2)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.viewappmenuButton3)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.viewappmenuButton4)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.viewappmenuButton5)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.viewappmenuButton6)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.viewappmenuButton7)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.viewappmenuButton8)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.viewappmenuButton9)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.viewappmenuButton10)).setOnClickListener(this);


        if (appinfo.isBacked)
        {
            ((Button)view.findViewById(R.id.viewappmenuButton10)).setText("删除备份");

        }
        else
        {
            ((Button)view.findViewById(R.id.viewappmenuButton10)).setText("备份");
        }



        AlertDialog dialog = new AlertDialog.Builder(context)

            .setTitle(appinfo.appName)
            .setIcon(appinfo.appIcon)
            .setView(view)
            .create();
        dialog.show();

    }





    @Override
    public void onClick(View p1)
    {
        Intent intent=new Intent();

        int i = p1.getId();//详细信息
//系统管理
//清除数据(ROOT)
//强力卸载(ROOT)
//快捷启动
//冻结应用(ROOT)
//保存图标
//备份数据(ROOT)
//备份/删除备份
        if (i == R.id.viewappmenuButton1) {
            new AppInfoDialog(context, appinfos, appinfo, handler, activity);

        } else if (i == R.id.viewappmenuButton2) {
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", appinfo.packageName, null));
            context.startActivity(intent);

        } else if (i == R.id.viewappmenuButton3) {
            Uri packageURI = Uri.parse("package:" + appinfo.packageName);
            intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(packageURI);
            context.startActivity(intent);


        } else if (i == R.id.viewappmenuButton4) {
            if (!Shell.haveRoot()) {
                new alert(context, "你还没有获取ROOT");
                return;
            }
            String result4 = Shell.execRootCmd("pm clear " + appinfo.packageName +
                    "\n");
            new alert(context, result4);

        } else if (i == R.id.viewappmenuButton5) {
            if (!Shell.haveRoot()) {
                new alert(context, "你还没有获取ROOT");
                return;
            }
            String result5 = Shell.execRootCmd(FileUtils.readFromAssets(context, "shell/sysrw.sh") + "\n" +
                    "rm -f " + appinfo.path +
                    "\n");
            new alert(context, result5);

        } else if (i == R.id.viewappmenuButton6) {
            PackageManager packageManager = context.getPackageManager();
            intent = new Intent();
            intent = packageManager.getLaunchIntentForPackage(appinfo.packageName);
            if (intent == null) {
                Toast.makeText(context, "该应用未安装", Toast.LENGTH_LONG).show();
                return;
            }

            Intent launch = new Intent(context, launch.class);
            launch.putExtra("packname", appinfo.packageName);
            context.startActivity(launch);


        } else if (i == R.id.viewappmenuButton7) {
            if (!Shell.haveRoot()) {
                new alert(context, "你还没有获取ROOT");
                return;
            }
            String result = Shell.execRootCmd("pm disable " + appinfo.packageName +
                    "\n");
            new alert(context, result);


        } else if (i == R.id.viewappmenuButton8) {
            Bitmap bitmap = ((BitmapDrawable) appinfo.appIcon).getBitmap();
            try {
                String sdcardPath = System.getenv("EXTERNAL_STORAGE");      //获得sd卡路径
                String dir = sdcardPath + "/DCIM/";                    //图片保存的文件夹名
                File file = new File(dir);                                 //已File来构建
                if (!file.exists()) {
                    file.mkdirs();
                }
                Log.i("SaveImg", "file uri==>" + dir);
                File mFile = new File(dir + appinfo.appName + ".png");//将要保存的图片文件

                FileOutputStream outputStream = new FileOutputStream(mFile);     //构建输出流
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);  //compress到输出outputStream
                Uri uri = Uri.fromFile(mFile);                                  //获得图片的uri
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)); //发送广播通知更新图库，这样系统图库可以找到这张图片
                Toast.makeText(context, "图标已被保存到：" + dir, Toast.LENGTH_SHORT).show();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(context, e + "", Toast.LENGTH_SHORT).show();

            }


        } else if (i == R.id.viewappmenuButton9) {
            Toast.makeText(context, "暂不支持该功能", Toast.LENGTH_SHORT).show();

        } else if (i == R.id.viewappmenuButton10) {
            if (appinfo.isBacked) {
                delBack(appinfo);
            } else {

                backApp();
            }


        } else {
        }


    }




    public void delBack(AppInfo appinfo)
    {

        File file=new File(comUtils.getBackPath() + appinfo.appName + "_" + appinfo.versionName + ".apk");

        try
        {
            file.delete();
            activity.updateIsBacked(appinfo, false);

            ((Button)view.findViewById(R.id.viewappmenuButton10)).setText("备份");
            handler.sendEmptyMessage(7);

        }
        catch (Exception e)
        {
            Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
        }

    }







    public void backApp()
    {



        handler.sendEmptyMessage(3);

        new Thread(){
            @Override
            public void run()
            {

                Message msg =new Message();
                msg.obj = appinfo.appName;
                msg.what = 5;
                handler.sendMessage(msg);

                try
                {
                    FileUtil.copy(appinfo.path, backpath + appinfo.appName + "_" + appinfo.versionName + ".apk");
                    activity.updateIsBacked(appinfo, true);

                    activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {

                                ((Button)view.findViewById(R.id.viewappmenuButton10)).setText("删除备份");
                            }
                        });

                }
                catch (Exception e)
                {
                    msg.obj = e.toString();
                    msg.what = 6;
                    handler.sendMessage(msg);

                }

                handler.sendEmptyMessage(4);
            }
        }.start();





    }


}
