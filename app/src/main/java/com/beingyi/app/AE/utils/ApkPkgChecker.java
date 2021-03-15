package com.beingyi.app.AE.utils;
import java.util.HashMap;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;

public class ApkPkgChecker
{
    ZipFile zipFile;
    HashMap<String,String> map=new HashMap<String,String>();
    HashMap<String,String> proMap=new HashMap<String,String>();
    
    public ApkPkgChecker(String path){
        
        try
        {
            zipFile = new ZipFile(path);
        }
        catch (Exception e)
        {
            
        }

        map.put("libchaosvmp.so", "娜迦");
        map.put("libddog.so", "娜迦");
        map.put("libfdog.so", "娜迦");
        map.put("libedog.so", "娜迦企业版");
        map.put("libexec.so", "爱加密");
        map.put("libexecmain.so", "爱加密");
        map.put("ijiami.dat", "爱加密");
        map.put("ijiami.ajm", "爱加密企业版");
        map.put("libsecexe.so", "梆梆免费版");
        map.put("libsecmain.so", "梆梆免费版");
        map.put("libSecShell.so", "梆梆免费版");
        map.put("libDexHelper.so", "梆梆企业版");
        map.put("libDexHelper-x86.so", "梆梆企业版");
        map.put("libprotectClass.so", "360");
        map.put("libjiagu.so", "360");
        map.put("libjiagu_art.so", "360");
        map.put("libjiagu_x86.so", "360");
        map.put("libegis.so", "通付盾");
        map.put("libNSaferOnly.so", "通付盾");
        map.put("libnqshield.so", "网秦");
        map.put("libbaiduprotect.so", "百度");
        map.put("aliprotect.dat", "阿里聚安全");
        map.put("libsgmain.so", "阿里聚安全");
        map.put("libsgsecuritybody.so", "阿里聚安全");
        map.put("libmobisec.so", "阿里聚安全");
        map.put("libtup.so", "腾讯");
        map.put("libexec.so", "腾讯");
        map.put("libshell.so", "腾讯");
        map.put("mix.dex", "腾讯");
        map.put("armeabi/mix.dex", "腾讯");
        map.put("armeabi/mixz.dex", "腾讯");
        map.put("libtosprotection.armeabi.so", "腾讯御安全");
        map.put("libtosprotection.armeabi-v7a.so", "腾讯御安全");
        map.put("libtosprotection.x86.so", "腾讯御安全");
        map.put("libnesec.so", "网易易盾");
        map.put("libAPKProtect.so", "APKProtect");
        map.put("libkwscmm.so", "几维安全");
        map.put("libkwscr.so", "几维安全");
        map.put("libkwslinker.so", "几维安全");
        map.put("libx3g.so", "顶像科技");
        map.put("libapssec.so", "盛大");
        map.put("librsprotect.so", "瑞星");
        map.put("libedog.so","娜迦");
        map.put("libegis.so","通付盾");
        map.put("libapssec.so","盛大加固");
        map.put("librsprotect.so","瑞星加固");
        map.put("libnqshield.so","网秦加固");
        map.put("libdx-ld.so","顶象加固");
        map.put("libcsn2.so","顶象加固");



        proMap.put("assets/.appkey","360");
        proMap.put("assets/libjiagu.so","360");
        proMap.put("lib/armeabi/libexecmain.so","爱加密");
        proMap.put("assets/ijiami.ajm","爱加密");
        proMap.put("assets/af.bin","爱加密");
        proMap.put("assets/signed.bin","爱加密");
        proMap.put("assets/ijm_lib/armeabi/libexec.so","爱加密");
        proMap.put("assets/ijm_lib/X86/libexec.so","爱加密");
        proMap.put("assets/dex.dat","几维安全");
        proMap.put("lib/armeabi/kdpdata.so","几维安全");
        proMap.put("lib/armeabi/libkdp.so","几维安全");
        proMap.put("lib/armeabi/libkwscmm.so","几维安全");
        proMap.put("assets/secData0.jar","梆梆安全免费版");
        proMap.put("assets/secData0.jar","梆梆安全免费版");
        proMap.put("assets/secData0.jar","梆梆安全免费版");
        proMap.put("assets/classes.jar","梆梆安全定制版");
        proMap.put("lib/armeabi/DexHelper.so","梆梆安全定制版");
        proMap.put("assets/libtosprotection.armeabi-v7a.so","腾讯御安全");
        proMap.put("assets/libtosprotection.armeabi-v7a.so","腾讯御安全");
        proMap.put("assets/libtosprotection.armeabi.so","腾讯御安全");
        proMap.put("assets/libtosprotection.x86.so","腾讯御安全");
        proMap.put("assets/tosversion","腾讯御安全");
        proMap.put("lib/armeabi/libtest.so","腾讯御安全");
        proMap.put("lib/armeabi/libTmsdk-xxx-mfr.so","腾讯御安全");
        proMap.put("顶象技术","lib/armeabi/libx3g.so");
        proMap.put("assets/armeabi/libfakejni.so","阿里加固");
        proMap.put("assets/armeabi/libzuma.so","阿里加固");
        proMap.put("assets/libzuma.so","阿里加固");
        proMap.put("assets/libzumadata.so","阿里加固");
        proMap.put("assets/libpreverify1.so","阿里加固");
        proMap.put("dexprotect","阿里加固");
        proMap.put("assets/classes.dex.dat","阿里加固");
        proMap.put("assets/dp.arm-v7.so.dat","阿里加固");
        proMap.put("assets/dp.arm.so.dat","阿里加固");
        proMap.put("lib/armeabi/libbaiduprotect.so","百度加固");
        proMap.put("assets/baiduprotect1.jar","百度加固");
        proMap.put("assets/baiduprotect.jar","百度加固");
        proMap.put("assets/itse","海云安加固");
        proMap.put("lib/armeabi/libitsec.so","海云安加固");
        proMap.put("apktoolplus","海云安加固");
        proMap.put("assets/jiagu_data.bin","海云安加固");
        proMap.put("assets/sign.bin","海云安加固");
        proMap.put("lib/armeabi/libapktoolplus_jiagu.so","海云安加固");
        
        
        

        proMap.put("assets/libuusafe.jar.so","UU安全");
        proMap.put("assets/libuusafe.so","UU安全");
        proMap.put("lib/armeabi/libuusafeempty.so","UU安全");
        proMap.put("assets/mogosec_classes","中国移动加固");
        proMap.put("assets/mogosec_data","中国移动加固");
        proMap.put("assets/mogosec_dexinfo","中国移动加固");
        proMap.put("assets/mogosec_march","中国移动加固");
        proMap.put("lib/armeabi/libcmvmp.so","中国移动加固");
        proMap.put("lib/armeabi/libmogosec_dex.so","中国移动加固");
        proMap.put("lib/armeabi/libmogosec_sodecrypt.so","中国移动加固");
        proMap.put("lib/armeabi/libmogosecurity.so","中国移动加固");
        proMap.put("assets/libreincp.so","珊瑚灵御");
        proMap.put("assets/libreincp_x86.so","珊瑚灵御");
        
        
        
    }
    
    
    
    
    public String getPkgName(){
        
        for(String key :map.keySet()){
            ZipEntry entry=zipFile.getEntry("assets/"+key);
            if(entry!=null){
                return map.get(key);
            }
            
            ZipEntry entry2=zipFile.getEntry("lib/"+key);
            if(entry2!=null){
                return map.get(key);
            }
        }
        
        for(String key :proMap.keySet()){
            ZipEntry entry=zipFile.getEntry(key);
            if(entry!=null){
                return proMap.get(key);
            }
            
        }
        
        
        return "未知壳";
    }
    
}
