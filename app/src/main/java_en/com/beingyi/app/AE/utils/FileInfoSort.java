package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import com.beingyi.app.AE.bean.*;

import java.io.*;
import java.util.*;

public class FileInfoSort {


    public static void sort(int window, List<FileInfo> fileList, AEUtils utils) {
        String type = utils.getSortType(window);
        boolean isDesc = utils.getIsDescSort(window);

        if (type.equals(BASE128.decode("B8T9mSJENI+tkPi/vSU8FQ=="))) {
            sortByName(fileList, isDesc);
        }
        if (type.equals(BASE128.decode("Wx+syHAFTWHfLp7vtFPBkw=="))) {
            sortByTime(fileList, isDesc);
        }

        sortByName(fileList, false);
    }


    public static List<FileInfo> sortByName(List<FileInfo> fileList, boolean isDesc) {
        List<FileInfo> result = fileList;

        Collections.sort(fileList, new Comparator<FileInfo>() {

            @Override
            public int compare(FileInfo p1, FileInfo p2) {

                File file1 = new File(p1.Path);
                File file2 = new File(p2.Path);


                if (file1.isDirectory() && file2.isFile()) {
                    return -1;
                }
                if (file1.isFile() && file2.isDirectory()) {
                    return 1;
                }


                return file1.getName().compareTo(file2.getName());


            }
        });


        return result;
    }


    public static List<FileInfo> sortByTime(List<FileInfo> fileList, boolean isDesc) {
        List<FileInfo> result = fileList;

        Collections.sort(fileList, new Comparator<FileInfo>() {

            @Override
            public int compare(FileInfo p1, FileInfo p2) {

                File file1 = new File(p1.Path);
                File file2 = new File(p2.Path);


                if (file1.lastModified() >= file2.lastModified()) {
                    return -1;
                }
                if (file1.lastModified() <= file2.lastModified()) {
                    return 1;
                }


                return 1;


            }
        });


        return result;
    }


}
