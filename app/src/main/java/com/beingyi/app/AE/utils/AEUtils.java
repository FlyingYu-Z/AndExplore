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
        SPUtils.putBoolean( "conf", "IsAgreeLicense", value);
    }

    public boolean getIsAgreeLicense() {
        return SPUtils.getBoolean( "conf", "IsAgreeLicense");
    }



    public void setCuFileBoard(String path) {
        SPUtils.putString( "conf", "cuFileBoard", path);
    }

    public String getCuFileBoard() {
        return SPUtils.getString( "conf", "cuFileBoard");
    }


    public void setSortType(int window, String type) {

        SPUtils.putString( "conf", "sort_mode" + window, type);
    }

    public String getSortType(int window) {

        return SPUtils.getString( "conf", "sort_mode" + window);
    }


    public void setIsDescSort(int window, boolean bool) {
        SPUtils.putBoolean( "conf", "isSortDesc" + window, bool);
    }

    public boolean getIsDescSort(int window) {
        return SPUtils.getBoolean( "conf", "isSortDesc" + window);
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
        SPUtils.putString( "conf", "path" + window, DesPath(path));
    }


    public String getCuPath(int window) {
        String path = SPUtils.getString( "conf", "path" + window);

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
        SPUtils.putInt( "position", "window" + window + DesPath(path), value);
    }


    public int getCuPosition(int window, String path) {
        return SPUtils.getInt( "position", "window" + window + DesPath(path));
    }


    public void setWindowSum(int value) {
        SPUtils.putInt( "conf", "WindowSum", value);
    }


    public int getWindowSum() {
        return SPUtils.getInt( "conf", "WindowSum");
    }




    public void setCuFile(String value) {
        SPUtils.putString( "conf", "CuFile", value);
    }


    public String getCuFile() {
        return SPUtils.getString( "conf", "CuFile");
    }


    public void setCuFileMD5(String value) {
        SPUtils.putString( "conf", "CuFileMD5", value);
    }


    public String getCuFileMD5() {
        return SPUtils.getString( "conf", "CuFileMD5");
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
                        ToastUtils.show("书签已存在");
                        return;
                    }
                }
            }


            PathInfo pathInfo = new PathInfo();
            pathInfo.setPath(path);

            db.save(pathInfo);

            ToastUtils.show("添加书签成功");
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.show("添加书签失败");
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

            ToastUtils.show("删除书签成功");
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.show("删除书签失败");
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
