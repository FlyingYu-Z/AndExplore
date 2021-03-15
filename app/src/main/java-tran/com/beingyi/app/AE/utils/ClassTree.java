package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.application.MyApplication;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jf.baksmali.Adaptors.ClassDefinition;
import org.jf.baksmali.BaksmaliOptions;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.writer.builder.DexBuilder;
import org.jf.dexlib2.writer.io.MemoryDataStore;

import com.beingyi.app.AE.activity.DexEditor;
import com.google.common.collect.Lists;

import org.jf.dexlib2.dexbacked.reference.DexBackedTypeReference;
import org.jf.util.IndentingWriter;


public class ClassTree {

    public Tree tree;
    public HashMap<String, ClassDef> classMap;
    public DexBackedDexFile dexFile;
    public ClassDef curClassDef;

    public int dep;
    public Stack<String> path;

    public String curFile;
    public List<ClassDef> classDefList = new ArrayList<>();
    public int mod;

    public String Path;
    byte[] data;

    byte[] input;


    public ClassTree(String mPath) throws Exception {
        this.Path = mPath;

        initDex();
    }

    private void initDex() throws Exception {
        input = FileUtils.read(Path);
        dexFile = DexBackedDexFile.fromInputStream(Opcodes.getDefault(), new ByteArrayInputStream(input));
        classDefList.addAll(dexFile.getClasses());
        initClassMap();
    }


    private void initClassMap() {


        if (classMap == null) {
            classMap = new HashMap<String, ClassDef>();
        } else {
            classMap.clear();
        }


        for (int i = 0; i < classDefList.size(); i++) {
            ClassDef classDef = classDefList.get(i);
            String type = classDef.getType();
            type = type.substring(1, type.length() - 1);
            classMap.put(type, classDef);
        }


        tree = null;
        tree = new Tree(classMap);

    }


    public List<String> getList(String dir) {


        if (dir.equals("/")) {
            initClassMap();
            return tree.list();
        }

        if (dir.equals("../")) {
            tree.pop();
            return tree.list();
        }

        if (!dir.endsWith("/") && !dir.equals("/")) {
            dir = dir + "/";
        }

        tree.push(dir);

        List<String> list = tree.list();

        return list;
    }


    public void setCurrnetClass(String className) {
        curClassDef = classMap.get(className);
    }


    public void removeClass(String className) {

        {
            Iterator classIterator = classMap.keySet().iterator();
            while (classIterator.hasNext()) {
                ClassDef classDef = classMap.get(classIterator.next());
                String type = classDef.getType();
                type = type.substring(1, type.length() - 1);

                if (tree.isDirectory(className)) {
                    if (type.startsWith(className)) {
                        classIterator.remove();
                    }
                } else {
                    if (type.equals(className)) {
                        classIterator.remove();
                    }
                }
            }
        }


        {
            Iterator classIterator = classDefList.iterator();
            while (classIterator.hasNext()) {
                ClassDef classDef = (ClassDef) classIterator.next();
                String type = classDef.getType();
                type = type.substring(1, type.length() - 1);

                if (tree.isDirectory(className)) {
                    if (type.startsWith(className)) {
                        classIterator.remove();
                    }
                } else {
                    if (type.equals(className)) {
                        classIterator.remove();
                    }
                }
            }

        }

        initClassMap();

    }


    public void clearAll() {
        if (classMap != null)
            classMap.clear();
        classMap = null;
        path = null;
        dexFile = null;
        curClassDef = null;
        tree = null;
        curFile = null;
        System.gc();
    }


    public List<ClassDef> searchClass(String content, SearchProgress searchProgress) {
        List<ClassDef> result = new ArrayList<>();

        int total = classMap.size();
        int i = 0;

        Iterator classIterator = classMap.keySet().iterator();
        while (classIterator.hasNext()) {
            ClassDef classDef = classMap.get(classIterator.next());
            String type = classDef.getType();

            if (type.contains(content)) {
                result.add(classDef);
            }

            i++;
            searchProgress.onProgress(i, total);

        }

        return result;
    }


    public List<Method> searchMethod(String content, SearchProgress searchProgress) {
        List<Method> result = new ArrayList<>();


        int total = classMap.size();
        int i = 0;


        Iterator classIterator = classMap.keySet().iterator();
        while (classIterator.hasNext()) {
            ClassDef classDef = classMap.get(classIterator.next());

            Iterator methods = classDef.getMethods().iterator();
            while (methods.hasNext()) {
                Method method = (Method) methods.next();
                String name = method.getName();

                if (name.contains(content)) {
                    result.add(method);
                }

            }


            i++;
            searchProgress.onProgress(i, total);

        }

        return result;
    }


    public List<ClassDef> searchCode(String content, SearchProgress searchProgress) {
        List<ClassDef> result = new ArrayList<>();


        int total = classMap.size();
        int i = 0;


        Iterator classIterator = classMap.keySet().iterator();
        while (classIterator.hasNext()) {
            ClassDef classDef = classMap.get(classIterator.next());
            String type = classDef.getType();

            String code = getSmali(classDef);

            if (code.contains(content)) {
                result.add(classDef);
            }


            i++;
            searchProgress.onProgress(i, total);

        }

        return result;
    }


