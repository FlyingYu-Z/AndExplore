package com.beingyi.app.AE.activity;

import com.beingyi.app.AE.application.MyApplication;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.beingyi.app.AE.R;
import com.beingyi.app.AE.base.BaseActivity;

import android.content.Context;

public class Test extends BaseActivity
{
    Context context;

    public void init(){
        context=this;


    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();




    }




}
