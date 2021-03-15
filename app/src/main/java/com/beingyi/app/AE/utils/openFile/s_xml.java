package com.beingyi.app.AE.utils.openFile;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.beingyi.app.AE.bean.PathInfo;
import com.bigzhao.xml2axml.Encoder;
import com.beingyi.app.AE.R;
import com.beingyi.app.AE.activity.TextEditor;
import com.beingyi.app.AE.application.MyApplication;
import com.beingyi.app.AE.dialog.alert;
import com.beingyi.app.AE.dialog.getSavePath;
import com.beingyi.app.AE.interfaces.GetSavePathCallBack;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.FileUtils;
import com.bigzhao.xml2axml.tools.AxmlDecoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;

import maobyte.xml.decode.AXmlDecoder;

public class s_xml extends s_base
{
    String Path;
    File file;

    public s_xml(Context mContext, int mWindow, String mPath, View view,boolean mIsInZip)
    {
        super(mContext, mWindow, view,mIsInZip);
        this.Path = mPath;
        this.file = new File(Path);

        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.file_xml, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item)
                {
                    int id=item.getItemId();
                    final File outFile;
                    switch (id)
                    {
                        case R.id.action_xml_decompile:

                            outFile = new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + "_src_.xml");

                            new getSavePath(context, window, outFile.getAbsolutePath(), new GetSavePathCallBack(){

                                    @Override
                                    public void onSuccess(String filePath)
                                    {
                                        try
                                        {
                                            AxmlDecoder.decode(file.getAbsolutePath(),outFile.getPath());
                                            ToastUtils.show("反编译完成");
                                            adapter.refresh();
                                            adapter.setItemHighLight(outFile.getAbsolutePath());
                                        }
                                        catch (Exception e)
                                        {
                                            new alert(context, e.toString());
                                        }

                                    }

                                    @Override
                                    public void onCancel()
                                    {

                                    }

                                });

                            break;

                        case R.id.action_xml_compile:

                            outFile = new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + "_encode_.xml");

                            new getSavePath(context, window, outFile.getAbsolutePath(), new GetSavePathCallBack(){

                                    @Override
                                    public void onSuccess(String filePath)
                                    {

                                        try
                                        {
                                            Encoder e = new Encoder();
                                            byte[] bs = e.encodeFile(MyApplication.getContext(), file.getAbsolutePath());
                                            org.apache.commons.io.FileUtils.writeByteArrayToFile(outFile, bs);                 
                                            ToastUtils.show("回编译完成");
                                            adapter.refresh();
                                            adapter.setItemHighLight(filePath);
                                        }
                                        catch (Exception e)
                                        {
                                            new alert(context, e.toString());
                                        }

                                    }

                                    @Override
                                    public void onCancel()
                                    {

                                    }

                                });



                            break;
                        case R.id.action_xml_strings:
                            new s_txt(context, window, file.getAbsolutePath(), view,isInZip);
                            break;
                        case R.id.action_xml_edit:

                            Intent intent=new Intent(context, TextEditor.class);
                            intent.putExtra("isFile", true);
                            intent.putExtra("Path", file.getAbsolutePath());
                            intent.putExtra("isEditAxml", FileUtils.isBinary(file.getAbsolutePath()));
                            context.startActivity(intent);


                            break;

                    }

                    return true;
                }
            });

        if (FileUtils.isBinary(file.getAbsolutePath()))
        {
            popupMenu.getMenu().findItem(R.id.action_xml_compile).setVisible(false);
            popupMenu.getMenu().findItem(R.id.action_xml_decompile).setVisible(true);
            popupMenu.getMenu().findItem(R.id.action_xml_strings).setVisible(true);
            popupMenu.getMenu().findItem(R.id.action_xml_edit).setVisible(true);
        }
        else
        {
            popupMenu.getMenu().findItem(R.id.action_xml_compile).setVisible(true);
            popupMenu.getMenu().findItem(R.id.action_xml_decompile).setVisible(false);
            popupMenu.getMenu().findItem(R.id.action_xml_strings).setVisible(false);
            popupMenu.getMenu().findItem(R.id.action_xml_edit).setVisible(true);
        }


    }


}
