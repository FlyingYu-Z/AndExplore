package com.beingyi.app.AE.ui;

import com.beingyi.app.AE.application.MyApplication;

import android.content.*;
import android.view.*;
import android.widget.*;
import com.beingyi.app.AE.*;
import com.beingyi.app.AE.application.*;


public class ToastUtils {

    public static void show(String msg) {
        show(msg, Toast.LENGTH_SHORT);
    }

    public static void show(int msg) {
        show(MyApplication.getContext().getString(msg), Toast.LENGTH_SHORT);
    }

    public static void showLong(String msg) {
        show(msg, Toast.LENGTH_LONG);
    }


    private static void show(String massage, int show_length) {
        Context context = MyApplication.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_toast, null);

        TextView title = (TextView) view.findViewById(R.id.item_toast_TextView);
        //设置显示的内容
        title.setText(massage);
        Toast toast = new Toast(context);
        //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 200);
        //设置显示时间
        toast.setDuration(show_length);

        toast.setView(view);
        toast.show();
    }


}  


