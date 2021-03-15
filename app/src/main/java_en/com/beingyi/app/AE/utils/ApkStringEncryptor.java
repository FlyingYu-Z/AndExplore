package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.IOException;
import java.util.zip.ZipOutputStream;
import java.io.FileOutputStream;

public class ApkStringEncryptor
{
    String Input;
    String Output;

    List<String> dexList = new ArrayList<String>();

    List<String> keeps;
    DexStringEncryptor.EncryptCallBack callBack;
    UICallBack uiCallBack;

    public ApkStringEncryptor(String input, String output, List<String> keeps, DexStringEncryptor.EncryptCallBack mCallBack,UICallBack mUICallBack)
    {
        this.Input = input;
        this.Output = output;
        this.keeps = keeps;
        this.callBack = mCallBack;
        this.uiCallBack=mUICallBack;


    }


    public void start() throws Exception
    {

        ZipFile zipFile = new ZipFile(Input);
        HashMap<String, byte[]> zipEnties = new HashMap<String, byte[]>();
        ZipOutputStream zos=new ZipOutputStream(new FileOutputStream(Output));

        readZip(zipFile, zipEnties);

        for (String dexName : dexList)
        {
            uiCallBack.onStep(BASE128.decode("mO/uhGU3mBSUuxIF0pA+Gg==")+dexName);
            
            InputStream inputStream = zipFile.getInputStream(zipFile.getEntry(dexName));
            ByteArrayOutputStream baos = FileUtils.cloneInputStream(inputStream);


            DexStringEncryptor dexStringEncryptor=new DexStringEncryptor(baos.toByteArray(), keeps, callBack);
            dexStringEncryptor.start();
            ZipOutUtil.AddFile(zos, dexName,dexStringEncryptor.getOutData());

        }
        
        if(dexList.size()==0){
            System.out.println(BASE128.decode("T5IU/FJDlygRo3FbC3G/nA=="));
            return;
        }

        uiCallBack.onStep(BASE128.decode("X8F6cB5TYm4wFvctF3NfWLv6cLknUhCfGZrecy3+cio="));
        ZipOutUtil.Sava(zipFile, zos, dexList, new ZipOutUtil.ZipSavsCallback(){

                @Override
                public void onStep(ZipOutUtil.Step step)
                {
                    
                }

                @Override
                public void onProgress(int progress, int total)
                {
                    uiCallBack.onSaveProgress(progress,total);
                }
            });
        
        System.out.println(BASE128.decode("e/V/IyWepSBkQ3tVXhyWJA=="));
        

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

            if (!entry.isDirectory())
            {
                map.put(entry.getName(), null);
            }
        }

    }






    public interface UICallBack {
        void onStep(String Name);
        void onSaveProgress(int progress, int total);
    }


}
