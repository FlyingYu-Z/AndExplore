package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.util.Log;

import com.beingyi.app.AE.ui.ToastUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Runtime.getRuntime;

/**
 * 执行命令的类
 * Created by Kappa
 */
public class ShellUtils {
    //shell进程
    private Process process;
    //对应进程的3个流
    private BufferedReader successResult;
    private BufferedReader errorResult;
    private DataOutputStream os;
    //是否同步，true：run会一直阻塞至完成或超时。false：run会立刻返回
    private boolean bSynchronous;
    //表示shell进程是否还在运行
    private boolean bRunning = false;
    public boolean isGotRoot=false;
    //同步锁
    ReadWriteLock lock = new ReentrantReadWriteLock();

    //保存执行结果
    private StringBuffer result = new StringBuffer();

    /**
     * 构造函数
     *
     * @param synchronous true：同步，false：异步
     */
    public ShellUtils(boolean synchronous) {
        bSynchronous = synchronous;
        init();
    }

    /**
     * 默认构造函数，默认是同步执行
     */
    public ShellUtils() {
        bSynchronous = true;
        init();
    }


    public void init(){


        try {
            process = getRuntime().exec(BASE128.decode("OEnQqXVXf08gMdsbcmF1Og=="));
            bRunning = true;
            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            os = new DataOutputStream(process.getOutputStream());
            isGotRoot=true;
            ToastUtils.show(BASE128.decode("jab9AYhG7W0JjQG2LWYbhhc6851N1Lg/CkAIgUvVwng="));
        } catch (Exception e) {
            ToastUtils.show(BASE128.decode("W2Xk7RaoDnTraVN/jeMc5Bc6851N1Lg/CkAIgUvVwng="));
            isGotRoot=false;
        }


    }



    /**
     * 还没开始执行，和已经执行完成 这两种情况都返回false
     *
     * @return 是否正在执行
     */
    public boolean isRunning() {
        return bRunning;
    }

    /**
     * @return 返回执行结果
     */
    public String getResult() {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            Log.i(BASE128.decode("2CykJIe9YVnIsvoof5/oWA=="), BASE128.decode("3YIDvY+CPZzacfXRDonmLA=="));
            return new String(result);
        } finally {
            readLock.unlock();
        }
    }


    public void close(){

        try {
            bRunning=false;
            successResult.close();
            errorResult.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 执行命令
     *
     * @param command eg: cat /sdcard/test.txt
     * 路径最好不要是自己拼写的路径，最好是通过方法获取的路径
     * example：Environment.getExternalStorageDirectory()
     * @param maxTime 最大等待时间 (ms)
     * @return this
     */
    public ShellUtils run(String command, final int maxTime) {
        Log.i(BASE128.decode("2CykJIe9YVnIsvoof5/oWA=="), BASE128.decode("GdvpmTEQ9q2bpz/cQJ5RZA==") + command + BASE128.decode("zPeG6+6QxbEpL+FG4UXRDg==") + maxTime);
        if (command == null || command.length() == 0) {
            return this;
        }

        init();

        try {
            //向sh写入要执行的命令
            os.write(command.getBytes());
            os.writeBytes("\n");
            os.flush();

            os.writeBytes("exit\n");
            os.flush();

            //os.close();
            //如果等待时间设置为非正，就不开启超时关闭功能
            if (maxTime > 0) {
                //超时就关闭进程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(maxTime);
                        } catch (Exception e) {
                        }
                        try {
                            int ret = process.exitValue();
                            Log.i(BASE128.decode("2CykJIe9YVnIsvoof5/oWA=="), BASE128.decode("7K/gIozTTx6QxgsoV0ROzAZb5sRkDZpRDHYMQ9R4Sn8=")+ret);
                        } catch (IllegalThreadStateException e) {
                            Log.i(BASE128.decode("2CykJIe9YVnIsvoof5/oWA=="), BASE128.decode("FELKFTM0HCHLfMullY0861P25ywafhFxPFXxAHtEnTYWhh23wQhkUxnAhqsd7GrN"));
                            process.destroy();
                        }
                    }
                }).start();
            }

            //开一个线程来处理input流
            final Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    String line;
                    Lock writeLock = lock.writeLock();
                    try {
                        while ((line = successResult.readLine()) != null) {
                            line += "\n";
                            writeLock.lock();
                            result.append(line);
                            writeLock.unlock();
                        }
                    } catch (Exception e) {
                        Log.i(BASE128.decode("2CykJIe9YVnIsvoof5/oWA=="), BASE128.decode("oKRIBTaL+MLtE4HdFc5khePsvC49OnydzowXmH825tE=") + e.toString());
                    } finally {
                        try {
                            //successResult.close();
                            Log.i(BASE128.decode("2CykJIe9YVnIsvoof5/oWA=="), BASE128.decode("oKRIBTaL+MLtE4HdFc5khQZb5sRkDZpRDHYMQ9R4Sn8="));
                        } catch (Exception e) {
                            Log.i(BASE128.decode("2CykJIe9YVnIsvoof5/oWA=="), BASE128.decode("en3LvqgXZiWTWkPw7pC6asECsA/dfB949eTM4x/mZXM=") + e.toString());
                        }
                    }
                }
            });
            t1.start();

            //开一个线程来处理error流
            final Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    String line;
                    Lock writeLock = lock.writeLock();
                    try {
                        while ((line = errorResult.readLine()) != null) {
                            line += "\n";
                            writeLock.lock();
                            result.append(line);
                            writeLock.unlock();
                        }
                    } catch (Exception e) {
                        Log.i(BASE128.decode("2CykJIe9YVnIsvoof5/oWA=="), BASE128.decode("+B06/ZFdiMYlbKpdtURuj+PsvC49OnydzowXmH825tE=") + e.toString());
                    } finally {
                        try {
                            //errorResult.close();
                            Log.i(BASE128.decode("2CykJIe9YVnIsvoof5/oWA=="), BASE128.decode("+B06/ZFdiMYlbKpdtURujwZb5sRkDZpRDHYMQ9R4Sn8="));
                        } catch (Exception e) {
                            Log.i(BASE128.decode("2CykJIe9YVnIsvoof5/oWA=="), BASE128.decode("+B06/ZFdiMYlbKpdtURuj+PsvC49OnydzowXmH825tE=") + e.toString());
                        }
                    }
                }
            });
            t2.start();

            Thread t3 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //等待执行完毕
                        t1.join();
                        t2.join();
                        process.waitFor();
                    } catch (Exception e) {

                    } finally {
                        bRunning = false;
                        Log.i(BASE128.decode("2CykJIe9YVnIsvoof5/oWA=="), BASE128.decode("iLjlU0aTmxSKf2U4K12cod8Sc+YDVnKcYXKjajkaoFc="));
                    }
                }
            });
            t3.start();

            if (bSynchronous) {
                Log.i(BASE128.decode("2CykJIe9YVnIsvoof5/oWA=="), BASE128.decode("9kWFhSbdEQfODt7R0kyWiBc6851N1Lg/CkAIgUvVwng="));
                t3.join();
                Log.i(BASE128.decode("2CykJIe9YVnIsvoof5/oWA=="), BASE128.decode("siz0c6MqyTywbmkxUjNrAQ=="));
            }
        } catch (Exception e) {
            Log.i(BASE128.decode("2CykJIe9YVnIsvoof5/oWA=="), BASE128.decode("iLjlU0aTmxSKf2U4K12coS1P9QVqCQWyXCl9HL+200c=") + e.toString());
        }
        return this;
    }

}
