package com.beingyi.apkfairy.activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.beingyi.apkfairy.R;
import com.beingyi.apkfairy.base.BaseActivity;
import com.beingyi.apkfairy.utils.ComUtils;
import java.util.ArrayList;
import java.util.List;

public class launch extends BaseActivity
{
    Context context;
    ComUtils comUtils;
    ListView listview;
    MyAdapter myAdapter;
    List<LaunchInfo> LaunchInfos = new ArrayList<LaunchInfo>();


    public void initview()
    {
        comUtils = new ComUtils(context);
        listview = (ListView)findViewById(R.id.activitylaunchListView1);


    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        context = launch.this;
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




        String packname=getIntent().getStringExtra("packname").toString();

        ArrayList<String> activities=comUtils.getAppActivity(packname);

        for (String array :activities)
        {
            LaunchInfo LaunchInfo=new LaunchInfo();
            LaunchInfo.ActivityName = array.substring(array.lastIndexOf(".")).replaceFirst(".", "");
            LaunchInfo.Allpackname = array;
            LaunchInfo.packname = packname;

            LaunchInfos.add(LaunchInfo);

        }


        myAdapter = new MyAdapter(context);
        myAdapter.setData(LaunchInfos);
        listview.setAdapter(myAdapter);


        listview.setOnItemClickListener(new OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> p1, View view, int position, long id)
                {
                    
                    
                    /**
                    try {
                        ComponentName componentName = new ComponentName(包名, activity);
                        Intent intent = new Intent();
                        intent.setComponent(componentName);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } catch (Exception e) {
                    }**/
                    
                    
                    
                    try
                    {
                        Intent intent = new Intent();
                        ComponentName cn=new ComponentName(LaunchInfos.get(position).packname, LaunchInfos.get(position).Allpackname);
                        intent.setComponent(cn);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(context, "启动失败", Toast.LENGTH_SHORT).show();
                    }
                }



            });


    }



    class MyAdapter extends BaseAdapter
    {
        Context context;
        List<LaunchInfo> launchInfos = new ArrayList<LaunchInfo>();

        public MyAdapter(Context context)
        {

            this.context = context;

        }


        @Override
        public int getCount()
        {
            if (LaunchInfos != null && LaunchInfos.size() > 0)
            {
                return LaunchInfos.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position)
        {
            if (LaunchInfos != null && LaunchInfos.size() > 0)
            {
                return LaunchInfos.get(position);
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



        public void setData(List<LaunchInfo> launchInfo)
        {
            this.launchInfos = launchInfo;
            notifyDataSetChanged();
        }

        public List<LaunchInfo> getData()
        {
            return LaunchInfos;
        }






        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            Holder holder;
            convertView = LayoutInflater.from(context).inflate(R.layout.item_launch, null);

            holder = new Holder();
            holder.ActivityName = (TextView)convertView.findViewById(R.id.itemlaunchTextView_activityname);
            holder.PackNmae = (TextView)convertView.findViewById(R.id.itemlaunchTextView_packname);



            LaunchInfo launchinfo=LaunchInfos.get(position);




            holder.ActivityName.setText(launchinfo.ActivityName);
            holder.PackNmae.setText("全名:" + launchinfo.Allpackname);

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
            public TextView ActivityName;
            public TextView PackNmae;


        }




    }


    class LaunchInfo
    {
        String Allpackname="";
        String packname="";
        String ActivityName;

    }



}
