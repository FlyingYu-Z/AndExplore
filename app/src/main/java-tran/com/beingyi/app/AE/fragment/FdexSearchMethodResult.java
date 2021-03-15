package com.beingyi.app.AE.fragment;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.activity.DexEditor;
import com.beingyi.app.AE.activity.TextEditor;
import com.beingyi.app.AE.adapter.ClassAdapter;
import com.beingyi.app.AE.adapter.SearchClassAdapter;
import com.beingyi.app.AE.adapter.SearchMethodAdapter;
import com.beingyi.app.AE.base.BaseFragment;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.utils.ClassTree;

import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Method;

import java.util.ArrayList;
import java.util.List;

public class FdexSearchMethodResult extends FdexBase {

    List<Method> methodList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            // 需要inflate一个布局文件 填充Fragment
            mView = inflater.inflate(R.layout.fragment_dex_search_result, container, false);
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




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int positiion, long id) {



            }
        });




        return mView;
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






    public void searchMethod(String content){

        if(content.isEmpty()){
            return;
        }


        new Thread() {
            @Override
            public void run() {
                setLoading();
                isRequested=true;

                methodList=classTree.searchMethod(content, new ClassTree.SearchProgress() {
                    @Override
                    public void onProgress(int progress, int total) {
                        setProgress(progress, total);
                    }
                });

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        tv_size.setText(MyApplication.getInstance().getString("d6cf5cd8d246ac7e04bac2710a9a2b05")+methodList.size()+MyApplication.getInstance().getString("9fcb4399907bc5ee774f80da6d1bf8f3"));
                        setData(methodList);
                    }
                });

                setFinish();

            }
        }.start();



    }





    public void setData(List<Method> methodList){

        SearchMethodAdapter adapter=new SearchMethodAdapter(context,methodList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }



}
