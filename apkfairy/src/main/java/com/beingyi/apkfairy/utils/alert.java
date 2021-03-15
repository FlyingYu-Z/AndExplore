package com.beingyi.apkfairy.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class alert
{

    public alert(final Context context, final String text)
    {



                AlertDialog dialog = new AlertDialog.Builder(context)

                    .setTitle("结果")
                    .setMessage(text)
                    .setCancelable(false)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    }).create();
                dialog.show();
            }


        
    
        
    }


