package com.beingyi.app.AE.activity.ArscEditor.ArscLib;
import com.beingyi.app.AE.activity.ArscEditor.ArscLib.ResDecoder.ARSCCallBack;
import com.beingyi.app.AE.activity.ArscEditor.ArscLib.ResDecoder.data.ResTable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Utils {
    // 存储字符串的集合
    public List<String> txtOriginal = new ArrayList<String>();
    // 存储修改后的字符串的集合
    public List<String> txtTranslated = new ArrayList<String>();
    // 存储字符串在资源中对应的键
    public List<String> txtTranslated_Key = new ArrayList<String>();
    // 存储资源Configs的集合
    public static List<String> Configs;
    // 存储资源种类的集合
    public static List<String> Types;
    // 存储资源的集合
    private List<Map<String,String>> RESOURCES = new ArrayList<Map<String,String>>();
    // ARSC解析器
    private AndrolibResources mAndRes;
    // 字符串是否修改
    public boolean isChanged = false;
    // 资源类型
    private int ResType;
    // 资源类型常量
    public static final int ARSC = 0, AXML = 1, DEX = 2;


    public AndrolibResources getLib(){
        return this.mAndRes;
    }


    private void open(String resFile) throws IOException {
        if (resFile.endsWith(".arsc")) {
            open(new FileInputStream(resFile), ARSC);
        } else if (resFile.endsWith(".xml")) {
            open(new FileInputStream(resFile), AXML);
        } else if (resFile.endsWith(".dex")) {
            open(new FileInputStream(resFile), DEX);
        } else {
            throw new IOException("Unsupported FileType");
        }
    }

    private void open(InputStream resInputStream, int resType) {
        // 初始化一个线程用来解析资源文件
        mAndRes = new AndrolibResources();

        // 如果储存资源类型的列表未初始化
        if (Types == null) {
            // 初始化储存资源类型的列表
            Types = new ArrayList<String>();
        }

        // 实现资源回调接口
        ARSCCallBack callback = new ARSCCallBack() {
            // 进度值
            int i = 0;

            @Override
            public void back(String config, String type, String key, String value) {
                // 这里是为了出去一些不能编辑的字符串
                if (type != null) {
                    // 初始化键值映射
                    Map<String,String> values = new HashMap<String,String>();
                    // 向映射中添加资源的键
                    values.put(MyObj.NAME, key);
                    // 向映射中添加资源的值
                    values.put(MyObj.VALUE, value);
                    // 向映射中添加资源的种类
                    values.put(MyObj.TYPE, type);
                    // 向映射中添加资源的Config
                    values.put(MyObj.CONFIG, config);
                    // 向资源中添加该映射
                    RESOURCES.add(values);
                }
                // 如果资源种类集合中不存在该种类
                if (!Types.contains(type))
                    // 向其中添加该种类
                    Types.add(type);
                // 进度值加1
                i++;
                // 更新进度条
            }
        };
        try {
            mAndRes.decodeARSC(getResTable(resInputStream), callback);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Collections.sort(Types);
    }

    // 获取ARSC文件的ResTable的方法
    public ResTable getResTable(InputStream ARSCStream) throws IOException {
        return mAndRes.getResTable(ARSCStream);
    }

    // 一个储存键的类
    class MyObj {
        public final static String NAME = "name";
        public final static String VALUE = "value";
        public final static String TYPE = "type";
        public final static String CONFIG = "config";
    }

    public void openArsc(String file_name){
        try {
            open(file_name);
        } catch (IOException e) {

        }
    }

    public void saveArsc(String input,String out){
        FileOutputStream fo1;
        try {
            fo1 = new FileOutputStream(out);
            FileInputStream fi1 = new FileInputStream(input);
            mAndRes.mARSCDecoder.write(fo1,fi1, txtOriginal, txtTranslated);
            fo1.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        isChanged = false;
    }

    public void getResouces(String key,String value){
        // 如果储存Config的列表未初始化
        if (Configs == null) {
            // 初始化Config列表
            Configs = new ArrayList<String>();
        }

        // 检查是否发生改变
        for (String str : txtTranslated) {
            if (!str.equals(""))
                isChanged = true;
            break;
        }

        if (isChanged) {
            // 排序整理修改后的内容，以方便一一写入
            for (int i = 0; i < txtOriginal.size(); i++)
                mAndRes.mARSCDecoder.mTableStrings.sortStringBlock(txtOriginal.get(i), txtTranslated.get(i));
        }

        // 清除几个列表中的元素
        txtOriginal.clear();
        txtTranslated.clear();
        txtTranslated_Key.clear();
        Configs.clear();

        for (Map<String,String> resource : RESOURCES) {
            // 获取资源的键
            String NAME = (String) resource.get(MyObj.NAME);
            // 获取资源的值
            String VALUE = (String) resource.get(MyObj.VALUE);
            // 获取资源类型
            String TYPE = (String) resource.get(MyObj.TYPE);
            // 获取资源分支
            String CONFIG = (String) resource.get(MyObj.CONFIG);
//			System.out.println("NAME: " + NAME + " VALUE: " + VALUE + " TYPE: " + TYPE + " CONGIG: " + CONFIG);
            // 如果资源的Config开头存在-符号，并且Config列表中不存在该资源的Config元素，并且资源种类是params[0]的值
            if (CONFIG.startsWith("-") && !Configs.contains(CONFIG.substring(1)) && TYPE.equals(key))
                // 向Config列表中添加元素
                Configs.add(CONFIG.substring(1));
                // 如果资源的Config开头不存在-符号，并且Config列表中不存在该资源的Config元素，并且资源种类是params[0]的值
            else if (!CONFIG.startsWith("-") && !Configs.contains(CONFIG) && TYPE.equals(key))
                Configs.add(CONFIG);

            // 如果资源的Config开头存在-符号，并且Config列表中存在该资源的Config元素，并且Config是params[1]的值
            if (TYPE.equals(key) && CONFIG.startsWith("-") && CONFIG.substring(1).equals(value)) {
                // 向储存字符串的列表中添加字符串成员
                txtOriginal.add(VALUE);
                // 向储存修改后的字符串的列表中添加空成员
                txtTranslated.add("");
                // 向储存资源的键的列表添加键
                txtTranslated_Key.add(NAME);
                // 如果资源的Config开头不存在-符号，并且Config列表中存在该资源的Config元素，并且Config是params[1]的值
            } else if (TYPE.equals(key) && !CONFIG.startsWith("-") && CONFIG.equals(value)) {
                // 向储存字符串的列表中添加字符串成员
                txtOriginal.add(VALUE);
                // 向储存修改后的字符串的列表中添加空成员
                txtTranslated.add("");
                // 向储存资源的键的列表添加键
                txtTranslated_Key.add(NAME);
            }
        }

        Collections.sort(Configs);
    }

    public void changeResouce(String key,String value){
        int position = txtTranslated_Key.indexOf("app_name");
        if(position == -1) return;
        System.out.println("txtTranslated: " + txtOriginal.get(position));
        txtTranslated.remove(position);
        // 向当前位置添加新的内容，以此实现文本的更新
        txtTranslated.add(position, value);
    }
}