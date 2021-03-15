package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.application.MyApplication;
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
        group1.put("group", MyApplication.getInstance().getString("8e7eca1f7079c6a7861b3dcaa2dc8c05"));
        Map<String, String> group2 = new HashMap<>();
        group2.put("group", MyApplication.getInstance().getString("0d98c74797e49d00bcc4c17c9d557a2b"));

        groups.add(group1);
        groups.add(group2);


        List<Map<String, ExpandedListAdapter.SlideMenuInfo>> child1 = new ArrayList<>();
        List<Map<String, ExpandedListAdapter.SlideMenuInfo>> child2 = new ArrayList<>();

        addMenu(child1, MyApplication.getInstance().getString("c2b9f4b9fc1e2052187f3117738c53d1"), R.drawable.ic_root);
        addMenu(child1, MyApplication.getInstance().getString("ce04e3020315c7400aadb1bac1775c96"), R.drawable.ic_sd);

        addMenu(child2, MyApplication.getInstance().getString("c43592d0b9d2dcdd58c8d9bccb232448"), R.drawable.ic_module);
        addMenu(child2, MyApplication.getInstance().getString("a2ab4a99f5b3da615aedd7762fcae32d"), R.drawable.ic_refresh);
        addMenu(child2, MyApplication.getInstance().getString("d5dcc4adbaddba1984be043a9bf13636"), R.drawable.ic_history);
        addMenu(child2, MyApplication.getInstance().getString("31ea588b191b8fb333a8d6370787fa92"), R.drawable.ic_join_group);
        addMenu(child2, MyApplication.getInstance().getString("ab6eb4cc1d108e5fb0a8063ab7a7e439"), R.drawable.ic_github);


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
                            Toast.makeText(context, MyApplication.getInstance().getString("23543256e9a0a72c3ab2888b784ddea0"), Toast.LENGTH_SHORT).show();
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
