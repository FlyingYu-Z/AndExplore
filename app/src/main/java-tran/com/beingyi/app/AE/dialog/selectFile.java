package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.beingyi.app.AE.R;
import com.beingyi.app.AE.bean.FileInfo;
import com.beingyi.app.AE.holder.FileInfoHolder;
import com.beingyi.app.AE.interfaces.SelectFileCallBack;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.AEUtils;
import com.beingyi.app.AE.utils.APKUtils;
import com.beingyi.app.AE.utils.BitmapUtils;
import com.beingyi.app.AE.utils.FileInfoSort;
import com.beingyi.app.AE.utils.FileSizeUtils;
import com.beingyi.app.AE.utils.FileUtils;
import com.beingyi.app.AE.utils.ImgHelper;
import com.beingyi.app.AE.utils.MP4Utils;
import com.beingyi.app.AE.utils.SPUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class selectFile
{

    Context context;
    Activity activity;
    AEUtils utils;
    int window;
    String path;
    String suffix;
    SelectFileCallBack callBack;
    
    TextView tv_path;
    ListView lv_file;

    AlertDialog dialog;

    public selectFile(Context mContext, int windows, String mPath,String mSuffix,SelectFileCallBack mCallBack)
    {
        this.context = mContext;
        this.activity = (Activity)context;
        this.utils = new AEUtils(context);
        this.window = windows;
        this.path = mPath;
        this.suffix=mSuffix;
        this.callBack=mCallBack;


        View view=View.inflate(context, R.layout.view_select_file, null);
        tv_path=view.findViewById(R.id.view_select_file_TextView_path);
        lv_file=view.findViewById(R.id.view_select_file_ListView);
        
        setHeader(lv_file);
        
        final FileAdapter adapter=new FileAdapter(context,lv_file);
        lv_file.setAdapter(adapter);
        
        View header=lv_file.findViewWithTag("header");
        header.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    adapter.goParent();

                }

            });
        
        
        
        lv_file.setOnItemClickListener(new OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    

                    FileInfo fileInfo= (FileInfo) lv_file.getItemAtPosition(position);
                    String filePath=fileInfo.Path;
                    File file=new File(filePath);
                    if(file.isDirectory()){
                        adapter.getFiles(filePath);
                        return;
                    }

                    if(!FileUtils.getSuffix(filePath).equals(suffix)){
                        ToastUtils.show(MyApplication.getInstance().getString("d969f84f22fc51e23928fa738cc4ea2b")+suffix+MyApplication.getInstance().getString("87353e260d6b709c1dba97db426312e9"));
                        return;
                    }

                    dialog.dismiss();
                    callBack.onSelected(filePath);
                    
                    
                }
                
            
        });
        
         dialog = new AlertDialog.Builder(context)
            .setTitle(MyApplication.getInstance().getString("fd7e0c997d0005a132605a5be7a5161c"))
            .setView(view)
            .setCancelable(false)
            .setNegativeButton(MyApplication.getInstance().getString("625fb26b4b3340f7872b411f401e754c"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                    callBack.onCancel();
                }})
            .create();
            
        dialog.show();



    }



    public void setHeader(ListView listview)
    {
        View view=LinearLayout.inflate(context, R.layout.item_file, null);
        ImageView icon=view.findViewById(R.id.item_file_ImageView_icon);
        TextView name=view.findViewById(R.id.item_file_TextView_name);
        TextView time=view.findViewById(R.id.item_file_TextView_time);
        TextView size=view.findViewById(R.id.item_file_TextView_size);
        ((LinearLayout)time.getParent()).setVisibility(View.GONE);
        icon.setImageResource(R.drawable.ic_folder);
        name.setText("…");
        view.setTag("header");

        listview.addHeaderView(view);


    }
    



    public class FileAdapter extends BaseAdapter
    {


        Context context;
        Activity activity;
        AEUtils utils;
        public List<FileInfo> fileList;
        ListView listview;
        public AsyncImageLoader asyncImageLoader;
        public String Path;
        
        public FileAdapter(final Context context, ListView listview)
        {
            this.context = context;
            this.activity = (Activity)context;
            this.utils = new AEUtils(context);
            this.fileList = new ArrayList<FileInfo>();
            this.listview = listview;
            asyncImageLoader = new AsyncImageLoader();
            
            Path=utils.getCuPath(window);
            
            getFiles(Path);

        }



        public void goParent()
        {
            fileList.clear();
            String path="/";
            path = Path;

            if (!path.equals("/"))
            {
                path = new File(path).getParentFile().getAbsolutePath();
            }
            getFiles(path);



        }



        public void refresh()
        {
            getFiles(Path);


        }




        public void getFiles(String dir)
        {
            Path=dir;
            fileList.clear();
            notifyDataSetChanged();

            try
            {
                dir = utils.DesPath(dir);
                File DirFile=new File(dir);
                String[] files=null;

                if (DirFile.list() == null)
                {
                    String listMode=SPUtils.getString(context, "conf", "sort_mode" + window);
                    Runtime mRuntime = Runtime.getRuntime();
                    //Process mProcess = mRuntime.exec("ls " + listMode + " " + dir);
                    Process mProcess = mRuntime.exec("ls " + dir);
                    BufferedReader mReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()));
                    StringBuffer mRespBuff = new StringBuffer();
                    char[] buff = new char[1024];
                    int ch = 0;
                    while ((ch = mReader.read(buff)) != -1)
                    {
                        mRespBuff.append(buff, 0, ch);
                    }
                    mReader.close();
                    String result=mRespBuff.toString();
                    //new alert(context,result);
                    if (!result.equals(""))
                    {
                        files = result.split("\n");
                    }
                }
                else
                {

                    files = DirFile.list();

                }


                for (int i=0;i < files.length;i++)
                {
                    File file=new File(dir + files[i]);

                    FileInfo fileInfo=new FileInfo();
                    fileInfo.Path = dir + files[i];
                    fileInfo.Name = file.getName();
                    fileInfo.Time = FileUtils.getModifiedTime(file.getAbsolutePath());
                    if (file.isFile())
                    {
                        fileInfo.Size = FileSizeUtils.getAutoFileOrFilesSize(file.getAbsolutePath());
                    }
                    else
                    {
                        fileInfo.Size = "";
                    }


                    fileList.add(fileInfo);

                }




            }
            catch (Exception e)
            {
                fileList.clear();
                new alert(context, e.toString());
            }



            FileInfoSort.sort(window, fileList, utils);

            tv_path.setText(Path);
            
            notifyDataSetChanged();

        }






        public void setData(ArrayList<FileInfo> fileList)
        {

            this.fileList = fileList;
            notifyDataSetChanged();

        }


        @Override
        public int getCount()
        {
            if (fileList != null && fileList.size() > 0)
            {
                return fileList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position)
        {
            if (fileList != null && fileList.size() > 0)
            {
                return fileList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public boolean hasStableIds()
        {
            return true ;
        }



        public List<FileInfo> getData()
        {
            return fileList;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            FileInfoHolder holder=null;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_file, null);
                holder = new FileInfoHolder();

                holder.Icon = (ImageView)convertView.findViewById(R.id.item_file_ImageView_icon);
                holder.Name = (TextView)convertView.findViewById(R.id.item_file_TextView_name);
                holder.Time = (TextView)convertView.findViewById(R.id.item_file_TextView_time);
                holder.Size = (TextView)convertView.findViewById(R.id.item_file_TextView_size);
                holder.State = (TextView)convertView.findViewById(R.id.item_file_TextView_state);

                convertView.setTag(holder);


            }
            else
            {
                holder = (FileInfoHolder) convertView.getTag();
            }



            FileInfo fileInfo=fileList.get(position);

            displayImage(position, fileInfo.Path, holder.Icon, holder.State);

            holder.Name.setText(fileInfo.Name);
            holder.Time.setText(fileInfo.Time);
            holder.Size.setText(fileInfo.Size);


            if (listview.isItemChecked(position))
            {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.AppColor));
            }
            else
            {
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }


            return convertView;
        }



        private void displayImage(int position , String imageUrl, ImageView imageView, TextView state)
        {
            imageView.setTag(imageUrl);
            state.setText("");
            state.setTag(MyApplication.getInstance().getString("3fea7ca76cdece641436d7ab0d02ab1b") + imageUrl);
            asyncImageLoader.loadImage(context, this, position, imageUrl, imageLoadListener);

        }

        public void loadImage()
        {
            int start = listview.getFirstVisiblePosition();
            int end = listview.getLastVisiblePosition();
            if (end >= getCount())
            {
                end = getCount() - 1;
            }
            asyncImageLoader.setLoadLimit(start, end);
            asyncImageLoader.unlock();
        }


        OnImageLoadListener imageLoadListener = new OnImageLoadListener() {

            @Override
            public void onImageLoad(Integer t, Drawable drawable, String url)
            {
                View view = listview.findViewWithTag(url);
                if (view != null)
                {
                    ImageView iv = (ImageView) view.findViewById(R.id.item_file_ImageView_icon);
                    iv.setBackgroundDrawable(drawable);

                    TextView state=(TextView) listview.findViewWithTag(MyApplication.getInstance().getString("3fea7ca76cdece641436d7ab0d02ab1b") + url).findViewById(R.id.item_file_TextView_state);
                    if (FileUtils.getSuffix(url).equals("apk") && 
                        APKUtils.isApkInstalled(context, APKUtils.getPkgName(context, url)) &&
                        state.getTag().equals(MyApplication.getInstance().getString("3fea7ca76cdece641436d7ab0d02ab1b") + url) &&
                        new File(url).isFile())
                    {
                        state.setText(MyApplication.getInstance().getString("9d5bf2a10a520071f965d7e87b12676e"));
                        state.setTextColor(Color.RED);
                    }

                }
            }

            @Override
            public void onError(Integer t, String url)
            {
                View view = listview.findViewWithTag(url);
                if (view != null)
                {
                    ImageView iv = (ImageView) view.findViewById(R.id.item_file_ImageView_icon);
                    iv.setBackgroundResource(R.drawable.ic_launcher);
                }
            }

        };






    }





    public interface OnImageLoadListener
    {
        public void onImageLoad(Integer t, Drawable drawable, String url);
        public void onError(Integer t, String url);
    }
    
    
    class AsyncImageLoader
    {

        private Object lock = new Object();
        private boolean mAllowLoad = true;
        private boolean firstLoad = true;
        private int mStartLoadLimit = 0;
        private int mStopLoadLimit = 0;
        final Handler handler = new Handler();

        private HashMap<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();   


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

        public void loadImage(final Context context, final FileAdapter adapter, Integer t, String imageUrl, OnImageLoadListener listener)
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

        public void loadImage(Context context, FileAdapter adapter, final String mImageUrl, final Integer mt, final OnImageLoadListener mListener)
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
                    else if (suffix.equals("zip") || suffix.equals("jar") || suffix.equals("rar") || suffix.equals("7z"))
                    {
                        drawable = ImgHelper.getDrawableFromResources(context, R.drawable.ic_zip);
                    }
                    else if (suffix.equals("dex"))
                    {
                        drawable = ImgHelper.getDrawableFromResources(context, R.drawable.ic_dex);
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








}
