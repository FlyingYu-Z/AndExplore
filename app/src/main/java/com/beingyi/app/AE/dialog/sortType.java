package com.beingyi.app.AE.dialog;

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

            .setTitle("排序方式")
            .setCancelable(true)
            .setItems(new String[]{
                "按文件名(升序)(默认)",
                "按文件名(降序)",
                "按修改时间(升序)",
                "按修改时间(降序)",
                "按文件大小(升序)",
                "按文件大小(降序)",
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

            .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            }).create();
        dialog.show();



    }


}
