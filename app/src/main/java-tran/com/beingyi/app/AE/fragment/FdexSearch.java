package com.beingyi.app.AE.fragment;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.activity.DexEditor;
import com.beingyi.app.AE.adapter.SectionsPagerAdapter;
import com.beingyi.app.AE.base.BaseFragment;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.ClassTree;

import org.jf.dexlib2.iface.ClassDef;

import java.util.ArrayList;
import java.util.List;

public class FdexSearch extends BaseFragment implements ViewPager.OnPageChangeListener {

    Context context;
    DexEditor activity;
    ClassTree classTree;


    EditText ed_content;
    Button btn_search;

    TabLayout tabLayout;
    ViewPager viewPager;

    FdexSearchClassResult fdexSearchClassResult;
    FdexSearchMethodResult fdexSearchMethodResult;
    FdexSearchCodeResult fdexSearchCodeResult;
    FdexSearchStringResult fdexSearchStringResult;

    private ArrayList<Fragment> fragments;

    private boolean isPrepared;
    private boolean mHasLoadedOnce;

    boolean isRequested = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            // 需要inflate一个布局文件 填充Fragment
            mView = inflater.inflate(R.layout.fragment_dex_search, container, false);
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

        initTab();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getKeyWord().isEmpty()){
                    ToastUtils.show(MyApplication.getInstance().getString("716daa56cf40a9b52201975cc1f0dc87"));
                    return;
                }

                int position=viewPager.getCurrentItem();

                if(position==0){
                    fdexSearchClassResult.searchClass(getKeyWord());
                }

                if(position==1){
                    fdexSearchMethodResult.searchMethod(getKeyWord());
                }

                if(position==2){
                    fdexSearchCodeResult.searchCode(getKeyWord());
                }


                if(position==3){
                    fdexSearchStringResult.searchString(getKeyWord());
                }



            }
        });


        initViewPager();


        return mView;
    }

    private void initView() {
        this.context = getActivity();
        this.activity = (DexEditor) context;
        this.classTree = activity.classTree;


        ed_content=find(R.id.fragment_dex_search_EditText_content);
        btn_search=find(R.id.fragment_dex_search_Button_search);

        tabLayout = find(R.id.fragment_dex_search_TabLayout);
        viewPager = find(R.id.fragment_dex_search_ViewPager);


    }



    private void initViewPager() {

        fragments = new ArrayList<>();

        fdexSearchClassResult=new FdexSearchClassResult();
        fdexSearchMethodResult=new FdexSearchMethodResult();
        fdexSearchCodeResult=new FdexSearchCodeResult();
        fdexSearchStringResult=new FdexSearchStringResult();

        fragments.add(fdexSearchClassResult);
        fragments.add(fdexSearchMethodResult);
        fragments.add(fdexSearchCodeResult);
        fragments.add(fdexSearchStringResult);


        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(5);

        SectionsPagerAdapter mainAdapter = new SectionsPagerAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(mainAdapter);
    }


    private void initTab() {


        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabTextColors(ContextCompat.getColor(context, R.color.gray), ContextCompat.getColor(context, R.color.colorPrimary));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(context, R.color.colorPrimary));


        final TabLayout.Tab tab1 = tabLayout.newTab().setText(MyApplication.getInstance().getString("fad060bd9a8bfac6c2e55c66dae11cf6"));
        final TabLayout.Tab tab2 = tabLayout.newTab().setText(MyApplication.getInstance().getString("ea340b9dda8b893ddf2d9176220aac32"));
        final TabLayout.Tab tab3 = tabLayout.newTab().setText(MyApplication.getInstance().getString("06e004ef21414c06d3e4ff2cefaf6a04"));
        final TabLayout.Tab tab4 = tabLayout.newTab().setText(MyApplication.getInstance().getString("cc4dd1da7e1a754534215f02fb9ba85d"));

        tabLayout.addTab(tab1);
        tabLayout.addTab(tab2);
        tabLayout.addTab(tab3);
        tabLayout.addTab(tab4);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                try {
                    viewPager.setCurrentItem(position);
                } catch (Exception e) {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                if(position==0){
                    if (!fdexSearchClassResult.isRequested)
                    fdexSearchClassResult.searchClass(getKeyWord());
                }

                if(position==1){
                    if (!fdexSearchMethodResult.isRequested)
                    fdexSearchMethodResult.searchMethod(getKeyWord());
                }

                if(position==2){
                    if (!fdexSearchCodeResult.isRequested)
                        fdexSearchCodeResult.searchCode(getKeyWord());
                }

                if(position==3){
                    if (!fdexSearchStringResult.isRequested)
                        fdexSearchStringResult.searchString(getKeyWord());
                }


            }
        });


    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        tabLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                tabLayout.getTabAt(position).select();
            }
        }, 100);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
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


    public String getKeyWord(){

        return ed_content.getText().toString();
    }
    

}
