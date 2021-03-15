package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.content.*;
import android.preference.PreferenceManager;

import com.beingyi.app.AE.application.MyApplication;
import com.beingyi.app.AE.bean.PathInfo;
import com.beingyi.app.AE.ui.ToastUtils;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.x;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class AEUtils {

    Context context;

    public AEUtils(Context context) {
        this.context = context;

    }


    public void init() {
        if (!new File(getCuPath(0)).exists()) {
            setCuPath(0, Conf.defDir);
        }

        if (
                !new File(getCuPath(1)).exists()
                ) {
            setCuPath(1, Conf.defDir);
            setCuPath(2, BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="));
        }
    }


    public boolean isHasRoot(){

        return RootChecker.isRoot()||RootChecker.checkSuFile()||RootChecker.haveRoot();
    }


    public void setIsAgreeLicense(boolean value) {
        SPUtils.putBoolean(context, BASE128.decode("W2pJPq03yxukoKDvS7KV3w=="), BASE128.decode("FoX+EivKgNv7EP7Td3TJVQ=="), value);
    }

    public boolean getIsAgreeLicense() {
        return SPUtils.getBoolean(context, BASE128.decode("W2pJPq03yxukoKDvS7KV3w=="), BASE128.decode("FoX+EivKgNv7EP7Td3TJVQ=="));
    }



    public void setCuFileBoard(String path) {
        SPUtils.putString(context, BASE128.decode("W2pJPq03yxukoKDvS7KV3w=="), BASE128.decode("o982gMRSrJARpHvYJ4f4ww=="), path);
    }

    public String getCuFileBoard() {
        return SPUtils.getString(context, BASE128.decode("W2pJPq03yxukoKDvS7KV3w=="), BASE128.decode("o982gMRSrJARpHvYJ4f4ww=="));
    }


    public void setSortType(int window, String type) {

        SPUtils.putString(context, BASE128.decode("W2pJPq03yxukoKDvS7KV3w=="), BASE128.decode("DVCOVY1gDmb+zezx+eoI6A==") + window, type);
    }

    public String getSortType(int window) {

        return SPUtils.getString(context, BASE128.decode("W2pJPq03yxukoKDvS7KV3w=="), BASE128.decode("DVCOVY1gDmb+zezx+eoI6A==") + window);
    }


    public void setIsDescSort(int window, boolean bool) {
        SPUtils.putBoolean(context, BASE128.decode("W2pJPq03yxukoKDvS7KV3w=="), BASE128.decode("oR+oelL4chiZRhMww51ThQ==") + window, bool);
    }

    public boolean getIsDescSort(int window) {
        return SPUtils.getBoolean(context, BASE128.decode("W2pJPq03yxukoKDvS7KV3w=="), BASE128.decode("oR+oelL4chiZRhMww51ThQ==") + window);
    }


    //修饰路径，如果没有以/结尾，则自动补充
    public String DesPath(String path) {
        if (path.endsWith(BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="))) {
            return path;
        } else {
            return path + BASE128.decode("AkZj32p4lHkS0d0LVieHfA==");
        }

    }


    public void setCuPath(int window, String path) {
        SPUtils.putString(context, BASE128.decode("W2pJPq03yxukoKDvS7KV3w=="), BASE128.decode("Tq7frmd+VKP6ElMGJva4ow==") + window, DesPath(path));
    }


    public String getCuPath(int window) {
        String path = SPUtils.getString(context, BASE128.decode("W2pJPq03yxukoKDvS7KV3w=="), BASE128.decode("Tq7frmd+VKP6ElMGJva4ow==") + window);

        if (path.equals(BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="))) {
            return DesPath(FileUtils.getSDPath());
        }

        File file = new File(path);
        if (file.exists()) {
            return DesPath(path);
        }

        return DesPath(FileUtils.getSDPath());
    }


    public void setCuPosition(int window, int value, String path) {
        SPUtils.putInt(context, BASE128.decode("F9lCm4qQbhArONUeTQeB3Q=="), BASE128.decode("cfZAckMzrIYUC0ap5FHUaw==") + window + DesPath(path), value);
    }


    public int getCuPosition(int window, String path) {
        return SPUtils.getInt(context, BASE128.decode("F9lCm4qQbhArONUeTQeB3Q=="), BASE128.decode("cfZAckMzrIYUC0ap5FHUaw==") + window + DesPath(path));
    }


    public void setWindowSum(int value) {
        SPUtils.putInt(context, BASE128.decode("W2pJPq03yxukoKDvS7KV3w=="), BASE128.decode("ch1D0iQE9AcUeLIr/H7WQg=="), value);
    }


    public int getWindowSum() {
        return SPUtils.getInt(context, BASE128.decode("W2pJPq03yxukoKDvS7KV3w=="), BASE128.decode("ch1D0iQE9AcUeLIr/H7WQg=="));
    }




    public void setCuFile(String value) {
        SPUtils.putString(context, BASE128.decode("W2pJPq03yxukoKDvS7KV3w=="), BASE128.decode("kXRVUTA/sWLEWg6im4umYw=="), value);
    }


    public String getCuFile() {
        return SPUtils.getString(context, BASE128.decode("W2pJPq03yxukoKDvS7KV3w=="), BASE128.decode("kXRVUTA/sWLEWg6im4umYw=="));
    }


    public void setCuFileMD5(String value) {
        SPUtils.putString(context, BASE128.decode("W2pJPq03yxukoKDvS7KV3w=="), BASE128.decode("0LVf1bkL01x4EGBZSQt9dg=="), value);
    }


    public String getCuFileMD5() {
        return SPUtils.getString(context, BASE128.decode("W2pJPq03yxukoKDvS7KV3w=="), BASE128.decode("0LVf1bkL01x4EGBZSQt9dg=="));
    }



    public SharedPreferences getSP(){
        SharedPreferences pre = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        return pre;
    }


    public void setClipBoardText(String str) {

        ClipboardManager clip = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        try {
            ClipData mClipData = ClipData.newPlainText(BASE128.decode("i8dmdFfzpVxMkh9lugRGIg=="), str);
            clip.setPrimaryClip(mClipData);

        } catch (Exception e) {

        }
    }

    public String getClipBoardText() {

        ClipboardManager cm = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData data = cm.getPrimaryClip();
        ClipData.Item item = data.getItemAt(0);
        String content = item.getText().toString();

        return content;
    }


    public void addPath(String path) {
        try {

            MyApplication application = new MyApplication();
            DbManager db = x.getDb(application.getDaoConfig());

            List<PathInfo> pathInfoList = db.findAll(PathInfo.class);
            if (pathInfoList != null && pathInfoList.size() != 0) {
                for(PathInfo pathInfo:pathInfoList){
                    if(pathInfo.getPath().equals(path)){
                        ToastUtils.show(BASE128.decode("3LEu2/QPJlglyi2Q0yL0WA=="));
                        return;
                    }
                }
            }


            PathInfo pathInfo = new PathInfo();
            pathInfo.setPath(path);

            db.save(pathInfo);

            ToastUtils.show(BASE128.decode("wYQYbKyUKJYzWcLI1KTrtTS1HhvHC7bpJcfVZVv3mYc="));
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.show(BASE128.decode("UdMyF94UOa1R9z0WbttG2uO8qc2Y/gPGYuT7uWo12lM="));
        }


    }


    public void removePath(String path) {

        try {
            MyApplication application = new MyApplication();
            DbManager db = x.getDb(application.getDaoConfig());

            List<PathInfo> pathInfoList = db.findAll(PathInfo.class);
            if (pathInfoList == null || pathInfoList.size() == 0) {
                return;
            }
            WhereBuilder whereBuilder = WhereBuilder.b();
            whereBuilder.and(BASE128.decode("cr62CT1jqczIn9Pg6z6GZg=="), BASE128.decode("umrqCDT02nVZcyC/FlPfQg=="), path);
            db.delete(PathInfo.class, whereBuilder);

            ToastUtils.show(BASE128.decode("LG6fi1TzDX3aNbdFanemDzS1HhvHC7bpJcfVZVv3mYc="));
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.show(BASE128.decode("Q1Lminl4fArHDEa28HntB+O8qc2Y/gPGYuT7uWo12lM="));
        }


    }


    public List<PathInfo> getPaths() {


        MyApplication application = new MyApplication();
        DbManager db = x.getDb(application.getDaoConfig());

        List<PathInfo> result = null;
        try {
            result = db.findAll(PathInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


}
