package com.beingyi.app.AE.activity;

import com.beingyi.app.AE.application.MyApplication;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.adapter.ClassAdapter;
import com.beingyi.app.AE.adapter.SectionsPagerAdapter;
import com.beingyi.app.AE.base.BaseActivity;
import com.beingyi.app.AE.dialog.alert;
import com.beingyi.app.AE.fragment.Fclasslist;
import com.beingyi.app.AE.fragment.FdexSearch;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.ClassTree;

import java.util.ArrayList;
import java.util.List;

public class DexEditor extends BaseActivity implements ViewPager.OnPageChangeListener{
    Context context;
    Activity activity;

    public Toolbar toolbar;


    private ArrayList<Fragment> fragments;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;

    Fclasslist fclasslist;
    public FdexSearch fdexSearch;


    public static byte[] output;
    public String Path;

    public ClassTree classTree;

    public static boolean isChanged = false;
    public static boolean isSaved = false;


    public void init() {
        context = this;
        activity = this;
        toolbar = (Toolbar) findViewById(R.id.activity_dexeditor_Toolbar);


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.activity_dexeditor_BottomNavigationView);
        viewPager = (ViewPager) findViewById(R.id.activity_dexeditor_ViewPager1);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setLabelVisibilityMode(1);


    }


    private void initViewPager() {

        fragments = new ArrayList<>();

        fclasslist=new Fclasslist();
        fdexSearch=new FdexSearch();

        fragments.add(fclasslist);
        fragments.add(fdexSearch);


        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(5);

        SectionsPagerAdapter mainAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(mainAdapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dexeditor);
        init();

        Intent intent = getIntent();
        Path = intent.getStringExtra("Path");


        final AlertProgress progres = new AlertProgress(context);
        new Thread() {
            @Override
            public void run() {

                progres.setLabel(MyApplication.getInstance().getString("f013ea9dcba3f5ca1278aa850931fec8"));
                progres.setNoProgress();
                progres.show();

                try {
                    classTree = new ClassTree(Path);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initViewPager();
                        }
                    });

                }catch (Exception e){
                    new alert(context,e.toString());
                }
                progres.dismiss();

            }
        }.start();


    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        bottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;

                case R.id.navigation_search:
                    viewPager.setCurrentItem(1);
                    return true;
            }
            return false;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_dexeditor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_dexeditor_saveDex:
                saveDexFile(false);
                break;

            case R.id.action_dexeditor_exit:
                exit();
                break;

        }


        return super.onOptionsItemSelected(item);
    }


    public void exit() {
        if (isChanged) {
            tip();
        } else {

            this.finish();
            classTree.clearAll();
            DexEditor.isChanged = false;
            DexEditor.isSaved = false;

        }
    }

    public void tip() {


        AlertDialog dialog = new AlertDialog.Builder(context)

                .setMessage(MyApplication.getInstance().getString("ce2ce435bfc3482ab4fe863b2c6b0b3c"))
                .setCancelable(false)
                .setNegativeButton(MyApplication.getInstance().getString("44b0a938f7110bd625cd5a1340957b2d"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        isChanged = false;
                        isSaved = false;
                        classTree.clearAll();
                        finish();
                        dialog.dismiss();
                    }
                })
                .setNeutralButton(MyApplication.getInstance().getString("625fb26b4b3340f7872b411f401e754c"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(MyApplication.getInstance().getString("be5fbbe34ce9979bfb6576d9eddc5612"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        saveDexFile(true);
                    }
                })
                .create();
        dialog.show();


    }


    public void saveDexFile(boolean isExit) {


        final AlertProgress progres = new AlertProgress(context);
        new Thread() {
            @Override
            public void run() {
                progres.setLabel(MyApplication.getInstance().getString("f013ea9dcba3f5ca1278aa850931fec8"));
                progres.show();
                try {

                    classTree.saveDexFile(Path, new ClassTree.DexSaveProgress() {
                        @Override
                        public void onProgress(int progress, int total) {

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progres.setProgress(progress, total);
                                }
                            });

                        }
                    });


                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(MyApplication.getInstance().getString("3b108349b93f7c8c4e2346f8d48c092a"));
                            if (isExit) {
                                exit();
                            }
                        }
                    });

                } catch (Exception e) {
                    new alert(context, e.toString());
                }

                progres.dismiss();
            }
        }.start();


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if(viewPager.getCurrentItem()!=0){
                viewPager.setCurrentItem(0);
            }else {
                fclasslist.goBack();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
