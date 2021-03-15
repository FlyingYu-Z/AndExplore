package com.beingyi.app.AE.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.beingyi.app.AE.utils.SPUtils;

public class SPEditText extends AppCompatEditText {

    Context context;

    public SPEditText(Context context) {
        super(context);
        init();
    }

    public SPEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SPEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init() {
        this.context = getContext();
        setWatch();
    }

    public void setWatch() {


        this.post(new Runnable() {
            @Override
            public void run() {

            }
        });


    }

    String key="";

    public void setHistory(final String tag){

        key = this.getClass().getName()+"_" + tag;
        String text=SPUtils.getString("input",key);
        setText(text);


        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                key = this.getClass().getName()+"_" + tag;
                SPUtils.putString( "input", key, getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }


}


