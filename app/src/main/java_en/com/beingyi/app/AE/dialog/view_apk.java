package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;

import android.content.Context;
import android.preference.Preference;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.beingyi.app.AE.R;
import com.beingyi.app.AE.adapter.FilesAdapter;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.AEUtils;
import com.beingyi.app.AE.utils.APKUtils;
import com.beingyi.app.AE.utils.ZipTree;
import com.beingyi.app.AE.activity.MainActivity;

public class view_apk
{
    Context context;
    MainActivity activity;
    FilesAdapter adapter;
    AEUtils utils;
    int window;
    String path;
    AlertDialog dialog;

    public view_apk(Context mContext, FilesAdapter mAdapter, int windows, String mPath)
    {

        this.context = mContext;
        this.activity=(MainActivity)context;
        this.adapter = mAdapter;
        this.utils = new AEUtils(context);
        this.window = windows;
        this.path = mPath;


        View view=View.inflate(context, R.layout.view_apk, null);
        ImageView img_icon=view.findViewById(R.id.view_apk_ImageView_icon);
        TextView tv_label=view.findViewById(R.id.view_apk_TextView_label);
        TextView tv_pakName=view.findViewById(R.id.view_apk_TextView_pakName);

        img_icon.setImageDrawable(APKUtils.getApkIcon(context, path));
        tv_label.setText(APKUtils.getLabel(context, path));
        tv_pakName.setText(APKUtils.getPkgName(context, path));


        view.findViewById(R.id.view_apk_Button_view).setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    dialog.dismiss();
                    //Intent intent=new Intent(context,ZipEditor.class);
                    //ZipEditor.zipFileName=path;
                    //context.startActivity(intent);
                    final AlertProgress progres=new AlertProgress(context);
                    new Thread(){
                        @Override
                        public void run()
                        {


                            progres.setLabel(BASE128.decode("NG6/2bPeC5kiLGc6CtSQOA=="));
                            progres.setNoProgress();
                            progres.show();
                            try
                            {
                                adapter.zipTree = new ZipTree(path);
                                adapter.ListType = 2;


                                activity.runOnUiThread(new Runnable(){
                                        @Override
                                        public void run()
                                        {
                                            adapter.refresh();
                                        }
                                    });
                            }
                            catch (Exception e)
                            {
                                activity.showDialog(e.toString());
                            }



                            progres.dismiss();

                        }
                    }.start();

                    
                }

            });

        view.findViewById(R.id.view_apk_Button_install).setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    dialog.dismiss();
                    APKUtils.installAPK(context, path);
                }

            });
            

        view.findViewById(R.id.view_apk_Button_property).setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    dialog.dismiss();
                    if(APKUtils.isValid(context,path)) {
                        new view_apk_property(context, adapter, window, path);
                    }else{
                        ToastUtils.show(BASE128.decode("T5IU/FJDlygRo3FbC3G/nA=="));
                    }
                    
                }

            });
        
        view.findViewById(R.id.view_apk_Button_menu).setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    PopupMenu popupMenu = new PopupMenu(context, view);
                    // 获取布局文件
                    popupMenu.getMenuInflater().inflate(R.menu.file_apk, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item)
                            {
                                int id=item.getItemId();
                                switch (id)
                                {
                                    case R.id.action_apk_sign:
                                        new signAPK(context, window, path);
                                        break;

                                    case R.id.action_apk_injectLog:
                                        new addLogPrinter(context,window,path);
                                        break;

                                    case R.id.action_apk_enString:
                                        new enApkString(context,window,path);
                                        break;

                                    case R.id.action_apk_addShell:
                                        new addSuperEncryption(context,window,path);
                                        break;

                                    default:
                                        break;
                                }

                                dialog.dismiss();
                                return true;
                            }
                        });
                    popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                            @Override
                            public void onDismiss(PopupMenu menu)
                            {

                            }
                        });


                }

            });


        dialog = new AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(true)
            .create();
        dialog.show();



    }


}
        
