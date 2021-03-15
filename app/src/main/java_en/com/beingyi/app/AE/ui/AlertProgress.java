package com.beingyi.app.AE.ui;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.beingyi.app.AE.R;
import android.app.Activity;

public class AlertProgress
{
    Context context;
    Activity activity;
    AlertDialog dialog;

    TextView tv_title;
    TextView tv_label;
    ProgressBar pb_progress;

    public AlertProgress(Context mContext)
    {
        this.context = mContext;
        this.activity = (Activity)context;

        View view=View.inflate(context, R.layout.item_progress, null);
        tv_title = view.findViewById(R.id.item_progress_TextView_title);
        tv_label = view.findViewById(R.id.item_progress_TextView_label);
        pb_progress = view.findViewById(R.id.item_progress_ProgressBar_progress);

        dialog = new AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(false)
            .create();



    }

    public void setNoProgress()
    {

        activity.runOnUiThread(new Runnable(){
                @Override
                public void run()
                {
                   pb_progress.setVisibility(View.GONE);
                }
            });
    }


    public void setLabel(final String label)
    {

        activity.runOnUiThread(new Runnable(){
            @Override
            public void run()
            {

                tv_label.setVisibility(View.VISIBLE);
                tv_label.setText(label);
            }
        });
    }

    public void setTitle(final String title)
    {

        activity.runOnUiThread(new Runnable(){
            @Override
            public void run()
            {

                tv_title.setVisibility(View.VISIBLE);
                tv_title.setText(title);
            }
        });
    }


    public void setProgress(final int value, final int max)
    {

        activity.runOnUiThread(new Runnable(){
                @Override
                public void run()
                {

                    pb_progress.setVisibility(View.VISIBLE);
                    pb_progress.setProgress(value);
                    pb_progress.setMax(max);
                }
            });
    }


    public void show()
    {

        activity.runOnUiThread(new Runnable(){
                @Override
                public void run()
                {

                    dialog.show();
                }
            });
    }


    public void dismiss()
    {

        activity.runOnUiThread(new Runnable(){
                @Override
                public void run()
                {

                    dialog.dismiss();
                }
            });
    }




}
