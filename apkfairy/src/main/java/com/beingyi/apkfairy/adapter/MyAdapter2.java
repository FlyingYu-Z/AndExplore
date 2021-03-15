package com.beingyi.apkfairy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.beingyi.apkfairy.R;
import com.beingyi.apkfairy.utils.AppInfo;
import com.beingyi.apkfairy.utils.FileSizeUtils;
import com.beingyi.apkfairy.utils.Holder;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter2 extends BaseAdapter
{
    Context context;
    ListView listview;
    List<AppInfo> appInfos = new ArrayList<AppInfo>();
    List<AppInfo> choosed_appInfos = new ArrayList<AppInfo>();


    public MyAdapter2(Context context,ListView listview){

        this.context=context;
        this.listview=listview;

    }


    @Override
    public int getCount()
    {
        if (appInfos != null && appInfos.size() > 0)
        {
            return appInfos.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position)
    {
        if (appInfos != null && appInfos.size() > 0)
        {
            return appInfos.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true ;
    }



    public void setData(List<AppInfo> appInfos)
    {
        this.appInfos = appInfos;
        notifyDataSetChanged();
    }

    public List<AppInfo> getData()
    {
        return appInfos;
    }

    public List<AppInfo> getChoosedData()
    {

        choosed_appInfos.clear();
        long[] Ids = listview.getCheckedItemIds(); 

        for(int i=0;i<Ids.length;i++){
            choosed_appInfos.add(appInfos.get((int)Ids[i]));

        }



        return choosed_appInfos;
    }





    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        Holder holder;
        //if (convertView == null)
        //{

        //convertView.setTag(holder);

        //}
        //else
        //{
        //    holder = (Holder) convertView.getTag();
        //}


        convertView = LayoutInflater.from(context).inflate(R.layout.item_app_info, null);

        holder = new Holder();
        holder.icon = (ImageView)convertView.findViewById(R.id.itemappinfoImageView1);
        holder.appName = (TextView)convertView.findViewById(R.id.itemappinfoTextView1_appName);
        holder.versionName = (TextView)convertView.findViewById(R.id.itemappinfoTextView1_versionName);
        holder.packageName = (TextView)convertView.findViewById(R.id.itemappinfoTextView1_packageName);
        holder.isBacked=(TextView)convertView.findViewById(R.id.itemappinfoTextView_isBacked);




        AppInfo appInfo=appInfos.get(position);




        holder.icon.setImageDrawable(appInfo.appIcon);
        holder.appName.setText(appInfo.appName);
        holder.versionName.setText("版本:"+appInfo.versionName);
        holder.packageName.setText("大小:"+FileSizeUtils.getAutoFileOrFilesSize(appInfo.path));
        if(appInfo.isBacked){
            holder.isBacked.setText("已备份");
        }

        if(listview.isItemChecked(position)) {

            convertView.setBackgroundColor(context.getResources().getColor(R.color.AppColor));

        } else {

            convertView.setBackgroundColor(Color.TRANSPARENT);


        }


        return convertView;
    }





}
