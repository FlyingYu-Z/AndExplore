package com.beingyi.apkfairy.activity;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.graphics.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.view.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import android.widget.AbsListView.*;
import android.widget.CompoundButton.*;

import com.beingyi.apkfairy.R;
import com.beingyi.apkfairy.adapter.*;
import com.beingyi.apkfairy.application.*;
import com.beingyi.apkfairy.base.*;
import com.beingyi.apkfairy.bean.Person;
import com.beingyi.apkfairy.dialog.*;
import com.beingyi.apkfairy.ui.*;
import com.beingyi.apkfairy.update.*;
import com.beingyi.apkfairy.utils.*;

import java.util.*;

import org.xutils.*;
import org.xutils.common.util.*;
import org.xutils.ex.*;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View.OnTouchListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class MainActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {


    Context context;
    ComUtils comUtils;

    SwipeRefreshLayout swiperereshlayout1;
    SwipeRefreshLayout swiperereshlayout2;
    ProgressBar loading1, loading2;
    ListView applist1, applist2;

    List<AppInfo> appInfo1, appInfo2;
    MyAdapter1 myAdapter1;
    MyAdapter2 myAdapter2;
    AppInfoProvider provider;

    LinearLayout ln_haschoice;
    LinearLayout ln_bottomLeader;


    boolean isChoosed1 = false;
    boolean isChoosed2 = false;
    String currentWindow = "左窗口";//当前窗口

    TextView tv_info;


    boolean scrollFlag1 = false;// 标记是否滑动
    boolean scrollFlag2 = false;// 标记是否滑动

    boolean isFinishLoading1 = false;
    boolean isFinishLoading2 = false;

    TextView tv_left, tv_right;


    ProgressDialog progress;


    MyDrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    SwitchCompat sw_nightmode;
    Toolbar toolbar;




    public void initview() {

        comUtils = new ComUtils(context);

        progress = new ProgressDialog(context);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);


        loading1 = (ProgressBar) findViewById(R.id.activitymainProgressBar1);
        loading2 = (ProgressBar) findViewById(R.id.activitymainProgressBar2);
        applist1 = (ListView) findViewById(R.id.activitymainListView1);
        applist2 = (ListView) findViewById(R.id.activitymainListView2);


        ln_haschoice = (LinearLayout) findViewById(R.id.activitymainLinearLayout_haschoice);
        ln_bottomLeader = (LinearLayout) findViewById(R.id.activitymainLinearLayout_bottomLeader);

        tv_info = (TextView) findViewById(R.id.activitymainTextView_info);

        swiperereshlayout1 = (SwipeRefreshLayout) findViewById(R.id.contentmainSwipeRefreshLayout1);
        swiperereshlayout2 = (SwipeRefreshLayout) findViewById(R.id.contentmainSwipeRefreshLayout2);


        tv_left = (TextView) findViewById(R.id.activitymainTextView_left);
        tv_right = (TextView) findViewById(R.id.activitymainTextView_right);


        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);


    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    applist1.setAdapter(myAdapter1);
                    loading1.setVisibility(View.GONE);

                    break;

                case 2:
                    applist2.setAdapter(myAdapter2);
                    loading2.setVisibility(View.GONE);


                    break;


                case 3:

                    progress.show();


                    break;


                case 4:
                    progress.dismiss();
                    clearChoosed();
                    myAdapter1.notifyDataSetChanged();
                    myAdapter2.notifyDataSetChanged();

                    AlertDialog dialog = new AlertDialog.Builder(context)

                            .setTitle("备份成功")
                            .setMessage("你的应用已备份于" + comUtils.getBackPath() + "目录下")

                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            }).create();
                    dialog.show();


                    break;


                case 5:

                    String str;
                    try {
                        str = msg.obj.toString() + "";
                        progress.setTitle("正在备份，请稍后…");
                        progress.setMessage(str);
                    } catch (Exception e) {
                        str = e.toString();
                    }


                    break;


                case 6:
                    progress.dismiss();
                    new AlertDialog.Builder(context)

                            .setTitle("备份失败")
                            .setMessage(msg.obj.toString())

                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            }).create().show();


                    break;


                case 7:
                    myAdapter1.notifyDataSetChanged();
                    myAdapter2.notifyDataSetChanged();
                    break;


                case 8:
                    myAdapter1.setData(appInfo1);
                    myAdapter1.notifyDataSetChanged();
                    swiperereshlayout1.setRefreshing(false);
                    break;

                case 9:
                    myAdapter2.setData(appInfo2);
                    myAdapter2.notifyDataSetChanged();
                    swiperereshlayout2.setRefreshing(false);
                    break;


                case 10:
                    new alert(context, msg.obj.toString());
                    break;

            }
        }
    };


    public void initlistscroll(final ListView listview, final SwipeRefreshLayout swiperereshlayout) {


        listview.setOnScrollListener(new OnScrollListener() {
            int lvIndex1 = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                switch (scrollState) {
                    // 当不滚动时
                    case OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                        scrollFlag1 = false;

                        break;

                    case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
                        scrollFlag1 = true;


                        lvIndex1 = view.getLastVisiblePosition();


                        break;
                    case OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
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


    boolean isNight = false;

    /**
     * // 切换主题
     * private void changeThemeWithColorful() {
     * if (!isNight) {
     * mColorful.setTheme(R.style.DayTheme);
     * } else {
     * mColorful.setTheme(R.style.NightTheme);
     * }
     * isNight = !isNight;
     * }
     **/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = MainActivity.this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_appmanager);


        initview();


        provider = new AppInfoProvider(context);
        myAdapter1 = new MyAdapter1(context, applist1);
        myAdapter2 = new MyAdapter2(context, applist2);

        initData();


        ln_haschoice.setVisibility(View.GONE);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (MyDrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        //设置打开按钮是否显示
        //toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        sw_nightmode = (SwitchCompat) headerView.findViewById(R.id.navigationheaderSwitchCompat_opennightmode);

        sw_nightmode.setChecked(SPUtils.getBoolean(context, "conf", "isNightMode"));
        sw_nightmode.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton p1, boolean checked) {
                if (checked) {
                    SPUtils.putBoolean(context, "conf", "isNightMode", true);
                    new Conf(context).setAppColor("#333333");

                    recreate();
                } else {
                    SPUtils.putBoolean(context, "conf", "isNightMode", false);
                    new Conf(context).setAppColor("#29B188");

                    recreate();
                }
            }

        });


        /**
         ListView  mNewsListView = (ListView) findViewById(R.id.listview);

         // 为 ListView 设置要修改的属性,在这里没有对 ListView 本身的属性做修改
         ViewGroupSetter listViewSetter = new ViewGroupSetter(mNewsListView, 0);
         // 绑定 ListView 的 Item View 中的 news_title 视图，在换肤时修改它的 text_color 属性
         listViewSetter.childViewTextColor(R.id.news_title, R.attr.text_color);


         // 构建 Colorful 对象
         Colorful mColorful = new Colorful.Builder(this)
         .backgroundDrawable(R.id.root_view, R.attr.root_view_bg) // 设置 view 的背景图片
         .backgroundColor(R.id.change_btn, R.attr.btn_bg) // 设置按钮的背景色
         .textColor(R.id.textview, R.attr.text_color) // 设置文本颜色
         .setter(listViewSetter)           // 手动设置 setter
         .create();

         **/


        swiperereshlayout1.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light);
        swiperereshlayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {

                    new Thread() {
                        @Override
                        public void run() {
                            appInfo1 = provider.getUserApps();
                            handler.sendEmptyMessage(8);
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
                            appInfo2 = provider.getSystemApps();
                            handler.sendEmptyMessage(9);
                        }
                    }.start();


                } catch (Exception e) {
                }


            }

        });

        swiperereshlayout1.setEnabled(false);
        swiperereshlayout2.setEnabled(false);


        initlistscroll(applist1, swiperereshlayout1);
        initlistscroll(applist2, swiperereshlayout2);


        applist1.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View p1, MotionEvent p2) {


                currentWindow = "左窗口";

                if (isChoosed1) {
                    tv_info.setText("已选:" + applist1.getCheckedItemCount() + "(" + currentWindow + ")");

                    ln_haschoice.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.GONE);

                } else {
                    ln_haschoice.setVisibility(View.GONE);
                    toolbar.setVisibility(View.VISIBLE);
                }

                return false;

            }

        });


        applist2.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View p1, MotionEvent p2) {


                currentWindow = "右窗口";

                if (isChoosed2) {
                    tv_info.setText("已选:" + applist2.getCheckedItemCount() + "(" + currentWindow + ")");

                    ln_haschoice.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.GONE);

                } else {
                    ln_haschoice.setVisibility(View.GONE);
                    toolbar.setVisibility(View.VISIBLE);
                }

                return false;

            }

        });


        applist1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, final int position, long id) {
                // TODO Auto-generated method stub
                AppInfo appInfo = (AppInfo) applist1.getItemAtPosition(position);


                if (isChoosed1) {
                    tv_info.setText("已选:" + applist1.getCheckedItemCount() + "(" + currentWindow + ")");
                    if (applist1.getCheckedItemCount() == 0) {
                        clearChoosed();
                    }
                    myAdapter1.notifyDataSetChanged();
                } else {

                    final AppInfo appinfo = myAdapter1.getData().get(position);

                    new AppClick(context, appInfo1, appinfo, handler, MainActivity.this);


                }


            }


        });


        applist2.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, final int position, long id) {
                // TODO Auto-generated method stub
                AppInfo appInfo = (AppInfo) applist2.getItemAtPosition(position);


                if (isChoosed2) {
                    tv_info.setText("已选:" + applist2.getCheckedItemCount() + "(" + currentWindow + ")");
                    if (applist2.getCheckedItemCount() == 0) {
                        clearChoosed();
                    }
                    myAdapter2.notifyDataSetChanged();
                } else {


                    final AppInfo appinfo = myAdapter2.getData().get(position);

                    new AppClick(context, appInfo2, appinfo, handler, MainActivity.this);


                }


            }


        });


        applist1.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> p1, View view, int position, long id) {

                applist1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                applist1.setClickable(false);
                ln_haschoice.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.GONE);
                isChoosed1 = true;


                myAdapter1.notifyDataSetChanged();

                return false;

            }


        });


        applist2.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> p1, View view, int position, long id) {

                applist2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                applist2.setClickable(false);
                ln_haschoice.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.GONE);
                isChoosed2 = true;

                myAdapter2.notifyDataSetChanged();


                return false;
            }


        });


        new CheckUpdate(context);


        new init(context);


        if (!SPUtils.getBoolean(context, "conf", "notTIP")) {
            AlertDialog dialog = new AlertDialog.Builder(context)

                    .setTitle("温馨提示")
                    .setMessage("长按进行多选！")


                    .setNeutralButton("不再提醒", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            SPUtils.putBoolean(context, "conf", "notTIP", true);

                        }
                    })

                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    }).create();
            dialog.show();

        }


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            StartActivity(setting.class);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_backuped) {
            StartActivity(backuped.class);

        } else if (id == R.id.nav_dostr) {
            StartActivity(backuped.class);

        } else if (id == R.id.nav_share) {
        } else {
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void initData() {


        new Thread() {
            @Override
            public void run() {


                try {

                    appInfo1 = provider.getUserApps();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            myAdapter1.setData(appInfo1);
                            myAdapter1.notifyDataSetChanged();
                            isFinishLoading1 = true;

                        }
                    });


                } catch (Exception e) {


                }

                handler.sendEmptyMessage(1);//加载结束


            }


        }.start();


        new Thread() {
            @Override
            public void run() {


                try {

                    appInfo2 = provider.getSystemApps();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            myAdapter2.setData(appInfo2);
                            myAdapter2.notifyDataSetChanged();
                            isFinishLoading2 = true;

                        }
                    });


                } catch (Exception e) {


                }

                handler.sendEmptyMessage(2);//加载结束


            }


        }.start();


        new Thread() {
            @Override
            public void run() {


                if (comUtils.getAppSign(MainActivity.this.getPackageName()) != -77861130) {
                    System.exit(0);
                }


                try {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("UPID", AES.encode(DeviceInfo.getUniquePsuedoID()));
                    params.put("AppName", AES.encode("APK精灵"));

                    String strUrlPath = Conf.getServer(context) + "post/addUserNum.php";
                    String Result = HttpUtils.submitPostData(strUrlPath, params, "utf-8");


                } catch (Exception e) {
                }


            }
        }.start();


    }


    public void onclick_enter_setting(View voliew) {

        Intent intent = new Intent(context, setting.class);
        startActivity(intent);

    }


    public void onclick_cancel(View view) {


        clearChoosed();


    }


    public void clearChoosed() {


        if (currentWindow.equals("左窗口")) {
            isChoosed1 = false;
            applist1.clearChoices();
            applist1.setChoiceMode(ListView.CHOICE_MODE_NONE);
            myAdapter1.notifyDataSetChanged();
            ln_haschoice.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
        }


        if (currentWindow.equals("右窗口")) {
            isChoosed2 = false;
            applist2.clearChoices();
            applist2.setChoiceMode(ListView.CHOICE_MODE_NONE);
            myAdapter2.notifyDataSetChanged();
            ln_haschoice.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
        }


    }


    List<AppInfo> choosed_appInfos = new ArrayList<AppInfo>();
    String backpath = "";

    int backupType = 0;


    //备份
    public void onclick_backup(View view) {


        LayoutInflater inflater = LayoutInflater.from(context);
        final View inflaterView = inflater.inflate(R.layout.view_backup_choose, null);
        RadioGroup radioGroup = (RadioGroup) inflaterView.findViewById(R.id.viewbackupchooseRadioGroup1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radio, int id) {

                if (id == R.id.viewbackupchooseRadioButton1) {
                    backupType = 1;//数据+应用

                } else if (id == R.id.viewbackupchooseRadioButton2) {
                    backupType = 2;//应用

                } else if (id == R.id.viewbackupchooseRadioButton3) {
                    backupType = 3;//数据

                }

            }

        });
        ((RadioButton) inflaterView.findViewById(R.id.viewbackupchooseRadioButton1)).setChecked(true);
        choosed_appInfos.clear();

        if (currentWindow.equals("左窗口")) {
            choosed_appInfos = myAdapter1.getChoosedData();
        } else {
            ((RadioButton) inflaterView.findViewById(R.id.viewbackupchooseRadioButton1)).setVisibility(View.GONE);
            ((RadioButton) inflaterView.findViewById(R.id.viewbackupchooseRadioButton2)).setChecked(true);
            choosed_appInfos = myAdapter2.getChoosedData();
        }

        AlertDialog dialog = new AlertDialog.Builder(context)

                .setTitle(currentWindow + "备份选项")
                .setView(inflaterView)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("开始备份", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        backpath = comUtils.getBackPath();

                        if (backupType == 1 || backupType == 3) {
                            if (!Shell.haveRoot()) {
                                new alert(context, "你还没有获取ROOT");
                                return;
                            }
                        } else {

                            handler.sendEmptyMessage(3);
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < choosed_appInfos.size(); i++) {
                                        startBackup(choosed_appInfos.get(i));
                                    }
                                    handler.sendEmptyMessage(4);
                                }

                            }.start();

                        }


                    }
                }).create();
        dialog.show();
    }


    public void startBackup(AppInfo appinfo) {

        if (backupType == 1) {//应用+数据

        }


        if (backupType == 2) {//应用
            Message msg = new Message();
            msg.obj = appinfo.appName;
            msg.what = 5;
            handler.sendMessage(msg);

            try {
                FileUtil.copy(appinfo.path, backpath + appinfo.appName + "_" + appinfo.versionName + ".apk");
                updateIsBacked(appinfo, true);

            } catch (Exception e) {
                msg.obj = e.toString();
                msg.what = 6;
                handler.sendMessage(msg);
            }

            try {
                MyApplication application = new MyApplication();
                DbManager db = x.getDb(application.getDaoConfig());
                Person person = new Person(appinfo.appName);
                person.setPackName(appinfo.packageName);
                person.setPath(backpath + appinfo.appName + "_" + appinfo.versionName + ".apk");
                person.setBackupType("2");
                db.save(person);

            } catch (DbException e) {

                msg = new Message();
                msg.obj = e.toString();
                msg.what = 10;
                handler.sendMessage(msg);

            }

        }

        if (backupType == 3) {//数据
            Message msg = new Message();
            msg.obj = appinfo.appName;
            msg.what = 5;
            handler.sendMessage(msg);

            try {
                String destFile = backpath + appinfo.packageName + ".zip";

                FileUtil.copy(appinfo.dataPath, destFile);
                updateIsBacked(appinfo, true);

            } catch (Exception e) {
                msg.obj = e.toString();
                msg.what = 6;
                handler.sendMessage(msg);
            }
        }


    }


    public void updateIsBacked(AppInfo appinfo, boolean isbacked) {
        if (currentWindow.equals("左窗口")) {
            int i = appInfo1.indexOf(appinfo);
            appInfo1.get(i).isBacked = isbacked;
        } else {
            int i = appInfo2.indexOf(appinfo);
            appInfo2.get(i).isBacked = isbacked;
        }


    }


    String state = "";


    @Override
    public void onClick(View p1) {
        FrameLayout frame1 = (FrameLayout) applist1.getParent();

        FrameLayout frame2 = (FrameLayout) applist2.getParent();


        int i = p1.getId();
        if (i == R.id.activitymainTextView_left) {
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


        } else if (i == R.id.activitymainTextView_right) {
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

            drawer.closeDrawer(GravityCompat.START);

            finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
