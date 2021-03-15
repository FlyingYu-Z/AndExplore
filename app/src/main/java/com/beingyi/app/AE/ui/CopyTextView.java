package com.beingyi.app.AE.ui;
import android.content.Context;
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
                    text=text.substring(text.indexOf("：")+1,text.length());
                    
                    try
                    {
                        ClipboardManager clip = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                        clip.setText(text);
                        
                        ToastUtils.show("文字已复制到剪切板");
                    }
                    catch (Exception e)
                    {
                        ToastUtils.show("文字复制失败");
                    }

                    return false;
                }

            });


    }

}
