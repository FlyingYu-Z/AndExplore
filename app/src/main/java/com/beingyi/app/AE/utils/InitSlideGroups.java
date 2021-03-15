package com.beingyi.app.AE.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.activity.MainActivity;
import com.beingyi.app.AE.activity.OpenSourceList;
import com.beingyi.app.AE.activity.webbrowser;
import com.beingyi.app.AE.adapter.ExpandedListAdapter;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.update.CheckUpdate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitSlideGroups {

    Context context;
    MainActivity activity;


    public InitSlideGroups(Context mContext) {
        this.context = mContext;
        this.activity = (MainActivity) context;

    }

    public void init(ExpandableListView el) {


        List<Map<String, String>> groups = new ArrayList<>();

        Map<String, String> group1 = new HashMap<>();
        group1.put("group", "本地");
        Map<String, String> group2 = new HashMap<>();
        group2.put("group", "其他");

        groups.add(group1);
        groups.add(group2);


        List<Map<String, ExpandedListAdapter.SlideMenuInfo>> child1 = new ArrayList<>();
        List<Map<String, ExpandedListAdapter.SlideMenuInfo>> child2 = new ArrayList<>();

        addMenu(child1, "根目录", R.drawable.ic_root);
        addMenu(child1, "内置储存卡", R.drawable.ic_sd);

        addMenu(child2, "模块管理", R.drawable.ic_module);
        addMenu(child2, "检测更新", R.drawable.ic_refresh);
        addMenu(child2, "更新历史", R.drawable.ic_history);
        addMenu(child2, "加入官方QQ群", R.drawable.ic_join_group);
        addMenu(child2, "开源项目", R.drawable.ic_github);


        List<List<Map<String, ExpandedListAdapter.SlideMenuInfo>>> childs = new ArrayList<>();
        childs.add(child1);
        childs.add(child2);

        ExpandedListAdapter adapter = new ExpandedListAdapter(context, groups, childs);
        el.setAdapter(adapter);
        el.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                //这里的position会根据group展开与否而变化，所以没有意义
                System.out.println("position=" + position + " id=" + id);
                return true;
            }
        });

        el.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {

                if (groupPosition == 0) {

                    switch (childPosition){

                        case 0:
                            activity.getCuAdapter().getFiles("/");
                            activity.hideDrawer();
                            break;

                        case 1:
                            activity.getCuAdapter().getFiles(FileUtils.getSDPath());
                            activity.hideDrawer();
                            break;


                    }

                    return false;
                }

                switch (childPosition) {

                    case 0:

                        break;

                    case 1:
                        new CheckUpdate(context, true);
                        break;

                    case 2: {
                        Intent intent = new Intent(context, webbrowser.class);
                        intent.putExtra("url", "http://idc.beingyi.cn/ae/updateHistory.html");
                        context.startActivity(intent);
                    }
                    break;

                    case 3:
                        Intent intent = new Intent();
                        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + "XwbnSgKId6k26ls8aoayeOBipbn22WmR"));
                        try {
                            context.startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(context, "未安装手Q或安装的版本不支持", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 4:
                        activity.StartActivity(OpenSourceList.class);
                        break;


                }


                return false;
            }
        });


    }


    public void addMenu(List<Map<String, ExpandedListAdapter.SlideMenuInfo>> child, String Name, int icon) {

        Map<String, ExpandedListAdapter.SlideMenuInfo> childDate = new HashMap<>();

        ExpandedListAdapter.SlideMenuInfo menuInfo = new ExpandedListAdapter.SlideMenuInfo();
        menuInfo.Name = Name;
        menuInfo.Icon = icon;

        childDate.put("child", menuInfo);

        child.add(childDate);

    }


}
