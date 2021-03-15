package com.beingyi.app.AE.utils.openFile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.beingyi.app.AE.activity.TextEditor;
import com.beingyi.app.AE.activity.webbrowser;

import java.io.File;

public class s_html extends s_base {

    String Path;
    File file;

    public s_html(Context mContext, int mWindow, String mPath, View view, boolean mIsInZip) {
        super(mContext, mWindow, view, mIsInZip);
        this.Path = mPath;
        this.file = new File(Path);


        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenu().add(1, 1, 1, "浏览");
        popupMenu.getMenu().add(2, 2, 2, "编辑");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case 1: {
                        Intent intent = new Intent(context, webbrowser.class);
                        intent.putExtra("url", "file://" + Path);
                        context.startActivity(intent);
                    }
                    break;

                    case 2: {
                        Intent intent = new Intent(context, TextEditor.class);
                        intent.putExtra("isFile", true);
                        intent.putExtra("Path", file.getAbsolutePath());
                        context.startActivity(intent);
                    }
                    break;
                }

                return true;
            }
        });


    }

}
