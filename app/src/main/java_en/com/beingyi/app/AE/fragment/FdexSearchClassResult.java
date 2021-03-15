package com.beingyi.app.AE.fragment;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.activity.TextEditor;
import com.beingyi.app.AE.adapter.SearchClassAdapter;
import com.beingyi.app.AE.base.BaseFragment;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.ClassTree;

import org.jf.dexlib2.iface.ClassDef;

import java.util.ArrayList;
import java.util.List;

public class FdexSearchClassResult extends FdexBase {


    List<ClassDef> classDefList=new ArrayList<>();

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

                String smali = classTree.getSmali(classDefList.get(positiion));
                byte[] b = smali.getBytes();
                TextEditor textEditor = new TextEditor();
                TextEditor.classTree = classTree;
                Intent intent = new Intent(context, textEditor.getClass());
                intent.putExtra(BASE128.decode("/h+2Zkr8crYDBzmTRWy5Cg=="), false);
                intent.putExtra(BASE128.decode("5LauhhqS4nOX+qM2+Dz8FQ=="), b);
                intent.putExtra(BASE128.decode("9lEtzJiab3u60p/FgJuLvw=="), true);
                context.startActivity(intent);

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



    public void searchClass(String content){


        if(content.isEmpty()){
            return;
        }

        new Thread() {
            @Override
            public void run() {

                setLoading();
                isRequested=true;

                classDefList=classTree.searchClass(content, new ClassTree.SearchProgress() {
                    @Override
                    public void onProgress(int progress, int total) {
                        setProgress(progress, total);
                    }
                });
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_size.setText(BASE128.decode("gEsJU34MLBlwjSqjIL6Zrw==")+classDefList.size()+BASE128.decode("gvyEHWW8jqsWpfAINVFkJw=="));
                        setData(classDefList);
                    }
                });

                setFinish();

            }
        }.start();



    }






    public void setData(List<ClassDef> classDefList){

        SearchClassAdapter adapter=new SearchClassAdapter(context,classDefList,fdexSearch);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }



}
