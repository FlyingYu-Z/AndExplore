package com.beingyi.app.AE.activity;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.base.BaseActivity;
import com.protectsoft.webviewcode.Codeview;

public class CodeViewActivity extends BaseActivity {

    Context context;

    WebView webView;

    public void init(){
        context=this;
        webView=find(R.id.activity_code_view_WebView);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeview);
        init();

        Intent intent=getIntent();

        String code=intent.getStringExtra("code");

        Codeview.with(getApplicationContext())
                .withCode(code)
                .into(webView);


        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);



    }




}
