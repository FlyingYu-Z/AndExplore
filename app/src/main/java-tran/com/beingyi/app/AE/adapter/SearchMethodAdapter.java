package com.beingyi.app.AE.adapter;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.holder.FileInfoHolder;

import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Method;

import java.util.ArrayList;
import java.util.List;

public class SearchMethodAdapter extends BaseAdapter {

    Context context;
    List<Method> methodList = new ArrayList<>();

    public SearchMethodAdapter(Context context, List<Method> methodList) {

        this.context = context;
        this.methodList = methodList;

    }


    @Override
    public int getCount() {
        if (methodList != null && methodList.size() > 0) {
            return methodList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (methodList != null && methodList.size() > 0) {
            return methodList.get(position);
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

    public List<Method> getData() {
        return methodList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        FileInfoHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_file, null);
            holder = new FileInfoHolder();

            holder.Icon = (ImageView) convertView.findViewById(R.id.item_file_ImageView_icon);
            holder.Name = (TextView) convertView.findViewById(R.id.item_file_TextView_name);
            holder.Time = (TextView) convertView.findViewById(R.id.item_file_TextView_time);
            holder.Size = (TextView) convertView.findViewById(R.id.item_file_TextView_size);
            holder.State = (TextView) convertView.findViewById(R.id.item_file_TextView_state);

            convertView.setTag(holder);

        } else {
            holder = (FileInfoHolder) convertView.getTag();
        }

        Method method = methodList.get(position);


        String methodName = method.getName();

        holder.Name.setText(methodName);
        holder.Time.setVisibility(View.INVISIBLE);
        holder.Size.setVisibility(View.INVISIBLE);


        holder.Icon.setImageResource(R.drawable.ic_method);

        return convertView;
    }





}
