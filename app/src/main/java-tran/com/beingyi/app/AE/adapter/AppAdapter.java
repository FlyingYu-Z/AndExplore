package com.beingyi.app.AE.adapter;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.beingyi.app.AE.R;
import com.beingyi.app.AE.holder.AppHolder;
import com.beingyi.app.AE.holder.FileInfoHolder;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends BaseAdapter {
    Context context;
    ListView listview;
    List<AppInfo> appInfos = new ArrayList<AppInfo>();
    List<AppInfo> backed_appInfos = new ArrayList<AppInfo>();

    public AppAdapter(Context context, ListView listview) {

        this.context = context;
        this.listview = listview;

    }


    @Override
    public int getCount() {
        if (appInfos != null && appInfos.size() > 0) {
            return appInfos.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (appInfos != null && appInfos.size() > 0) {
            return appInfos.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    public void setData(List<AppInfo> appInfos) {
        this.appInfos = appInfos;
        this.backed_appInfos.addAll(appInfos);
        notifyDataSetChanged();
    }

    public List<AppInfo> getData() {
        return appInfos;
    }


    public void filterItem(String name){

        if(!name.isEmpty()) {
            appInfos.clear();

            for (int i=0;i<backed_appInfos.size();i++) {
                AppInfo appInfo=backed_appInfos.get(i);
                if (appInfo.appName.contains(name)) {
                    appInfos.add(appInfo);
                }

            }

        }else{
            appInfos.clear();
            appInfos.addAll(backed_appInfos);
        }

        notifyDataSetChanged();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AppHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.item_app, null);

            holder = new AppHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.item_app_ImageView1);
            holder.appName = (TextView) convertView.findViewById(R.id.item_app_TextView1_appName);
            holder.versionName = (TextView) convertView.findViewById(R.id.item_app_TextView1_versionName);
            holder.packageName = (TextView) convertView.findViewById(R.id.item_app_TextView1_packageName);
            holder.isBacked = (TextView) convertView.findViewById(R.id.item_app_TextView_isBacked);

            convertView.setTag(holder);

        } else {
            holder = (AppHolder) convertView.getTag();
        }

        AppInfo appInfo = appInfos.get(position);


        holder.icon.setImageDrawable(appInfo.appIcon);
        holder.appName.setText(appInfo.appName);
        holder.versionName.setText(MyApplication.getInstance().getString("e9685f5d7283e30568497596acfd841f") + appInfo.versionName);
        holder.packageName.setText(MyApplication.getInstance().getString("0c0ebf2711893f5071dfbb464217818e") + appInfo.size);
        if (appInfo.isBacked) {
            holder.isBacked.setText(MyApplication.getInstance().getString("8a814fc4c664d96702d88b73d46a8d82"));
        }

        if (listview.isItemChecked(position)) {

            convertView.setBackgroundColor(context.getResources().getColor(R.color.AppColor));

        } else {

            convertView.setBackgroundColor(Color.TRANSPARENT);


        }


        return convertView;
    }





}
