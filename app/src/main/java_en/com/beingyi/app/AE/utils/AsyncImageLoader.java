package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import com.beingyi.app.AE.*;


import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.content.*;
import android.graphics.drawable.*;
import com.beingyi.app.AE.adapter.FilesAdapter;

public class AsyncImageLoader
{

    private Object lock = new Object();
    private boolean mAllowLoad = true;
    private boolean firstLoad = true;
    private int mStartLoadLimit = 0;
    private int mStopLoadLimit = 0;
    final Handler handler = new Handler();

    private HashMap<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();   

    public interface OnImageLoadListener
    {
        public void onImageLoad(Integer t, Drawable drawable, String url);
        public void onError(Integer t, String url);
    }

    public void setLoadLimit(int startLoadLimit, int stopLoadLimit)
    {
        if (startLoadLimit > stopLoadLimit)
        {
            return;
        }
        mStartLoadLimit = startLoadLimit;
        mStopLoadLimit = stopLoadLimit;
    }

    public void restore()
    {
        mAllowLoad = true;
        firstLoad = true;
    }

    public void lock()
    {
        mAllowLoad = false;
        firstLoad = false;
    }

    public void unlock()
    {
        mAllowLoad = true;
        synchronized (lock)
        {
            lock.notifyAll();
        }
    }

    public void loadImage(final Context context, final FilesAdapter adapter, Integer t, String imageUrl, OnImageLoadListener listener)
    {
        final OnImageLoadListener mListener = listener;
        final String mImageUrl = imageUrl;
        final Integer mt = t;

        new Thread(new Runnable() {

                @Override
                public void run()
                {
                    if (!mAllowLoad)
                    {
                        synchronized (lock)
                        {
                            try
                            {
                                lock.wait();
                            }
                            catch (InterruptedException e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }

                    if (mAllowLoad && firstLoad)
                    {
                        loadImage(context, adapter, mImageUrl, mt, mListener);
                    }

                    if (mAllowLoad && mt <= mStopLoadLimit && mt >= mStartLoadLimit)
                    {
                        loadImage(context, adapter, mImageUrl, mt, mListener);
                    }
                }

            }).start();
    }

    private void loadImage(Context context, FilesAdapter adapter, final String mImageUrl, final Integer mt, final OnImageLoadListener mListener)
    {

        if (imageCache.containsKey(mImageUrl))
        {  
            System.out.println(BASE128.decode("f1dAzo9x09EqsxIXyBGO+g=="));
            SoftReference<Drawable> softReference = imageCache.get(mImageUrl);  
            final Drawable d = softReference.get();  
            if (d != null)
            {  
                handler.post(new Runnable() {
                        @Override
                        public void run()
                        {
                            if (mAllowLoad)
                            {
                                mListener.onImageLoad(mt, d, mImageUrl);
                            }
                        }
                    });
                return;  
            }  
        }  
        try
        {


            Drawable drawable=null;

            if (adapter.ListType == 1)
            {

                File file=new File(mImageUrl);
                if (file.isFile())
                {
                    
                    String suffix=FileUtils.getSuffix(file.getAbsolutePath());
                    String path=file.getAbsolutePath();

                    if (suffix.equals(BASE128.decode("OG7Rh/GWl2IcHxbkCB+7rA==")))
                    {
                        drawable = APKUtils.getApkIcon(context, path);
                    }
                    else if (suffix.equals(BASE128.decode("brcCLZXd8erXPyg/cEbZ9w==")) || suffix.equals(BASE128.decode("h9bjbkfEP3TuESCjfIW1ZQ==")) || suffix.equals(BASE128.decode("76eOSi8quqxN5Dnz+g7yhQ==")) || suffix.equals(BASE128.decode("fsmYh/KUZoEDi63FYaK3Zg==")))
                    {
                        drawable = new BitmapDrawable(BitmapUtils.decodeSampledBitmapFromSd(path, 100, 100));
                    }
                    else if (suffix.equals(BASE128.decode("uAZAzccbXqg9fWG4baZ1jw==")))
                    {
                        drawable = MP4Utils.getIcon(path);
                    }
                    else if (suffix.equals(BASE128.decode("3m8oJWeZo0IKRlsXf8e9DA==")) || suffix.equals(BASE128.decode("pnWrCRPGzR7fVf5XLGFwsg==")) || suffix.equals(BASE128.decode("HnYqhiy/hQDpFVfK3Kudig==")))
                    {
                        drawable = ImgHelper.getDrawableFromResources(context, R.drawable.ic_zip);
                    }
                    else if (suffix.equals(BASE128.decode("77q/pLYqCIj4QyskZ26SCg==")))
                    {
                        drawable = ImgHelper.getDrawableFromResources(context, R.drawable.ic_jar);
                    }
                    else if (suffix.equals(BASE128.decode("VaTAdJm+d5FSeh5o/14p4g==")))
                    {
                        drawable = ImgHelper.getDrawableFromResources(context, R.drawable.ic_mp3);
                    }
                    else if (suffix.equals(BASE128.decode("VgRrq1lBW0G/1ZLBdMGKtw==")))
                    {
                        drawable = ImgHelper.getDrawableFromResources(context, R.drawable.ic_dex);
                    }
                    else if (suffix.equals(BASE128.decode("tBH25y9Lop0hRjmAcxC9Xg==")))
                    {
                        drawable = ImgHelper.getDrawableFromResources(context, R.drawable.ic_module);
                    }
                    else
                    {
                        drawable = ImgHelper.getDrawableFromResources(context, R.drawable.ic_file);
                    }
                }
                else
                {

                    drawable = ImgHelper.getDrawableFromResources(context, R.drawable.ic_folder);
                }

            }
            else
            {


                if (!mImageUrl.endsWith(BASE128.decode("AkZj32p4lHkS0d0LVieHfA==")))
                {

                    String suffix=FileUtils.getSuffix(mImageUrl);

                    if (suffix.equals(BASE128.decode("3m8oJWeZo0IKRlsXf8e9DA==")) || suffix.equals(BASE128.decode("77q/pLYqCIj4QyskZ26SCg==")) || suffix.equals(BASE128.decode("pnWrCRPGzR7fVf5XLGFwsg==")) || suffix.equals(BASE128.decode("HnYqhiy/hQDpFVfK3Kudig==")))
                    {
                        drawable = ImgHelper.getDrawableFromResources(context, R.drawable.ic_zip);
                    }
                    else
                    {
                        drawable = ImgHelper.getDrawableFromResources(context, R.drawable.ic_file);
                    }
                }
                else
                {

                    drawable = ImgHelper.getDrawableFromResources(context, R.drawable.ic_folder);
                }

            }



            final Drawable d = drawable;
            if (d != null)
            {
                imageCache.put(mImageUrl, new SoftReference<Drawable>(d));
            }
            handler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        if (mAllowLoad)
                        {
                            mListener.onImageLoad(mt, d, mImageUrl);
                        }
                    }
                });
        }
        catch (Exception e)
        {
            handler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        mListener.onError(mt, mImageUrl);
                    }
                });
            e.printStackTrace();
        }
    }


}
