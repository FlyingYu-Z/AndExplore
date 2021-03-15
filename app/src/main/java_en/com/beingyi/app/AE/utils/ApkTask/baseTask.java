package com.beingyi.app.AE.ApkTask;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.support.annotation.NonNull;

import org.jf.baksmali.Adaptors.ClassDefinition;
import org.jf.baksmali.Adaptors.MethodDefinition;
import org.jf.baksmali.BaksmaliOptions;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.ExceptionHandler;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.TryBlock;
import org.jf.dexlib2.iface.debug.DebugItem;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.writer.builder.DexBuilder;
import org.jf.util.IndentingWriter;
import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
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

public class baseTask {


    public String Input;
    public String Output;

    public ZipFile zipFile;
    public List<String> dexList = new ArrayList<>();
    public List<String> activityList = new ArrayList<>();
    HashMap<String, byte[]> zipEnties = new HashMap<String, byte[]>();
    public ZipOutputStream zos;


    public baseTask(String mInput, String mOutput)throws Exception{
        this.Input = mInput;
        this.Output = mOutput;

        zos = new ZipOutputStream(new FileOutputStream(Output));
        zipFile = new ZipFile(Input);
        readZip(zipFile, zipEnties);
        activityList = parseManifestActivity(zipFile.getInputStream(zipFile.getEntry(BASE128.decode("D+BmCCsRH3FxOj5eLEEB8NBW2g58+EBHfDisVC/GmJA="))));

    }




    private static List<String> parseManifestActivity(InputStream is) throws IOException
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




    private void readZip(ZipFile zip, Map<String, byte[]> map) throws Exception {
        Enumeration enums = zip.entries();
        while (enums.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) enums.nextElement();
            String entryName = entry.getName();

            if (entryName.startsWith(BASE128.decode("+39Kd/WJM9okdPSfuJcDig==")) && entryName.endsWith(BASE128.decode("DTIyvVuENygDCzUKMfV5bg=="))) {
                dexList.add(entryName);
            }

            if (!entry.isDirectory()) {
                map.put(entry.getName(), null);
            }
        }

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
