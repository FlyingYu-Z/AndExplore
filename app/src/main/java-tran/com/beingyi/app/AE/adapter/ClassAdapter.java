package com.beingyi.app.AE.adapter;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beingyi.app.AE.activity.CodeViewActivity;
import com.beingyi.app.AE.activity.DexEditor;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.utils.Smali2Java;
import com.beingyi.app.AE.R;
import com.beingyi.app.AE.activity.TextEditor;
import com.beingyi.app.AE.dialog.alert;
import com.beingyi.app.AE.holder.FileInfoHolder;
import com.beingyi.app.AE.utils.AEUtils;
import com.beingyi.app.AE.utils.ClassTree;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.jf.baksmali.Adaptors.ClassDefinition;
import org.jf.baksmali.BaksmaliOptions;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.util.IndentingWriter;


public class ClassAdapter extends BaseAdapter {


    Context context;
    ClassTree classTree;
    DexEditor activity;
    AEUtils utils;
    ListView listview;
    List<String> classList = new ArrayList<>();
    Toolbar toolbar;

    boolean isChoosed = false;

    public ClassAdapter(final Context context, ClassTree mTree, ListView mlistview, List<String> mClassList, Toolbar mToolbar) {
        this.context = context;
        this.classTree = mTree;
        this.activity = (DexEditor) context;
        this.utils = new AEUtils(context);

        this.listview = mlistview;
        this.classList = mClassList;
        this.toolbar = mToolbar;


        toolbar.setTitle("/" + classTree.tree.getCurPath());
        toolbar.setTitleTextColor(Color.WHITE);

        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (isChoosed) {

                    if (listview.isItemChecked(position)) {
                        listview.setItemChecked(position, true);
                    } else {
                        listview.setItemChecked(position, false);
                    }

                    if (listview.getCheckedItemCount() == 0) {
                        clearChoosed();
                    }

                    notifyDataSetChanged();
                    return;
                }


                String curFile = (String) listview.getItemAtPosition(position);
                if (classTree.tree.isDirectory(curFile)) {
                    classList.clear();
                    classList = classTree.getList(curFile);
                    notifyDataSetChanged();
                    toolbar.setTitle("/" + classTree.tree.getCurPath());
                    return;
                }


                try {
                    classTree.setCurrnetClass(classTree.tree.getCurPath()+curFile);
                    itemClick(view,curFile);
                } catch (Exception e) {
                    new alert(context, e.toString());
                }

            }

        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                String curFile = (String) listview.getItemAtPosition(position);


                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenu().add(1, 1, 1, listview.isItemChecked(position) ? MyApplication.getInstance().getString("76159d0d1261c0b6c310901244457e36") : MyApplication.getInstance().getString("153fa67a7fb6ada66a1fcccabbbfab72"));
                popupMenu.getMenu().add(2, 2, 2, MyApplication.getInstance().getString("66eeacd93a7c1bda93906fe908ad11a0"));
                popupMenu.getMenu().add(3, 3, 3, MyApplication.getInstance().getString("101195543165c689abbb2d927f1fe500"));
                popupMenu.getMenu().add(4, 4, 4, MyApplication.getInstance().getString("2f4aaddde33c9b93c36fd2503f3d122b"));
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        switch (id) {
                            case 1:
                                listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                                isChoosed = true;
                                if (listview.isItemChecked(position)) {
                                    listview.setItemChecked(position, false);
                                } else {
                                    listview.setItemChecked(position, true);
                                }
                                if (listview.getCheckedItemCount() == 0) {
                                    clearChoosed();
                                }
                                break;

                            case 2:
                                chooseAll();
                                break;

                            case 3:
                                clearChoosed();
                                break;

                            case 4:
                                String cuPath = classTree.tree.getCurPath();
                                if (!isChoosed) {
                                    classTree.removeClass(classTree.tree.getCurPath() + curFile);
                                } else {
                                    for (int i = 0; i < listview.getCount(); i++) {
                                        //如果是选中的
                                        if (listview.isItemChecked(i)) {
                                            String curItemFile = (String) listview.getItemAtPosition(i);
                                            classTree.removeClass(classTree.tree.getCurPath() + curItemFile);
                                        }
                                    }


                                }

                                classList.clear();
                                classList = classTree.tree.list(cuPath);
                                clearChoosed();
                                activity.isChanged = true;
                                break;
                        }

