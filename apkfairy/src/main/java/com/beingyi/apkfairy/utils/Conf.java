package com.beingyi.apkfairy.utils;

import android.content.Context;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Conf
{
    Context context;

    public Conf(Context context)
    {

        this.context = context;
    }



    public static String server()
    {
        String address="";
        Enumeration allNetInterfaces = null;  
        try
        {  
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();  
        }
        catch (java.net.SocketException e)
        {  
            e.printStackTrace();  
        }  
        InetAddress ip = null;  
        while (allNetInterfaces.hasMoreElements())  
        {  
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();  

            Enumeration addresses = netInterface.getInetAddresses();  
            while (addresses.hasMoreElements())  
            {  
                ip = (InetAddress) addresses.nextElement();  

                if (ip != null && ip instanceof Inet4Address)  
                {  
                    if (ip.getHostAddress().equals("127.0.0.1"))
                    {
                        continue;
                    }
                    address = ip.getHostAddress();  

                }  
            }  
        }  

        return address;
    }

    public static String getServer(Context context)
    {

        //return SPUtils.getString(context,"conf","server");
        return mainServer;

    }



    public static String tmp_server="http://localhost:8000/";


    public static String mainServer="http://www.beingyi.cn/";
    public static String app_dir=FileUtils.getSDPath() + "/beingyi/";






    public static String key="zy" + "4f5" + "da22" + "ad5" + "f4" + "yz";


    public String getAppColor()
    {
        return  SPUtils.getString(context, "conf", "AppColor","#29B188");

    }


    public void setAppColor(String color)
    {
        SPUtils.putString(context, "conf", "AppColor", color);
    }


}
