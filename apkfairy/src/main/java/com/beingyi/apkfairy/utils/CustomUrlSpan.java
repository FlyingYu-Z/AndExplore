package com.beingyi.apkfairy.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.style.ClickableSpan;
import android.view.View;

public class CustomUrlSpan extends ClickableSpan
{

    private Context context;
    private String url;
    public CustomUrlSpan(Context context,String url){
        this.context = context;
        this.url = url;
    }

    @Override
    public void onClick(View widget) {
        // 在这里可以做任何自己想要的处理
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        
        context.startActivity(intent);
    }

}

