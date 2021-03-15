package com.beingyi.app.AE.utils;
import com.google.common.collect.Lists;
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

            String typePkg=type.substring(1, type.length() - 1).replace("/", ".");

            if (activitis.contains(typePkg))
            {
                System.out.println("正在处理activity："+typePkg);
                //String activityName=type.substring(type.lastIndexOf("/")+1,type.length()-1);

                String smali1=BYProtectUtils.readAssetsTxt("Child.smali");
                String smali2=BYProtectUtils.readAssetsTxt("Child$100000000.smali");

                smali1=smali1.replace(".super Landroid/app/Activity;",".super "+type);
                smali1=smali1.replace("Lfly/sub","L"+typePkg.replace(".","/")+"_");
                smali2=smali2.replace("Lfly/sub","L"+typePkg.replace(".","/")+"_");
                
                
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
                System.out.println("正在处理Application");
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
