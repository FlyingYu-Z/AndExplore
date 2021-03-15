package com.beingyi.app.AE.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.activity.MainActivity;
import com.beingyi.app.AE.bean.FileInfo;
import com.beingyi.app.AE.holder.FileInfoHolder;
import com.beingyi.app.AE.utils.AEUtils;
import com.beingyi.app.AE.utils.APKUtils;
import com.beingyi.app.AE.utils.AsyncImageLoader;
import com.beingyi.app.AE.utils.Shell;
import com.beingyi.app.AE.utils.ShellUtils;
import com.beingyi.app.AE.utils.FileInfoSort;
import com.beingyi.app.AE.utils.FileSizeUtils;
import com.beingyi.app.AE.utils.FileUtils;
import com.beingyi.app.AE.utils.ZipTree;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;

import com.beingyi.app.AE.dialog.alert;

public class FilesAdapter extends BaseAdapter {


    Context context;
    MainActivity activity;
    AEUtils utils;
    ShellUtils shellUtils;
    public List<FileInfo> fileList;
    ListView listview;
    int window = 0;
    public AsyncImageLoader asyncImageLoader;
    public int ListType = 1;//1为文件夹,2为zip

    public ZipTree zipTree;


    public FilesAdapter(final Context context, ListView listview, int mWindow) {
        this.context = context;
        this.activity = (MainActivity) context;
        this.shellUtils=activity.shell;
        this.utils = new AEUtils(context);
        this.fileList = new ArrayList<FileInfo>();
        this.listview = listview;
        this.window = mWindow;
        asyncImageLoader = new AsyncImageLoader();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        ListType = 1;
        getFiles(utils.getCuPath(window));

    }


    public void goParent() {
        fileList.clear();

        if (ListType == 1) {
            String path = "/";
            path = utils.getCuPath(window);

            if (!path.equals("/")) {
                path = new File(path).getParentFile().getAbsolutePath();
            }
            getFiles(path);

        } else {
            if (zipTree.tree.getCurPath().equals("")) {
                ListType = 1;
                refresh();
                zipTree = null;
            } else {
                getFiles("../");
            }

        }

    }


    public void refresh() {
        if (ListType == 1) {
            String path = "/";
            path = utils.getCuPath(window);
            getFiles(path);

        } else {

            getFiles("/");

        }

    }


    public void getFiles(String dir) {
        fileList.clear();
        notifyDataSetChanged();

        final String Dir = dir;

        if (ListType == 1) {

            try {
                dir = utils.DesPath(dir);
                File DirFile = new File(dir);
                String[] files = null;


                if (utils.isHasRoot() && !DirFile.canRead()) {

                    if(!DirFile.canRead()&&shellUtils.isGotRoot){
                        shellUtils.run("mount -o remount,ro "+dir,2000);
                        //String result=shellUtils.run("ls "+dir,6000).getResult();
                        //new alert(context,result);
                        //files = result.split("\n");
                        files = DirFile.list();
                    }

                } else {
                    files = DirFile.list();
                }

                for (int i = 0; i < files.length; i++) {
                    File file = new File(dir + files[i]);

                    FileInfo fileInfo = new FileInfo();
                    fileInfo.Path = dir + files[i];
                    fileInfo.Name = file.getName();
                    fileInfo.Time = FileUtils.getModifiedTime(file.getAbsolutePath());
                    if (file.isFile()) {
                        fileInfo.Size = FileSizeUtils.getAutoFileOrFilesSize(file.getAbsolutePath());
                    } else {
                        fileInfo.Size = "";
                    }

                    if (files[i].startsWith(".")) {

                        boolean viHideFiles = utils.getSP().getBoolean("pre_settings_viHideFiles", true);
                        if (viHideFiles) {
                            fileList.add(fileInfo);
                        }
                    } else {
                        fileList.add(fileInfo);
                    }


                }


            } catch (Exception e) {
                fileList.clear();
                //new alert(context, e.toString());
            }


            FileInfoSort.sort(window, fileList, utils);

            listview.post(new Runnable() {
                @Override
                public void run() {
                    listview.setSelection(utils.getCuPosition(window, Dir));
                }
            });
        }


        if (ListType == 2) {

            try {
                List<String> files = zipTree.getFiles(dir);

                for (int i = 0; i < files.size(); i++) {

                    FileInfo fileInfo = new FileInfo();

                    fileInfo.Name = files.get(i);
                    fileInfo.Path = zipTree.Path;
                    fileInfo.Entry = zipTree.tree.getCurPath() + files.get(i);
                    fileInfo.Time = "";


                    fileList.add(fileInfo);
                }

            } catch (Exception e) {
                activity.showMessage(context, "错误", e.toString());
            }


        }


        notifyDataSetChanged();


        Intent intent = new Intent("android.intent.action.update");
        intent.putExtra("window", window);
        intent.putExtra("path", dir);
        intent.putExtra("type", ListType);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


    }


