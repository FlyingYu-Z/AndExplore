package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
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
        group1.put(BASE128.decode("UubAzefLlePbZtWnRVXEvA=="), BASE128.decode("5noeCeh83948g/09171+eA=="));
        Map<String, String> group2 = new HashMap<>();
        group2.put(BASE128.decode("UubAzefLlePbZtWnRVXEvA=="), BASE128.decode("5HZmxdydK2+INJTc447BEA=="));

        groups.add(group1);
        groups.add(group2);


        List<Map<String, ExpandedListAdapter.SlideMenuInfo>> child1 = new ArrayList<>();
        List<Map<String, ExpandedListAdapter.SlideMenuInfo>> child2 = new ArrayList<>();

        addMenu(child1, BASE128.decode("6ofvX7QxIEFa7BBgWaDsSg=="), R.drawable.ic_root);
        addMenu(child1, BASE128.decode("d9qd2tgxkLHPqEpgkr8k+g=="), R.drawable.ic_sd);

        addMenu(child2, BASE128.decode("L9EhB/CQrrXxCMQsIO4X7g=="), R.drawable.ic_module);
        addMenu(child2, BASE128.decode("hCvt0zUfrVbbEyni4qAOjA=="), R.drawable.ic_refresh);
        addMenu(child2, BASE128.decode("zRF+AQPA1k98boFpje3A8A=="), R.drawable.ic_history);
        addMenu(child2, BASE128.decode("orweWerFi1EJALGN4Hl/BTYCp68hOw42Iv7aq8JR7VY="), R.drawable.ic_join_group);
        addMenu(child2, BASE128.decode("aZMfDZ9C03IBUbULR38Prw=="), R.drawable.ic_github);


        List<List<Map<String, ExpandedListAdapter.SlideMenuInfo>>> childs = new ArrayList<>();
        childs.add(child1);
        childs.add(child2);

        ExpandedListAdapter adapter = new ExpandedListAdapter(context, groups, childs);
        el.setAdapter(adapter);
        el.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                //这里的position会根据group展开与否而变化，所以没有意义
                System.out.println(BASE128.decode("XbKi4AODy0QAkPgXQxbOuQ==") + position + BASE128.decode("FFLE6hGJVf/GeZBdEQzyjw==") + id);
                return true;
            }
        });

        el.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {

                if (groupPosition == 0) {

                    switch (childPosition){

                        case 0:
                            activity.getCuAdapter().getFiles(BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="));
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
                        intent.putExtra(BASE128.decode("TDroNxxo64YIQk6RVasoFQ=="), BASE128.decode("NNSl0ud9+7dr4lvoe5hG8djoN+ETEYt8qFKYIxBPh2DyTAgE7FBIpJI+ofzEuUKm"));
                        context.startActivity(intent);
                    }
                    break;

                    case 3:
                        Intent intent = new Intent();
                        intent.setData(Uri.parse(BASE128.decode("aY44Q9LJUNdkogVvHZRJBkNLTexQFbULdir3+rTVpv2+1p2EHkZJGRel9ugfxvoXJ3/XXju2Ed07x0xnby2x0xnby1vinEMgMQW0Qg9sUpbdbTE49i2RsyUvFVI3UEwJdmkHrHGd8jg6Vn8raHLMMllUvsD1uJc/CoBQ==") + BASE128.decode("Qm88zpcnPOQEtW+mBBrDVgXXn0ERvuF+Y/Wk+aglaZ4XOvOdTdS4PwpACIFL1cJ4")));
                        try {
                            context.startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(context, BASE128.decode("4yDr2t7GpWWTNG2tLbDIPDUeHFTXVOyg2L+Ih12o02q4PzNJSkW0tAjGoJo5pw42"), Toast.LENGTH_SHORT).show();
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

        childDate.put(BASE128.decode("vZ1SApSEcFlgkdMS6G1NLQ=="), menuInfo);

        child.add(childDate);

    }


}
