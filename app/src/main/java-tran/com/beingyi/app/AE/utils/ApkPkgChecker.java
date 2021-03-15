package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.application.MyApplication;import java.util.HashMap;
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

        map.put("libchaosvmp.so", MyApplication.getInstance().getString("033e271b7b6e77bdf2fbb66d586c21ff"));
        map.put("libddog.so", MyApplication.getInstance().getString("033e271b7b6e77bdf2fbb66d586c21ff"));
        map.put("libfdog.so", MyApplication.getInstance().getString("033e271b7b6e77bdf2fbb66d586c21ff"));
        map.put("libedog.so", MyApplication.getInstance().getString("24a32c21e7274114094ea4c9d7de339a"));
        map.put("libexec.so", MyApplication.getInstance().getString("91cb89198ac7ef1f6d120f7cf74a548a"));
        map.put("libexecmain.so", MyApplication.getInstance().getString("91cb89198ac7ef1f6d120f7cf74a548a"));
        map.put("ijiami.dat", MyApplication.getInstance().getString("91cb89198ac7ef1f6d120f7cf74a548a"));
        map.put("ijiami.ajm", MyApplication.getInstance().getString("afe7c9f40a70621013015545312dd920"));
        map.put("libsecexe.so", MyApplication.getInstance().getString("af9b7441080b3ccb42f6dfc296c5bfff"));
        map.put("libsecmain.so", MyApplication.getInstance().getString("af9b7441080b3ccb42f6dfc296c5bfff"));
        map.put("libSecShell.so", MyApplication.getInstance().getString("af9b7441080b3ccb42f6dfc296c5bfff"));
        map.put("libDexHelper.so", MyApplication.getInstance().getString("ed6f16297b4e1c987d43e92c9ea34f09"));
        map.put("libDexHelper-x86.so", MyApplication.getInstance().getString("ed6f16297b4e1c987d43e92c9ea34f09"));
        map.put("libprotectClass.so", "360");
        map.put("libjiagu.so", "360");
        map.put("libjiagu_art.so", "360");
        map.put("libjiagu_x86.so", "360");
        map.put("libegis.so", MyApplication.getInstance().getString("6d2caf9fbb465cc0351be35f2693c414"));
        map.put("libNSaferOnly.so", MyApplication.getInstance().getString("6d2caf9fbb465cc0351be35f2693c414"));
        map.put("libnqshield.so", MyApplication.getInstance().getString("72aeaa3151ff7f2a3d4cd6e9255cdcd8"));
        map.put("libbaiduprotect.so", MyApplication.getInstance().getString("a3f4a5b080e2a4ef4a708b9c9f5ad003"));
        map.put("aliprotect.dat", MyApplication.getInstance().getString("087c8ab5355a83534628e70c5f002202"));
        map.put("libsgmain.so", MyApplication.getInstance().getString("087c8ab5355a83534628e70c5f002202"));
        map.put("libsgsecuritybody.so", MyApplication.getInstance().getString("087c8ab5355a83534628e70c5f002202"));
        map.put("libmobisec.so", MyApplication.getInstance().getString("087c8ab5355a83534628e70c5f002202"));
        map.put("libtup.so", MyApplication.getInstance().getString("1977803150186fe4d2a3e226e2869497"));
        map.put("libexec.so", MyApplication.getInstance().getString("1977803150186fe4d2a3e226e2869497"));
        map.put("libshell.so", MyApplication.getInstance().getString("1977803150186fe4d2a3e226e2869497"));
        map.put("mix.dex", MyApplication.getInstance().getString("1977803150186fe4d2a3e226e2869497"));
        map.put("armeabi/mix.dex", MyApplication.getInstance().getString("1977803150186fe4d2a3e226e2869497"));
        map.put("armeabi/mixz.dex", MyApplication.getInstance().getString("1977803150186fe4d2a3e226e2869497"));
        map.put("libtosprotection.armeabi.so", MyApplication.getInstance().getString("31727d4c09bc5a250172e7078589c877"));
        map.put("libtosprotection.armeabi-v7a.so", MyApplication.getInstance().getString("31727d4c09bc5a250172e7078589c877"));
        map.put("libtosprotection.x86.so", MyApplication.getInstance().getString("31727d4c09bc5a250172e7078589c877"));
        map.put("libnesec.so", MyApplication.getInstance().getString("fee648b2a0151f4b6ab3c6d3b25ea3ff"));
        map.put("libAPKProtect.so", "APKProtect");
        map.put("libkwscmm.so", MyApplication.getInstance().getString("704aa37c02b4cd86ee8af267505eb156"));
        map.put("libkwscr.so", MyApplication.getInstance().getString("704aa37c02b4cd86ee8af267505eb156"));
        map.put("libkwslinker.so", MyApplication.getInstance().getString("704aa37c02b4cd86ee8af267505eb156"));
        map.put("libx3g.so", MyApplication.getInstance().getString("5854d991df17f6e268289ead5c806e7d"));
        map.put("libapssec.so", MyApplication.getInstance().getString("db47d1042b00a5392811ac582aebef4d"));
        map.put("librsprotect.so", MyApplication.getInstance().getString("7e15cb3758a0bbad42c3c91571145f24"));
        map.put("libedog.so",MyApplication.getInstance().getString("033e271b7b6e77bdf2fbb66d586c21ff"));
        map.put("libegis.so",MyApplication.getInstance().getString("6d2caf9fbb465cc0351be35f2693c414"));
        map.put("libapssec.so",MyApplication.getInstance().getString("c517063eca3014ee39b83d834158161f"));
        map.put("librsprotect.so",MyApplication.getInstance().getString("8d9e8524670a20ed44954e4121d47f93"));
        map.put("libnqshield.so",MyApplication.getInstance().getString("a95ee666af5b6d8e04962ecc87987578"));
        map.put("libdx-ld.so",MyApplication.getInstance().getString("2cb9e54a17218d7207fd0a2546b35685"));
        map.put("libcsn2.so",MyApplication.getInstance().getString("2cb9e54a17218d7207fd0a2546b35685"));



        proMap.put("assets/.appkey","360");
        proMap.put("assets/libjiagu.so","360");
        proMap.put("lib/armeabi/libexecmain.so",MyApplication.getInstance().getString("91cb89198ac7ef1f6d120f7cf74a548a"));
        proMap.put("assets/ijiami.ajm",MyApplication.getInstance().getString("91cb89198ac7ef1f6d120f7cf74a548a"));
        proMap.put("assets/af.bin",MyApplication.getInstance().getString("91cb89198ac7ef1f6d120f7cf74a548a"));
        proMap.put("assets/signed.bin",MyApplication.getInstance().getString("91cb89198ac7ef1f6d120f7cf74a548a"));
        proMap.put("assets/ijm_lib/armeabi/libexec.so",MyApplication.getInstance().getString("91cb89198ac7ef1f6d120f7cf74a548a"));
        proMap.put("assets/ijm_lib/X86/libexec.so",MyApplication.getInstance().getString("91cb89198ac7ef1f6d120f7cf74a548a"));
        proMap.put("assets/dex.dat",MyApplication.getInstance().getString("704aa37c02b4cd86ee8af267505eb156"));
        proMap.put("lib/armeabi/kdpdata.so",MyApplication.getInstance().getString("704aa37c02b4cd86ee8af267505eb156"));
        proMap.put("lib/armeabi/libkdp.so",MyApplication.getInstance().getString("704aa37c02b4cd86ee8af267505eb156"));
        proMap.put("lib/armeabi/libkwscmm.so",MyApplication.getInstance().getString("704aa37c02b4cd86ee8af267505eb156"));
        proMap.put("assets/secData0.jar",MyApplication.getInstance().getString("1cdc6d8d2a0abb43f9b039aaa28a8710"));
        proMap.put("assets/secData0.jar",MyApplication.getInstance().getString("1cdc6d8d2a0abb43f9b039aaa28a8710"));
        proMap.put("assets/secData0.jar",MyApplication.getInstance().getString("1cdc6d8d2a0abb43f9b039aaa28a8710"));
        proMap.put("assets/classes.jar",MyApplication.getInstance().getString("a06bce5d5f73852c5a4345e66a888472"));
        proMap.put("lib/armeabi/DexHelper.so",MyApplication.getInstance().getString("a06bce5d5f73852c5a4345e66a888472"));
        proMap.put("assets/libtosprotection.armeabi-v7a.so",MyApplication.getInstance().getString("31727d4c09bc5a250172e7078589c877"));
        proMap.put("assets/libtosprotection.armeabi-v7a.so",MyApplication.getInstance().getString("31727d4c09bc5a250172e7078589c877"));
        proMap.put("assets/libtosprotection.armeabi.so",MyApplication.getInstance().getString("31727d4c09bc5a250172e7078589c877"));
        proMap.put("assets/libtosprotection.x86.so",MyApplication.getInstance().getString("31727d4c09bc5a250172e7078589c877"));
        proMap.put("assets/tosversion",MyApplication.getInstance().getString("31727d4c09bc5a250172e7078589c877"));
        proMap.put("lib/armeabi/libtest.so",MyApplication.getInstance().getString("31727d4c09bc5a250172e7078589c877"));
        proMap.put("lib/armeabi/libTmsdk-xxx-mfr.so",MyApplication.getInstance().getString("31727d4c09bc5a250172e7078589c877"));
        proMap.put(MyApplication.getInstance().getString("791d461ba0dacbb59d81be83b59dc341"),"lib/armeabi/libx3g.so");
        proMap.put("assets/armeabi/libfakejni.so",MyApplication.getInstance().getString("d25c380f15dab2c3b6399ff6946f3e12"));
        proMap.put("assets/armeabi/libzuma.so",MyApplication.getInstance().getString("d25c380f15dab2c3b6399ff6946f3e12"));
        proMap.put("assets/libzuma.so",MyApplication.getInstance().getString("d25c380f15dab2c3b6399ff6946f3e12"));
        proMap.put("assets/libzumadata.so",MyApplication.getInstance().getString("d25c380f15dab2c3b6399ff6946f3e12"));
        proMap.put("assets/libpreverify1.so",MyApplication.getInstance().getString("d25c380f15dab2c3b6399ff6946f3e12"));
        proMap.put("dexprotect",MyApplication.getInstance().getString("d25c380f15dab2c3b6399ff6946f3e12"));
        proMap.put("assets/classes.dex.dat",MyApplication.getInstance().getString("d25c380f15dab2c3b6399ff6946f3e12"));
        proMap.put("assets/dp.arm-v7.so.dat",MyApplication.getInstance().getString("d25c380f15dab2c3b6399ff6946f3e12"));
        proMap.put("assets/dp.arm.so.dat",MyApplication.getInstance().getString("d25c380f15dab2c3b6399ff6946f3e12"));
        proMap.put("lib/armeabi/libbaiduprotect.so",MyApplication.getInstance().getString("9f803610f521d6fdc08c5a5e740fd82d"));
        proMap.put("assets/baiduprotect1.jar",MyApplication.getInstance().getString("9f803610f521d6fdc08c5a5e740fd82d"));
        proMap.put("assets/baiduprotect.jar",MyApplication.getInstance().getString("9f803610f521d6fdc08c5a5e740fd82d"));
        proMap.put("assets/itse",MyApplication.getInstance().getString("ac2b5dceb1ead2e3031ee27f903bc289"));
        proMap.put("lib/armeabi/libitsec.so",MyApplication.getInstance().getString("ac2b5dceb1ead2e3031ee27f903bc289"));
        proMap.put("apktoolplus",MyApplication.getInstance().getString("ac2b5dceb1ead2e3031ee27f903bc289"));
        proMap.put("assets/jiagu_data.bin",MyApplication.getInstance().getString("ac2b5dceb1ead2e3031ee27f903bc289"));
        proMap.put("assets/sign.bin",MyApplication.getInstance().getString("ac2b5dceb1ead2e3031ee27f903bc289"));
        proMap.put("lib/armeabi/libapktoolplus_jiagu.so",MyApplication.getInstance().getString("ac2b5dceb1ead2e3031ee27f903bc289"));
        
        
        

        proMap.put("assets/libuusafe.jar.so",MyApplication.getInstance().getString("fd0177dd82bd5674ec55afa7776f0f42"));
        proMap.put("assets/libuusafe.so",MyApplication.getInstance().getString("fd0177dd82bd5674ec55afa7776f0f42"));
        proMap.put("lib/armeabi/libuusafeempty.so",MyApplication.getInstance().getString("fd0177dd82bd5674ec55afa7776f0f42"));
        proMap.put("assets/mogosec_classes",MyApplication.getInstance().getString("8581458624933fc3f4ee73b49bb189f7"));
        proMap.put("assets/mogosec_data",MyApplication.getInstance().getString("8581458624933fc3f4ee73b49bb189f7"));
        proMap.put("assets/mogosec_dexinfo",MyApplication.getInstance().getString("8581458624933fc3f4ee73b49bb189f7"));
        proMap.put("assets/mogosec_march",MyApplication.getInstance().getString("8581458624933fc3f4ee73b49bb189f7"));
        proMap.put("lib/armeabi/libcmvmp.so",MyApplication.getInstance().getString("8581458624933fc3f4ee73b49bb189f7"));
        proMap.put("lib/armeabi/libmogosec_dex.so",MyApplication.getInstance().getString("8581458624933fc3f4ee73b49bb189f7"));
        proMap.put("lib/armeabi/libmogosec_sodecrypt.so",MyApplication.getInstance().getString("8581458624933fc3f4ee73b49bb189f7"));
        proMap.put("lib/armeabi/libmogosecurity.so",MyApplication.getInstance().getString("8581458624933fc3f4ee73b49bb189f7"));
        proMap.put("assets/libreincp.so",MyApplication.getInstance().getString("57c11b141c548ba005e8487a84fc8db1"));
        proMap.put("assets/libreincp_x86.so",MyApplication.getInstance().getString("57c11b141c548ba005e8487a84fc8db1"));
        
        
        
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
        
        
        return MyApplication.getInstance().getString("4cb850028a4b61935fc62e33a25aa99e");
    }
    
}
