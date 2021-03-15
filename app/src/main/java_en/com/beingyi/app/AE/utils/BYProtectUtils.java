package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;

import com.beingyi.app.AE.application.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.beingyi.app.AE.utils.FileUtils.delSingleFile;


public class BYProtectUtils {







    public static String readAssetsTxt(String fileName)
    {
        try
        {
            InputStream localInputStream = getStreamFromAssets(fileName);
            byte[] arrayOfByte = new byte[localInputStream.available()];
            localInputStream.read(arrayOfByte);
            localInputStream.close();
            String str = new String(arrayOfByte, BASE128.decode("YF+TOQhsLibxaX9DZrlrMw=="));
            return str;
        }
        catch (Exception localIOException)
        {
            localIOException.printStackTrace();
        }
        return BASE128.decode("FzrznU3UuD8KQAiBS9XCeA==");
    }





    public static void copyAssetsFile(String fileName, String desPath) {
        delSingleFile(desPath);
        InputStream in = null;
        OutputStream out = null;
        try {
            in = getStreamFromAssets(fileName);
            out = new FileOutputStream(desPath);
            byte[] bytes = new byte[1024];
            int i;
            while ((i = in.read(bytes)) != -1)
                out.write(bytes, 0 , i);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }




    public static InputStream getStreamFromAssets(String fileName)
    {
        try
        {
            InputStream localInputStream = MyApplication.getContext().getAssets().open(getAssetsName(fileName));
            return FileEntry.decrypt(localInputStream,Conf.getBase128Key(MyApplication.getContext()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getAssetsName(String path){

        if(path.contains(BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="))) {
            String result = path.subSequence(path.lastIndexOf(BASE128.decode("AkZj32p4lHkS0d0LVieHfA==")) + 1, path.length()).toString();
            return path.replace(result,MD5.encode(result));
        }else{
            return MD5.encode(path);
        }

    }


    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }


}
