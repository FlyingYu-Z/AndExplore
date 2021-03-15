package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;

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
    String customApplicationName=BASE128.decode("EnM/8rXT2pKwONschPT5jVu4ecBx1q6aCyC7uf9/bTw=");
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
            activityList = parseManifestActivity(zipFile.getInputStream(zipFile.getEntry(BASE128.decode("D+BmCCsRH3FxOj5eLEEB8NBW2g58+EBHfDisVC/GmJA="))));
            
            System.out.println(json_FileMD5.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        try
        {

            byte[] xml=parseManifest(zipFile.getInputStream(zipFile.getEntry(BASE128.decode("D+BmCCsRH3FxOj5eLEEB8NBW2g58+EBHfDisVC/GmJA="))));
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            AXMLDoc doc = new AXMLDoc();
            doc.parse(new ByteArrayInputStream(xml));


            PermissionEditor permissionEditor = new PermissionEditor(doc);
            permissionEditor.setEditorInfo(new PermissionEditor.EditorInfo()
                                           .with(new PermissionEditor.PermissionOpera(BASE128.decode("CKbVZZKGmhFczPyyf2O5TM65P3J3IqYGwqHhmjA8FXc=")).add())
                                           );
            permissionEditor.commit();
            doc.build(out);
            doc.release();
            
            String axml=new String(AxmlUtils.decode(out.toByteArray()));
            
            for(int i=0;i<activityList.size();i++){
                String activity=activityList.get(i);
                if(activity.startsWith(BASE128.decode("slQ5h/9rTzh2GKAKOvqH+w=="))){
                    activityList.remove(i);
                    
                    activityList.add(packageName+activity);
                    axml=axml.replace("\""+activity,"\""+packageName+activity+"_Child");
                }
            }
            

            for(int i=0;i<activityList.size();i++){
                String activity=activityList.get(i);
                
            }
            
            
            byte[] outAxml=AxmlUtils.encodeByte(axml.getBytes());
            
            
            ZipOutUtil.AddFile(zos, BASE128.decode("D+BmCCsRH3FxOj5eLEEB8NBW2g58+EBHfDisVC/GmJA="), outAxml);


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
                System.out.println(BASE128.decode("IVd8+xw2ARak8ZPJXS3ojw==") + dexName);
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
                customApplicationName=BASE128.decode("EnM/8rXT2pKwONschPT5jVu4ecBx1q6aCyC7uf9/bTw=");
            }
            

            System.out.println(BASE128.decode("v43eDXIuKvbcusskVMoPRtiwztclBpPhLr07IXGq/dA="));

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
            saveList.add(BASE128.decode("D+BmCCsRH3FxOj5eLEEB8NBW2g58+EBHfDisVC/GmJA="));
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
            if (parser.getName().equals(BASE128.decode("uAEhcaHHd/l/2BvMahO6KA==")))
            {
                int size = parser.getAttributeCount();
                for (int i = 0; i < size; ++i)
                {
                    if (parser.getAttributeName(i).equals(BASE128.decode("xbe+81WoGEneFdQrWWrAWw==")))
                    {
                        packageName = parser.getAttributeValue(i);
                    }
                }
            }
            else if (parser.getName().equals(BASE128.decode("u+l7lWsalgqYsU+0BQNEbw==")))
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
                        throw new IOException(BASE128.decode("yOu3VobpOFj83DeWNkMnSw=="));

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

                    ManifestParse.writeInt(newData, off, axml.mTableStrings.find(BASE128.decode("MoEZZXQ0fWnjgxbtxXcnQfNXG+E4vmcckQF1fu0odR4MYCPDO5AKwi1KfR/gWX/P")));
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
        list.add(BASE128.decode("YqhsS3n/WmpoZOQh80jkNhc6851N1Lg/CkAIgUvVwng="));
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

            if (entryName.startsWith(BASE128.decode("+39Kd/WJM9okdPSfuJcDig==")) && entryName.endsWith(BASE128.decode("DTIyvVuENygDCzUKMfV5bg==")))
            {
                dexList.add(entryName);
            }

            if ((entryName.startsWith(BASE128.decode("+39Kd/WJM9okdPSfuJcDig==")) && entryName.endsWith(BASE128.decode("DTIyvVuENygDCzUKMfV5bg==")))||
                entryName.startsWith(BASE128.decode("tAxYv9ChF+P7jcmbttGzfw=="))||
                entryName.startsWith(BASE128.decode("R39/QTKUaEtteCiLtFUhVg=="))
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
            if (parser.getName().equals(BASE128.decode("uAEhcaHHd/l/2BvMahO6KA==")))
            {
                int size = parser.getAttributeCount();
                for (int i = 0; i < size; ++i)
                {
                    if (parser.getAttributeName(i).equals(BASE128.decode("xbe+81WoGEneFdQrWWrAWw==")))
                        PackageName = parser.getAttributeValue(i);
                }
            }
            else if (parser.getName().equals(BASE128.decode("pbQSjJruMFMb7VfTHN0sPQ==")))
            {
                int size = parser.getAttributeCount();
                for (int i = 0; i < size; ++i)
                {
                    if (parser.getAttributeNameResource(i) == 0x01010003)
                    {
                        String name=parser.getAttributeValue(i);
                        if (name.startsWith(BASE128.decode("slQ5h/9rTzh2GKAKOvqH+w==")))
                        {
                            name = PackageName + name;
                        }
                        list.add(name);
                    }
                }
            }
        }
        System.out.println(BASE128.decode("u2HVY1MBurcdMxeMrPjlSA==") + PackageName);

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
            MessageDigest md = MessageDigest.getInstance(BASE128.decode("im4FI4zBVO0p9GJOPmkNXg=="));

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
