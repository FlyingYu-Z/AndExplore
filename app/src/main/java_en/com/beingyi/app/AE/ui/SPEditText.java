package com.beingyi.app.AE.ui;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
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

    String key=BASE128.decode("FzrznU3UuD8KQAiBS9XCeA==");

    public void setHistory(final String tag){

        key = this.getClass().getName()+BASE128.decode("9wphzVfcegbjwNAGjcJYMw==") + tag;
        String text=SPUtils.getString(context,BASE128.decode("5LauhhqS4nOX+qM2+Dz8FQ=="),key);
        setText(text);


        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                key = this.getClass().getName()+BASE128.decode("9wphzVfcegbjwNAGjcJYMw==") + tag;
                SPUtils.putString(context, BASE128.decode("5LauhhqS4nOX+qM2+Dz8FQ=="), key, getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }


}
