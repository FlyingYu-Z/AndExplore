package com.beingyi.app.AE.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.beingyi.app.AE.R;
import com.beingyi.app.AE.adapter.FilesAdapter;
import com.beingyi.app.AE.utils.AEUtils;
import java.io.File;
import com.beingyi.app.AE.activity.MainActivity;
import com.beingyi.app.AE.interfaces.GetSavePathCallBack;

public class getSavePath
{
    
    Context context;
    MainActivity activity;
    AEUtils utils;
    int window;
    String path;
    GetSavePathCallBack callBack;

    TextView tv_destDir;
    EditText ed_name;
    Button btn_cancel;
    Button btn_ok;

    AlertDialog dialog;
    
    public getSavePath(Context mContext, int windows, String mPath,GetSavePathCallBack mCallBack)
    {
        this.context = mContext;
        this.activity = (MainActivity)context;
        this.utils = new AEUtils(context);
        this.window = windows;
        this.path = mPath;
        this.callBack=mCallBack;


        View view=View.inflate(context, R.layout.view_get_save_path, null);
        tv_destDir = view.findViewById(R.id.view_get_save_path_TextView_destDir);
        ed_name = view.findViewById(R.id.view_get_save_path_EditText_Name);
        btn_cancel = view.findViewById(R.id.view_get_save_path_Button_cancel);
        btn_ok = view.findViewById(R.id.view_get_save_path_Button_ok);
        
        final FilesAdapter adapter=activity.adapters.get(window);
        if (adapter.ListType == 1)
        {
            tv_destDir.setText(utils.getCuPath(window));
        }

        if (adapter.ListType == 2)
        {
            tv_destDir.setText(utils.getCuPath(window));
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

        ed_name.setText(new File(path).getName());

        btn_cancel.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    dialog.dismiss();
                    callBack.onCancel();
                }
            });


        btn_ok.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    dialog.dismiss();
                    callBack.onSuccess(tv_destDir.getText().toString()+ed_name.getText().toString());
                    
                    
                }
            });



        dialog = new AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(false)
            .create();
        dialog.show();




    }



    private void checkExists(){
        File file=new File(tv_destDir.getText().toString(),ed_name.getText().toString());
        if(file.exists()){
            btn_ok.setText("覆盖");
        }else{
            btn_ok.setText("确定");
        }

    }



}
