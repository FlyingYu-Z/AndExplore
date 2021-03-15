package com.beingyi.app.AE.ApkTask.ConexistTask;

import android.content.Context;

import com.beingyi.app.AE.ApkTask.baseTask;
import com.beingyi.app.AE.utils.APKUtils;
import com.beingyi.app.AE.utils.FileUtils;
import com.bigzhao.xml2axml.tools.AxmlDecoder;
import com.bigzhao.xml2axml.tools.AxmlEncoder;

public class CloneAPK extends baseTask {


    public CloneAPK(Context context, String mInput, String mOutput,String destPkg, DealCallBack callback) throws Exception {
        super(context, mInput, mOutput, callback);

        String axml= AxmlDecoder.decode(FileUtils.toByteArray(getZipInputStream("AndroidManifest.xml")));
        axml=axml.replace("package=\""+APKUtils.getPkgName(context,inputPath)+"\"","package=\""+destPkg+"\"");


        zipOut.addFile("AndroidManifest.xml", AxmlEncoder.encode(axml));

        for(String dexName:dexEntries){




        }


    }




}
