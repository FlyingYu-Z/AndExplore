package com.beingyi.app.AE.activity;

import android.*;
import android.app.*;
import android.content.*;
import android.content.res.Resources;
import android.net.Uri;
import android.os.*;
import android.support.annotation.*;
import android.support.design.widget.*;
import android.support.v4.content.*;
import android.support.v4.view.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.util.DisplayMetrics;
import android.view.*;
import android.view.View.*;
import android.widget.*;

import com.beingyi.app.AE.adapter.*;
import com.beingyi.app.AE.base.*;
import com.beingyi.app.AE.dialog.*;
import com.beingyi.app.AE.ui.*;
import com.beingyi.app.AE.update.CheckUpdate;
import com.beingyi.app.AE.utils.*;

import java.io.*;
import java.util.*;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;

import com.beingyi.app.AE.R;
import com.google.common.collect.Lists;

import android.graphics.Color;



public class MainActivity extends BaseActivity {

    Context context;
    MainActivity activity;
    AEUtils utils;
    public FileClip fileClip;
    ProgressDialog progress;
    public Toolbar toolbar;
    TextView tv_CurPath;
    TextView tv_short;

    public DrawerLayout drawer;

    public AppManager appManager;

    public SwipeRefreshLayout swipeRefreshLayout1;
    public ListView lv_files1;
    Menu mMenu;

    public Toolbar toolbar_menu;

    LinearLayout ln_parent;

    public int window = 0;
    public List<FilesAdapter> adapters;


    public File CurrentFile;
    public ShellUtils shell;

    public static final int ShowProgress = 1;
    public static final int DismissProgress = 2;
    public static final int ShowToast = 3;
    public static final int ShowMsg = 4;
    public static final int Show = 5;



    public static final int openFile = 1000;
    public static final int updateFile = 1001;


    private final static String EMPTY = "";
    public final static String ENTRYPATH = "ZipEntry";
    public final static String SELECTEDMOD = "selected_mod";
    private final static String TAG = "FileBrowser";



    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ShowProgress:
                    progress.show();
                    break;

                case DismissProgress:
                    progress.dismiss();
                    break;

                case ShowToast:
                    ToastUtils.show(msg.obj.toString());
                    break;

