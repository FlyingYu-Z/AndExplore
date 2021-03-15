package com.beingyi.app.AE.activity;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.base.BaseActivity;


public class webbrowser extends BaseActivity {

    Context context;
    ProgressBar progressBar;
    WebView webview;

    String url;

    public void init(){

        context=this;
        progressBar=findViewById(R.id.activity_webbrowser_ProgressBar1);
        webview=findViewById(R.id.activity_webbrowser_WebView);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webbrowser);
        init();

        Intent intent=getIntent();

        url=intent.getStringExtra(BASE128.decode("TDroNxxo64YIQk6RVasoFQ=="));

        webview.loadUrl(url);

        webview.setWebChromeClient(webChromeClient);
        webview.setWebViewClient(webviewClient);
        WebSettings webSettings=webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_webbrowser_toolbar);
        toolbar.setTitle(BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_web_open:

                Uri uri = Uri.parse(webview.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
                break;


            case R.id.action_web_refresh:
                webview.reload();
                break;

            case R.id.action_web_copy:

                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboard != null) {
                    clipboard.setPrimaryClip(ClipData.newPlainText(null, webview.getUrl()));

                }
                Toast.makeText(context, BASE128.decode("d7Oxe2GkFbuiXwMv7fkKkrEnqmkqS/vta6Vh8CjPGiA="), Toast.LENGTH_LONG).show();
                break;


            case R.id.action_web_exit:
                finish();
                break;
        }


        return super.onOptionsItemSelected(item);
    }










    //webviewClient主要帮助webview处理各种通知、请求事件
    private WebViewClient webviewClient=new WebViewClient(){

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error)
        {
            super.onReceivedError(view, request, error);

        }



        @Override
        public void onPageFinished(WebView view, String url)
        {

            webview.getSettings().setBlockNetworkImage(false);
            //判断webview是否加载了，图片资源
            if (!webview.getSettings().getLoadsImagesAutomatically())
            {
                //设置wenView加载图片资源
                webview.getSettings().setLoadsImagesAutomatically(true);
            }
            progressBar.setVisibility(View.GONE);
            super.onPageFinished(view, url);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            webview.getSettings().setBlockNetworkImage(false);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {

            return super.shouldOverrideUrlLoading(view, url);
        }


        @Override
        public void onLoadResource(WebView view, String url)
        {
            super.onLoadResource(view, url);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url)
        {
            if (url.contains(BASE128.decode("62SscmacPHAvyFrrlAlKbA==")))
            {
                try
                {
                    return new WebResourceResponse(BASE128.decode("uzaIdvZAYKIfhzwMbqUezw=="), BASE128.decode("YF+TOQhsLibxaX9DZrlrMw=="), context.getAssets().open(BASE128.decode("zp8P1shkMuW2tP8tbuE6JQ==")));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            return super.shouldInterceptRequest(view, url);
        }




    };


    private WebChromeClient webChromeClient=new WebChromeClient(){
        @Override
        public boolean onJsAlert(WebView webview, String url, String message, JsResult result)
        {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webview.getContext());
            localBuilder.setMessage(message).setPositiveButton(BASE128.decode("GXe18lf7nDG5Ik/56BTUhw=="), null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();

            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title)
        {
            super.onReceivedTitle(view, title);
            Log.i(BASE128.decode("cmz51A/BTpujsqDDgyW5Eg=="), BASE128.decode("DD6L5fmM4C+jSi6wvdDUVQ==") + title);
            getSupportActionBar().setTitle(title);
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress)
        {
            progressBar.setProgress(newProgress);
        }
    };




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (webview.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK )
        {
            webview.goBack();
            return true;
        }
        else
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }





}
