package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.activity.MainActivity;
import com.beingyi.app.AE.adapter.FilesAdapter;
import com.beingyi.app.AE.bean.FileInfo;
import com.beingyi.app.AE.dialog.fileLongClick;
import com.beingyi.app.AE.dialog.inzipLongClick;
import com.beingyi.app.AE.dialog.openFileByWay;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.ui.ToastUtils;

import java.io.File;

import com.beingyi.app.AE.utils.openFile.s_html;
import com.beingyi.app.AE.utils.openFile.s_mod;
import com.beingyi.app.AE.utils.openFile.s_xml;
import com.beingyi.app.AE.utils.openFile.s_jar;
import com.beingyi.app.AE.utils.openFile.s_dex;
import com.beingyi.app.AE.utils.openFile.s_zip;
import com.beingyi.app.AE.utils.openFile.s_apk;
import com.beingyi.app.AE.utils.openFile.s_txt;
import com.beingyi.app.AE.utils.openFile.s_smali;
import com.beingyi.app.AE.utils.openFile.s_d_smali;

import android.animation.ObjectAnimator;
import android.support.v7.widget.Toolbar;

public class InitListView {
    Context context;
    MainActivity activity;
    AEUtils utils;
    SwipeRefreshLayout swipeRefreshLayout;
    ListView listview;
    FilesAdapter adapter;

    
    public InitListView(ListView mlistview, SwipeRefreshLayout mSwipeRefreshLayout, FilesAdapter mAdapter) {
        this.context = mlistview.getContext();
        this.activity = (MainActivity) context;
        this.utils = new AEUtils(context);
        this.swipeRefreshLayout = mSwipeRefreshLayout;
        this.listview = mlistview;
        //this.adapter=(FilesAdapter)((HeaderViewListAdapter)listview.getAdapter()).getWrappedAdapter();
        this.adapter = mAdapter;
        
        setHeader(listview);
        listview.setAdapter(adapter);
        listview.setFastScrollEnabled(false);
        listview.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> p1, View view, int position, long id) {
                FileInfo fileInfo = (FileInfo) adapter.getItem(position - 1);


                if (adapter.ListType == 1) {


                    File file = new File(fileInfo.Path);
                    if (!file.exists()) {
                        ToastUtils.show(BASE128.decode("rsPdGg/70noG9TAq9SdgMw=="));
                        return true;
                    }

                    new fileLongClick(context, adapter.getWindow(), file.getAbsolutePath());

                } else {
                    new inzipLongClick(context, view, adapter.getWindow(), fileInfo.Path, fileInfo.Entry);
                }


                return true;
            }

        });


        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> p1, View view, int position, long id) {
                FileInfo fileInfo = adapter.fileList.get(position - 1);

                if (adapter.ListType == 1) {

                    final File file = new File(fileInfo.Path);
                    openFile(file, view, false);
                } else {
                    String Name = fileInfo.Name;
                    if (Name.endsWith(BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="))) {
                        adapter.getFiles(Name);

                    } else {
                        final String entryName = fileInfo.Entry;

                        final AlertProgress progres = new AlertProgress(context);
                        new Thread() {
                            @Override
                            public void run() {
                                progres.setLabel(BASE128.decode("NG6/2bPeC5kiLGc6CtSQOA=="));
                                progres.setNoProgress();
                                progres.show();
                                try {
                                    File file = adapter.zipTree.extractToFile(entryName, new File(context.getCacheDir() + BASE128.decode("Rxik4eK35Zg1EDIhRi1Umg==") + (new File(adapter.zipTree.zipFile.getName())).getName() + BASE128.decode("AkZj32p4lHkS0d0LVieHfA==") + entryName).getAbsolutePath());

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            openFile(file, view, true);
                                        }
                                    });

                                } catch (Exception e) {
                                    activity.showMessage(context, BASE128.decode("5x3zP/7tNw0g1zTyNGUeyQ=="), e.toString());
                                }

                                progres.dismiss();
                            }
                        }.start();


                    }


                }


            }
        });


        View header = listview.findViewWithTag(BASE128.decode("SMLdjkamg/ZL1fB4/sGvcQ=="));
        header.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                adapter.goParent();

            }

        });


        listview.setOnScrollListener(onScrollListener);
        listview.setOnTouchListener(new OnTouchListener() {

            private float currentY;
            private int direction = -1;
            private boolean mShow = true;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                MainActivity activity = (MainActivity) context;

                activity.window = adapter.getWindow();
                activity.update();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        firstY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        currentY = event.getY();
                        //向下滑动
                        if (currentY - firstY > scaledTouchSlop) {
                            direction = 0;
                        }
                        //向上滑动
                        else if (firstY - currentY > scaledTouchSlop) {
                            direction = 1;
                        }
                        //如果是向上滑动，并且ToolBar是显示的，就隐藏ToolBar
                        if (direction == 1) {
                            if (mShow) {
                                //toobarAnim(1);
                                mShow = !mShow;
                            }
                        } else if (direction == 0) {
                            if (!mShow) {
                                //toobarAnim(0);
                                mShow = !mShow;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }


                return false;
            }

        });


        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                activity.refreshList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }


    public void openFile(File file, View view, boolean isInZip) {


        if (file.isDirectory()) {
            if (file.getAbsolutePath().endsWith(BASE128.decode("3BiZts67m0DtzniDAfnpJA=="))) {
                new s_d_smali(context, adapter.getWindow(), file.getAbsolutePath(), view, isInZip);
            } else {
                adapter.getFiles(file.getAbsolutePath());
            }


        } else {
            utils.setCuFile(file.getAbsolutePath());
            utils.setCuFileMD5(FileUtils.getMd5ByFile(file.getAbsolutePath()));

            String suffix = FileUtils.getSuffix(file.getAbsolutePath());

            if (suffix.equals(BASE128.decode("OG7Rh/GWl2IcHxbkCB+7rA=="))) {
                new s_apk(context, adapter.getWindow(), file.getAbsolutePath(), view, isInZip);
                return;
            }

            if (suffix.equals(BASE128.decode("K72s/Vbw7LRlRWdVOpQ8eQ==")) ||
                    suffix.equals(BASE128.decode("HkklReTLpauVSMXzvfIEZw==")) ||
                    suffix.equals(BASE128.decode("6d92T8MC/q7nNmqKZ5WEkg==")) ||
                    suffix.equals(BASE128.decode("3t5M6zoOHS1muAmW3ORGJw==")) ||
                    suffix.equals(BASE128.decode("cGdJmz+ZQIsWe5wj4DjMJg==")) ||
                    suffix.equals(BASE128.decode("36VsDx39kwjV+Z7W+Cpm/A==")) ||
                    suffix.equals(BASE128.decode("i89kERUgyz6N/rSvl89HuQ==")) ||
                    suffix.equals(BASE128.decode("fMqD1vdNAEn5xl+bYdYC4w==")) ||
                    suffix.equals(BASE128.decode("uVATupe/fh15/cjX3V3XAg==")) ||
                    suffix.equals(BASE128.decode("tyvgUIIS4RJjsPLYOf9OUg==")) ||
                    suffix.equals(BASE128.decode("W2pJPq03yxukoKDvS7KV3w==")) ||
                    suffix.equals(BASE128.decode("EzjxfEeZlT39f3I1bsbC3w==")) ||
                    suffix.equals(BASE128.decode("6d92T8MC/q7nNmqKZ5WEkg==")) ||
                    suffix.equals(BASE128.decode("DOVaWgpEFAam1oCBFmeVKQ=="))
                    ) {
                new s_txt(context, adapter.getWindow(), file.getAbsolutePath(), view, isInZip);
                return;
            }

            if (suffix.equals(BASE128.decode("0FbaDnz4QEd8OKxUL8aYkA=="))) {
                new s_xml(context, adapter.getWindow(), file.getAbsolutePath(), view, isInZip);
                return;
            }


            if (suffix.equals(BASE128.decode("3m8oJWeZo0IKRlsXf8e9DA=="))) {
                new s_zip(context, adapter.getWindow(), file.getAbsolutePath(), view, isInZip);
                return;

            }

            if (suffix.equals(BASE128.decode("77q/pLYqCIj4QyskZ26SCg=="))) {
                new s_jar(context, adapter.getWindow(), file.getAbsolutePath(), view, isInZip);
                return;
            }

            if (suffix.equals(BASE128.decode("VgRrq1lBW0G/1ZLBdMGKtw=="))) {
                new s_dex(context, adapter.getWindow(), file.getAbsolutePath(), view, isInZip);
                return;
            }

            if (suffix.equals(BASE128.decode("O/GR/farGQZHzNbVx92yvw=="))) {
                new s_smali(context, adapter.getWindow(), file.getAbsolutePath(), view, isInZip);
                return;
            }

            if (suffix.equals(BASE128.decode("AsgGmv9wTHpC1fakTXZjUw=="))) {
                new s_html(context, adapter.getWindow(), file.getAbsolutePath(), view, isInZip);
                return;
            }

            if (suffix.equals(BASE128.decode("tBH25y9Lop0hRjmAcxC9Xg=="))) {
                new s_mod(context, adapter.getWindow(), file.getAbsolutePath(), view, isInZip);
                return;
            }

            new openFileByWay(context,adapter.getWindow(),file.getPath());

        }


    }


    private ObjectAnimator animtor;
    private float scaledTouchSlop;
    private float firstY = 0;

    /**
     * ToolBar显示隐藏动画
     *
     * @param direction
     */
    public void toobarAnim(int direction) {

        Toolbar toolbar = activity.toolbar_menu;
        //开始新的动画之前要先取消以前的动画
        if (animtor != null && animtor.isRunning()) {
            animtor.cancel();
        }
        //toolbar.getTranslationY()获取的是Toolbar距离自己顶部的距离
        float translationY = toolbar.getTranslationY();
        if (direction == 0) {
            animtor = ObjectAnimator.ofFloat(toolbar, BASE128.decode("fw+x2XL+i5LCYFSPzhsHPQ=="), translationY, 0);
        } else if (direction == 1) {
            animtor = ObjectAnimator.ofFloat(toolbar, BASE128.decode("fw+x2XL+i5LCYFSPzhsHPQ=="), translationY, -toolbar.getHeight());
        }
        animtor.start();
    }


    public void setHeader(ListView listview) {
        View view = LinearLayout.inflate(context, R.layout.item_file, null);
        ImageView icon = view.findViewById(R.id.item_file_ImageView_icon);
        TextView name = view.findViewById(R.id.item_file_TextView_name);
        TextView time = view.findViewById(R.id.item_file_TextView_time);
        TextView size = view.findViewById(R.id.item_file_TextView_size);
        ((LinearLayout) time.getParent()).setVisibility(View.GONE);
        icon.setImageResource(R.drawable.ic_folder);
        name.setText(BASE128.decode("DRaqDFOWtSBGCwItQ2VsPg=="));
        view.setTag(BASE128.decode("SMLdjkamg/ZL1fB4/sGvcQ=="));

        listview.addHeaderView(view);


    }


    AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

            activity.window = adapter.getWindow();
            int mPosition = listview.getFirstVisiblePosition();
            utils.setCuPosition(adapter.getWindow(), mPosition, utils.getCuPath(adapter.getWindow()));

            switch (scrollState) {
                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                    //asyncImageLoader.lock();
                    //loadImage();


                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    //loadImage();
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    //asyncImageLoader.lock();
                    //loadImage();
                    break;

                default:
                    break;
            }

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {


            View firstView = view.getChildAt(firstVisibleItem);
            if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {
                swipeRefreshLayout.setEnabled(true);
            } else {
                swipeRefreshLayout.setEnabled(false);
            }


        }
    };


}
