package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import com.beingyi.app.AE.R;
import com.beingyi.app.AE.activity.MainActivity;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.AEUtils;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View.OnClickListener;
import com.beingyi.app.AE.adapter.FilesAdapter;
import android.text.TextWatcher;
import android.text.Editable;
import java.io.File;

public class pasteFile
{

    Context context;
    MainActivity activity;
    AEUtils utils;
    int window;
    FilesAdapter adapter;
    AlertDialog dialog;

    TextView tv_destDir;
    EditText ed_name;
    Button btn_cancel;
    Button btn_ok;

    boolean hideRename;


    public pasteFile(Context mContext, int windows,boolean mHideRename)
    {
        this.context = mContext;
        this.activity = (MainActivity)context;
        this.utils = new AEUtils(context);
        this.window = windows;
        this.adapter=activity.adapters.get(window);
        this.hideRename=mHideRename;


        if(hideRename){
            start();
            return;
        }


        View view=View.inflate(context, R.layout.view_paste_file, null);

        tv_destDir = view.findViewById(R.id.view_paste_file_TextView_destDir);
        ed_name = view.findViewById(R.id.view_paste_file_EditText_Name);
        btn_cancel = view.findViewById(R.id.view_paste_file_Button_cancel);
        btn_ok = view.findViewById(R.id.view_paste_file_Button_ok);

        if (adapter.ListType == 1)
        {
            tv_destDir.setText(utils.getCuPath(window));
        }
        else
        {
            tv_destDir.setText(adapter.zipTree.Path + BASE128.decode("AkZj32p4lHkS0d0LVieHfA==") + adapter.zipTree.tree.getCurPath());
        }

        ed_name.addTextChangedListener(new TextWatcher(){

                @Override
                public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
                {
                    checkExists();
                }

                @Override
                public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
                {
                    checkExists();
                }

                @Override
                public void afterTextChanged(Editable p1)
                {
                    checkExists();
                }
                
            
        });
        
        ed_name.setText(activity.fileClip.getName());

        btn_cancel.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    dialog.dismiss();
                }
            });


        btn_ok.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    dialog.dismiss();
                    start();
                }
            });



        dialog = new AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(false)
            .create();
        dialog.show();




    }

    public void start(){

        boolean toZip=(activity.adapters.get(window).ListType == 2) ? true: false;

        String Name=BASE128.decode("FzrznU3UuD8KQAiBS9XCeA==");

        if(hideRename){
            Name=activity.fileClip.getName();
        }else{
            Name=ed_name.getText().toString();
        }

        if (toZip)
        {
            activity.fileClip.putToZip(adapter, Name);
        }
        else
        {
            activity.fileClip.putToDir(adapter,utils.getCuPath(window), Name);
        }

    }


    
    private void checkExists(){
        File file=new File(tv_destDir.getText().toString(),ed_name.getText().toString());
        if(file.equals(new File(activity.fileClip.getCuFilePath()))){
            btn_ok.setEnabled(false);
        }else{
            btn_ok.setEnabled(true);
        }

        if(file.exists()){
            btn_ok.setText(BASE128.decode("NTV5aw+GTeVNeQLNyOjRHg=="));
        }else{
            btn_ok.setText(BASE128.decode("GXe18lf7nDG5Ik/56BTUhw=="));
        }



    }
    

}
