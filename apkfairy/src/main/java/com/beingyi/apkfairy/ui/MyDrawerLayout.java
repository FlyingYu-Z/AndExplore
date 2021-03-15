package com.beingyi.apkfairy.ui;


import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Megatronus on 2016/4/27.
 * Versions 1.0
 */
public class MyDrawerLayout extends DrawerLayout {
    private boolean isTouchMove = true ;

    public MyDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public MyDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyDrawerLayout(Context context) {
        super(context);
    }

    @Override
    public void removeDrawerListener(DrawerListener listener) {
        super.removeDrawerListener(listener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isTouchMove)
            return super.onTouchEvent(ev);
        else return  false ;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isTouchMove)
            return super.onInterceptTouchEvent(ev);
        else return false ;
    }

    public boolean isTouchMove() {
        return isTouchMove;
    }

    public void setIsTouchMove(boolean isTouchMove) {
        this.isTouchMove = isTouchMove;
    }
}
