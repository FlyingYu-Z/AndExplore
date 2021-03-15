package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.application.MyApplication;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.jf.dexlib.ClassDataItem;
import org.jf.dexlib.ClassDefItem;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.IndexedSection;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.ByteArrayAnnotatedOutput;
import org.jf.util.Parser;


public class ClassTree2
{

    public Tree tree;

    public static HashMap<String,ClassDefItem> classMap;
    public static HashMap<String,ClassDefItem> deleteclassMap;
    public static DexFile dexFile;
    public static boolean isChanged;
    public static ClassDefItem curClassDef;


    public static int dep;
    public static Stack<String> path;

    public static String curFile;
    public List<String> classList;


    private static final int OPENDIR=10;
    private static final int BACK=11;
    private static final int UPDATE=12;
    private static final int INIT=13;
    private static final int TOAST=14;
    private static final int SEARCH=15;
    private static final int SEARCHDISMISS=16;

    public int mod;

    public String Path;

    byte[] data;

    public ClassTree2(byte[] in){

        try
        {
            dexFile = new DexFile(in);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        init();



    }





    public ClassTree2(String mPath){
        this.Path=mPath;

        try
        {
            dexFile = new DexFile(Path);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        init();



    }



    private void init(){
        if(classMap==null){
            classMap=new HashMap<String,ClassDefItem>();
        }else{
            classMap.clear();
        }

        HashMap<String,ClassDefItem> classMap=ClassTree2.classMap;
        HashMap<String,ClassDefItem> deleteclassMap=ClassTree2.deleteclassMap;

        for(ClassDefItem classItem: dexFile.ClassDefsSection.getItems()){
            String className=classItem.getClassType().getTypeDescriptor();
            className=className.substring(1,className.length()-1);
            if(deleteclassMap!=null&&deleteclassMap.get(className)!=null){
                continue;
            }
            classMap.put(className,classItem);
        }
        tree=new Tree(classMap.keySet());

        classList=tree.list();


    }



    public List<String> getList(String dir){


        if (dir.equals("/"))
        {
            return tree.list();
        }
        if (dir.equals("../"))
        {
            tree.pop();
            return tree.list();
        }

        if (!dir.endsWith("/") && !dir.equals("/"))
        {
            dir = dir + "/";
        }

        tree.push(dir);

        List<String> list=tree.list();

        return list;
    }


    public static void setCurrnetClass(String className){
        curClassDef=classMap.get(className);
    }


    public void clearAll(){
        if(classMap !=null)
            classMap.clear();
        classMap=null;
        deleteclassMap=null;
        path=null;
        dexFile=null;
        curClassDef=null;
        tree=null;
        curFile=null;
        isChanged=false;
        System.gc();
    }

    private  void saveDexFile(){
        DexFile outDexFile=new DexFile();

        HashMap<String,ClassDefItem> classMap=ClassTree2.classMap;
        HashMap<String,ClassDefItem> deleteclassMap=ClassTree2.deleteclassMap;

        for(Map.Entry<String,ClassDefItem> entry:classMap.entrySet()){
            if(deleteclassMap!=null&&deleteclassMap.get(entry.getKey())!=null){
                continue;
            }
            ClassDefItem classDef=entry.getValue();
            classDef.internClassDefItem(outDexFile);
        }
        outDexFile.setSortAllItems(true);
        outDexFile.place();

        //out dex byte array
        byte[] buf=new byte[outDexFile.getFileSize()];
        ByteArrayAnnotatedOutput out=new ByteArrayAnnotatedOutput(buf);
        outDexFile.writeTo(out);

        DexFile.calcSignature(buf);
        DexFile.calcChecksum(buf);
        data=buf;
        outDexFile=null;
        isChanged=false;
    }

    private boolean mergerDexFile(String name){
        try{
            DexFile tmp=new DexFile(name);
            DexFile dexFile=ClassTree2.dexFile;
            IndexedSection<ClassDefItem> classes=tmp.ClassDefsSection;
            List<ClassDefItem> classDefList=classes.getItems();
            for(ClassDefItem classDef:classDefList){
                String className=classDef.getClassType().getTypeDescriptor();
                className=className.substring(1,className.length()-1);
                if(deleteclassMap!=null){
                    deleteclassMap.put(className,null);
                }
                classDef.internClassDefItem(dexFile);
            }
            mod=INIT;

            isChanged=true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        System.gc();
        return true;
    }




    private void replaceClassType(String src,String dst){
        for(TypeIdItem type: dexFile.TypeIdsSection.getItems()){
            String s=type.getTypeDescriptor();
            //skip start 'L'
            int pos=1;
            for(int i=0;i<s.length();i++){
                if(s.charAt(i)!='['){
                    break;
                }
                pos++;
            }
            int i=s.indexOf(src);
            if(i !=-1&&i==pos){
                s=s.replace(src,dst);
                type.setTypeDescriptor(s);
            }
        }
    }





    private static void searchStringInMethods(List<String> list,String src){

        HashMap<String,ClassDefItem> classMap=ClassTree2.classMap;
        HashMap<String,ClassDefItem> deleteclassMap=ClassTree2.deleteclassMap;

        for(Map.Entry<String,ClassDefItem> entry:classMap.entrySet()){
            if(deleteclassMap!=null&&deleteclassMap.get(entry.getKey())!=null){
                continue;
            }
            ClassDefItem classItem=entry.getValue();
            boolean isSearch=false;
            ClassDataItem classData=classItem.getClassData();
            if(classData !=null){
                //
                ClassDataItem.EncodedMethod[] methods=classData.getDirectMethods();
                for(ClassDataItem.EncodedMethod method :methods){
                    if(Parser.searchStringInMethod(method,src)){
                        String name=classItem.getClassType().getTypeDescriptor();
                        list.add(name.substring(1,name.length()-1));
                        isSearch=true;
                        break;
                    }
                }
                if(isSearch){
                    continue;
                }
                //virtual methods
                methods=classData.getVirtualMethods();
                for(ClassDataItem.EncodedMethod method :methods){
                    if(Parser.searchStringInMethod(method,src)){
                        String name=classItem.getClassType().getTypeDescriptor();
                        list.add(name.substring(1,name.length()-1));
                        break ;
                    }
                }
            }
        }
    }


    private static void searchFieldInMethods(List<String> list,String classType,String name,String descriptor,boolean ignoreNameAndDescriptor,boolean ignoreDescriptor){

        HashMap<String,ClassDefItem> classMap=ClassTree2.classMap;
        HashMap<String,ClassDefItem> deleteclassMap=ClassTree2.deleteclassMap;

        for(Map.Entry<String,ClassDefItem> entry:classMap.entrySet()){
            if(deleteclassMap!=null&&deleteclassMap.get(entry.getKey())!=null){
                continue;
            }
            ClassDefItem classItem=entry.getValue();
            boolean isSearch=false;
            ClassDataItem classData=classItem.getClassData();
            if(classData !=null){
                //
                ClassDataItem.EncodedMethod[] methods=classData.getDirectMethods();
                for(ClassDataItem.EncodedMethod method :methods){
                    if(Parser.searchFieldInMethod(method,classType,name,descriptor,ignoreNameAndDescriptor,ignoreDescriptor)){
                        String string=classItem.getClassType().getTypeDescriptor();
                        list.add(string.substring(1,string.length()-1));
                        isSearch=true;
                        break;
                    }
                }
                if(isSearch){
                    continue;
                }
                //virtual methods
                methods=classData.getVirtualMethods();
                for(ClassDataItem.EncodedMethod method :methods){
                    if(Parser.searchFieldInMethod(method,classType,name,descriptor,ignoreNameAndDescriptor,ignoreDescriptor)){
                        String string=classItem.getClassType().getTypeDescriptor();
                        list.add(string.substring(1,string.length()-1));
                        break ;
                    }
                }
            }
        }
    }


    private static void searchMethodInMethods(List<String> list,String classType,String name,String descriptor,boolean ignoreNameAndDescriptor,boolean ignoreDescriptor){

        HashMap<String,ClassDefItem> classMap=ClassTree2.classMap;
        HashMap<String,ClassDefItem> deleteclassMap=ClassTree2.deleteclassMap;

        for(Map.Entry<String,ClassDefItem> entry:classMap.entrySet()){
            if(deleteclassMap!=null&&deleteclassMap.get(entry.getKey())!=null){
                continue;
            }
            ClassDefItem classItem=entry.getValue();
            boolean isSearch=false;
            ClassDataItem classData=classItem.getClassData();
            if(classData !=null){
                //
                ClassDataItem.EncodedMethod[] methods=classData.getDirectMethods();
                for(ClassDataItem.EncodedMethod method :methods){
                    if(Parser.searchMethodInMethod(method,classType,name,descriptor,ignoreNameAndDescriptor,ignoreDescriptor)){
                        String string=classItem.getClassType().getTypeDescriptor();
                        list.add(string.substring(1,string.length()-1));
                        isSearch=true;
                        break;
                    }
                }
                if(isSearch){
                    continue;
                }
                //virtual methods
                methods=classData.getVirtualMethods();
                for(ClassDataItem.EncodedMethod method :methods){
                    if(Parser.searchMethodInMethod(method,classType,name,descriptor,ignoreNameAndDescriptor,ignoreDescriptor)){
                        String string=classItem.getClassType().getTypeDescriptor();
                        list.add(string.substring(1,string.length()-1));
                        break ;
                    }
                }
            }
        }
    }




    private void removeClassesDir(String name){
        if(deleteclassMap==null){
            deleteclassMap=new HashMap<String,ClassDefItem>();
        }

        HashMap<String,ClassDefItem> deleteclassMap=ClassTree2.deleteclassMap;

        String cur=tree.getCurPath()+name;
        for(String key:classMap.keySet()){
            if(key.indexOf(cur) == 0){
                deleteclassMap.put(key,classMap.get(key));
            }
        }
        isChanged=true;
        mod=INIT;

    }

    private void removeClasses(String name){
        if(deleteclassMap==null){
            deleteclassMap=new HashMap<String,ClassDefItem>();
        }

        String cur=tree.getCurPath()+name;
        deleteclassMap.put(cur,classMap.get(cur));
        isChanged=true;
        mod=INIT;
    }






    public static class Tree{
        private List<Map<String,String>> node;
        private Comparator<String> sortByType=new Comparator<String>(){
            public int compare(String a,String b){
                if(isDirectory(a) && !isDirectory(b)){
                    return -1;
                }
                if(!isDirectory(a) &&isDirectory(b)){
                    return 1;
                }
                return a.toLowerCase().compareTo(b.toLowerCase());
            }
        };

        public Tree(Set<String> names){
            if(path==null){
                path=new Stack<String>();
                dep=0;
            }
            HashMap<String,ClassDefItem> classMap=ClassTree2.classMap;
            node=new ArrayList<Map<String,String>>();
            for(String name :names){
                String[] token=name.split("/");
                String tmp="";
                for(int i=0,len=token.length;i<len;i++){
                    String value=token[i];
                    if(i>=node.size()){
                        Map<String,String> map=new HashMap<String,String>();
                        if(classMap.containsKey(tmp+value)
                                &&i+1 == len){
                            map.put(tmp+value,tmp);
                        }else{
                            map.put(tmp+value+"/",tmp);
                        }
                        node.add(map);
                        tmp+=value+"/";
                    }else{
                        Map<String,String> map=node.get(i);
                        if(classMap.containsKey(tmp+value)
                                &&i+1 == len){
                            map.put(tmp+value,tmp);
                        }else{
                            map.put(tmp+value+"/",tmp);
                        }
                        tmp+=value+"/";
                    }
                }
            }
        }

        public List<String> list(String parent){
            Map<String,String> map=null;
            List<String> str=new ArrayList<String>();
            while(dep>=0&&node.size()>0){
                map=node.get(dep);
                if(map != null){
                    break;
                }
                pop();
            }
            if(map ==null){
                return str;
            }
            for(String key :map.keySet()){
                if(parent.equals(map.get(key))){
                    int index;
                    if(key.endsWith("/")){
                        index=key.lastIndexOf("/",key.length()-2);
                    }else{
                        index=key.lastIndexOf("/");
                    }
                    if(index != -1)
                        key=key.substring(index+1);
                    str.add(key);
                    //        Log.e("tree",key);
                }
            }
            Collections.sort(str,sortByType);

            return str;
        }

        public List<String> list(){
            return list(getCurPath());
        }
        private void push(String name){
            dep++;
            path.push(name);
        }
        private String pop(){
            if(dep>0){
                dep--;
                return path.pop();
            }
            return null;
        }
        public String getCurPath(){
            return join(path,"/");
        }
        public boolean isDirectory(String name){
            return name.endsWith("/");
        }

        private String join(Stack<String> stack,String d){
            StringBuilder sb=new StringBuilder("");
            for(String s: stack){
                sb.append(s);
            }
            return sb.toString();
        }


    }






}
