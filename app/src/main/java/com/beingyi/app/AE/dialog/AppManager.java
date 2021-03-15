package com.beingyi.app.AE.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.adapter.AppAdapter;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.APKUtils;
import com.beingyi.app.AE.utils.AppInfo;
import com.beingyi.app.AE.utils.AppInfoProvider;
import com.beingyi.app.AE.utils.FileUtils;
import com.beingyi.app.AE.utils.InputMethodUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class AppManager extends baseDialog implements View.OnClickListener{
    AlertDialog dialog;
    AppInfoProvider appInfoProvider;
    List<AppInfo> userAppList;
    List<AppInfo> sysAppList;

    SearchView searchView;

    SwipeRefreshLayout swiperereshlayout1;
    SwipeRefreshLayout swiperereshlayout2;
    ProgressBar loading1, loading2;
    ListView applist1, applist2;
    
    LinearLayout ln_bottomLeader;

    TextView tv_left, tv_right;


    AppAdapter appAdapter1;
    AppAdapter appAdapter2;


    boolean isChoosed1 = true;
    boolean isChoosed2 = true;

    boolean scrollFlag1 = false;// 标记是否滑动
    boolean scrollFlag2 = false;// 标记是否滑动

    boolean isFinishLoading1 = false;
    boolean isFinishLoading2 = false;



    public void init(View view){
        loading1 = (ProgressBar)view.findViewById(R.id.view_app_manager_ProgressBar1);
        loading2 = (ProgressBar)view.findViewById(R.id.view_app_manager_ProgressBar2);
        applist1 = (ListView)view.findViewById(R.id.view_app_manager_ListView1);
        applist2 = (ListView)view.findViewById(R.id.view_app_manager_ListView2);
        
        ln_bottomLeader = (LinearLayout)view.findViewById(R.id.view_app_manager_LinearLayout_bottomLeader);

        searchView=view.findViewById(R.id.view_app_manager_SearchView);

        swiperereshlayout1 = (SwipeRefreshLayout)view.findViewById(R.id.view_app_manager_SwipeRefreshLayout1);
        swiperereshlayout2 = (SwipeRefreshLayout)view.findViewById(R.id.view_app_manager_SwipeRefreshLayout2);

        appAdapter1=new AppAdapter(context,applist1);
        appAdapter2=new AppAdapter(context,applist2);

        tv_left = (TextView) view.findViewById(R.id.view_app_manager_TextView_left);
        tv_right = (TextView) view.findViewById(R.id.view_app_manager_TextView_right);


        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);

    }


    public AppManager(Context mContext, int mWindow, String mPath) {
        super(mContext, mWindow, mPath);
        
        appInfoProvider=new AppInfoProvider(context);


        new Thread(){
            @Override
            public void run(){
                userAppList=appInfoProvider.getUserApps();
                handler.sendEmptyMessage(1);
            }
        }.start();

        new Thread(){
            @Override
            public void run(){
                sysAppList=appInfoProvider.getSystemApps();
                handler.sendEmptyMessage(2);
            }
        }.start();


        View view=View.inflate(context, R.layout.view_app_manager,null);
        init(view);

        initAll();

        dialog = new AlertDialog.Builder(context)
                .setTitle("安装包提取")
                .setView(view)
                .setCancelable(true)
                .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        
                    }
                })
                .create();

    }


    public void initAll(){

        swiperereshlayout1.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light);
        swiperereshlayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {

                    new Thread() {
                        @Override
                        public void run() {
                            userAppList = appInfoProvider.getUserApps();
                            handler.sendEmptyMessage(1);
                        }
                    }.start();


                } catch (Exception e) {
                }


            }

        });


        swiperereshlayout2.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light);
        swiperereshlayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {


                    new Thread() {
                        @Override
                        public void run() {
                            sysAppList = appInfoProvider.getSystemApps();
                            handler.sendEmptyMessage(2);
                        }
                    }.start();


                } catch (Exception e) {
                }


            }

        });

        swiperereshlayout1.setEnabled(false);
        swiperereshlayout2.setEnabled(false);

        initlistscroll(applist1,swiperereshlayout1);
        initlistscroll(applist2,swiperereshlayout2);

        applist1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AppInfo appInfo=userAppList.get(position);
                final String path=appInfo.path;

                final AlertProgress progres=new AlertProgress(context);
                new Thread(){
                    @Override
                    public void run()
                    {
                        progres.setLabel("处理中");
                        progres.setNoProgress();
                        progres.show();
                        try
                        {

                            File outFile=new File(activity.getCuPath(),APKUtils.getLabel(context,path)+"_"+APKUtils.getVersionName(context,path)+".apk");
                            FileUtils.copyFile(path,outFile.getAbsolutePath());

                            activity.runOnUiThread(new Runnable(){
                                @Override
                                public void run()
                                {
                                    activity.showToast("提取完成");
                                    activity.drawer.closeDrawers();
                                    dialog.dismiss();
                                    activity.getCuAdapter().refresh();
                                    activity.getCuAdapter().setItemHighLight(outFile.getAbsolutePath());
                                }
                            });

                        }
                        catch (Exception e)
                        {
                            activity.showMessage(context, "错误：", e.toString());
                        }

                        progres.dismiss();
                    }
                }.start();

            }
        });


        applist2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AppInfo appInfo=sysAppList.get(position);
                final String path=appInfo.path;

                final AlertProgress progres=new AlertProgress(context);
                new Thread(){
                    @Override
                    public void run()
                    {
                        progres.setLabel("处理中");
                        progres.setNoProgress();
                        progres.show();
                        try
                        {

                            File outFile=new File(activity.getCuPath(),APKUtils.getLabel(context,path)+"_"+APKUtils.getVersionName(context,path)+".apk");
                            FileUtils.copyFile(path,outFile.getAbsolutePath());

                            activity.runOnUiThread(new Runnable(){
                                @Override
                                public void run()
                                {
                                    activity.showToast("提取完成");
                                    activity.drawer.closeDrawers();
                                    dialog.dismiss();
                                    activity.getCuAdapter().refresh();
                                    activity.getCuAdapter().setItemHighLight(outFile.getAbsolutePath());
                                }
                            });

                        }
                        catch (Exception e)
                        {
                            activity.showMessage(context, "错误：", e.toString());
                        }

                        progres.dismiss();
                    }
                }.start();

            }
        });

        searchView.onActionViewExpanded();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            //当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                appAdapter1.filterItem(newText);
                appAdapter2.filterItem(newText);

                return false;
            }
        });

    }



    /**设置SearchView下划线透明**/
    private void setUnderLinetransparent(SearchView searchView){
        try {
            Class<?> argClass = searchView.getClass();
            // mSearchPlate是SearchView父布局的名字
            Field ownField = argClass.getDeclaredField("mSearchPlate");
            ownField.setAccessible(true);
            View mView = (View) ownField.get(searchView);
            mView.setBackgroundColor(Color.TRANSPARENT);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public void setWindow(int value){
        this.window=value;
        this.adapter=activity.adapters.get(value);
        this.Path=utils.getCuPath(value);
    }

    public void showDialog(){
        dialog.show();
    }


    public void closeDialog(){
        dialog.dismiss();
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    appAdapter1.setData(userAppList);
                    applist1.setAdapter(appAdapter1);
                    loading1.setVisibility(View.GONE);
                    appAdapter1.notifyDataSetChanged();
                    break;

                case 2:
                    appAdapter2.setData(sysAppList);
                    applist2.setAdapter(appAdapter2);
                    loading2.setVisibility(View.GONE);
                    appAdapter2.notifyDataSetChanged();
                    break;
            }
        }
    };



    public void initlistscroll(final ListView listview, final SwipeRefreshLayout swiperereshlayout) {


        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            int lvIndex1 = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                        scrollFlag1 = false;

                        break;

                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
                        scrollFlag1 = true;


                        lvIndex1 = view.getLastVisiblePosition();


                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
                        scrollFlag1 = false;


                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                //记录滚动停止后 记录当前item的位置
                int scrolled = view.getLastVisiblePosition();
                //滚动后下标大于滚动前 向下滚动了
                if (scrolled > lvIndex1) {
                    ln_bottomLeader.setVisibility(View.GONE);
                }
                //向上滚动了
                else {
                    ln_bottomLeader.setVisibility(View.VISIBLE);

                }


                // 判断滚动到底部
                if (listview.getLastVisiblePosition() == (listview.getCount() - 1)) {
                    ln_bottomLeader.setVisibility(View.GONE);

                }


                // 判断滚动到顶部
                if (listview.getFirstVisiblePosition() == 0) {
                    ln_bottomLeader.setVisibility(View.VISIBLE);

                    if (isFinishLoading1 && !isChoosed1) {
                        swiperereshlayout1.setEnabled(true);
                    } else {
                        swiperereshlayout1.setEnabled(false);
                    }

                    if (isFinishLoading2 && !isChoosed2) {
                        swiperereshlayout2.setEnabled(true);
                    } else {
                        swiperereshlayout2.setEnabled(false);
                    }


                } else {
                    swiperereshlayout.setEnabled(false);
                }


            }
        });


    }

    String state = "";
    @Override
    public void onClick(View view) {
        FrameLayout frame1 = (FrameLayout) applist1.getParent();
        FrameLayout frame2 = (FrameLayout) applist2.getParent();

        int i = view.getId();
        if (i == R.id.view_app_manager_TextView_left) {
            if (state.equals("left")) {
                frame1.setVisibility(View.VISIBLE);
                frame2.setVisibility(View.VISIBLE);
                swiperereshlayout1.setVisibility(View.VISIBLE);
                swiperereshlayout2.setVisibility(View.VISIBLE);
                tv_left.setBackgroundColor(Color.TRANSPARENT);
                tv_right.setBackgroundColor(Color.TRANSPARENT);
                state = "";
            } else {
                tv_left.setBackgroundColor(Color.GRAY);
                tv_right.setBackgroundColor(Color.TRANSPARENT);
                frame1.setVisibility(View.VISIBLE);
                swiperereshlayout1.setVisibility(View.VISIBLE);
                frame2.setVisibility(View.GONE);
                swiperereshlayout2.setVisibility(View.GONE);
                state = "left";
            }


        } else if (i == R.id.view_app_manager_TextView_right) {
            if (state.equals("right")) {
                frame1.setVisibility(View.VISIBLE);
                frame2.setVisibility(View.VISIBLE);
                swiperereshlayout1.setVisibility(View.VISIBLE);
                swiperereshlayout2.setVisibility(View.VISIBLE);
                tv_left.setBackgroundColor(Color.TRANSPARENT);
                tv_right.setBackgroundColor(Color.TRANSPARENT);
                state = "";
            } else {

                tv_left.setBackgroundColor(Color.TRANSPARENT);
                tv_right.setBackgroundColor(Color.GRAY);

                frame1.setVisibility(View.GONE);
                swiperereshlayout1.setVisibility(View.GONE);
                frame2.setVisibility(View.VISIBLE);
                swiperereshlayout2.setVisibility(View.VISIBLE);
                state = "right";
            }


        } else {
        }

    }
    
    
}
