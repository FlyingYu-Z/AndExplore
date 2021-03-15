package com.beingyi.apkfairy.activity;





import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.beingyi.apkfairy.CallBack.OpenFileDialogCallBack;
import com.beingyi.apkfairy.R;
import com.beingyi.apkfairy.base.BaseActivity;
import com.beingyi.apkfairy.dialog.OpenFileDialog;
import com.beingyi.apkfairy.utils.ComUtils;
import com.beingyi.apkfairy.utils.CustomUrlSpan;
import com.beingyi.apkfairy.utils.SPUtils;
import com.beingyi.apkfairy.utils.SelfInfo;
import com.beingyi.apkfairy.utils.init;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class setting extends BaseActivity
{
    Context context;
    ComUtils comUtils;
    TextView tv_path;


    public void initview()
    {
        comUtils = new ComUtils(context);

        tv_path = (TextView)findViewById(R.id.activitysettingTextView_path);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        context = setting.this;
        initview();


        tv_path.setText(comUtils.getBackPath());




        initOnclick(tv_path);



    }

    
    
    
    @Override  
    protected Dialog onCreateDialog(int id) {  
        if(id==0){  
            Map<String, Integer> images = new HashMap<String, Integer>();  
            // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹  
            images.put(OpenFileDialog.sRoot, R.drawable.ic_home);// 根目录图标  
            images.put(OpenFileDialog.sParent, R.drawable.ic_up);//返回上一层的图标  
            images.put(OpenFileDialog.sFolder, R.drawable.ic_folder);//文件夹图标  
            images.put("wav", R.drawable.ic_wav);   //wav文件图标  
            images.put(OpenFileDialog.sEmpty, R.drawable.ic_file);
            OpenFileDialog open=new OpenFileDialog();
            Dialog dialog = open.createDialog(id, this, "选择路径", new OpenFileDialogCallBack() {  
                    @Override  
                    public void callback(Bundle bundle) {  
                        String path = bundle.getString("path");
                        
                        tv_path.setText(path);
                        
                        String input = bundle.getString("input")+"";
                        
                        if(input.equals("true")){
                            input_path();
                        }
                        
                    }  
                },   
                
                images);  
            return dialog;  
        }  
    
        return null;
        }
    


    public void initOnclick(View view)
    {

        LinearLayout ln=(LinearLayout)view.getParent();

        ln.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    change_path();
                }

            });


    }



    //选择路径对话框
    public void change_path(){
        
        showDialog(0);
        
    }
    
    
    
    
    //输入路径
    public void input_path()
    {


        boolean ishaspath=SPUtils.getBoolean(context, "conf", "ishaspath");

        String backpath="";
        if (ishaspath)
        {
            backpath = SPUtils.getString(context, "conf", "backpath");
        }
        else
        {
            backpath = init.app_dir + "backup/";

        }



        final EditText ed_path=new EditText(context);
        ed_path.setText(backpath);
        AlertDialog dialog = new AlertDialog.Builder(context)

            .setTitle("请设置备份路径")//设置对话框的标题
            .setView(ed_path)
            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            })
            .setNeutralButton("恢复默认", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                    
                    
                    SPUtils.putBoolean(context, "conf", "ishaspath", false);
                    tv_path.setText(comUtils.getBackPath());

                    
                    

                }
            })
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();

                    String path=ed_path.getText().toString();

                    if (path.endsWith("/"))
                    {


                    }
                    else
                    {
                        path = ed_path.getText().toString() + "/";
                    }


                    try
                    {

                        if (!new File(path).exists())
                        {
                            new File(path).mkdirs();
                            SPUtils.putString(context, "conf", "backpath", path);
                            SPUtils.putBoolean(context, "conf", "ishaspath", true);
                            
                        }
                    }
                    catch (Exception e)
                    {

                        Toast.makeText(context,"路径不合法",Toast.LENGTH_LONG).show();

                    }




                    tv_path.setText(comUtils.getBackPath());



                }
            }).create();
        dialog.show();


    }



    public void onclick_enterGroup(View view){
     
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + "XbWsKSIIxpQB_dzUpaTTJMbwC-8y560k"));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            Toast.makeText(context,"未安装QQ",Toast.LENGTH_SHORT).show();
        }
    
        
    }


    public void onclick_about(View view)
    {

        LayoutInflater inflater=LayoutInflater.from(context);
        View about=inflater.inflate(R.layout.view_about, null);

        TextView info=(TextView)about.findViewById(R.id.viewaboutTextView1);
        info.setText("本软件由小冰树开发\n官网http://www.beingyi.cn/\n关注我们，即时下载官方正版！" +
                     "\n" + "当前版本：" + SelfInfo.versionName(context));
        info.setText("本软件由小冰树开发！" +
                     "\n" + "当前版本：" + SelfInfo.versionName(context));

        interceptHyperLink(context, info);
        AlertDialog dialog = new AlertDialog.Builder(context)

            .setTitle("关于我们")
            .setView(about)
            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            })
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();

                }
            }).create();
        dialog.show();



    }





    /**
     * 拦截超链接
     * @param tv
     */
    public static void interceptHyperLink(Context context, TextView tv)
    {
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = tv.getText();
        if (text instanceof Spannable)
        {
            int end = text.length();
            Spannable spannable = (Spannable) tv.getText();
            URLSpan[] urlSpans = spannable.getSpans(0, end, URLSpan.class);
            if (urlSpans.length == 0)
            {
                return;
            }

            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
            // 循环遍历并拦截 所有http://开头的链接
            for (URLSpan uri : urlSpans)
            {
                String url = uri.getURL();
                if (url.indexOf("http://") == 0)
                {
                    CustomUrlSpan customUrlSpan = new CustomUrlSpan(context, url);
                    spannableStringBuilder.setSpan(customUrlSpan, spannable.getSpanStart(uri),
                                                   spannable.getSpanEnd(uri), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                }
            }
            tv.setText(spannableStringBuilder);
        }
    }












}
