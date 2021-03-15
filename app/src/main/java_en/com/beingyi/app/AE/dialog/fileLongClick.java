package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
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
        menu.add(BASE128.decode("LEr79gfcCaz0/n6s7+wDqg=="));//0
        menu.add(BASE128.decode("RLZhXkgzAZFnbuWJPjwm9g=="));//1
        menu.add(BASE128.decode("uSSgg4ntY+kpfLtqY4bqPw=="));//2
        menu.add(BASE128.decode("DBtDOorkl+TzH/aRlk96gA=="));//3
        menu.add(BASE128.decode("SCSXN4rqETREoY19wrmISg=="));//4
        menu.add(BASE128.decode("lnIliZT+BvKi8tJ95eGCQg=="));//5
        menu.add(BASE128.decode("Xa09NbqugaX54J4jx1Mhpg=="));//6
        menu.add(BASE128.decode("9W7g49s+t6sAC838s3GtHA=="));//7
        menu.add(BASE128.decode("Pugrb7z/vVtZo2kSHke8hQ=="));//8
        menu.add(BASE128.decode("X/83UCLRZzJQZ5cGpnowMQ=="));//9
        menu.add(BASE128.decode("3f+oMIZECNLV0yFuY0S04Q=="));//10
        menu.add(BASE128.decode("5pAbky2yLn/aa5K8tImb/A=="));//11

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
                        ToastUtils.show(BASE128.decode("3w81FrYW83NWoAF6uR0tHCz7M2IpRNQoCPYzZX1Aji0="));
                    }
                    break;

                    case 1: {
                        FileClip clip = activity.fileClip;
                        clip.putFileClip(path);
                        ToastUtils.show(BASE128.decode("3w81FrYW83NWoAF6uR0tHCz7M2IpRNQoCPYzZX1Aji0="));
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
                            ToastUtils.show(BASE128.decode("NMW25GeszTaNHw75OClXG1SdFH3/Vqzc9G69CzRE19k="));
                        }
                        break;

                    case 6:

                        File file = new File(path);
                        if (file.exists()) {

                            Intent shareIntent = new Intent(Intent.ACTION_SEND);

                            if (Build.VERSION.SDK_INT >= 24) {
                                shareIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, BASE128.decode("qTy0S6HzisSLDitZhdnyDLYR5CBsaD+VxzLEEjOuQszDTW8RWwjrqSpwfKA5mfeE"), file));
                            } else {
                                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                            }

                            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            shareIntent.setType(getMimeType(path));
                            //shareIntent.setType("*/*");
                            context.startActivity(Intent.createChooser(shareIntent, BASE128.decode("h7Gx5LrX7a2V6aZkaTdJ6w==")));
                        } else {
                            ToastUtils.show(BASE128.decode("rsPdGg/70noG9TAq9SdgMw=="));
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
                        ToastUtils.show(BASE128.decode("d5gItZ0gk7w4hq6NhVNBryHRdB4X/ObrY5RPNppjD/0="));
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
        String mime = BASE128.decode("QxfmFuCs3ln/tlsPUeahDA==");
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
