package com.beingyi.app.AE.ApkTask;


import android.content.Context;

import com.beingyi.app.AE.utils.Conf;
import com.beingyi.app.AE.utils.ManifestParse;
import com.beingyi.app.AE.utils.ZipOut;

import org.jf.baksmali.Adaptors.ClassDefinition;
import org.jf.baksmali.Adaptors.MethodDefinition;
import org.jf.baksmali.BaksmaliOptions;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.util.IndentingWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import maobyte.xml.decode.AXmlDecoder;
import maobyte.xml.decode.AXmlResourceParser;
import maobyte.xml.decode.XmlPullParser;


public class baseTask {

    public Context context;
    public String inputPath;
    public String outputPath;
    public DealCallBack callback;
    public ZipFile inputZipFile;
    public List<String> entries = new ArrayList<>();
    public List<String> dexEntries = new ArrayList<>();

    public List<String> activityList = new ArrayList<>();


    public boolean customApplication = false;
    public String customApplicationName = "android.app.Application";
    public String packageName;
    public ZipOut zipOut;


    public baseTask(Context context, String mInput, String mOutput,DealCallBack callback)throws Exception{
        this.context=context;
        this.inputPath = mInput;
        this.outputPath = mOutput;
        this.callback=callback;

        this.inputZipFile = new ZipFile(inputPath);
        this.zipOut=new ZipOut(outputPath).setInput(inputZipFile);

        HashMap<String, byte[]> zipEnties = new HashMap<String, byte[]>();
        readZip(inputZipFile,zipEnties);
        activityList = ManifestParse.parseManifestActivity(inputZipFile.getInputStream(inputZipFile.getEntry("AndroidManifest.xml")));


    }




    public InputStream getZipInputStream(String entry) throws IOException {

        return inputZipFile.getInputStream(inputZipFile.getEntry(entry));
    }


    private void readZip(ZipFile zip, Map<String, byte[]> map) throws Exception {
        Enumeration enums = zip.entries();
        while (enums.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) enums.nextElement();
            String entryName = entry.getName();

            if (entryName.startsWith("classes") && entryName.endsWith(".dex")) {
                dexEntries.add(entryName);
            }
            entries.add(entryName);
            if (!entry.isDirectory()) {
                map.put(entry.getName(), null);
            }
        }

    }



    // 修改AndroidManifest Application Name属性
    public byte[] parseManifest(InputStream is, String Name) throws IOException {
        AXmlDecoder axml = AXmlDecoder.decode(is);
        AXmlResourceParser parser = new AXmlResourceParser();
        parser.open(new ByteArrayInputStream(axml.getData()), axml.mTableStrings);

        boolean success = false;
        int type;
        while ((type = parser.next()) != maobyte.xml.decode.XmlPullParser.END_DOCUMENT) {
            if (type != XmlPullParser.START_TAG)
                continue;
            if (parser.getName().equals("manifest")) {
                int size = parser.getAttributeCount();
                for (int i = 0; i < size; ++i) {
                    if (parser.getAttributeName(i).equals("package")) {
                        packageName = parser.getAttributeValue(i);
                    }
                }
            } else if (parser.getName().equals("application")) {
                int size = parser.getAttributeCount();
                for (int i = 0; i < size; ++i) {
                    if (parser.getAttributeNameResource(i) == 0x01010003) {
                        customApplication = true;
                        customApplicationName = parser.getAttributeValue(i);
                        if (customApplicationName.startsWith(".")) {
                            if (packageName == null)
                                throw new NullPointerException("Package name is null.");
                            customApplicationName = packageName + customApplicationName;
                        }
                        int index = axml.mTableStrings.getSize();
                        byte[] data = axml.getData();
                        int off = parser.currentAttributeStart + 20 * i;
                        off += 8;
                        ManifestParse.writeInt(data, off, index);
                        off += 8;
                        ManifestParse.writeInt(data, off, index);
                    }
                }
                if (!customApplication) {
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
                    for (int i = 0; i < size; ++i) {
                        int id = parser.getAttributeNameResource(i);
                        if (id > 0x01010003) {
                            isMax = false;
                            if (i != 0) {
                                System.arraycopy(newData, off + 20, newData, off, 20 * i);
                                off += 20 * i;
                            }
                            break;
                        }
                    }
                    if (isMax) {
                        System.arraycopy(newData, off + 20, newData, off, 20 * size);
                        off += 20 * size;
                    }

                    ManifestParse.writeInt(newData, off,
                            axml.mTableStrings.find("http://schemas.android.com/apk/res/android"));
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
        list.add(Name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        axml.write(list, baos);
        return baos.toByteArray();
    }



    public static String getSmali(ClassDef classDef)
    {
        String code=null;
        try
        {
            StringWriter stringWriter = new StringWriter();
            IndentingWriter writer = new IndentingWriter(stringWriter);
            ClassDefinition classDefinition = new ClassDefinition(new BaksmaliOptions(), classDef);
            classDefinition.writeTo(writer);
            writer.close();
            code = stringWriter.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return code;
    }


    public static String getSmali(ClassDef classDef,Method method)
    {
        String code=null;
        try
        {
            StringWriter stringWriter = new StringWriter();
            IndentingWriter writer = new IndentingWriter(stringWriter);
            ClassDefinition classDefinition = new ClassDefinition(new BaksmaliOptions(), classDef);
            MethodImplementation methodImplementation=method.getImplementation();
            MethodDefinition methodDefinition = new MethodDefinition(classDefinition, method,methodImplementation);
            classDefinition.writeTo(writer);
            writer.close();
            code = stringWriter.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return code;
    }





    public interface DealCallBack {
        void onStep(String Name);
        void onLabel(String Name);
        void onProgress(int progress, int total);
        void onSaveProgress(int progress, int total);
    }


}