    public List<ClassDef> searchString(String content, SearchProgress searchProgress) {
        List<ClassDef> result = new ArrayList<>();


        int total = classMap.size();
        int i = 0;


        Iterator classIterator = classMap.keySet().iterator();
        while (classIterator.hasNext()) {
            ClassDef classDef = classMap.get(classIterator.next());
            String type = classDef.getType();

            String code = getSmali(classDef);


            Pattern p = Pattern.compile("(const-string\\s.([0-9]*),\\s\"(.*)\"\n)");
            Matcher m = p.matcher(code);
            while (m.find()) {
                String str = m.group(3);
                if (str.contains(content)) {
                    result.add(classDef);
                }
            }


            i++;
            searchProgress.onProgress(i, total);

        }

        return result;
    }


    public void saveClassDef(ClassDef classDef) {

        DexBuilder dexBuilder = new DexBuilder(Opcodes.getDefault());
        dexBuilder.setIgnoreMethodAndFieldError(true);
        dexBuilder.internClassDef(classDef);


        String type = classDef.getType();
        type = type.substring(1, type.length() - 1);
        classMap.remove(type);
        classMap.put(type, classDef);

    }


    public void saveDexFile(String destPath, DexSaveProgress dexSaveProgress) {

        DexBuilder dexBuilder = new DexBuilder(Opcodes.getDefault());
        dexBuilder.setIgnoreMethodAndFieldError(true);

        try {

            int index = 0;
            for (String type : classMap.keySet()) {

                ClassDef classDef = classMap.get(type);
                dexBuilder.internClassDef(classDef);

                index++;

                dexSaveProgress.onProgress(index, classMap.size());

            }


            MemoryDataStore memoryDataStore = new MemoryDataStore();
            dexBuilder.writeTo(memoryDataStore);
            byte[] result = Arrays.copyOf(memoryDataStore.getBufferData(), memoryDataStore.getSize());
            FileUtils.saveFile(result, destPath);
            data = result;
            DexEditor.isChanged = false;
            DexEditor.isSaved = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public String getSmali(ClassDef classDef) {
        String code = null;
        try {
            StringWriter stringWriter = new StringWriter();
            IndentingWriter writer = new IndentingWriter(stringWriter);
            ClassDefinition classDefinition = new ClassDefinition(new BaksmaliOptions(), classDef);
            classDefinition.writeTo(writer);
            writer.close();
            code = stringWriter.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return code;
    }


    public class Tree {

        private List<Map<String, String>> node;
        private Comparator<String> sortByType = new Comparator<String>() {
            public int compare(String a, String b) {
                if (isDirectory(a) && !isDirectory(b)) {
                    return -1;
                }
                if (!isDirectory(a) && isDirectory(b)) {
                    return 1;
                }
                return a.toLowerCase().compareTo(b.toLowerCase());
            }
        };

        public Tree(HashMap<String, ClassDef> classMap) {
            if (path == null) {
                path = new Stack<String>();
                dep = 0;
            }
            Set<String> names = classMap.keySet();

            node = new ArrayList<Map<String, String>>();
            for (String name : names) {
                String[] token = name.split("/");
                String tmp = "";
                for (int i = 0, len = token.length; i < len; i++) {
                    String value = token[i];
                    if (i >= node.size()) {
                        Map<String, String> map = new HashMap<String, String>();
                        if (classMap.containsKey(tmp + value)
                                && i + 1 == len) {
                            map.put(tmp + value, tmp);
                        } else {
                            map.put(tmp + value + "/", tmp);
                        }
                        node.add(map);
                        tmp += value + "/";
                    } else {
                        Map<String, String> map = node.get(i);
                        if (classMap.containsKey(tmp + value)
                                && i + 1 == len) {
                            map.put(tmp + value, tmp);
                        } else {
                            map.put(tmp + value + "/", tmp);
                        }
                        tmp += value + "/";
                    }
                }
            }
        }

        public List<String> list(String parent) {
            Map<String, String> map = null;
            List<String> str = new ArrayList<String>();
            while (dep >= 0 && node.size() > 0) {
                map = node.get(dep);
                if (map != null) {
                    break;
                }
                pop();
            }
            if (map == null) {
                return str;
            }
            for (String key : map.keySet()) {
                if (parent.equals(map.get(key))) {
                    int index;
                    if (key.endsWith("/")) {
                        index = key.lastIndexOf("/", key.length() - 2);
                    } else {
                        index = key.lastIndexOf("/");
                    }
                    if (index != -1)
                        key = key.substring(index + 1);
                    str.add(key);
                }
            }
            Collections.sort(str, sortByType);

            return str;
        }

        public List<String> list() {
            return list(getCurPath());
        }

        private void push(String name) {
            dep++;
            path.push(name);
        }

        private String pop() {
            if (dep > 0) {
                dep--;
                return path.pop();
            }
            return null;
        }

        public String getCurPath() {
            return join(path, "/");
        }

        public boolean isDirectory(String name) {
            return name.endsWith("/");
        }

        private String join(Stack<String> stack, String d) {
            StringBuilder sb = new StringBuilder("");
            for (String s : stack) {
                sb.append(s);
            }
            return sb.toString();
        }


    }


    public static interface DexSaveProgress {

        void onProgress(int progress, int total);

    }


    public static interface SearchProgress {

        void onProgress(int progress, int total);

    }


}
