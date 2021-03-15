package com.beingyi.app.AE.utils;

import android.content.Context;

import com.beingyi.app.AE.activity.MainActivity;
import com.beingyi.app.AE.adapter.FilesAdapter;
import com.beingyi.app.AE.ui.AlertProgress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FileClip {
    Context context;
    MainActivity activity;

    public static final int File = 1;
    public static final int inZip = 2;


    public int cuType;//1为复制文件到剪切板,2为复制zip里的文件到剪切板
    public boolean isHas = false;
    public boolean isCut = false;


    private String cuFilePath;
    private String cuZipPath;
    private String cuEntry;
    public static ZipTree zipTree;


    public FileClip(Context context) {
        this.context = context;
        this.activity = (MainActivity) context;
    }

    public void putFileClip(String path) {
        isHas = true;
        cuType = 1;
        cuFilePath = path;
    }


    public void putInZipClip(String path, String entry) {
        isHas = true;
        cuType = 2;
        cuZipPath = path;
        cuFilePath=path;
        cuEntry = entry;
    }


    public String getName() {

        String result = "";

        if (cuType == 1) {
            result = new File(cuFilePath).getName();
        }

        if (cuType == 2) {
            result = new File(cuEntry).getName();
        }

        return result;
    }


    public String getCuFilePath() {
        return cuFilePath;
    }


    public void putToZip(FilesAdapter adapter, final String Name) {

        if(cuType==1) {

            if (new File(cuFilePath).isFile()) {

                final AlertProgress progres = new AlertProgress(context);
                new Thread() {
                    @Override
                    public void run() {
                        progres.setLabel(new File(cuFilePath).getName());
                        progres.setNoProgress();
                        progres.show();
                        try {
                            zipTree = adapter.zipTree;
                            zipTree.putSingleFile(cuFilePath, Name);
                            zipTree.saveFile();
                            activity.showToast("复制完成");
                            activity.refreshList();

                        } catch (Exception e) {
                            activity.showMessage(context, "错误", e.toString());
                        }

                        progres.dismiss();
                    }
                }.start();

            } else {

                final AlertProgress progres = new AlertProgress(context);
                new Thread() {
                    @Override
                    public void run() {
                        progres.show();
                        listfile(cuFilePath);
                        progres.setProgress(1, fileList.size());

                        try {
                            for (File file : fileList) {
                                progres.setProgress(fileList.indexOf(file), fileList.size());
                                zipTree = adapter.zipTree;
                                zipTree.putSingleFile(file.getAbsolutePath(), file.getAbsolutePath().replace(cuFilePath, Name));
                                progres.setLabel(file.getAbsolutePath().replace(cuFilePath, Name));
                            }
                            zipTree.saveFile();
                        } catch (Exception e) {
                            activity.showMessage(context, "错误", e.toString());
                        }

                        activity.showToast("复制完成");
                        activity.refreshList();
                        progres.dismiss();
                    }
                }.start();

            }

        }

    }


    public void putToDir(FilesAdapter adapter, final String dir, final String Name) {

        if(cuType==1) {

            if (new File(cuFilePath).isFile()) {

                final AlertProgress progres = new AlertProgress(context);
                new Thread() {
                    @Override
                    public void run() {
                        progres.setLabel(new File(cuFilePath).getName());
                        progres.setNoProgress();
                        progres.show();
                        try {

                            copy(cuFilePath, dir + Name);

                            if(isCut){
                                FileUtils.delSingleFile(cuFilePath);
                                isCut=false;
                            }

                            activity.showToast("复制完成");
                            activity.refreshList();

                            adapter.setItemHighLight(dir + Name);

                        } catch (Exception e) {
                            activity.showMessage(context, "错误", e.toString());
                        }

                        progres.dismiss();
                    }
                }.start();

            } else {

                final AlertProgress progres = new AlertProgress(context);
                new Thread() {
                    @Override
                    public void run() {
                        progres.show();
                        listfile(cuFilePath);
                        progres.setProgress(1, fileList.size());

                        try {


                            for (File file : fileList) {

                                progres.setProgress(fileList.indexOf(file), fileList.size());
                                copy(file.getAbsolutePath(), file.getAbsolutePath().replace(cuFilePath, dir + Name + "/"));
                                progres.setLabel(file.getName());

                            }

                            if(isCut){
                                FileUtils.delFolder(cuFilePath);
                                isCut=false;
                            }

                            activity.showToast("复制完成");
                            activity.refreshList();

                        } catch (Exception e) {
                            activity.showMessage(context, "错误", e.toString());
                        }

                        progres.dismiss();
                    }
                }.start();


            }


        }



        if (cuType == 2) {

            final AlertProgress progres = new AlertProgress(context);
            new Thread() {
                @Override
                public void run() {
                    progres.setLabel("正在解压");
                    progres.setNoProgress();
                    progres.show();
                    try {
                        zipTree.extract(cuEntry, dir);

                        activity.showToast("解压完成");
                        activity.refreshList();

                    } catch (Exception e) {
                        activity.showMessage(context, "错误", e.toString());
                    }

                    progres.dismiss();
                }
            }.start();


        }




    }



    List<File> fileList = new ArrayList<>();

    private void listfile(String path) {
        File file = new File(path);

        if (file != null) {
            if (file.isDirectory()) {
                //fileList.add(file);  
                File[] files = file.listFiles();
                for (File f : files) {
                    listfile(f.getAbsolutePath());
                }
            } else {
                fileList.add(file);
            }
        }
    }


    private void copy(String src, String dest) throws Exception {

        int bytesum = 0;
        int byteread = 0;
        File oldfile = new File(src);

        if (!new File(dest).getParentFile().exists()) {
            new File(dest).getParentFile().mkdirs();
        }

        if (oldfile.exists()) {
            if (oldfile.isFile()) {

                InputStream inStream = new FileInputStream(src);
                FileOutputStream fs = new FileOutputStream(dest);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();

            } else {
                new File(dest).mkdirs();
            }

        }


    }


    public byte[] getCuByte() {

        byte[] data = null;

        if (cuType == 1) {
            try {
                data = FileUtils.read(cuFilePath);
            } catch (Exception e) {
                data = null;
            }
        }

        if (cuType == 2) {
            //FileUtils.read();
        }


        return data;

    }


}
