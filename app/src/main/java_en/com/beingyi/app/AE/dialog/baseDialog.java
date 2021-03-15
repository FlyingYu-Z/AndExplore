package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.content.Context;

import com.beingyi.app.AE.activity.MainActivity;
import com.beingyi.app.AE.adapter.FilesAdapter;
import com.beingyi.app.AE.utils.AEUtils;

public class baseDialog {


    Context context;
    MainActivity activity;
    FilesAdapter adapter;
    AEUtils utils;
    int window;
    String Path;


    public baseDialog(Context mContext, int mWindow, String mPath){
        this.context = mContext;
        this.activity = (MainActivity) context;
        this.window = mWindow;
        this.adapter=activity.adapters.get(window);
        this.utils = new AEUtils(context);
        this.Path = mPath;


    }


}
