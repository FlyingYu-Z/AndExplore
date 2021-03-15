package com.beingyi.app.AE.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.beingyi.app.AE.R;
import com.beingyi.app.AE.application.MyApplication;
import com.beingyi.app.AE.base.BaseActivity;
import com.beingyi.app.AE.fragment.Fsettings;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

public class settings extends BaseActivity
{
    Context context;

    Toolbar toolbar;
    FrameLayout frameLayout;

    public void init(){
        context=this;
        toolbar=find(R.id.activity_settings_Toolbar);
        frameLayout=find(R.id.activity_settings_FrameLayout);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();

        setSupportActionBar(toolbar);

        Fsettings fsettings=new Fsettings();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.activity_settings_FrameLayout,fsettings).commit();

    }





}
