package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipTree {
    public String Path;
    public HashMap<String, byte[]> zipEnties;
    public Tree tree;
    public ZipFile zipFile;
    public Stack<String> path;
    public int dep;


    public ZipTree(String mPath) throws IOException {
        this.Path = mPath;
        init();
    }

    public void init() throws IOException {

        zipEnties = new HashMap<String, byte[]>();

        zipFile = new ZipFile(Path);

        readZip(zipFile, zipEnties);
        tree = new Tree(zipEnties.keySet(), this);
    }

    private void readZip(ZipFile zip, Map<String, byte[]> map) throws IOException {
        Enumeration enums = zip.entries();
        while (enums.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) enums.nextElement();
            if (!entry.isDirectory()) {
                map.put(entry.getName(), null);
            }
        }

    }


    public List<String> getFiles(String dir) throws IOException {

        if (dir.equals(BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="))) {
            init();
            return tree.list();
        }
        if (dir.equals(BASE128.decode("07+hAGLvM2zqKN2Kzxs4VQ=="))) {
            tree.pop();
            return tree.list();
        }

        if (!dir.endsWith(BASE128.decode("AkZj32p4lHkS0d0LVieHfA==")) && !dir.equals(BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="))) {
            dir = dir + BASE128.decode("AkZj32p4lHkS0d0LVieHfA==");
        }

        tree.push(dir);
        List<String> list = tree.list();

        return list;
    }


    public ZipEntry getEntry(String name) {
        byte[] buf = zipEnties.get(tree.getCurPath() + name);
        if (buf == null) {
            if (zipFile != null) {
                return zipFile.getEntry(tree.getCurPath() + name);
            }
            ZipEntry zipEntry = new ZipEntry(tree.getCurPath() + name);
            zipEntry.setTime(0);
            zipEntry.setSize(0);
            return zipEntry;

        }
        ZipEntry zipEntry = new ZipEntry(tree.getCurPath() + name);
        //ZipEntry zipEntry=zipFile.getEntry(tree.getCurPath()+name);
        zipEntry.setTime(System.currentTimeMillis());
        zipEntry.setSize(buf.length);
        return zipEntry;
    }


    public void putSingleFile(String path, String name) throws Exception {

        if(new File(path).isFile()) {
            byte[] b = null;
            try {
                b = FileUtils.read(path);
            } catch (Exception e) {

            }
            zipEnties.put(tree.getCurPath() + name, b);
            tree.addNode(tree.getCurPath() + name);
            b=null;
        }

    }


    public void saveFile() throws Exception {
        FileUtils.copyFile(zipFile.getName(),zipFile.getName() + BASE128.decode("XSUE7dNrW1XKANoQWPNKjg=="));
        File tmp=new File(zipFile.getName()+BASE128.decode("eo+cFeo1W+fbazF6txzkjw=="));
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(tmp));

        for (String key : zipEnties.keySet()) {
            byte[] data = zipEnties.get(key);
            if (data != null) {
                ZipEntry zipEntry = new ZipEntry(key);
                zipEntry.setSize(data.length);
                zipEntry.setTime(System.currentTimeMillis());
                zos.putNextEntry(zipEntry);
                zos.write(data);
                zos.closeEntry();
            } else {
                ZipEntry zipEntry=new ZipEntry(key);
                InputStream in = zipFile.getInputStream(zipEntry);
                byte[] buf = FileUtils.toByteArray(in);
                if (zipEntry != null) {
                    zipEntry.setSize(buf.length);
                    zipEntry.setTime(System.currentTimeMillis());
                    zos.putNextEntry(zipEntry);
                    zos.write(buf);
                    zos.closeEntry();
                }
            }
        }

        zos.close();

        FileUtils.delSingleFile(zipFile.getName());
        tmp.renameTo(new File(zipFile.getName()));

    }



    public void extract(String entryName, String destDir) throws Exception {

        File outPath = new File(destDir);
        Map<String, byte[]> zipEnties = this.zipEnties;
        for (String key : zipEnties.keySet()) {
            if (key.startsWith(entryName)) {
                byte[] buf = zipEnties.get(key);
                if (buf != null) {
                    ZipExtract.extractEntryForByteArray(this, buf, key, outPath);
                } else {
                    ZipEntry entry = zipFile.getEntry(key);
                    ZipExtract.extractEntry(this, zipFile, entry, outPath);
                }
            }
        }
    }

    public File extractToFile(String entryName, String destPath) throws Exception {

        File outPath = new File(destPath);

        if (!outPath.getParentFile().exists()) {
            outPath.getParentFile().mkdirs();
        }

        ZipEntry entry = zipFile.getEntry(entryName);
        InputStream inputStream = zipFile.getInputStream(entry);
        FileUtils.saveFile(FileUtils.toByteArray(inputStream), outPath.getAbsolutePath());

        return outPath;
    }


    public void remove(String name){
        if(name.endsWith(BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="))){
            removeDirectory(name);
        }else{
            removeFile(name);
        }
    }

    private void removeFile(String name) {
        zipEnties.remove(tree.getCurPath() + name);
    }

    private void removeDirectory(String name) {
        Map<String, byte[]> zipEnties = this.zipEnties;
        Tree tree = this.tree;
        String curr = tree.getCurPath();
        Set<String> keySet = zipEnties.keySet();
        String[] keys = new String[keySet.size()];
        keySet.toArray(keys);
        for (String key : keys) {
            if (key.startsWith(curr + name)) {
                zipEnties.remove(key);
            }
        }
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

        public Tree(Set<String> names, ZipTree zipTree) {
            if (path == null) {
                path = new Stack<String>();
                dep = 0;
            }
            HashMap<String, byte[]> zipEnties = zipTree.zipEnties;
            node = new ArrayList<Map<String, String>>();
            for (String name : names) {
                String[] token = name.split(BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="));
                String tmp = BASE128.decode("FzrznU3UuD8KQAiBS9XCeA==");
                for (int i = 0, len = token.length; i < len; i++) {
                    String value = token[i];
                    if (i >= node.size()) {
                        Map<String, String> map = new HashMap<String, String>();
                        if (zipEnties.containsKey(tmp + value)
                                && i + 1 == len) {
                            map.put(tmp + value, tmp);
                        } else {
                            map.put(tmp + value + BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="), tmp);
                        }
                        node.add(map);
                        tmp += value + BASE128.decode("AkZj32p4lHkS0d0LVieHfA==");
                    } else {
                        Map<String, String> map = node.get(i);
                        if (zipEnties.containsKey(tmp + value)
                                && i + 1 == len) {
                            map.put(tmp + value, tmp);
                        } else {
                            map.put(tmp + value + BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="), tmp);
                        }
                        tmp += value + BASE128.decode("AkZj32p4lHkS0d0LVieHfA==");
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
                    if (key.endsWith(BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="))) {
                        index = key.lastIndexOf(BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="), key.length() - 2);
                    } else {
                        index = key.lastIndexOf(BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="));
                    }
                    if (index != -1)
                        key = key.substring(index + 1);
                    str.add(key);
                    //Log.e("tree",key);
                }
            }
            Collections.sort(str, sortByType);

            return str;
        }

        private void addNode(String entryName) {
            Map<String, String> map = node.get(dep);
            map.put(entryName, getCurPath());
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

            //    Log.e("tree Curpath",join(path,"/"));
            return join(path, BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="));
        }

        public boolean isDirectory(String name) {
            return name.endsWith(BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="));
        }

        private String join(Stack<String> stack, String d) {
            StringBuilder sb = new StringBuilder(BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="));
            for (String s : stack) {
                sb.append(s);
            }
            return sb.toString();
        }


    }


}
