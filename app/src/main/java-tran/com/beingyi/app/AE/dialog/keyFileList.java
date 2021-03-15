package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.util.List;

public class keyFileList extends baseDialog {


    public keyFileList(Context mContext, int mWindow, String mPath, List<String> list) {
        super(mContext, mWindow, mPath);

        String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, array);

        final ListView listView = new ListView(context);
        listView.setAdapter(arrayAdapter);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(MyApplication.getInstance().getString("098f9e940d9c0517f2a86758de701f1b"))
                .setView(listView)
                .setCancelable(false)
                .setPositiveButton(MyApplication.getInstance().getString("b15d91274e9fc68608c609999e0413fa"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .create();
        dialog.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String path = array[position];
                File file = new File(path);

                adapter.getFiles(file.getParent());
                adapter.setItemHighLight(file.getAbsolutePath());
                dialog.dismiss();

            }
        });


    }
}
