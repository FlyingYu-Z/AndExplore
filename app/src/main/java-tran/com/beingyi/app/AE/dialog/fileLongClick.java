package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
import android.content.*;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.view.*;
import android.widget.*;
import android.widget.LinearLayout.*;

import com.beingyi.app.AE.utils.*;

import android.view.View.OnTouchListener;

import com.beingyi.app.AE.adapter.*;

import java.io.File;
import java.util.*;

import android.app.*;
import android.graphics.*;
import android.widget.AdapterView.*;

import com.beingyi.app.AE.ui.*;
import com.beingyi.app.AE.activity.MainActivity;

public class fileLongClick {

    Context context;
    MainActivity activity;
    AEUtils utils;
    int window;
    String path;


    public fileLongClick(Context mContext, int windows, String mPath) {

        this.context = mContext;
        this.activity = (MainActivity) context;
        this.utils = new AEUtils(context);
        this.window = windows;
        this.path = mPath;

        showPopupWindow();
    }


    private void showPopupWindow() {

        final ArrayList<String> menu = new ArrayList<String>();
        menu.add(MyApplication.getInstance().getString("43fc77886b7fbcd658f0ccdba36bd5a0"));//0
        menu.add(MyApplication.getInstance().getString("79d3abe929f67f0644a78bf32adb3a89"));//1
        menu.add(MyApplication.getInstance().getString("c8ce4b36cb6a0ff587974a738f32facf"));//2
        menu.add(MyApplication.getInstance().getString("2f4aaddde33c9b93c36fd2503f3d122b"));//3
        menu.add(MyApplication.getInstance().getString("6612548ad85b6a83b6f57b2dc1f27549"));//4
        menu.add(MyApplication.getInstance().getString("7a59d8873fce5aa70bc7fb6db91a1f78"));//5
        menu.add(MyApplication.getInstance().getString("c31f48f84ef207e66a03c015a7243b43"));//6
        menu.add(MyApplication.getInstance().getString("ba6e91fa9e0438e1ab780add0f6f5203"));//7
        menu.add(MyApplication.getInstance().getString("0b30610113e3ee7139de1993821e6dce"));//8
        menu.add(MyApplication.getInstance().getString("18782e80ef002b29c8907a73955bd905"));//9
        menu.add(MyApplication.getInstance().getString("f4130cae7d9d0026fd681c2aae22aa12"));//10
        menu.add(MyApplication.getInstance().getString("d7098f5050f017673319c5db1473ada7"));//11

        final Map map = new HashMap<Integer, String>();
        for (int i = 0; i < menu.size(); i++) {
            map.put(i, menu.get(i));
        }

        GridView menus = new GridView(context);
        menus.setBackgroundColor(Color.WHITE);
        menus.setNumColumns(3);

        MenusAdapter adapter = new MenusAdapter(context);
        adapter.setDate(menu);
        menus.setAdapter(adapter);


        final PopupWindow popupWindow = new PopupWindow(menus, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });
        //popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_launcher));
        //popupWindow.showAsDropDown(view);
        popupWindow.showAtLocation(((Activity) context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);


        menus.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> p1, View view, int position, long id) {

                switch (position) {
                    case 0: {
                        FileClip clip = activity.fileClip;
                        clip.putFileClip(path);
                        clip.isCut=true;
                        ToastUtils.show(MyApplication.getInstance().getString("7780807386446b8ba6ddf70a636193a6"));
                    }
                    break;

                    case 1: {
                        FileClip clip = activity.fileClip;
                        clip.putFileClip(path);
                        ToastUtils.show(MyApplication.getInstance().getString("7780807386446b8ba6ddf70a636193a6"));
                    }
                    break;

                    case 2:
                        new renameFile(context, window, path);
                        break;

                    case 3:
                        new deleteFile(context, window, path);
                        break;

                    case 4:
                        new zipFiles(context, window, path);
                        break;

                    case 5:
                        if (new File(path).isFile()) {
                            new fileEncoder(context, window, path);
                        } else {
                            ToastUtils.show(MyApplication.getInstance().getString("c3bd689bffe0803b137b305fc147bf81"));
                        }
                        break;

                    case 6:

                        File file = new File(path);
                        if (file.exists()) {

                            Intent shareIntent = new Intent(Intent.ACTION_SEND);

                            if (Build.VERSION.SDK_INT >= 24) {
                                shareIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, "com.beingyi.app.AE.install.fileProvider", file));
                            } else {
                                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                            }

                            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            shareIntent.setType(getMimeType(path));
                            //shareIntent.setType("*/*");
                            context.startActivity(Intent.createChooser(shareIntent, MyApplication.getInstance().getString("52a326c5f55bbf44f592490bc10a5fe4")));
                        } else {
                            ToastUtils.show(MyApplication.getInstance().getString("d9523e347c0a2971241d3ac4120a2854"));
                        }
                        break;

                    case 7:
                        new setPermissions(context, window, path);
                        break;

                    case 8:
                        utils.addPath(path);
                        break;

                    case 9:
                        new view_file_property(context,window,path);
                        break;

                    case 10:
                        utils.setClipBoardText(path);
                        ToastUtils.show(MyApplication.getInstance().getString("18e21437b5321d845e5d0d13a5efb40c"));
                        break;

                    case 11:
                        new openFileByWay(context,window,path);
                        break;

                    default:
                        break;

                }


                popupWindow.dismiss();
            }
        });


    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param filePath
     */
    public static String getMimeType(String filePath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String mime = "text/plain";
        if (filePath != null) {
            try {
                mmr.setDataSource(filePath);
                mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
            } catch (IllegalStateException e) {
                return mime;
            } catch (IllegalArgumentException e) {
                return mime;
            } catch (RuntimeException e) {
                return mime;
            }
        }
        return mime;
    }


}
