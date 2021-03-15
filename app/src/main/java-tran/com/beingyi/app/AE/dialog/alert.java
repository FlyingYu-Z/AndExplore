package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class alert
{

    public alert(final Context context, final String text)
    {


        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                AlertDialog dialog = new AlertDialog.Builder(context)

                        .setMessage(text)
                        .setCancelable(false)

                        .setPositiveButton(MyApplication.getInstance().getString("38cf16f2204ffab8a6e0187070558721"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();


            }
        });


        
    }





}



