package com.beingyi.apkfairy.utils;

import android.util.Log;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Shell
{
    static String TAG="Shell";
    static boolean HaveRoot=false;
    
    /**
     *   判断机器Android是否已经root，即是否获取root权限
     */
    public static boolean haveRoot() {
        if (!HaveRoot) {
            int ret = execRootCmdSilent("echo test"); // 通过执行测试命令来检测
            if (ret != -1) {
                Log.i(TAG, "have root!");
                HaveRoot = true;
            } else {
                Log.i(TAG, "not root!");
            }
        } else {
            Log.i(TAG, "mHaveRoot = true, have root!");
        }
        return HaveRoot;
    }
    
    
    /**
     * 执行命令并且输出结果
     */
    public static String execRootCmd(String cmd) {
        String result = "";
        DataOutputStream dos = null;
        DataInputStream dis = null;

        try {
            Process p = Runtime.getRuntime().exec("su");// 经过Root处理的android系统即有su命令
            dos = new DataOutputStream(p.getOutputStream());
            dis = new DataInputStream(p.getInputStream());

            Log.i(TAG, cmd);
            dos.writeBytes(cmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            String line = null;
            while ((line = dis.readLine()) != null) {
                Log.d("result", line);
                result += line;
            }
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    
    
    // 执行命令但不关注结果输出 
    public static int execRootCmdSilent(String cmd)
    {  
        int result = -1; 
        DataOutputStream dos = null; 
        try
        {  Process p = Runtime.getRuntime().exec("su"); 
            dos = new DataOutputStream(p.getOutputStream());  

            dos.writeBytes(cmd + "\n");  
            dos.flush(); 
            dos.writeBytes("exit\n");
            dos.flush();  
            p.waitFor(); 
            result = p.exitValue();
        }
        catch (Exception e)
        { 
            e.printStackTrace();  }
        finally
        {  
            if (dos != null)
            {  
                try
                {  dos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();  }  }  }
        return result;  
    }  
    
    
    
}
