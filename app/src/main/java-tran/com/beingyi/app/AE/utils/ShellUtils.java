package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.application.MyApplication;
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
            process = getRuntime().exec("su");
            bRunning = true;
            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            os = new DataOutputStream(process.getOutputStream());
            isGotRoot=true;
            ToastUtils.show(MyApplication.getInstance().getString("7506c5e1ec3345f538dbc6125d446a0c"));
        } catch (Exception e) {
            ToastUtils.show(MyApplication.getInstance().getString("756fd432fd163cd2732dc60dbd0669dc"));
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
            Log.i("auto", "getResult");
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
        Log.i("auto", "run command:" + command + ",maxtime:" + maxTime);
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
                            Log.i("auto", "exitValue Stream over"+ret);
                        } catch (IllegalThreadStateException e) {
                            Log.i("auto", "take maxTime,forced to destroy process");
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
                        Log.i("auto", "read InputStream exception:" + e.toString());
                    } finally {
                        try {
                            //successResult.close();
                            Log.i("auto", "read InputStream over");
                        } catch (Exception e) {
                            Log.i("auto", "close InputStream exception:" + e.toString());
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
                        Log.i("auto", "read ErrorStream exception:" + e.toString());
                    } finally {
                        try {
                            //errorResult.close();
                            Log.i("auto", "read ErrorStream over");
                        } catch (Exception e) {
                            Log.i("auto", "read ErrorStream exception:" + e.toString());
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
                        Log.i("auto", "run command process end");
                    }
                }
            });
            t3.start();

            if (bSynchronous) {
                Log.i("auto", "run is go to end");
                t3.join();
                Log.i("auto", "run is end");
            }
        } catch (Exception e) {
            Log.i("auto", "run command process exception:" + e.toString());
        }
        return this;
    }

}
