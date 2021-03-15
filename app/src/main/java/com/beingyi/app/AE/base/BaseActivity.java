package com.beingyi.app.AE.base;


import android.annotation.*;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;

import com.readystatesoftware.systembartint.*;
import com.beingyi.app.AE.*;
public class BaseActivity extends AppCompatActivity
{
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);   
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setStatus();

        //x.view().inject(this);
        context = this;


        //获取状态栏高度
        /**
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        ViewGroup viewgroup=getWindow().getDecorView().findViewById(android.R.id.content);
        LinearLayout.LayoutParams lp =(LinearLayout.LayoutParams) viewgroup.getLayoutParams(); 
        lp.setMargins(0, result, 0, 0);
        viewgroup.setLayoutParams(lp);
        **/

         
         Window window = getWindow();
         window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
         | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
         window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
         | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
         | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
         window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
         window.setNavigationBarColor(getResources().getColor(R.color.AppColor));

         


    }


    protected <T extends View> T find(int viewId) {
        return (T) findViewById(viewId);
    }



    public void setStatus(){

        // 4.4及以上版本开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);

        // 自定义颜色
        tintManager.setTintColor(getResources().getColor(R.color.AppColor));




    }



    @TargetApi(19)
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }






    public Context getContext() {
        return context;
    }


    public void StartActivity(Class<?> cls) {
        startActivity(new Intent(getContext(), cls));
    }



}

