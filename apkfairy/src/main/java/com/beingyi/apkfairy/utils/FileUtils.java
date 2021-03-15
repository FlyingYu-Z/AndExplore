package com.beingyi.apkfairy.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

public class FileUtils
{


    public static String getSDPath()
    { 
        File sdDir = null; 
        boolean sdCardExist = Environment.getExternalStorageState()   
            .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist)   
        {                               
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }   
        return sdDir.toString(); 
    }

    
    public static String getMd5ByFile(String path) throws Exception {
        String value = null;
        File file=new File(path);
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0,file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }
    
    
    
    

    public static String getFilePathFromUri(final Uri uri)
    {
        if (null == uri)
        {
            return null;
        }

        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
        {
            data = uri.getPath();
        }

        return data;
    }



    public static void copyFile(String oldPath, String newPath)
    {  
        try
        {  
            int bytesum = 0;  
            int byteread = 0;  
            File oldfile = new File(oldPath);  

            if (!new File(newPath).getParentFile().exists())
            {
                new File(newPath).getParentFile().mkdirs();
            }

            if (oldfile.exists())
            { //文件存在时  
                InputStream inStream = new FileInputStream(oldPath); //读入原文件  
                FileOutputStream fs = new FileOutputStream(newPath);  
                byte[] buffer = new byte[1444];  
                while ((byteread = inStream.read(buffer)) != -1)
                {  
                    bytesum += byteread; //字节数 文件大小  
                    System.out.println(bytesum);  
                    fs.write(buffer, 0, byteread);  
                }  
                inStream.close();  
                fs.close();  
            }  
        }  
        catch (Exception e)
        {  
            System.out.println("复制单个文件操作出错");  
            e.printStackTrace();  
        }  
    }  




    public static void delFolder(String folderPath)
    {
        try
        {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        }
        catch (Exception e)
        {
            e.printStackTrace(); 
        }
    }

//删除指定文件夹下所有文件
//param path 文件夹完整绝对路径
    public static boolean delAllFile(String path)
    {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists())
        {
            return flag;
        }
        if (!file.isDirectory())
        {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++)
        {
            if (path.endsWith(File.separator))
            {
                temp = new File(path + tempList[i]);
            }
            else
            {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile())
            {
                temp.delete();
            }
            if (temp.isDirectory())
            {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }







public static String readFromAssets(Context context,String fileName){
    String result="";
    
    
    try {
        //Return an AssetManager instance for your application's package
        InputStream is = context.getAssets().open(fileName);
        int size = is.available();
        // Read the entire asset into a local byte buffer.
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        // Convert the buffer into a string.
        String text = new String(buffer, "utf-8");
        // Finally stick the string into the text view.
        result=text;
    } catch (IOException e) {
        // Should never happen!
//            throw new RuntimeException(e);
        e.printStackTrace();
        
    }
    
    
    return result;
}










}
