package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.utils.AEUtils;

import proguard.obfuscate.ClassObfuscator;

public class License  extends Dialog{

    Context context;

    TextView tv_title;
    TextView tv_content;
    Button btn_cancel;
    Button btn_ok;


    public License(Context mContext){
        super(mContext);
        this.context=mContext;


        final Dialog dialog=new Dialog(context);
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        View view=View.inflate(context, R.layout.view_license,null);

        tv_title=view.findViewById(R.id.view_dialog_TextView_title);
        tv_content=view.findViewById(R.id.view_dialog_TextView_content);
        btn_cancel=view.findViewById(R.id.view_dialog_Button_cancle);
        btn_ok=view.findViewById(R.id.view_dialog_Button_ok);

        ViewGroup.LayoutParams layoutParams = new  ViewGroup.LayoutParams(width, height);
        setContentView(view, layoutParams);
        setCancelable(true);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = height;
        p.width = height;
        p.gravity = Gravity.TOP;
        p.alpha =0.9f;
        getWindow().setGravity(Gravity.CENTER | Gravity.TOP);
        getWindow().setAttributes(p);


    }


    public void show(String title,String content){

        tv_title.setText(title);
        tv_content.setText(content);

        show();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                AEUtils utils=new AEUtils(context);
                utils.setIsAgreeLicense(true);
            }
        });

    }



}
