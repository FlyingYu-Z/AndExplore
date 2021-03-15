package com.beingyi.app.AE.fragment;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.activity.DexEditor;
import com.beingyi.app.AE.base.BaseFragment;
import com.beingyi.app.AE.utils.ClassTree;

public class FdexBase extends BaseFragment {


    public Context context;
    public DexEditor activity;
    public ClassTree classTree;
    public FdexSearch fdexSearch;

    public TextView tv_size;
    public ListView listView;

    LinearLayout ln_finish;
    LinearLayout ln_loading;
    ProgressBar progressBar;


    public boolean isPrepared;
    public boolean mHasLoadedOnce;

    public boolean isRequested = false;


    public void initView() {
        this.context = getActivity();
        this.activity = (DexEditor) context;
        this.classTree = activity.classTree;
        this.fdexSearch = activity.fdexSearch;

        tv_size = find(R.id.fragment_dex_search_result_TextView_size);
        listView = find(R.id.fragment_dex_search_result_ListView);

        ln_finish = find(R.id.fragment_dex_search_result_LinearLayout_finish);
        ln_loading = find(R.id.fragment_dex_search_result_LinearLayout_loading);

        progressBar=find(R.id.fragment_dex_search_result_ProgressBar);
    }


    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        //填充各控件的数据
        mHasLoadedOnce = true;
    }


    public void setFinish() {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ln_finish.setVisibility(View.VISIBLE);
                ln_loading.setVisibility(View.GONE);
            }
        });
    }

    public void setLoading() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ln_finish.setVisibility(View.GONE);
                ln_loading.setVisibility(View.VISIBLE);
            }
        });
    }


    public void setProgress(int progress,int total) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setMax(total);
                progressBar.setProgress(progress);
            }
        });
    }



}
