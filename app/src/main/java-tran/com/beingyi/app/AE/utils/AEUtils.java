package com.beingyi.app.AE.utils;

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
            setCuPath(2, "/");
        }
    }


    public boolean isHasRoot(){

        return RootChecker.isRoot()||RootChecker.checkSuFile()||RootChecker.haveRoot();
    }


    public void setIsAgreeLicense(boolean value) {
        SPUtils.putBoolean(context, "conf", "IsAgreeLicense", value);
    }

    public boolean getIsAgreeLicense() {
        return SPUtils.getBoolean(context, "conf", "IsAgreeLicense");
    }



    public void setCuFileBoard(String path) {
        SPUtils.putString(context, "conf", "cuFileBoard", path);
    }

    public String getCuFileBoard() {
        return SPUtils.getString(context, "conf", "cuFileBoard");
    }


    public void setSortType(int window, String type) {

        SPUtils.putString(context, "conf", "sort_mode" + window, type);
    }

    public String getSortType(int window) {

        return SPUtils.getString(context, "conf", "sort_mode" + window);
    }


    public void setIsDescSort(int window, boolean bool) {
        SPUtils.putBoolean(context, "conf", "isSortDesc" + window, bool);
    }

    public boolean getIsDescSort(int window) {
        return SPUtils.getBoolean(context, "conf", "isSortDesc" + window);
    }


    //修饰路径，如果没有以/结尾，则自动补充
    public String DesPath(String path) {
        if (path.endsWith("/")) {
            return path;
        } else {
            return path + "/";
        }

    }


    public void setCuPath(int window, String path) {
        SPUtils.putString(context, "conf", "path" + window, DesPath(path));
    }


    public String getCuPath(int window) {
        String path = SPUtils.getString(context, "conf", "path" + window);

        if (path.equals("")) {
            return DesPath(FileUtils.getSDPath());
        }

        File file = new File(path);
        if (file.exists()) {
            return DesPath(path);
        }

        return DesPath(FileUtils.getSDPath());
    }


    public void setCuPosition(int window, int value, String path) {
        SPUtils.putInt(context, "position", "window" + window + DesPath(path), value);
    }


    public int getCuPosition(int window, String path) {
        return SPUtils.getInt(context, "position", "window" + window + DesPath(path));
    }


    public void setWindowSum(int value) {
        SPUtils.putInt(context, "conf", "WindowSum", value);
    }


    public int getWindowSum() {
        return SPUtils.getInt(context, "conf", "WindowSum");
    }




    public void setCuFile(String value) {
        SPUtils.putString(context, "conf", "CuFile", value);
    }


    public String getCuFile() {
        return SPUtils.getString(context, "conf", "CuFile");
    }


    public void setCuFileMD5(String value) {
        SPUtils.putString(context, "conf", "CuFileMD5", value);
    }


    public String getCuFileMD5() {
        return SPUtils.getString(context, "conf", "CuFileMD5");
    }



    public SharedPreferences getSP(){
        SharedPreferences pre = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        return pre;
    }


    public void setClipBoardText(String str) {

        ClipboardManager clip = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        try {
            ClipData mClipData = ClipData.newPlainText("Label", str);
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
                        ToastUtils.show(MyApplication.getInstance().getString("c70c97a5516a149bb511b133179d27ee"));
                        return;
                    }
                }
            }


            PathInfo pathInfo = new PathInfo();
            pathInfo.setPath(path);

            db.save(pathInfo);

            ToastUtils.show(MyApplication.getInstance().getString("43fcdd4206e7b1e58a447d3474cdefea"));
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.show(MyApplication.getInstance().getString("4f140e231b81f1e48759a692159a5afd"));
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
            whereBuilder.and("Path", "=", path);
            db.delete(PathInfo.class, whereBuilder);

            ToastUtils.show(MyApplication.getInstance().getString("99d85aaf2fa388e7914084e59e17129d"));
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.show(MyApplication.getInstance().getString("bf0158780df510e2f48cd6fb1ec5a40c"));
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
