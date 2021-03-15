package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.KeyGenerator;

import Decoder.BASE64Encoder;

public class FileEntry
{

    public static void encrypt(String fileUrl, String destpath, String key) throws Exception
    {
        File file = new File(fileUrl);
        String path = file.getPath();
        if (!file.exists())
        {
            return;
        }

        File dest = new File(destpath);
        InputStream in = new FileInputStream(fileUrl);
        OutputStream out = new FileOutputStream(dest);
        byte[] buffer = new byte[1024];
        int len=0;;
        byte[] outputBuffer = new byte[1024];
        String regEx=BASE128.decode("NEnd0TJnnO8DXh1Oo8O3fw==");  
        Pattern p = Pattern.compile(regEx);  
        Matcher m = p.matcher(getMd5(key));
        String result=m.replaceAll(BASE128.decode("FzrznU3UuD8KQAiBS9XCeA==")).trim();

        while ((len = in.read(buffer)) > 0)
        {
            for (int i=0;i < len;i++)
            {
                byte b = buffer[i];
                b =(byte)(b^ Integer.parseInt(result));
                outputBuffer[i] = b;
            }

            out.write(outputBuffer, 0, len);
            out.flush();
        }
        in.close();
        out.close();
        System.out.println(BASE128.decode("OznNIvs/rC3g4zKLparr4g=="));
    }





    public static String decrypt(String fileUrl, String destpath, String key) throws Exception
    {
        File file = new File(fileUrl);
        if (!file.exists())
        {
            return null;
        }
        File dest = new File(destpath);
        if (!dest.getParentFile().exists())
        {
            dest.getParentFile().mkdirs();
        }
        InputStream is = new FileInputStream(fileUrl);
        OutputStream out = new FileOutputStream(dest);
        byte[] buffer = new byte[1024];
        int len=0;;
        byte[] outputBuffer = new byte[1024];
        String regEx=BASE128.decode("NEnd0TJnnO8DXh1Oo8O3fw==");  
        Pattern p = Pattern.compile(regEx);  
        Matcher m = p.matcher(getMd5(key));
        String result=m.replaceAll(BASE128.decode("FzrznU3UuD8KQAiBS9XCeA==")).trim();
        while ((len = is.read(buffer)) > 0)
        {
            for (int i=0;i < len;i++)
            {
                byte b = buffer[i];
                b =(byte)(b^ Integer.parseInt(result));
                outputBuffer[i] = b;
            }
            out.write(outputBuffer, 0, len);
            out.flush();
        }
        out.close();
        is.close();
        System.out.println(BASE128.decode("zNHKgSYku0s7WxswoO5egQ=="));
        return destpath;
    }




    public static InputStream encrypt(InputStream is, String key) throws Exception
    {

        InputStream resultStream=null;
        byte[] buffer = new byte[1024];
        int len=0;;
        byte[] outputBuffer = new byte[1024];
        ByteArrayOutputStream outputByte = new ByteArrayOutputStream();
        String regEx=BASE128.decode("NEnd0TJnnO8DXh1Oo8O3fw==");
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(getMd5(key));
        String result=m.replaceAll(BASE128.decode("FzrznU3UuD8KQAiBS9XCeA==")).trim();

        while ((len = is.read(buffer)) > 0)
        {
            for (int i=0;i < len;i++)
            {
                byte b = buffer[i];
                b =(byte)(b^ Integer.parseInt(result));
                outputBuffer[i] = b;
            }

            outputByte.write(outputBuffer, 0, len);
            outputByte.flush();
        }
        is.close();

        resultStream=new ByteArrayInputStream(outputByte.toByteArray());

        return resultStream;
    }





    public static InputStream decrypt(InputStream is, String key) throws Exception
    {
        InputStream resultStream=null;
        byte[] buffer = new byte[1024];
        int len=0;;
        byte[] outputBuffer = new byte[1024];
        ByteArrayOutputStream outputByte = new ByteArrayOutputStream();
        String regEx=BASE128.decode("NEnd0TJnnO8DXh1Oo8O3fw==");
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(getMd5(key));
        String result=m.replaceAll(BASE128.decode("FzrznU3UuD8KQAiBS9XCeA==")).trim();
        while ((len = is.read(buffer)) > 0)
        {
            for (int i=0;i < len;i++)
            {
                byte b = buffer[i];
                b =(byte)(b^ Integer.parseInt(result));
                outputBuffer[i] = b;
            }
            outputByte.write(outputBuffer, 0, len);
            outputByte.flush();
        }

        resultStream=new ByteArrayInputStream(outputByte.toByteArray());
        is.close();


        return resultStream;
    }





    public static String getMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance(BASE128.decode("im4FI4zBVO0p9GJOPmkNXg=="));
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr=base64en.encode(md5.digest(str.getBytes(BASE128.decode("YF+TOQhsLibxaX9DZrlrMw=="))));
        return newstr;
    }






    Key key;   
    public FileEntry(String str) {   
        getKey(str);//生成密匙   
    }   
    /**  
     * 根据参数生成KEY  
     */   
    public void getKey(String strKey) {   
        try {   
            KeyGenerator _generator = KeyGenerator.getInstance(BASE128.decode("pztquF0dtDwLiwP9m4Prjg=="));   
            _generator.init(new SecureRandom(strKey.getBytes()));   
            this.key = _generator.generateKey();   
            _generator = null;   
        } catch (Exception e) {   
            throw new RuntimeException(BASE128.decode("Ix3FJLUz8KpNsV+v6So7iQyBThWhA7baPjnZnDtfBbnpbKjIJBRRa34IRnFGimW8") + e);   
        }   
    }








}
