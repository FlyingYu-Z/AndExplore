package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.application.MyApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.xmlpull.v1.XmlPullParser;
import org.json.JSONObject;
import android.content.Context;

import com.beingyi.app.AE.axmleditor.decode.AXMLDoc;
import com.beingyi.app.AE.axmleditor.editor.PermissionEditor;
import com.bigzhao.xml2axml.AxmlUtils;

import maobyte.xml.decode.AXmlDecoder;
import maobyte.xml.decode.AXmlResourceParser;

public class SuperEncryption
{
    String Input;
    String Output;

    ZipFile zipFile;

    JSONObject json_FileMD5;
    List<String> dexList=new ArrayList<>();
    List<String> activityList=new ArrayList<>();
    HashMap<String, byte[]> zipEnties = new HashMap<String, byte[]>();
    ZipOutputStream zos;


    String packageName;
    String customApplicationName="android.app.Application";
    boolean customApplication=true;

    public SuperEncryption(String mInput, String mOutput)
    {
        this.Input = mInput;
        this.Output = mOutput;

    }


    public void start()
    {

        try
        {
            json_FileMD5=new JSONObject();
            zos = new ZipOutputStream(new FileOutputStream(Output));
            zipFile = new ZipFile(Input);
            readZip(zipFile, zipEnties);
            activityList = parseManifestActivity(zipFile.getInputStream(zipFile.getEntry("AndroidManifest.xml")));
            
            System.out.println(json_FileMD5.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        try
        {

            byte[] xml=parseManifest(zipFile.getInputStream(zipFile.getEntry("AndroidManifest.xml")));
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            AXMLDoc doc = new AXMLDoc();
            doc.parse(new ByteArrayInputStream(xml));


            PermissionEditor permissionEditor = new PermissionEditor(doc);
            permissionEditor.setEditorInfo(new PermissionEditor.EditorInfo()
                                           .with(new PermissionEditor.PermissionOpera("android.permission.INTERNET").add())
                                           );
            permissionEditor.commit();
            doc.build(out);
            doc.release();
            
            String axml=new String(AxmlUtils.decode(out.toByteArray()));
            
            for(int i=0;i<activityList.size();i++){
                String activity=activityList.get(i);
                if(activity.startsWith(".")){
                    activityList.remove(i);
                    
                    activityList.add(packageName+activity);
                    axml=axml.replace("\""+activity,"\""+packageName+activity+"_Child");
                }
            }
            

            for(int i=0;i<activityList.size();i++){
                String activity=activityList.get(i);
                
            }
            
            
            byte[] outAxml=AxmlUtils.encodeByte(axml.getBytes());
            
            
            ZipOutUtil.AddFile(zos, "AndroidManifest.xml", outAxml);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




        try
        {
            boolean isApplicationNameValid=false;
            for (int i=0;i < dexList.size();i++)
            {

                String dexName=dexList.get(i);
                System.out.println(MyApplication.getInstance().getString("aa7d858f31fa870d0b559f50c9082315") + dexName);
                InputStream inputStream=zipFile.getInputStream(zipFile.getEntry(dexName));
                byte[] dexByte=toByteArray(inputStream);
                dealDEX dealDEX=new dealDEX(this, customApplicationName, activityList, dexByte);
                byte[] result=dealDEX.getBytes();
                if (dealDEX.isApplicationNameValid)
                {
                    isApplicationNameValid = true;
                }
                ZipOutUtil.AddFile(zos, dexName, result);

            }
            
            if(!isApplicationNameValid){
                customApplicationName="android.app.Application";
            }
            

            System.out.println(MyApplication.getInstance().getString("ca6336a9b039f92b1a86998f11d11fe5"));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }





        try
        {




        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        try
        {
            List saveList=new ArrayList<>();
            saveList = dexList;
            saveList.add("AndroidManifest.xml");
            ZipOutUtil.Sava(zipFile, zos, saveList, new ZipOutUtil.ZipSavsCallback(){

                    @Override
                    public void onStep(ZipOutUtil.Step step)
                    {

                    }

                    @Override
                    public void onProgress(int progress, int total)
                    {

                    }
                });

        }
        catch (Exception e)
        {

        }






    }






    //修改AndroidManifest Application Name属性
    public byte[] parseManifest(InputStream is) throws IOException
    {
        AXmlDecoder axml = AXmlDecoder.decode(is);
        maobyte.xml.decode.AXmlResourceParser parser = new maobyte.xml.decode.AXmlResourceParser();
        parser.open(new ByteArrayInputStream(axml.getData()), axml.mTableStrings);
        boolean success = false;
        int type;
        while ((type = parser.next()) != XmlPullParser.END_DOCUMENT)
        {
            if (type != XmlPullParser.START_TAG)
                continue;
            if (parser.getName().equals("manifest"))
            {
                int size = parser.getAttributeCount();
                for (int i = 0; i < size; ++i)
                {
                    if (parser.getAttributeName(i).equals("package"))
                    {
                        packageName = parser.getAttributeValue(i);
                    }
                }
            }
            else if (parser.getName().equals("application"))
            {
                int size = parser.getAttributeCount();
                for (int i = 0; i < size; ++i)
                {
                    if (parser.getAttributeNameResource(i) == 0x01010003)
                    {
                        customApplication = true;
                        customApplicationName = parser.getAttributeValue(i);
                        int index = axml.mTableStrings.getSize();
                        byte[] data = axml.getData();
                        int off = parser.currentAttributeStart + 20 * i;
                        off += 8;
                        ManifestParse.writeInt(data, off, index);
                        off += 8;
                        ManifestParse.writeInt(data, off, index);
                    }
                }
                if (!customApplication)
                {
                    int off = parser.currentAttributeStart;
                    byte[] data = axml.getData();
                    byte[] newData = new byte[data.length + 20];
                    System.arraycopy(data, 0, newData, 0, off);
                    System.arraycopy(data, off, newData, off + 20, data.length - off);

                    // chunkSize
                    int chunkSize = ManifestParse.readInt(newData, off - 32);
                    ManifestParse.writeInt(newData, off - 32, chunkSize + 20);
                    // attributeCount
                    ManifestParse.writeInt(newData, off - 8, size + 1);

                    int idIndex = parser.findResourceID(0x01010003);
                    if (idIndex == -1)
                        throw new IOException("idIndex == -1");

                    boolean isMax = true;
                    for (int i = 0; i < size; ++i)
                    {
                        int id = parser.getAttributeNameResource(i);
                        if (id > 0x01010003)
                        {
                            isMax = false;
                            if (i != 0)
                            {
                                System.arraycopy(newData, off + 20, newData, off, 20 * i);
                                off += 20 * i;
                            }
                            break;
                        }
                    }
                    if (isMax)
                    {
                        System.arraycopy(newData, off + 20, newData, off, 20 * size);
                        off += 20 * size;
                    }

                    ManifestParse.writeInt(newData, off, axml.mTableStrings.find("http://schemas.android.com/apk/res/android"));
                    ManifestParse.writeInt(newData, off + 4, idIndex);
                    ManifestParse.writeInt(newData, off + 8, axml.mTableStrings.getSize());
                    ManifestParse.writeInt(newData, off + 12, 0x03000008);
                    ManifestParse.writeInt(newData, off + 16, axml.mTableStrings.getSize());
                    axml.setData(newData);
                }
                success = true;
                break;
            }
        }
        if (!success)
            throw new IOException();
        ArrayList<String> list = new ArrayList<>(axml.mTableStrings.getSize());
        axml.mTableStrings.getStrings(list);
        list.add("com.fly.ShellSub");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        axml.write(list, baos);
        return baos.toByteArray();
    }





    private void readZip(ZipFile zip, Map<String, byte[]> map) throws Exception
    {
        Enumeration enums = zip.entries();
        while (enums.hasMoreElements())
        {
            ZipEntry entry = (ZipEntry) enums.nextElement();
            String entryName = entry.getName();
            // System.out.println(entryName);

            if (entryName.startsWith("classes") && entryName.endsWith(".dex"))
            {
                dexList.add(entryName);
            }

            if ((entryName.startsWith("classes") && entryName.endsWith(".dex"))||
                entryName.startsWith("res/")||
                entryName.startsWith("assets/")
            )
            {
                InputStream input=zipFile.getInputStream(zipFile.getEntry(entryName));
                String md5=getMD5ByStream(input);
                json_FileMD5.put(entryName,md5);
                
            }

            
            
            
            if (!entry.isDirectory())
            {
                map.put(entry.getName(), null);
            }
        }

    }




    public static List<String> parseManifestActivity(InputStream is) throws IOException
    {
        String PackageName = null;
        List<String> list=new ArrayList<String>();
        AXmlDecoder axml = AXmlDecoder.decode(is);
        AXmlResourceParser parser = new AXmlResourceParser();
        parser.open(new ByteArrayInputStream(axml.getData()), axml.mTableStrings);
        int type;
        while ((type = parser.next()) != XmlPullParser.END_DOCUMENT)
        {
            if (type != XmlPullParser.START_TAG)
                continue;
            if (parser.getName().equals("manifest"))
            {
                int size = parser.getAttributeCount();
                for (int i = 0; i < size; ++i)
                {
                    if (parser.getAttributeName(i).equals("package"))
                        PackageName = parser.getAttributeValue(i);
                }
            }
            else if (parser.getName().equals("activity"))
            {
                int size = parser.getAttributeCount();
                for (int i = 0; i < size; ++i)
                {
                    if (parser.getAttributeNameResource(i) == 0x01010003)
                    {
                        String name=parser.getAttributeValue(i);
                        if (name.startsWith("."))
                        {
                            name = PackageName + name;
                        }
                        list.add(name);
                    }
                }
            }
        }
        System.out.println(MyApplication.getInstance().getString("0b2bd723f74c95966ac15443c0803373") + PackageName);

        return list;
	}


    private static ByteArrayOutputStream cloneInputStream(InputStream input)
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1)
            {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }





    public static byte[] toByteArray(InputStream in) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    public static String getMD5ByStream(InputStream is) {
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");

            while ((len = is.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            is.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bi.toString(16);
    }

    


}