                        return true;
                    }
                });


                return true;
            }
        });


    }


    public void clearChoosed() {

        isChoosed = false;
        listview.clearChoices();
        listview.setChoiceMode(ListView.CHOICE_MODE_NONE);
        for (int i = 0; i < listview.getCount(); i++) {
            listview.setItemChecked(i, false);
        }

        notifyDataSetChanged();
    }

    public void chooseAll() {

        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        isChoosed = true;
        for (int i = 0; i < listview.getCount(); i++) {
            listview.setItemChecked(i, true);
        }
    }

    public void refresh() {
        String cuPath = classTree.tree.getCurPath();
        classList.clear();
        classList = classTree.tree.list(cuPath);
        notifyDataSetChanged();
        clearChoosed();
    }


    public void goParent() {
        if (classTree.tree.getCurPath().equals("")) {
            classTree.clearAll();
            activity.finish();
        } else {
            classList.clear();
            classList = classTree.getList("../");
            notifyDataSetChanged();
            toolbar.setTitle("/" + classTree.tree.getCurPath());
        }

    }


    public void itemClick(View view,String curFile) {


        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenu().add(1, 1, 1, MyApplication.getInstance().getString("95b351c86267f3aedf89520959bce689"));
        popupMenu.getMenu().add(2, 2, 2, MyApplication.getInstance().getString("961184670c9c338f7bccd36a59506737"));
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case 1: {
                        String smali = getSmaliByType(classTree.classMap.get(classTree.tree.getCurPath() + curFile), "L" + (classTree.tree.getCurPath() + curFile) + ";");
                        byte[] b = smali.getBytes();
                        TextEditor textEditor = new TextEditor();
                        TextEditor.classTree = classTree;
                        Intent intent = new Intent(context, textEditor.getClass());
                        intent.putExtra("isFile", false);
                        intent.putExtra("input", b);
                        intent.putExtra("isSmali", true);
                        context.startActivity(intent);
                    }

                        break;

                    case 2:

                        final AlertProgress progres = new AlertProgress(context);
                        new Thread() {
                            @Override
                            public void run() {
                                progres.setLabel(MyApplication.getInstance().getString("f013ea9dcba3f5ca1278aa850931fec8"));
                                progres.setNoProgress();
                                progres.show();
                                try {

                                    String content=Smali2Java.translate(classTree.curClassDef);

                                    Intent intent=new Intent(context,CodeViewActivity.class);
                                    intent.putExtra("code",content);
                                    context.startActivity(intent);

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //ToastUtils.show(MyApplication.getInstance().getString("6bfe16584f867f8128f075b6f43e8a8c"));
                                        }
                                    });

                                } catch (final Exception e) {
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new alert(context,e.toString());
                                        }
                                    });

                                }

                                progres.dismiss();
                            }
                        }.start();



                        break;
                }

                return true;
            }
        });


    }


    @Override
    public int getCount() {
        if (classList != null && classList.size() > 0) {
            return classList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (classList != null && classList.size() > 0) {
            return classList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    public List<String> getData() {
        return classList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        FileInfoHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_file, null);
            holder = new FileInfoHolder();

            holder.Icon = (ImageView) convertView.findViewById(R.id.item_file_ImageView_icon);
            holder.Name = (TextView) convertView.findViewById(R.id.item_file_TextView_name);
            holder.Time = (TextView) convertView.findViewById(R.id.item_file_TextView_time);
            holder.Size = (TextView) convertView.findViewById(R.id.item_file_TextView_size);
            holder.State = (TextView) convertView.findViewById(R.id.item_file_TextView_state);

            convertView.setTag(holder);


        } else {
            holder = (FileInfoHolder) convertView.getTag();
        }


        String className = classList.get(position);

        holder.Name.setText(className);
        holder.Time.setVisibility(View.INVISIBLE);
        holder.Size.setVisibility(View.INVISIBLE);

        if (classTree.tree.isDirectory(className)) {
            holder.Icon.setImageResource(R.drawable.ic_folder);
        } else {
            holder.Icon.setImageResource(R.drawable.ic_clazz);
        }


        if (listview.isItemChecked(position)) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.AppColor));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }


    public static String getSmaliByType(ClassDef classDef, String Type) {
        String code = null;
        try {
            StringWriter stringWriter = new StringWriter();
            IndentingWriter writer = new IndentingWriter(stringWriter);
            ClassDefinition classDefinition1 = new ClassDefinition(new BaksmaliOptions(), classDef);
            classDefinition1.writeTo(writer);
            writer.close();
            code = stringWriter.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return code;
    }


}
