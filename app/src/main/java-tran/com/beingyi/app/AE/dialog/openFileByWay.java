package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.beingyi.app.AE.activity.TextEditor;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.ZipTree;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class openFileByWay extends baseDialog {
    public openFileByWay(Context mContext, int mWindow, String mPath) {
        super(mContext, mWindow, mPath);

        List<String> menus=new ArrayList<>();
        menus.add(MyApplication.getInstance().getString("a607de59aaa2f3687a98627f86499c5a"));
        menus.add(MyApplication.getInstance().getString("3069e77a1e0506302d778ecc3ddfb997"));
        menus.add(MyApplication.getInstance().getString("673866ff903b86fcc61130d955068e8a"));

        final String[] items = menus.toArray(new String[menus.size()]);
        ListAdapter itemlist = new ArrayAdapter(context, android.R.layout.simple_list_item_1, items);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(MyApplication.getInstance().getString("921d7be60128a408cdf7386678e52747"));
        builder.setAdapter(itemlist, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                if(which==0) {
                    Intent intent = new Intent(context, TextEditor.class);
                    intent.putExtra("isFile", true);
                    intent.putExtra("Path", Path);
                    context.startActivity(intent);

                }

                if(which==1){

                    final AlertProgress progres=new AlertProgress(context);
                    new Thread(){
                        @Override
                        public void run()
                        {


                            progres.setLabel(MyApplication.getInstance().getString("f013ea9dcba3f5ca1278aa850931fec8"));
                            progres.setNoProgress();
                            progres.show();
                            try
                            {
                                adapter.zipTree = new ZipTree(Path);
                                adapter.ListType = 2;

                                activity.runOnUiThread(new Runnable(){
                                    @Override
                                    public void run()
                                    {
                                        adapter.refresh();
                                    }
                                });
                            }
                            catch (Exception e)
                            {
                                activity.showDialog(e.toString());
                            }

                            progres.dismiss();

                        }
                    }.start();


                }

                if(which==2){
                    openFileByPath(context,Path);
                }


            }
        });

        builder.create().show();




    }

    /**
     * 根据路径打开文件
     * @param context 上下文
     * @param path 文件路径
     */
    public static void openFileByPath(Context context,String path) {
        if(context==null||path==null)
            return;


        String type = "";
        for(int i =0;i<MATCH_ARRAY.length;i++){

            if(path.toString().contains(MATCH_ARRAY[i][0].toString())){
                type = MATCH_ARRAY[i][1];
                break;
            }

        }


        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.beingyi.app.AE.install.fileProvider", new File(path));
            intent.setDataAndType(contentUri, type);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(new File(path)), type);
        }

        if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            context.startActivity(intent);
        }




    }



    private static final String[][] MATCH_ARRAY={
            //{后缀名，    文件类型}
            {".3gp",    "video/3gpp"},
            {".apk",    "application/vnd.android.package-archive"},
            {".asf",    "video/x-ms-asf"},
            {".avi",    "video/x-msvideo"},
            {".bin",    "application/octet-stream"},
            {".bmp",      "image/bmp"},
            {".c",        "text/plain"},
            {".class",    "application/octet-stream"},
            {".conf",    "text/plain"},
            {".cpp",    "text/plain"},
            {".doc",    "application/msword"},
            {".exe",    "application/octet-stream"},
            {".gif",    "image/gif"},
            {".gtar",    "application/x-gtar"},
            {".gz",        "application/x-gzip"},
            {".h",        "text/plain"},
            {".htm",    "text/html"},
            {".html",    "text/html"},
            {".jar",    "application/java-archive"},
            {".java",    "text/plain"},
            {".jpeg",    "image/jpeg"},
            {".jpg",    "image/jpeg"},
            {".js",        "application/x-javascript"},
            {".log",    "text/plain"},
            {".m3u",    "audio/x-mpegurl"},
            {".m4a",    "audio/mp4a-latm"},
            {".m4b",    "audio/mp4a-latm"},
            {".m4p",    "audio/mp4a-latm"},
            {".m4u",    "video/vnd.mpegurl"},
            {".m4v",    "video/x-m4v"},
            {".mov",    "video/quicktime"},
            {".mp2",    "audio/x-mpeg"},
            {".mp3",    "audio/x-mpeg"},
            {".mp4",    "video/mp4"},
            {".mpc",    "application/vnd.mpohun.certificate"},
            {".mpe",    "video/mpeg"},
            {".mpeg",    "video/mpeg"},
            {".mpg",    "video/mpeg"},
            {".mpg4",    "video/mp4"},
            {".mpga",    "audio/mpeg"},
            {".msg",    "application/vnd.ms-outlook"},
            {".ogg",    "audio/ogg"},
            {".pdf",    "application/pdf"},
            {".png",    "image/png"},
            {".pps",    "application/vnd.ms-powerpoint"},
            {".ppt",    "application/vnd.ms-powerpoint"},
            {".prop",    "text/plain"},
            {".rar",    "application/x-rar-compressed"},
            {".rc",        "text/plain"},
            {".rmvb",    "audio/x-pn-realaudio"},
            {".rtf",    "application/rtf"},
            {".sh",        "text/plain"},
            {".tar",    "application/x-tar"},
            {".tgz",    "application/x-compressed"},
            {".txt",    "text/plain"},
            {".wav",    "audio/x-wav"},
            {".wma",    "audio/x-ms-wma"},
            {".wmv",    "audio/x-ms-wmv"},
            {".wps",    "application/vnd.ms-works"},
            {".xml",    "text/plain"},
            {".z",        "application/x-compress"},
            {".zip",    "application/zip"},
            {"",        "*/*"}
    };


}
