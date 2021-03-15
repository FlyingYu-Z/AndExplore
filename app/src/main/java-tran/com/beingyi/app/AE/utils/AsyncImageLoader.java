package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.application.MyApplication;
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
            System.out.println("drawable");
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

                    if (suffix.equals("apk"))
                    {
                        drawable = APKUtils.getApkIcon(context, path);
                    }
                    else if (suffix.equals("png") || suffix.equals("jpg") || suffix.equals("jpeg") || suffix.equals("gif"))
                    {
                        drawable = new BitmapDrawable(BitmapUtils.decodeSampledBitmapFromSd(path, 100, 100));
                    }
                    else if (suffix.equals("mp4"))
                    {
                        drawable = MP4Utils.getIcon(path);
                    }
                    else if (suffix.equals("zip") || suffix.equals("rar") || suffix.equals("7z"))
                    {
                        drawable = ImgHelper.getDrawableFromResources(context, R.drawable.ic_zip);
                    }
                    else if (suffix.equals("jar"))
                    {
                        drawable = ImgHelper.getDrawableFromResources(context, R.drawable.ic_jar);
                    }
                    else if (suffix.equals("mp3"))
                    {
                        drawable = ImgHelper.getDrawableFromResources(context, R.drawable.ic_mp3);
                    }
                    else if (suffix.equals("dex"))
                    {
                        drawable = ImgHelper.getDrawableFromResources(context, R.drawable.ic_dex);
                    }
                    else if (suffix.equals("mod"))
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


                if (!mImageUrl.endsWith("/"))
                {

                    String suffix=FileUtils.getSuffix(mImageUrl);

                    if (suffix.equals("zip") || suffix.equals("jar") || suffix.equals("rar") || suffix.equals("7z"))
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





