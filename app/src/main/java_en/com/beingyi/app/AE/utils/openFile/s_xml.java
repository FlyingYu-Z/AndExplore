package com.beingyi.app.AE.utils.openFile;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.beingyi.app.AE.bean.PathInfo;
import com.bigzhao.xml2axml.Encoder;
import com.bigzhao.xml2axml.test.AXMLPrinter;
import com.beingyi.app.AE.R;
import com.beingyi.app.AE.activity.TextEditor;
import com.beingyi.app.AE.application.MyApplication;
import com.beingyi.app.AE.dialog.alert;
import com.beingyi.app.AE.dialog.getSavePath;
import com.beingyi.app.AE.interfaces.GetSavePathCallBack;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.FileUtils;

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

                            outFile = new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + BASE128.decode("uVu1Hzd9Y/FVJNcGiTgL1w=="));

                            new getSavePath(context, window, outFile.getAbsolutePath(), new GetSavePathCallBack(){

                                    @Override
                                    public void onSuccess(String filePath)
                                    {
                                        try
                                        {
                                            AXMLPrinter.main(new String[]{file.getAbsolutePath()});
                                            AXMLPrinter.out.close();
                                            ToastUtils.show(BASE128.decode("b6EWr/j3oBbo+/879a0HEg=="));
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

                            outFile = new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + BASE128.decode("L6sUo0IDrZ4ksgGe7qV6Cw=="));

                            new getSavePath(context, window, outFile.getAbsolutePath(), new GetSavePathCallBack(){

                                    @Override
                                    public void onSuccess(String filePath)
                                    {

                                        try
                                        {
                                            Encoder e = new Encoder();
                                            byte[] bs = e.encodeFile(MyApplication.getContext(), file.getAbsolutePath());
                                            org.apache.commons.io.FileUtils.writeByteArrayToFile(outFile, bs);                 
                                            ToastUtils.show(BASE128.decode("HnnXc97Hy9EummMbq+oWVA=="));
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
                            intent.putExtra(BASE128.decode("/h+2Zkr8crYDBzmTRWy5Cg=="), true);
                            intent.putExtra(BASE128.decode("cr62CT1jqczIn9Pg6z6GZg=="), file.getAbsolutePath());
                            intent.putExtra(BASE128.decode("aDp+/gaMqx2Pa8OZlGXlQA=="), FileUtils.isBinary(file.getAbsolutePath()));
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
