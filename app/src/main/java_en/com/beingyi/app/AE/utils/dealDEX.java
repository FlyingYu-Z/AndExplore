package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;import com.google.common.collect.Lists;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jf.baksmali.Adaptors.ClassDefinition;
import org.jf.baksmali.BaksmaliOptions;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.analysis.ClassPath;
import org.jf.dexlib2.analysis.DexClassProvider;
import org.jf.dexlib2.dexbacked.DexBackedClassDef;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.writer.builder.DexBuilder;
import org.jf.dexlib2.writer.io.MemoryDataStore;
import org.jf.smali.Smali;
import org.jf.smali.SmaliOptions;
import org.jf.util.IndentingWriter;

public class dealDEX
{
    byte[] resultByte;
    public boolean isApplicationNameValid=false;

    public dealDEX(SuperEncryption mSuperEncryption, String customApplicationName , List<String> activitis, byte[] dexByte) throws Exception
    {

        //List<ClassDef> mClassDef=new ArrayList<ClassDef>();
        DexBuilder dexBuilder = new DexBuilder(Opcodes.getDefault());
        dexBuilder.setIgnoreMethodAndFieldError(true);

        DexBackedDexFile dex = DexBackedDexFile.fromInputStream(Opcodes.getDefault(), new ByteArrayInputStream(dexByte));
        List<DexBackedClassDef> dexBackedClassDef=Lists.newArrayList(dex.getClasses());
        ClassPath classpath = new ClassPath(Lists.newArrayList(new DexClassProvider(dex)), false, dex.getClasses().size());

        
        for (int i=0;i < dexBackedClassDef.size();i++)
        {

            String type=dexBackedClassDef.get(i).getType();
            //System.out.println(type);

            String typePkg=type.substring(1, type.length() - 1).replace(BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="), BASE128.decode("slQ5h/9rTzh2GKAKOvqH+w=="));

            if (activitis.contains(typePkg))
            {
                System.out.println(BASE128.decode("mcjuimGwMqV+tlrlL5ipGPhIHndqp1laPXCPC1KNnrY=")+typePkg);
                //String activityName=type.substring(type.lastIndexOf("/")+1,type.length()-1);

                String smali1=BYProtectUtils.readAssetsTxt(BASE128.decode("9OKjWEe89TJ69zOslGfa0A=="));
                String smali2=BYProtectUtils.readAssetsTxt(BASE128.decode("8Lkda8xK7oPKgVGe7vZF7Dvxkf32qxkGR8zW1cfdsr8="));

                smali1=smali1.replace(BASE128.decode("uIh57VMaYon1rF/8lKPjljPCcuNqyGXzWgB3MXY90Pk="),BASE128.decode("CMQ2lzQ9k9QlI2ApQBivpQ==")+type);
                smali1=smali1.replace(BASE128.decode("23W3XjAnK8Nwcxo9Arq+bg=="),BASE128.decode("BG0TE+dtfbzeiznki74vFA==")+typePkg.replace(BASE128.decode("slQ5h/9rTzh2GKAKOvqH+w=="),BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="))+BASE128.decode("9wphzVfcegbjwNAGjcJYMw=="));
                smali2=smali2.replace(BASE128.decode("23W3XjAnK8Nwcxo9Arq+bg=="),BASE128.decode("BG0TE+dtfbzeiznki74vFA==")+typePkg.replace(BASE128.decode("slQ5h/9rTzh2GKAKOvqH+w=="),BASE128.decode("AkZj32p4lHkS0d0LVieHfA=="))+BASE128.decode("9wphzVfcegbjwNAGjcJYMw=="));
                
                
                String smali=getSmaliByType(classpath, type);
                
                try{
                
                Smali.assembleSmaliFile(smali1 , dexBuilder, new SmaliOptions());
                Smali.assembleSmaliFile(smali2 , dexBuilder, new SmaliOptions());
                Smali.assembleSmaliFile(smali , dexBuilder, new SmaliOptions());


                }catch(Exception e){
                    e.printStackTrace();
                }
                
                
            }
            else if (typePkg.equals(customApplicationName))
            {
                isApplicationNameValid=true;
                System.out.println(BASE128.decode("ucLZGV22RwF2Ka9PgSbuGFu4ecBx1q6aCyC7uf9/bTw="));
            }
            else
            {
                dexBuilder.internClassDef(classpath.getClassDef(type));
            }




        }
        
        MemoryDataStore memoryDataStore = new MemoryDataStore();
        dexBuilder.writeTo(memoryDataStore);
        resultByte = Arrays.copyOf(memoryDataStore.getBufferData(), memoryDataStore.getSize());


    }


    public byte[] getBytes()
    {
        return resultByte;
    }




    public static String getSmaliByType(ClassPath classpath, String Type)
    {
        String code=null;
        try
        {
            DexBackedClassDef dexBackedClassDef = (DexBackedClassDef) classpath.getClassDef(Type);

            StringWriter stringWriter = new StringWriter();
            IndentingWriter writer = new IndentingWriter(stringWriter);
            ClassDefinition classDefinition1 = new ClassDefinition(new BaksmaliOptions(), dexBackedClassDef);
            classDefinition1.writeTo(writer);
            writer.close();
            code = stringWriter.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return code;
    }






}
