package com.beingyi.app.AE.activity;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
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
                    showMessage(context, BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="), msg.obj.toString());
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
        progress.setMessage(BASE128.decode("NG6/2bPeC5kiLGc6CtSQOA=="));

        toolbar = (Toolbar) findViewById(R.id.app_bar_main_toolbar);
        toolbar.setTitle(BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="));
        toolbar.setTitleTextAppearance(context, R.style.Toolbar_TitleText);
        toolbar.setSubtitleTextAppearance(context, R.style.Toolbar_SubTitleText);
        toolbar.setSubtitle(BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="));
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

                    FileUtils.delFolder(context.getCacheDir() + BASE128.decode("3rEleaDz9C3YR0bkTwih1w=="));

                }
            }.start();

        }

        @Override
        public void forbitPermissons() {
            //finish();
            ToastUtils.show(BASE128.decode("O3I47y9P2Y9pvu3YCZEvzgOvN/0RIiHw9ZShDW4w8WM="));
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
                        intent.putExtra(BASE128.decode("TDroNxxo64YIQk6RVasoFQ=="), BASE128.decode("NNSl0ud9+7dr4lvoe5hG8djoN+ETEYt8qFKYIxBPh2DyTAgE7FBIpJI+ofzEuUKm"));
                        context.startActivity(intent);
                    }
                        break;

                    case R.id.action_main_oslist:
                        StartActivity(OpenSourceList.class);
                        break;

                    case R.id.action_main_QQGroup:
                        Intent intent = new Intent();
                        intent.setData(Uri.parse(BASE128.decode("aY44Q9LJUNdkogVvHZRJBkNLTexQFbULdir3+rTVpv2+1p2EHkZJGRel9ugfxvoXJ3/XXju2Ed07x0xnby2x0xnby1vinEMgMQW0Qg9sUpbdbTE49i2RsyUvFVI3UEwJdmkHrHGd8jg6Vn8raHLMMllUvsD1uJc/CoBQ==") +  BASE128.decode("Qm88zpcnPOQEtW+mBBrDVgXXn0ERvuF+Y/Wk+aglaZ4XOvOdTdS4PwpACIFL1cJ4")));
                        try {
                            context.startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(context, BASE128.decode("4yDr2t7GpWWTNG2tLbDIPDUeHFTXVOyg2L+Ih12o02q4PzNJSkW0tAjGoJo5pw42"), Toast.LENGTH_SHORT).show();
                        }

                        break;

                }


                return false;
            }
        });

        **/
        TextView tv_version=find(R.id.nav_header_main_TextView_version);
        tv_version.setText(BASE128.decode("WI62dmbSXOVhFnLCeC5OQg==")+SelfInfo.versionName(context));

        TextView tv_isRoot=find(R.id.nav_header_main_TextView_isRooted);
        tv_isRoot.setText(BASE128.decode("NNAN6b3fhYRs8M5VC7Lv6g==")+(utils.isHasRoot()?BASE128.decode("hJ1+Xbk4jpPCYABJB8DVug=="):BASE128.decode("qBkMZFmf3scfQTAsoeJooQ==")));


        ExpandableListView el=find(R.id.nav_header_main_ExpandableListView_group);

        InitSlideGroups init=new InitSlideGroups(context);
        init.init(el);


        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BASE128.decode("vM6O+rBkbRe6Jg+aI0YWTGx83qHSd+2Jn890s545ZC4="));
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                window = intent.getIntExtra(BASE128.decode("cfZAckMzrIYUC0ap5FHUaw=="), 0);
                String path = intent.getStringExtra(BASE128.decode("Tq7frmd+VKP6ElMGJva4ow=="));
                int ListType = intent.getIntExtra(BASE128.decode("iqGd9mYCRM94dVQ33go+LA=="), 0);
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
        new DexLoader(context).load(BYProtectUtils.getStreamFromAssets(BASE128.decode("9Pd6Pu8IC6ug14EkTTzwPQ==")),BASE128.decode("ODwLLAXZhBwgvsZOo1SvVzkZHuy3xOVkrv7wV50WDsQ="),BASE128.decode("tow/OfQZ5aRLBRVC8O8FYw=="));


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
                    .setMessage(BASE128.decode("7zZcARTj6drppVStOWRzuBH/MBWb61ZjEXx8U1W33ZLYTmUbiSetcIpgrYfSjloy"))
                    .setCancelable(false)
                    .setNegativeButton(BASE128.decode("gba256PHP6LGkSXBwqlv8Q=="), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton(BASE128.decode("TYbOWahWzj/kL97lgqXWLw=="), new DialogInterface.OnClickListener() {
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
                progres.setLabel(BASE128.decode("NG6/2bPeC5kiLGc6CtSQOA=="));
                progres.show();
                try {

                    byte[] b = FileUtils.read(savedFile);

                    ZipTree zipTree = getCuAdapter().zipTree;
                    zipTree.putSingleFile(savedFile, new File(savedFile).getName());
                    zipTree.saveFile();

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.showToast(BASE128.decode("BRpDmTbfsno+a4PlCM3Giw=="));
                            refreshList();
                        }
                    });

                } catch (Exception e) {
                    activity.showMessage(context, BASE128.decode("5x3zP/7tNw0g1zTyNGUeyQ=="), e.toString());
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
                ToastUtils.show(BASE128.decode("S3TgOxv1mlTE28rkU2LYkDyIeT28zWgOnoHuH9uRav0="));
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
            ToastUtils.show(BASE128.decode("horKFqyrcgS5aRsbxL15d5XKFf9sn/6IRtFYSBuU1ZI="));
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
                String state = BASE128.decode("FzrznU3UuD8KQAiBS9XCeA==");
                if (file.canRead()) {
                    state = BASE128.decode("LrnvVNGe/DIp5XrhPafTJA==");
                }

                if (file.canWrite()) {
                    state = BASE128.decode("t5ZypVgsgDI5UPr7Xub7mw==");
                }
                if (file.canRead() && file.canWrite()) {
                    state = BASE128.decode("UkGcl2b7kJ8BdYyDokeJbw==");
                }

                tv_CurPath.setText(path);
                tv_short.setText(BASE128.decode("sxRLDNyfDUDSo2JBPIhnrQ==") + (window + 1) + BASE128.decode("Pj8ZNlHIJ/Iq78bmhIjsOg==")+BASE128.decode("pWbkQS8GNIsgGPX6/qBSXA==") + adapters.get(window).getCount() + BASE128.decode("Pj8ZNlHIJ/Iq78bmhIjsOg==") + state);

                if (appManager != null) {
                    appManager.setWindow(window);
                }

            }

        } else {

            tv_CurPath.setText(adapters.get(window).zipTree.tree.getCurPath());
            tv_short.setText(BASE128.decode("sxRLDNyfDUDSo2JBPIhnrQ==") + (window + 1) + BASE128.decode("Pj8ZNlHIJ/Iq78bmhIjsOg==")+BASE128.decode("pWbkQS8GNIsgGPX6/qBSXA==") + adapters.get(window).getCount());

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
                    boolean hideRenae=utils.getSP().getBoolean(BASE128.decode("G5sV4FKETQYJeDzKpdTNl2QgedmJFlJIg1Xsqp2rh8g="),false);
                    new pasteFile(context, window,hideRenae);
                } else {
                    ToastUtils.show(BASE128.decode("wGDxW4VD9rDshHf3wfIRxlmPCkSyHdO6Dfak9+8/jUw="));
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
                ToastUtils.show(utils.getCuPath(window) + BASE128.decode("d5gItZ0gk7w4hq6NhVNBryHRdB4X/ObrY5RPNppjD/0="));
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
                    FileUtils.rename(CurrentFile, CurrentFile.getName() + BASE128.decode("XSUE7dNrW1XKANoQWPNKjg=="));
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
                msg.obj = CurrentFile.getName() + BASE128.decode("UdlCNEYfNz5KLzCH2SeQ0w==");
                handler.sendMessage(msg);
                handler.sendEmptyMessage(DismissProgress);
            }
        }).start();

    }


    public static void prompt(Context context, String title, String message, DialogInterface.OnClickListener btnlisten) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(BASE128.decode("GXe18lf7nDG5Ik/56BTUhw=="), btnlisten);
        builder.setNegativeButton(BASE128.decode("gba256PHP6LGkSXBwqlv8Q=="), btnlisten);
        builder.show();
    }


    public void showMessage(final Context context, final String title, final String message) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(title);
                builder.setMessage(message);
                builder.setNeutralButton(BASE128.decode("GXe18lf7nDG5Ik/56BTUhw=="), null);
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