                case ShowMsg:
                    showMessage(context, "", msg.obj.toString());
                    break;

            }

        }
    };


    public void init() {
        context = this;
        activity = this;
        utils = new AEUtils(context);
        fileClip = new FileClip(context);
        utils.init();

        shell=new ShellUtils();


        progress = new ProgressDialog(context);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("加载中");

        toolbar = (Toolbar) findViewById(R.id.app_bar_main_toolbar);
        toolbar.setTitle("");
        toolbar.setTitleTextAppearance(context, R.style.Toolbar_TitleText);
        toolbar.setSubtitleTextAppearance(context, R.style.Toolbar_SubTitleText);
        toolbar.setSubtitle("");
        toolbar.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                new goDir(context,window,utils.getCuPath(window));
                return false;
            }
        });

        toolbar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new goDir(context,window,utils.getCuPath(window));
            }
        });


        setSupportActionBar(toolbar);
        ln_parent = find(R.id.content_main_LinearLayout_parent);
        tv_CurPath = find(R.id.app_bar_main_TextView_CurPath);
        tv_short = find(R.id.app_bar_main_TextView_short);
        swipeRefreshLayout1 = find(R.id.content_main_SwipeRefreshLayout1);
        lv_files1 = find(R.id.content_main_ListView1);
        toolbar_menu = find(R.id.content_main_toolbar_menu);

        adapters = new ArrayList<FilesAdapter>();
        adapters.add(new FilesAdapter(context, lv_files1, 0));

    }


    public void initPermissions() {

        String[] permissions = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };
        PermissionsUtils.showSystemSetting = true;//是否支持显示系统设置权限设置窗口跳转
        //这里的this不是上下文，是Activity对象！
        PermissionsUtils.getInstance().chekPermissions(activity, permissions, permissionsResult);


    }


    //创建监听权限的接口对象
    PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
        @Override
        public void passPermissons() {
            window = 0;
            refreshList();


            new Thread() {
                @Override
                public void run() {

                    FileUtils.delFolder(context.getCacheDir() + "/cache");

                }
            }.start();

        }

        @Override
        public void forbitPermissons() {
            //finish();
            ToastUtils.show("你还没有授予权限！");
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCommunity, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCommunity, permissions, grantResults);
        //就多一个参数this
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCommunity, permissions, grantResults);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initPermissions();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        /**
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.inflateHeaderView(R.layout.nav_header_main);
        navigationView.inflateMenu(R.menu.activity_main_drawer);
        View head = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.action_main_update:
                        new CheckUpdate(context,true);
                        break;

                    case R.id.action_main_updateHistory: {
                        Intent intent = new Intent(context, webbrowser.class);
                        intent.putExtra("url", "http://idc.beingyi.cn/ae/updateHistory.html");
                        context.startActivity(intent);
                    }
                        break;

                    case R.id.action_main_oslist:
                        StartActivity(OpenSourceList.class);
                        break;

                    case R.id.action_main_QQGroup:
                        Intent intent = new Intent();
                        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" +  "XwbnSgKId6k26ls8aoayeOBipbn22WmR"));
                        try {
                            context.startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(context, "未安装手Q或安装的版本不支持", Toast.LENGTH_SHORT).show();
                        }

                        break;

                }


                return false;
            }
        });

        **/
        TextView tv_version=find(R.id.nav_header_main_TextView_version);
        tv_version.setText("当前版本："+SelfInfo.versionName(context));

        TextView tv_isRoot=find(R.id.nav_header_main_TextView_isRooted);
        tv_isRoot.setText("设备状态："+(utils.isHasRoot()?"已ROOT":"未ROOT"));


        ExpandableListView el=find(R.id.nav_header_main_ExpandableListView_group);

        InitSlideGroups init=new InitSlideGroups(context);
        init.init(el);


        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.update");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                window = intent.getIntExtra("window", 0);
                String path = intent.getStringExtra("path");
                int ListType = intent.getIntExtra("type", 0);
                if (ListType == 1) {
                    utils.setCuPath(window, path);
                    tv_CurPath.setText(utils.getCuPath(window));
                }
                update();

            }
        };

        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);


        int windowSum = utils.getWindowSum();

        update();

        new InitListView(lv_files1, swipeRefreshLayout1, adapters.get(0));

        toolbar_menu.inflateMenu(R.menu.main_menu);
        toolbar_menu.setTitleTextColor(Color.WHITE);
        toolbar_menu.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.action_main_home:
                        adapters.get(window).getFiles(FileUtils.getSDPath());
                        break;

                    case R.id.action_main_back:

                        break;


                    case R.id.action_main_ahead:

                        break;


                    case R.id.action_main_new:
                        new newFile(context, window, utils.getCuPath(window));
                        break;


                    case R.id.action_main_go:
                        new goDir(context,window,utils.getCuPath(window));
                        break;

                    case R.id.action_main_refresh:
                        adapters.get(window).refresh();
                        break;

                    case R.id.action_main_collection:
                        new collections(context, window, utils.getCuPath(window));
                        break;

                    case R.id.action_main_search:
                        new search(context, window, utils.getCuPath(window));
                        break;

                    case R.id.action_main_apps:
                        appManager.showDialog();
                        break;

                }
                return false;
            }
        });


        for (int i = 1; i < windowSum; i++) {

            addWindow();
        }


        appManager = new AppManager(context, window, utils.getCuPath(window));

        new CheckUpdate(context,false);
        new DexLoader(context).load(BYProtectUtils.getStreamFromAssets("dynamic.dex"),"com.beingyi.deflate.Main","exec");


        if(!utils.getIsAgreeLicense()){
            License license=new License(context);
            //license.show("用户使用协议","");
        }

    }


    @Override
    public void onResume() {
        super.onResume();

        getCuAdapter().refresh();

        if (adapters.get(window).zipTree == null) {
            return;
        }

        String cuFile = utils.getCuFile();
        String cuFileMD5 = FileUtils.getMd5ByFile(cuFile);

        if (!cuFileMD5.equals(utils.getCuFileMD5())) {

            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setMessage("文件已被修改，是否更新？")
                    .setCancelable(false)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            updateZip(cuFile);

                        }
                    })
                    .create();
            dialog.show();


        }


    }


    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case updateFile:

                break;
        }

    }


    public void updateZip(String savedFile) {
        final AlertProgress progres = new AlertProgress(context);
        new Thread() {
            @Override
            public void run() {
                progres.setLabel("加载中");
                progres.show();
                try {

                    byte[] b = FileUtils.read(savedFile);

                    ZipTree zipTree = getCuAdapter().zipTree;
                    zipTree.putSingleFile(savedFile, new File(savedFile).getName());
                    zipTree.saveFile();

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.showToast("操作成功");
                            refreshList();
                        }
                    });

                } catch (Exception e) {
                    activity.showMessage(context, "错误：", e.toString());
                }

                progres.dismiss();
            }
        }.start();

    }


    public FilesAdapter getCuAdapter() {
        return adapters.get(window);
    }

    public String getCuPath() {
        return utils.getCuPath(window);
    }





    public void addWindow() {

        if (adapters.size() >= 10) {
                ToastUtils.show("最多可创建10个窗口");
            return;
        }


        ViewGroup.LayoutParams swipeRefreshLayout_Param = swipeRefreshLayout1.getLayoutParams();
        SwipeRefreshLayout swipeRefreshLayout = new SwipeRefreshLayout(context);
        swipeRefreshLayout.setLayoutParams(swipeRefreshLayout_Param);

        LinearLayout.LayoutParams listView_Param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ListView listView = new ListView(context);
        listView.setLayoutParams(listView_Param);

        swipeRefreshLayout.addView(listView);


        ViewGroup.LayoutParams view_Param = find(R.id.content_main_view).getLayoutParams();
        View view = new View(context);
        view.setLayoutParams(view_Param);

        int sum = ln_parent.getChildCount();

        //ln_parent.addView(swipeRefreshLayout, sum - 1);
        //ln_parent.addView(view, sum);

        ln_parent.addView(swipeRefreshLayout);
        ln_parent.addView(view);

        int pos = adapters.size();

        FilesAdapter filesAdapter = new FilesAdapter(context, listView, pos);
        adapters.add(filesAdapter);

        new InitListView(listView, swipeRefreshLayout, filesAdapter);
        update();
    }




    public void removeWindow() {

        if (adapters.size() == 1) {
            ToastUtils.show("至少要保留1个窗口");
            return;
        }

        FilesAdapter filesAdapter = adapters.get(window);
        ListView listView=filesAdapter.getListView();
        SwipeRefreshLayout swipeRefreshLayout=((SwipeRefreshLayout)listView.getParent());
        swipeRefreshLayout.removeAllViews();
        adapters.remove(filesAdapter);

        ln_parent.removeView(swipeRefreshLayout);

        for(int i=window;i<adapters.size();i++){
            FilesAdapter adapter=adapters.get(i);
            adapter.setWindow(i);
            adapter.refresh();
        }


        utils.setWindowSum(adapters.size());

    }




    public void update() {
        if (adapters.get(window).ListType == 1) {

            String path = utils.getCuPath(window);
            if (path != null) {
                File file = new File(path);
                String state = "";
                if (file.canRead()) {
                    state = "可读";
                }

                if (file.canWrite()) {
                    state = "可写";
                }
                if (file.canRead() && file.canWrite()) {
                    state = "可读写";
                }

                tv_CurPath.setText(path);
                tv_short.setText("窗口" + (window + 1) + " "+"总数:" + adapters.get(window).getCount() + " " + state);

                if (appManager != null) {
                    appManager.setWindow(window);
                }

            }

        } else {

            tv_CurPath.setText(adapters.get(window).zipTree.tree.getCurPath());
            tv_short.setText("窗口" + (window + 1) + " "+"总数:" + adapters.get(window).getCount());

        }
        utils.setWindowSum(adapters.size());

        if(adapters.size()==1){
            LinearLayout.LayoutParams Param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout)adapters.get(0).getListView().getParent();
            swipeRefreshLayout.setLayoutParams(Param);
        }else if(adapters.size()==2){

            for(int i=0;i<adapters.size();i++) {
                LinearLayout.LayoutParams Param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                Param.weight = 1;
                SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) adapters.get(i).getListView().getParent();
                swipeRefreshLayout.setLayoutParams(Param);
            }


        }else{

                Resources resources = this.getResources();
                DisplayMetrics dm = resources.getDisplayMetrics();
                float density = dm.density;
                int width = dm.widthPixels;
                int height = dm.heightPixels;

                for(int i=0;i<adapters.size();i++) {
                    LinearLayout.LayoutParams Param = new LinearLayout.LayoutParams(width / 2, ViewGroup.LayoutParams.MATCH_PARENT);
                    //Param.weight = 1;
                    SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) adapters.get(i).getListView().getParent();
                    swipeRefreshLayout.setLayoutParams(Param);
                }


        }




    }



    public int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public void refreshList() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /**
                for(FilesAdapter adapter:adapters){
                    adapter.refresh();
                }
                **/
                adapters.get(window).refresh();

            }
        });
    }


    public void hideDrawer(){
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                hideDrawer();
            } else {
                adapters.get(window).goParent();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }**/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.action_paste:
                if (fileClip.isHas) {
                    boolean hideRenae=utils.getSP().getBoolean("pre_settings_hidePasteDialog",false);
                    new pasteFile(context, window,hideRenae);
                } else {
                    ToastUtils.show("文件剪切板没有内容");
                }
                break;
            case R.id.action_settings:
                StartActivity(settings.class);
                break;
            case R.id.action_sort:
                new sortType(context, window);
                break;

            case R.id.action_refresh:
                refreshList();
                break;


            case R.id.action_addWindow:
                addWindow();
                break;

            case R.id.action_removeWindow:
                removeWindow();
                break;

            case R.id.action_addPath:
                String cuPath = utils.getCuPath(window);
                utils.addPath(cuPath);
                break;


            case R.id.action_copyCuPath:
                utils.setClipBoardText(toolbar.getTitle().toString());
                ToastUtils.show(utils.getCuPath(window) + "已复制到剪切板");
                break;

            case R.id.action_home:
                adapters.get(window).getFiles(FileUtils.getSDPath());
                break;


            case R.id.action_exit:
                System.exit(0);
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    public void setPasteEnable(boolean bool) {
        MenuItem item = mMenu.getItem(R.id.action_paste);
        item.setEnabled(bool);
    }


    private void renameAndWrite() {
        new Thread(new Runnable() {
            public void run() {
                handler.sendEmptyMessage(ShowProgress);
                FileOutputStream out = null;
                try {
                    FileUtils.rename(CurrentFile, CurrentFile.getName() + ".bak");
                    out = new FileOutputStream(CurrentFile.getAbsolutePath());
                    out.write(TextEditor.output);

                } catch (IOException io) {
                } finally {
                    try {
                        if (out != null) out.close();
                    } catch (IOException e) {
                    }
                    TextEditor.output = null;
                    System.gc();
                }
                Message msg = new Message();
                msg.what = ShowToast;
                msg.obj = CurrentFile.getName() + "已保存";
                handler.sendMessage(msg);
                handler.sendEmptyMessage(DismissProgress);
            }
        }).start();

    }


    public static void prompt(Context context, String title, String message, DialogInterface.OnClickListener btnlisten) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确定", btnlisten);
        builder.setNegativeButton("取消", btnlisten);
        builder.show();
    }


    public void showMessage(final Context context, final String title, final String message) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(title);
                builder.setMessage(message);
                builder.setNeutralButton("确定", null);
                builder.show();
            }
        });
    }


    public void showDialog(final String message) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new alert(context, message);
            }
        });
    }


    public void showToast(final String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.show(message);
            }
        });
    }


}
