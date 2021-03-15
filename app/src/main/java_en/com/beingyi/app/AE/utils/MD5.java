package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;

import android.text.TextUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5
{
    

    public static String encode(String string) {
        if (TextUtils.isEmpty(string)) {
            return BASE128.decode("FzrznU3UuD8KQAiBS9XCeA==");
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance(BASE128.decode("im4FI4zBVO0p9GJOPmkNXg=="));
            byte[] bytes = md5.digest(string.getBytes());
            String result = BASE128.decode("FzrznU3UuD8KQAiBS9XCeA==");
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = BASE128.decode("6DZ1PVWnXoHRL4kkqY+fzQ==") + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return BASE128.decode("FzrznU3UuD8KQAiBS9XCeA==");
    }
    
    
    
    
    
}
