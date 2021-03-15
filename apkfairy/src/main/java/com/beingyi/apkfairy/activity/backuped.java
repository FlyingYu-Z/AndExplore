package com.beingyi.apkfairy.activity;

import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.beingyi.apkfairy.R;
import com.beingyi.apkfairy.application.*;
import com.beingyi.apkfairy.base.*;
import com.beingyi.apkfairy.bean.*;
import com.beingyi.apkfairy.utils.*;
import java.io.*;
import java.util.*;
import org.xutils.*;
import org.xutils.ex.*;

public class backuped extends BaseActivity
{


    Context context;
    ComUtils comUtils;
    ListView listview;
    MyAdapter myAdapter;


    List<BackupInfo> backupinfos = new ArrayList<BackupInfo>();


    public void initview()
    {
        comUtils = new ComUtils(context);
        listview = (ListView)findViewById(R.id.activitybackupedListView1);
    }




    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    myAdapter.setData(backupinfos);
                    listview.setAdapter(myAdapter);

                    break;

                default:
                    break;


            }

        }};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backuped);
        context = this;
        initview();

        
        //获取状态栏高度
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        FrameLayout fl=(FrameLayout)getWindow().getDecorView().findViewById(android.R.id.content);
        LinearLayout.LayoutParams lp =(LinearLayout.LayoutParams) fl.getLayoutParams(); 
        lp.setMargins(0, result, 0, 0);
        fl.setLayoutParams(lp);
        
        
        
        
        myAdapter = new MyAdapter(context);

        new Thread(){
            @Override
            public void run()
            {
                MyApplication application=new MyApplication();
                DbManager db=x.getDb(application.getDaoConfig());



                try
                {
                    List<Person> persons =db.findAll(Person.class);

                    for (int i=0;i < persons.size();i++)
                    {
                        Person person=persons.get(i);
                        BackupInfo BackupInfo=new BackupInfo();
                        BackupInfo.Name = person.getName();
                        BackupInfo.PackName = person.getPackName();
                        BackupInfo.Path = person.getPath();
                        if(!new File(BackupInfo.Path).exists()){
                            BackupInfo.isDeleted=true;
                        }else{
                            BackupInfo.isDeleted=false;
                        }
                        BackupInfo.BackupType = person.getBackupType();

                        backupinfos.add(BackupInfo);

                    }

                    handler.sendEmptyMessage(1);


                }
                catch (DbException e)
                {

                }
                
                
            }
        }.start();

        
        
        
        

    }





    class MyAdapter extends BaseAdapter
    {
        Context context;
        List<BackupInfo> BackupInfos = new ArrayList<BackupInfo>();
        List<BackupInfo> choosed_BackupInfos = new ArrayList<BackupInfo>();


        public MyAdapter(Context context)
        {

            this.context = context;

        }


        @Override
        public int getCount()
        {
            if (BackupInfos != null && BackupInfos.size() > 0)
            {
                return BackupInfos.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position)
        {
            if (BackupInfos != null && BackupInfos.size() > 0)
            {
                return BackupInfos.get(position);
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
            // TODO Auto-generated method stub
            return true ;
        }



        public void setData(List<BackupInfo> BackupInfos)
        {
            this.BackupInfos = BackupInfos;
            notifyDataSetChanged();
        }

        public List<BackupInfo> getData()
        {
            return BackupInfos;
        }

        public List<BackupInfo> getChoosedData()
        {

            choosed_BackupInfos.clear();
            long[] Ids = listview.getCheckedItemIds(); 

            for (int i=0;i < Ids.length;i++)
            {
                choosed_BackupInfos.add(BackupInfos.get((int)Ids[i]));

            }



            return choosed_BackupInfos;
        }





        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            Holder holder;
            convertView = LayoutInflater.from(context).inflate(R.layout.item_backup, null);

            holder = new Holder();
            holder.icon = (ImageView)convertView.findViewById(R.id.itembackupImageView_icon);
            holder.name = (TextView)convertView.findViewById(R.id.itembackupTextView_name);
            holder.path = (TextView)convertView.findViewById(R.id.itembackupTextView_path);
            holder.type = (TextView)convertView.findViewById(R.id.itembackupTextView_type);
            holder.isDeleted = (TextView)convertView.findViewById(R.id.itembackupTextView_isDeleted);




            BackupInfo BackupInfo=BackupInfos.get(position);




            //holder.icon.setImageDrawable(R.drawable.ic_launcher);
            holder.name.setText(BackupInfo.Name);
            holder.path.setText("路径:" + BackupInfo.Path);
            holder.type.setText("类型:" + BackupInfo.BackupType);
            if(BackupInfo.isDeleted){
            holder.isDeleted.setText("已删除");
            }

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



        class Holder
        {
            public ImageView icon;
            public TextView name;
            public TextView path;
            public TextView type;
            public TextView isDeleted;


        }




    }


    class BackupInfo
    {
        String Name="";
        String PackName="";
        String Path="";
        String BackupType="";
        boolean isDeleted=false;
    }


}
