package com.beingyi.app.AE.fragment;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.activity.DexEditor;
import com.beingyi.app.AE.adapter.ClassAdapter;
import com.beingyi.app.AE.base.BaseFragment;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.utils.ClassTree;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fclasslist extends BaseFragment {

    Context context;
    DexEditor activity;
    ClassTree classTree;


    SwipeRefreshLayout swipeRefreshLayout;
    ListView listview;

    ClassAdapter adapter;
    List<String> classList = new ArrayList<>();


    private boolean isPrepared;
    private boolean mHasLoadedOnce;

    boolean isRequested = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            // 需要inflate一个布局文件 填充Fragment
            mView = inflater.inflate(R.layout.fragment_classlist, container, false);
            initView();
            isPrepared = true;
            //实现懒加载
            lazyLoad();
        }
        //缓存的mView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个mView已经有parent的错误。
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        parseDEX();

        return mView;
    }

    private void initView() {
        this.context = getActivity();
        this.activity = (DexEditor) context;
        this.classTree = activity.classTree;


        swipeRefreshLayout = find(R.id.fragment_classlist_SwipeRefreshLayout);
        listview = find(R.id.fragment_classlist_ListView);
    }


    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        //填充各控件的数据
        mHasLoadedOnce = true;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isRequested) {

        }

    }


    public void parseDEX() {

        classList = classTree.getList(BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="));

        isRequested = true;


        adapter = new ClassAdapter(context, classTree, listview, classList, activity.toolbar);
        listview.setAdapter(adapter);
        activity.setSupportActionBar(activity.toolbar);


        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });



    }


    public void goBack() {
        if (!classTree.tree.getCurPath().equals(BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="))) {
            adapter.goParent();
        } else {
            activity.exit();
        }

    }


}
