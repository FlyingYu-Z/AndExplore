package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.beingyi.app.AE.utils.Shell.execRootCmdSilent;

public class RootChecker {

    private final static String TAG = "RootUtil";


    static boolean HaveRoot=false;

    /**
     *   判断机器Android是否已经root，即是否获取root权限
     */
    public static boolean haveRoot() {
        if (!HaveRoot) {
            int ret = execRootCmdSilent(BASE128.decode("aBQ+FffYPTVG9soqatXtVw==")); // 通过执行测试命令来检测
            if (ret != -1) {
                Log.i(TAG, BASE128.decode("eNJKitu1ab+fzMD+EUsp1A=="));
                HaveRoot = true;
            } else {
                Log.i(TAG, BASE128.decode("4aATKieJJjxaxR77vIQufw=="));
            }
        } else {
            Log.i(TAG, BASE128.decode("NtgbZqtLtsTA8eCfx2Ov66/1CUr7fo7rL+nj83H++og="));
        }
        return HaveRoot;
    }


    public static boolean isRoot() {
        String binPath = BASE128.decode("b/P1mfr9TGnXG0jtJ2Vv4A==");
        String xBinPath = BASE128.decode("RCTXW/XGhsOux0+m7AaKGw==");
        if (new File(binPath).exists() && isExecutable(binPath))
            return true;
        if (new File(xBinPath).exists() && isExecutable(xBinPath))
            return true;
        return false;
    }

    private static boolean isExecutable(String filePath) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(BASE128.decode("ijOvkeBjqsAl9KzAHE5k3Q==") + filePath);
            // 获取返回内容
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = in.readLine();
            Log.i(TAG, str);
            if (str != null && str.length() >= 4) {
                char flag = str.charAt(3);
                if (flag == 's' || flag == 'x')
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
        return false;
    }


    public static boolean checkSuFile() {
        Process process = null;
        try {
            //   /system/xbin/which 或者  /system/bin/which
            process = Runtime.getRuntime().exec(new String[]{BASE128.decode("t3NROi5yfbl6wd+LErHRRA=="), BASE128.decode("OEnQqXVXf08gMdsbcmF1Og==")});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
            return false;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }

    public static File checkRootFile() {
        File file = null;
        String[] paths = {BASE128.decode("CxIBXjY7XnGHKxxu+G9tmQ=="), BASE128.decode("b/P1mfr9TGnXG0jtJ2Vv4A=="), BASE128.decode("RCTXW/XGhsOux0+m7AaKGw=="), BASE128.decode("0vpThoforwLCFANlWWeernyK9ioW6fdqUerwQti+3JQ="), BASE128.decode("gZplt9AUbzq83mU/mSDQhzhJ0Kl1V39PIDHbG3JhdTo="), BASE128.decode("HrsVLWw9EzVxBLWIM1CZYzhJ0Kl1V39PIDHbG3JhdTo="),
                BASE128.decode("gfg0uQQJ1LLseFCOCZSTQ7uqFrEo60fqrt0FRiclB10="), BASE128.decode("B26mbnD6N8QccOaSVlieMw==")};
        for (String path : paths) {
            file = new File(path);
            if (file.exists()) return file;
        }
        return file;
    }


}
