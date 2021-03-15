package com.beingyi.app.AE.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.fragment.FdexSearch;
import com.beingyi.app.AE.holder.AppHolder;
import com.beingyi.app.AE.holder.FileInfoHolder;
import com.beingyi.app.AE.utils.AppInfo;
import com.beingyi.app.AE.utils.KeyWordUtils;

import org.jf.dexlib2.iface.ClassDef;

import java.util.ArrayList;
import java.util.List;

public class SearchClassAdapter extends BaseAdapter {

    Context context;
    FdexSearch fdexSearch;
    List<ClassDef> classDefList = new ArrayList<>();

    public SearchClassAdapter(Context context, List<ClassDef> classDefList,FdexSearch fdexSearch) {

        this.context = context;
        this.classDefList = classDefList;
        this.fdexSearch=fdexSearch;

    }


    @Override
    public int getCount() {
        if (classDefList != null && classDefList.size() > 0) {
            return classDefList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (classDefList != null && classDefList.size() > 0) {
            return classDefList.get(position);
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

    public List<ClassDef> getData() {
        return classDefList;
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

        ClassDef classDef = classDefList.get(position);


        String className = classDef.getType();

        KeyWordUtils.setHighlight(holder.Name,className,fdexSearch.getKeyWord());
        //holder.Name.setText(className);

        holder.Time.setVisibility(View.INVISIBLE);
        holder.Size.setVisibility(View.INVISIBLE);


        holder.Icon.setImageResource(R.drawable.ic_clazz);

        return convertView;
    }



}
