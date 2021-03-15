package com.beingyi.app.AE.dialog;

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

        dialog = new AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(true)
            .create();
        dialog.show();


        img_icon.setImageDrawable(APKUtils.getApkIcon(context, path));
        tv_label.setText(APKUtils.getLabel(context, path));
        tv_pakName.setText(APKUtils.getPkgName(context, path));

        String VersionName="版本名称："+APKUtils.getVersionName(context,path);
        String VersionCode="版本号："+APKUtils.getVersionCode(context,path);
        String PkgName="壳类型："+new ApkPkgChecker(path).getPkgName();
        String MD5="MD5："+FileUtils.getMd5ByFile(path);
        String Sign="签名哈西："+GetApkSign.getApkSignature(path);
        String Permissions="权限："+APKUtils.getPermissions(context,path);
        
        
        
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
