package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
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
            BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="),
            BASE128.decode("9PsPXaWODQXV1K7ZZBIWaw=="),
            BASE128.decode("pBVx2FEwTsetY6H0wbN5+g=="),
            BASE128.decode("0pgS4NwFpl4LnqM+Okt5nA=="),
            BASE128.decode("QRcHLfBQoPWvCQkdHpUVEg=="),
            BASE128.decode("rKeOvHHEGNKvL9rfnsbmbBc6851N1Lg/CkAIgUvVwng=")
        };**/
        
        final String[] mode=new String[]{
            BASE128.decode("B8T9mSJENI+tkPi/vSU8FQ=="),
            BASE128.decode("B8T9mSJENI+tkPi/vSU8FQ=="),
            BASE128.decode("Wx+syHAFTWHfLp7vtFPBkw=="),
            BASE128.decode("Wx+syHAFTWHfLp7vtFPBkw=="),
            BASE128.decode("IQjkYKpcJbNj8Uu1nyQvbQ=="),
            BASE128.decode("IQjkYKpcJbNj8Uu1nyQvbQ==")
        };
        
        
        AlertDialog dialog = new AlertDialog.Builder(context)

            .setTitle(BASE128.decode("2QWBgNbDR+oXder3XbFkTw=="))
            .setCancelable(true)
            .setItems(new String[]{
                BASE128.decode("npRwttaKiAp8/u4cwuFlxMXhNQ+XAnMOt51H7OIGmwE="),
                BASE128.decode("FC47OkzG+sGuUGZqH4JX5BUS/nZDPIYjR0+U2VhB1yE="),
                BASE128.decode("f/q+vtbzi6KfX1l7IbxIMcLTtbO4YOef4hSu36pQIbM="),
                BASE128.decode("f/q+vtbzi6KfX1l7IbxIMVLs4DrXXAIgW+ohKJCXInY="),
                BASE128.decode("Eq4zb9ZdWjhyhGI0gZPqZMLTtbO4YOef4hSu36pQIbM="),
                BASE128.decode("Eq4zb9ZdWjhyhGI0gZPqZFLs4DrXXAIgW+ohKJCXInY="),
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

            .setPositiveButton(BASE128.decode("xr/TmYmg1a8edM78/WSQ7g=="), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            }).create();
        dialog.show();



    }


}