    public void setItemHighLight(final String path) {


        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                File file = new File(path);
                for (FileInfo info : fileList) {
                    if (info.Name.equals(file.getName())) {
                        final int position = fileList.indexOf(info) + 1;
                        listview.post(new Runnable() {

                            @Override
                            public void run() {
                                listview.setSelection(position);
                                getViewByPosition(position, listview).setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                            }
                        });

                        break;
                    }
                }

            }
        });


    }

    private View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    public ListView getListView() {
        return listview;
    }

    public int getWindow() {

        return window;
    }

    public void setWindow(int value) {

        utils.setCuPath(value, utils.getCuPath(window));
        this.window = value;
    }

    public void setData(ArrayList<FileInfo> fileList) {

        this.fileList = fileList;
        notifyDataSetChanged();

    }


    @Override
    public int getCount() {
        if (fileList != null && fileList.size() > 0) {
            return fileList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (fileList != null && fileList.size() > 0) {
            return fileList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    public List<FileInfo> getData() {
        return fileList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        FileInfoHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_file, null);
            holder = new FileInfoHolder();

            holder.Icon = (ImageView) convertView.findViewById(R.id.item_file_ImageView_icon);
            holder.Name = (TextView) convertView.findViewById(R.id.item_file_TextView_name);
            holder.Time = (TextView) convertView.findViewById(R.id.item_file_TextView_time);
            holder.Size = (TextView) convertView.findViewById(R.id.item_file_TextView_size);
            holder.State = (TextView) convertView.findViewById(R.id.item_file_TextView_state);

            convertView.setTag(holder);


        } else {
            holder = (FileInfoHolder) convertView.getTag();
        }


        FileInfo fileInfo = fileList.get(position);


        if (ListType == 1) {

            holder.Name.setText(fileInfo.Name);
            holder.Time.setText(fileInfo.Time);
            holder.Size.setText(fileInfo.Size);

            displayImage(position, fileInfo.Path, holder.Icon, holder.State);

        } else {
            try {
                ZipEntry zipEntry = zipTree.getEntry(fileInfo.Name);
                holder.Name.setText(fileInfo.Name);

                //如果不是文件夹
                if (!zipTree.tree.isDirectory(fileInfo.Name)) {
                    holder.Size.setText(convertBytesLength(zipEntry.getSize()));
                    Date date = new Date(zipEntry.getTime());
                    SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                    holder.Time.setText(format.format(date));

                } else {
                    holder.Size.setText("");
                    holder.Time.setText("");
                }
            } catch (Exception e) {
                new alert(context, fileInfo.Name + "：" + e.toString());
            }

            displayImage(position, fileInfo.Entry, holder.Icon, holder.State);

        }


        if (listview.isItemChecked(position)) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.AppColor));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }


        return convertView;
    }


    private String convertBytesLength(long len) {
        if (len < 1024) {
            return len + "B";
        }
        if (len < 1024 * 1024) {
            return String.format("%.2f%s", (len / 1024.0), "K");
        }
        if (len < 1024 * 1024 * 1024)
            return String.format("%.2f%s", (len / (1024 * 1024.0)), "M");
        return String.format("%.2f%s", (len / (1024 * 1024 * 1024.0)), "G");
    }


    private void displayImage(int position, String imageUrl, ImageView imageView, TextView state) {
        imageView.setTag(imageUrl);
        state.setText("");
        state.setTag("状态" + imageUrl);
        asyncImageLoader.loadImage(context, this, position, imageUrl, imageLoadListener);

    }

    public void loadImage() {
        int start = listview.getFirstVisiblePosition();
        int end = listview.getLastVisiblePosition();
        if (end >= getCount()) {
            end = getCount() - 1;
        }
        asyncImageLoader.setLoadLimit(start, end);
        asyncImageLoader.unlock();
    }


    AsyncImageLoader.OnImageLoadListener imageLoadListener = new AsyncImageLoader.OnImageLoadListener() {

        @Override
        public void onImageLoad(Integer t, Drawable drawable, String url) {
            View view = listview.findViewWithTag(url);
            if (view != null) {
                ImageView iv = (ImageView) view.findViewById(R.id.item_file_ImageView_icon);
                iv.setBackgroundDrawable(drawable);

                TextView state = (TextView) listview.findViewWithTag("状态" + url).findViewById(R.id.item_file_TextView_state);
                if (FileUtils.getSuffix(url).equals("apk") &&
                        APKUtils.isApkInstalled(context, APKUtils.getPkgName(context, url)) &&
                        state.getTag().equals("状态" + url) &&
                        new File(url).isFile()) {
                    state.setText("已安装");
                    state.setTextColor(Color.RED);
                }

            }
        }

        @Override
        public void onError(Integer t, String url) {
            View view = listview.findViewWithTag(url);
            if (view != null) {
                ImageView iv = (ImageView) view.findViewById(R.id.item_file_ImageView_icon);
                iv.setBackgroundResource(R.drawable.ic_launcher);
            }
        }

    };


}
