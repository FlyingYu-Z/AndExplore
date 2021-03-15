package com.beingyi.app.AE.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.dialog.setKeyStore;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.Conf;

public class Fsettings extends PreferenceFragment {

    Conf conf;

    public Fsettings() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        conf=new Conf(getActivity());

        addPreferencesFromResource(R.xml.pre_settings);

    }


    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){

        Preference language=findPreference("pre_settings_language");

        language.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {

                conf.setLanguage(o.toString());
                restartApplication(getActivity());
                return false;
            }
        });




        EditTextPreference ed_confKey=(EditTextPreference)findPreference("confKey");
        ed_confKey.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                if ("confKey".equals(preference.getKey())) {
                    new setKeyStore(getActivity());
                }

                ed_confKey.getDialog().dismiss();
                return true;
            }
        });




    }


    public void restartApplication(Context context) {
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }


}
