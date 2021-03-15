package com.beingyi.app.AE.ui;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;import android.content.Context;
import android.text.ClipboardManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class CopyTextView extends TextView
{
    Context context;
    TextView textView;

    public CopyTextView(Context context)
    {
        super(context);
        init();

    }



    public CopyTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();

    }


    public CopyTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }


    public void init()
    {
        context = this.getContext();
        textView=this;

        this.setOnLongClickListener(new OnLongClickListener(){

                @Override
                public boolean onLongClick(View view)
                {
                    String text=textView.getText().toString();
                    text=text.substring(text.indexOf(BASE128.decode("iUMKMmZbTxXeSR4CpWfITQ=="))+1,text.length());
                    
                    try
                    {
                        ClipboardManager clip = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                        clip.setText(text);
                        
                        ToastUtils.show(BASE128.decode("E7ecG9QWdAkSkxRmf8SGeLEnqmkqS/vta6Vh8CjPGiA="));
                    }
                    catch (Exception e)
                    {
                        ToastUtils.show(BASE128.decode("BJXvyf87TftmUhCRmFj2YuO8qc2Y/gPGYuT7uWo12lM="));
                    }

                    return false;
                }

            });


    }

}
