package com.beingyi.app.AE.adapter;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beingyi.app.AE.R;

import java.util.List;
import java.util.Map;

public class ExpandedListAdapter extends BaseExpandableListAdapter {

    Context context;
    List<Map<String, String>> groups;
    List<List<Map<String, ExpandedListAdapter.SlideMenuInfo>>> childs;


    /*
     * 构造函数:
     * 参数1:context对象
     * 参数2:一级列表数据源
     * 参数3:二级列表数据源
     */
    public ExpandedListAdapter(Context context, List<Map<String, String>> groups, List<List<Map<String, ExpandedListAdapter.SlideMenuInfo>>> childs) {
        this.groups = groups;
        this.childs = childs;
        this.context = context;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return childs.get(groupPosition).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        @SuppressWarnings("unchecked")
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.item_child, null);
        TextView tv = (TextView) view.findViewById(R.id.child);

        ExpandedListAdapter.SlideMenuInfo menuInfo = ((Map<String, ExpandedListAdapter.SlideMenuInfo>) getChild(groupPosition, childPosition)).get("child");
        tv.setText(menuInfo.Name);

        ImageView imageView = (ImageView) view.findViewById(R.id.item_child_ImageView_icon);
        imageView.setImageResource(menuInfo.Icon);

        return view;
    }

    public int getChildrenCount(int groupPosition) {
        return childs.get(groupPosition).size();
    }

    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    public int getGroupCount() {
        return groups.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //获取一级列表View对象
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String text = groups.get(groupPosition).get("group");
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //获取一级列表布局文件,设置相应元素属性
        View view =layoutInflater.inflate(R.layout.item_group, null);
        TextView textView = (TextView) view.findViewById(R.id.group);
        textView.setText(text);

        ImageView imageView = (ImageView) view.findViewById(R.id.item_group_ImageView_icon);

        if (getChildrenCount(groupPosition) == 0) {//该组下没有子项
            imageView.setVisibility(View.GONE);
        } else {
            if (isExpanded == true) {//展开状态
                imageView.setImageResource(R.drawable.ic_down);
            } else {//收起状态
                imageView.setImageResource(R.drawable.ic_up);
            }
        }
        return view;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    public static class SlideMenuInfo{

        public String Name="";
        public int Icon;

    }


}
