package com.beingyi.apkfairy.base;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.beingyi.apkfairy.R;
import com.beingyi.apkfairy.utils.Conf;
import com.beingyi.apkfairy.utils.SPUtils;
public class BaseActivity extends AppCompatActivity
{
	Context context;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
		
        requestWindowFeature(Window.FEATURE_NO_TITLE);   
        if(SPUtils.getBoolean(context,"conf","isNightMode")){
            setTheme(R.style.NightTheme);
        }
        
        setStatus();
        
		//x.view().inject(this);
        
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                          | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // 虚拟导航键
        window.setNavigationBarColor(Color.parseColor(new Conf(context).getAppColor()));
        
    
	
    
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
    tintManager.setTintColor(Color.parseColor(new Conf(this).getAppColor()));
    
    
    
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
