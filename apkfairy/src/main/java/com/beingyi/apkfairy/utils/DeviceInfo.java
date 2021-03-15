package com.beingyi.apkfairy.utils;

import android.os.Build;
import java.util.UUID;

public class DeviceInfo
{
    
    

    //获得独一无二的Psuedo ID
    public static String getUniquePsuedoID()
    {
        String serial = null;

        String m_szDevIDShort = "35" + 
            Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + 

            Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 + 

            Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + 

            Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + 

            Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + 

            Build.TAGS.length() % 10 + Build.TYPE.length() % 10 + 

            Build.USER.length() % 10 ; //13 位

        try
        {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        }
        catch (Exception exception)
        {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();

    }

    
    
}
