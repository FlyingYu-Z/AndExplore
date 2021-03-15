package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.beingyi.app.AE.R;
import com.beingyi.app.AE.activity.MainActivity;
import com.beingyi.app.AE.adapter.FilesAdapter;
import com.beingyi.app.AE.utils.AEUtils;
import com.beingyi.app.AE.utils.APKUtils;
import com.beingyi.app.AE.utils.ApkPkgChecker;
import com.beingyi.app.AE.utils.FileUtils;
import com.beingyi.app.AE.utils.GetApkSign;

public class view_apk_property
{
    
    
    Context context;
    MainActivity activity;
    FilesAdapter adapter;
    AEUtils utils;
    int window;
    String path;
    AlertDialog dialog;

    public view_apk_property(Context mContext, FilesAdapter mAdapter, int windows, String mPath)
    {

        this.context = mContext;
        this.activity=(MainActivity)context;
        this.adapter = mAdapter;
        this.utils = new AEUtils(context);
        this.window = windows;
        this.path = mPath;


        View view=View.inflate(context, R.layout.view_apk_property, null);
        ImageView img_icon=view.findViewById(R.id.view_apk_property_ImageView_icon);
        TextView tv_label=view.findViewById(R.id.view_apk_property_TextView_label);
        TextView tv_pakName=view.findViewById(R.id.view_apk_property_TextView_pakName);

        img_icon.setImageDrawable(APKUtils.getApkIcon(context, path));
        tv_label.setText(APKUtils.getLabel(context, path));
        tv_pakName.setText(APKUtils.getPkgName(context, path));
        
        dialog = new AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(true)
            .create();
        dialog.show();
        
        String VersionName=BASE128.decode("6XImt+Bau2kGg3EIAwfWkw==")+APKUtils.getVersionName(context,path);
        String VersionCode=BASE128.decode("2nG/iSy3Ie9TN+0iVAJ/0g==")+APKUtils.getVersionCode(context,path);
        String PkgName=BASE128.decode("CkdHL1S8Mxv178rqEDP8zQ==")+new ApkPkgChecker(path).getPkgName();
        String MD5=BASE128.decode("xxfCsjpKGhJM+8Nd7zokng==")+FileUtils.getMd5ByFile(path);
        String Sign=BASE128.decode("udMOV2A5/7yHt4CbUVgIwg==")+GetApkSign.getApkSignature(path);
        String Permissions=BASE128.decode("+3BSdPLx16laGdsVJ9mWcg==")+APKUtils.getPermissions(context,path);
        
        
        
        getTextViewById(view,R.id.view_apk_property_CopyTextView1).setText(VersionName);
        getTextViewById(view,R.id.view_apk_property_CopyTextView2).setText(VersionCode);
        getTextViewById(view,R.id.view_apk_property_CopyTextView3).setText(PkgName);
        getTextViewById(view,R.id.view_apk_property_CopyTextView4).setText(MD5);
        getTextViewById(view,R.id.view_apk_property_CopyTextView5).setText(Sign);
        getTextViewById(view,R.id.view_apk_property_CopyTextView6).setText(Permissions);
        


    }
    
    private TextView getTextViewById(View view,int id){
        
        return view.findViewById(id);
    }
    
}
