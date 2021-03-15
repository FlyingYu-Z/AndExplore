package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.beingyi.app.AE.activity.TextEditor;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.ui.ToastUtils;
import com.beingyi.app.AE.utils.ZipTree;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class openFileByWay extends baseDialog {
    public openFileByWay(Context mContext, int mWindow, String mPath) {
        super(mContext, mWindow, mPath);

        List<String> menus=new ArrayList<>();
        menus.add(BASE128.decode("mPwcyWd81K3VfL59gTHsODOwfsQkL9U1nmsIC5rdN4A="));
        menus.add(BASE128.decode("5/m3PjIh7dRkbrqFe34Mlw=="));
        menus.add(BASE128.decode("yHco1VVswn3snZ3WlKVWzEpm8o8V3+cW+InSE7Hx/Z0="));

        final String[] items = menus.toArray(new String[menus.size()]);
        ListAdapter itemlist = new ArrayAdapter(context, android.R.layout.simple_list_item_1, items);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(BASE128.decode("M7dbFCofM5j7KyPCqjgXjlyZPyyJmcUjYWo9NSYDbvs="));
        builder.setAdapter(itemlist, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                if(which==0) {
                    Intent intent = new Intent(context, TextEditor.class);
                    intent.putExtra(BASE128.decode("/h+2Zkr8crYDBzmTRWy5Cg=="), true);
                    intent.putExtra(BASE128.decode("cr62CT1jqczIn9Pg6z6GZg=="), Path);
                    context.startActivity(intent);

                }

                if(which==1){

                    final AlertProgress progres=new AlertProgress(context);
                    new Thread(){
                        @Override
                        public void run()
                        {


                            progres.setLabel(BASE128.decode("NG6/2bPeC5kiLGc6CtSQOA=="));
                            progres.setNoProgress();
                            progres.show();
                            try
                            {
                                adapter.zipTree = new ZipTree(Path);
                                adapter.ListType = 2;

                                activity.runOnUiThread(new Runnable(){
                                    @Override
                                    public void run()
                                    {
                                        adapter.refresh();
                                    }
                                });
                            }
                            catch (Exception e)
                            {
                                activity.showDialog(e.toString());
                            }

                            progres.dismiss();

                        }
                    }.start();


                }

                if(which==2){
                    openFileByPath(context,Path);
                }


            }
        });

        builder.create().show();




    }

    /**
     * 根据路径打开文件
     * @param context 上下文
     * @param path 文件路径
     */
    public static void openFileByPath(Context context,String path) {
        if(context==null||path==null)
            return;


        String type = BASE128.decode("FzrznU3UuD8KQAiBS9XCeA==");
        for(int i =0;i<MATCH_ARRAY.length;i++){

            if(path.toString().contains(MATCH_ARRAY[i][0].toString())){
                type = MATCH_ARRAY[i][1];
                break;
            }

        }


        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, BASE128.decode("qTy0S6HzisSLDitZhdnyDLYR5CBsaD+VxzLEEjOuQszDTW8RWwjrqSpwfKA5mfeE"), new File(path));
            intent.setDataAndType(contentUri, type);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(new File(path)), type);
        }

        if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            context.startActivity(intent);
        }




    }



    private static final String[][] MATCH_ARRAY={
            //{后缀名，    文件类型}
            {BASE128.decode("Mf8pRhTPqlLPfcTTmAztcw=="),    BASE128.decode("/9PVsLsyXW1Y6495wZxDLg==")},
            {BASE128.decode("6ATRmqaRZDN1uwz9jSj3Sw=="),    BASE128.decode("HVyfc8yGeHIt6E3KP4FKKcvlBbqfimBwR5IpRinXMyLyBXHWDFNdcoV2+JBq2PyO")},
            {BASE128.decode("wRniRds12dcQO+8KuObVow=="),    BASE128.decode("/CKZ59X0n7KvI9fxhnbUig==")},
            {BASE128.decode("gujZM6geF4iYhwCNYJ4dmg=="),    BASE128.decode("0jxZWF1ByCNbVrQ+QG6qyA==")},
            {BASE128.decode("Nz05UsJ+zte1EAy9adTMWQ=="),    BASE128.decode("TmNzjAf224wHnJexi8RRGoUKvByBIU4/Scju0zBjhV8=")},
            {BASE128.decode("tgnPZ+MDl8JrXzejf6xfEw=="),      BASE128.decode("4FSIaLjT7UCeMhLlKJTXoA==")},
            {BASE128.decode("08K2+g8EoBgIBPZV+1wTLg=="),        BASE128.decode("QxfmFuCs3ln/tlsPUeahDA==")},
            {BASE128.decode("A3Ek6g3difgUXV5pt2NclQ=="),    BASE128.decode("TmNzjAf224wHnJexi8RRGoUKvByBIU4/Scju0zBjhV8=")},
            {BASE128.decode("dDntyh++nTPOD9BFu2XJzg=="),    BASE128.decode("QxfmFuCs3ln/tlsPUeahDA==")},
            {BASE128.decode("7aevcdVxDk9yoEL55Pk59g=="),    BASE128.decode("QxfmFuCs3ln/tlsPUeahDA==")},
            {BASE128.decode("bews1Ne2841DOLS3JMBmIg=="),    BASE128.decode("Ht1dH1aah7/ksOf3iLm4sVSJpS7itvCRxmYaQJodNok=")},
            {BASE128.decode("of4TaN+vVhxRnj83kHKvKA=="),    BASE128.decode("TmNzjAf224wHnJexi8RRGoUKvByBIU4/Scju0zBjhV8=")},
            {BASE128.decode("Tpn6k4oNPeNzJIfZ3xe/Kg=="),    BASE128.decode("000VQDhecrmhuj3sfvKctg==")},
            {BASE128.decode("68wpQcUcehY3fMj2hbbThg=="),    BASE128.decode("0WRuLNQgUQG+cFcEsSyC5yCMZecUcoC1870WYBcNzY4=")},
            {BASE128.decode("wYB3ZbI6z6Gj8ksHp6NIvg=="),        BASE128.decode("cG6ra6K0leElCpI+hpAj2NPiNEnK/sjI1T4RDACi8g8=")},
            {BASE128.decode("QisWvC05UKObc9MPYci6og=="),        BASE128.decode("QxfmFuCs3ln/tlsPUeahDA==")},
            {BASE128.decode("+Ht0oxIohTnV04z9qMmK+A=="),    BASE128.decode("laOie/WwfTPCbUfgUbEn3Q==")},
            {BASE128.decode("FjJPOx+BFcSnCvOUq7RE1g=="),    BASE128.decode("laOie/WwfTPCbUfgUbEn3Q==")},
            {BASE128.decode("4cRj6/wxRrM18+XUGtYi7A=="),    BASE128.decode("cyOVkJLOTLAy/8vJR4lXfsU0FuOkZFxz99t587opQiQ=")},
            {BASE128.decode("heaA92H6uOb2iKdsR0Hp3g=="),    BASE128.decode("QxfmFuCs3ln/tlsPUeahDA==")},
            {BASE128.decode("+6DRS2EfWmPFxgn5yW8KkQ=="),    BASE128.decode("5Fc54aNXi4JksOIo5nEZyg==")},
            {BASE128.decode("Ru1wvacD64fRorW1idzdNw=="),    BASE128.decode("5Fc54aNXi4JksOIo5nEZyg==")},
            {BASE128.decode("Mi8PLm/kaX73I1QmcQMR8A=="),        BASE128.decode("JCAe3LlVUFzKpqXPQT2errb9IMrgldgtRSz4DNMo86E=")},
            {BASE128.decode("QlJ5He01DoJZytgM+Nauaw=="),    BASE128.decode("QxfmFuCs3ln/tlsPUeahDA==")},
            {BASE128.decode("p2kXsx1Tyg7vE8JJcpNczw=="),    BASE128.decode("+9vyf02YPRtnQpkQb6lXJA==")},
            {BASE128.decode("7SISdDfDbBCYTgTh288e1A=="),    BASE128.decode("TJ1NKw21BGR5gwkvHzHGSA==")},
            {BASE128.decode("Rg6+sW0CH7ZnAQ0gfCRQhg=="),    BASE128.decode("TJ1NKw21BGR5gwkvHzHGSA==")},
            {BASE128.decode("9cs9rjQTzYYMfNOe5ZUIYw=="),    BASE128.decode("TJ1NKw21BGR5gwkvHzHGSA==")},
            {BASE128.decode("N69Xvmy3U8blfUMrHDMEBA=="),    BASE128.decode("bimn0zMXIRIOqR/pDBhtP4JuZjue3i22nFQduIHn4Xg=")},
            {BASE128.decode("aLGZqTwTt0H9Ld7huYAagQ=="),    BASE128.decode("/6B/GKR4eRf/Jxx+hJXG+Q==")},
            {BASE128.decode("JrJ7vI2mSnGF0LCgnFA5hA=="),    BASE128.decode("JTvbkZreGHuI5NpKBDVrvg==")},
            {BASE128.decode("2nqjVpMIZCX0Q4lCp63MpQ=="),    BASE128.decode("FRJzdtzYFRLiLJxocV44Eg==")},
            {BASE128.decode("Eb6C8G+i9yya6dlofGo49Q=="),    BASE128.decode("FRJzdtzYFRLiLJxocV44Eg==")},
            {BASE128.decode("7cniacq80LQJpvZIjV5mmQ=="),    BASE128.decode("M76fBdB5jfZVTk6c2miQQg==")},
            {BASE128.decode("S2pyq271sV78zex8K9sDQw=="),    BASE128.decode("HVyfc8yGeHIt6E3KP4FKKUfpCOoOwhJPNTM2EoWTun0WlJuoj8uTxyYG87u7nPcI")},
            {BASE128.decode("uK5LsEXVV4mkH4aTD2SymQ=="),    BASE128.decode("GPuHaxcpiL2ZGifrmqeAFQ==")},
            {BASE128.decode("x8b4Gt4O16f5pJ9PxFWcXg=="),    BASE128.decode("GPuHaxcpiL2ZGifrmqeAFQ==")},
            {BASE128.decode("DR3tkrO4/7sBtw/6OM0NHA=="),    BASE128.decode("GPuHaxcpiL2ZGifrmqeAFQ==")},
            {BASE128.decode("cOElNQs/lE8UECgHIihItQ=="),    BASE128.decode("M76fBdB5jfZVTk6c2miQQg==")},
            {BASE128.decode("Aq33bawylVv+2EEBH4tssA=="),    BASE128.decode("nNcN1ng9CHouQ512AxPHxQ==")},
            {BASE128.decode("ZKX1eTthrVyRq7uuI3x/xA=="),    BASE128.decode("HVyfc8yGeHIt6E3KP4FKKXiJPcBfbTwvdgQ+g9jBTgg=")},
            {BASE128.decode("bPDpWIs89R0mEEcy2kcReQ=="),    BASE128.decode("Ho4lKoA17leGqPpVQBWz5Q==")},
            {BASE128.decode("ZcPi4x9NCrxbi97vkRqR7Q=="),    BASE128.decode("wpOFGGNxFRJKqzcVNcTEng==")},
            {BASE128.decode("JqtGmpgnGjhASqvPNFNKoQ=="),    BASE128.decode("uzaIdvZAYKIfhzwMbqUezw==")},
            {BASE128.decode("xosnZNyzB7olh8+fSPSLvA=="),    BASE128.decode("HVyfc8yGeHIt6E3KP4FKKaXBZ36id9+d8yfzcOE4K70=")},
            {BASE128.decode("V7rzKPaZ4CevPZXlo0ZAoQ=="),    BASE128.decode("HVyfc8yGeHIt6E3KP4FKKaXBZ36id9+d8yfzcOE4K70=")},
            {BASE128.decode("/o8eOzwRXfYMGe53KD4bRg=="),    BASE128.decode("QxfmFuCs3ln/tlsPUeahDA==")},
            {BASE128.decode("8iCe04wbOFh1k+6lRhezAg=="),    BASE128.decode("EPlLg2x8raj+yhjavzsCJM5SjJ/XxDkNFIWAr3vynFU=")},
            {BASE128.decode("1W8jZxOqOQlwq23BChbZ/Q=="),        BASE128.decode("QxfmFuCs3ln/tlsPUeahDA==")},
            {BASE128.decode("kaaPS0L5ViA+qYT+7Gje/A=="),    BASE128.decode("sY7vK7Nt//t24wK6LMcwRzkzOrrjXIV+L1Gh5F9Ahg8=")},
            {BASE128.decode("nf285f40zNitOOpg36TlRA=="),    BASE128.decode("nRBnv5HRVPzwjRVe88Uoxg==")},
            {BASE128.decode("kcegABZbjTA4LIPg1+0DvQ=="),        BASE128.decode("QxfmFuCs3ln/tlsPUeahDA==")},
            {BASE128.decode("z2Mjo4gYDyyT/mpB9RYVVg=="),    BASE128.decode("v2TuuEpUT2gM1IEP9RmfbyL1wkwO9+eECa/n2xx9Py8=")},
            {BASE128.decode("/KA4Q8ETPwvK6TgwVKpstQ=="),    BASE128.decode("C6m7S2Q6M2l6c6hLGNJ62wBGPfl+2sAwUaFbMEJV64M=")},
            {BASE128.decode("nEGUUN2/mO6P/1vLVClFcg=="),    BASE128.decode("QxfmFuCs3ln/tlsPUeahDA==")},
            {BASE128.decode("Xd8aDNpZuSJIjprjxRJtFA=="),    BASE128.decode("hkIODMdJgoQ3WT4rsaqAiQ==")},
            {BASE128.decode("JwoR/L3wrMwoS5Ii2d2Drw=="),    BASE128.decode("u1arpcwl+ui7CQzVG1jD+Q==")},
            {BASE128.decode("PYjAcRQUbVLyhfdMEucy+A=="),    BASE128.decode("SwOBRgzfRJthYsnm868YkQ==")},
            {BASE128.decode("MM+CKoOrBCpkjGTqq7ATzw=="),    BASE128.decode("HVyfc8yGeHIt6E3KP4FKKS9eR1FVoNdlCOcQogFCugw=")},
            {BASE128.decode("ADVlZ/G/qoFG7kFwVAfSBQ=="),    BASE128.decode("QxfmFuCs3ln/tlsPUeahDA==")},
            {BASE128.decode("Rw6l5unIvF5P9MQjQ8/8Ww=="),        BASE128.decode("C6m7S2Q6M2l6c6hLGNJ625y23MESLXT+MON6FezLjm0=")},
            {BASE128.decode("Hdbe0DwbK39Q7L01OqkqQw=="),    BASE128.decode("OM2bIVNB7A9s8VvKajbVkQ==")},
            {"",        "*/*"}
    };


}
