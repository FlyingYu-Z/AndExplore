package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
import android.content.*;
import android.support.v7.app.*;
import com.beingyi.app.AE.utils.*;
import com.beingyi.app.AE.activity.*;

public class sortType
{
    Context context;
    AEUtils utils;
    int window;
    public sortType(Context mContext,int windows)
    {
        this.context = mContext;
        this.utils=new AEUtils(context);
        this.window=windows;
        
        /**
        final String[] mode=new String[]{
            "",
            "-r",
            "-lrt",
            "-lt",
            "-l|sort -n -k 5",
            "-l|sort -nr -k 5"
        };**/
        
        final String[] mode=new String[]{
            "Name",
            "Name",
            "Time",
            "Time",
            "Size",
            "Size"
        };
        
        
        AlertDialog dialog = new AlertDialog.Builder(context)

            .setTitle(MyApplication.getInstance().getString("a2946a1a42abc1e524ae69dd475b30af"))
            .setCancelable(true)
            .setItems(new String[]{
                MyApplication.getInstance().getString("377440f6ef8c9e7fe00dc60790efd661"),
                MyApplication.getInstance().getString("de52dd76455bf76977c42a9661548e04"),
                MyApplication.getInstance().getString("c370ba636a1eaff449572cae6a2f7ba4"),
                MyApplication.getInstance().getString("cdb713ebb2a0545686bddcc556043aec"),
                MyApplication.getInstance().getString("1c1a978fcc2964ceab75c4688d8ecbfc"),
                MyApplication.getInstance().getString("c275ee15ec65d8487f7ad5638c04046a"),
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    if(which==1||which==3||which==5){
                        utils.setIsDescSort(window,true);
                    }else{
                        utils.setIsDescSort(window,false);
                    }
                    
                    
                    switch (which)
                    {
                        default:
                        utils.setSortType(window,mode[which]);
                        dialog.dismiss();
                        ((MainActivity)context).refreshList();
                        break;
                    }
                    
                    dialog.dismiss();
                }
			})

            .setPositiveButton(MyApplication.getInstance().getString("b15d91274e9fc68608c609999e0413fa"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            }).create();
        dialog.show();



    }


}
