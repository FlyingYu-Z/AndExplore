package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.application.MyApplication;
import com.google.common.collect.Lists;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jf.baksmali.Adaptors.ClassDefinition;
import org.jf.baksmali.BaksmaliOptions;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.analysis.ClassPath;
import org.jf.dexlib2.analysis.DexClassProvider;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.writer.builder.DexBuilder;
import org.jf.dexlib2.writer.io.MemoryDataStore;
import org.jf.smali.Smali;
import org.jf.smali.SmaliOptions;
import org.jf.util.IndentingWriter;

import java.io.IOException;
import java.io.ByteArrayInputStream;


public class DexStringEncryptor {

    byte[] out;

    DexBackedDexFile dex;
    List<String> keeps;
    EncryptCallBack callBack;

    public DexStringEncryptor(String Input, List<String> keeps, EncryptCallBack mCallBack) throws Exception {
        File file = new File(Input);
        dex = DexBackedDexFile.fromInputStream(Opcodes.getDefault(), new BufferedInputStream(new FileInputStream(file)));
        this.keeps = keeps;
        this.callBack = mCallBack;
        init();

    }

    public DexStringEncryptor(byte[] Input, List<String> keeps, EncryptCallBack mCallBack) throws Exception {
        dex = DexBackedDexFile.fromInputStream(Opcodes.getDefault(), new ByteArrayInputStream(Input));
        this.keeps = keeps;
        this.callBack = mCallBack;
        init();
    }


    public void init() {

        if (keeps == null) {
            keeps = new ArrayList<>();
        }

        keeps.add("LDecoder/");
        keeps.add("Lcom/ae/");
        keeps.add("Ljavax/");
        keeps.add("Lsun1/");
        keeps.add("Lsun/");
        keeps.add("Ljava/");
        keeps.add("Lcom/google/");
        keeps.add("Landroid/");
        keeps.add("Landroidx");

    }

    public void start() throws Exception {

        DexBuilder dexBuilder = new DexBuilder(Opcodes.getDefault());
        dexBuilder.setIgnoreMethodAndFieldError(true);

        DexBackedDexFile baseDex = DexBackedDexFile.fromInputStream(Opcodes.getDefault(), new BufferedInputStream(BYProtectUtils.getStreamFromAssets("EncryptString.dex")));

        List<ClassDef> baseClassDefs = Lists.newArrayList(baseDex.getClasses());

        for (int i = 0; i < baseClassDefs.size(); i++) {
            try {
                dexBuilder.internClassDef(baseClassDefs.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        List<ClassDef> classDefs = Lists.newArrayList(dex.getClasses());

        for (int i = 0; i < classDefs.size(); i++) {
            dealClassDef(dexBuilder, classDefs.get(i));
            callBack.onProgress(i, classDefs.size());

        }


        MemoryDataStore memoryDataStore = new MemoryDataStore();
        dexBuilder.writeTo(memoryDataStore);
        out = Arrays.copyOf(memoryDataStore.getBufferData(), memoryDataStore.getSize());


        System.out.println(MyApplication.getInstance().getString("400047895b647639339b218df76f3d28"));
    }


    public void dealClassDef(DexBuilder dexBuilder, ClassDef classDef) {

        String type = classDef.getType();
        System.out.println(type);
        callBack.onClassDefName(type);

        boolean isKeep = false;
        for (int l = 0; l < keeps.size(); l++) {
            if (type.startsWith(keeps.get(l))) {
                isKeep = true;
                break;
            } else {
                isKeep = false;
            }
        }


        if (isKeep) {
            innternClassDef(dexBuilder, classDef);
        } else {
            String smali = getSmaliByType(classDef);
            smali = dealSmali1(smali);
            smali = dealSmali2(smali);
            try {
                Smali.assembleSmaliFile(smali, dexBuilder, new SmaliOptions());
                //dexBuilder.internClassDef(classDef);
            } catch (Exception e) {
                e.printStackTrace();
                if (!type.startsWith("LDecoder/")
                        && !type.startsWith("Lcom/ae/")) {
                    //return;
                }

                innternClassDef(dexBuilder, classDef);
            }
        }


    }


    public void innternClassDef(DexBuilder dexBuilder, ClassDef classDef) {
        try {
            dexBuilder.internClassDef(classDef);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public byte[] getOutData() {
        return out;
    }

    public void save(String destPath) throws Exception {
        FileUtils.saveFile(out, destPath);
    }


    public String dealSmali1(String smali) {

        List<String> codes = new ArrayList<>();
        List<String> enCodes = new ArrayList<>();

        Pattern p = Pattern.compile("(const-string\\sv([0-9]*),\\s\"(.*)\"\n)");
        Matcher m = p.matcher(smali);
        while (m.find()) {
            String code = m.group();
            System.out.println(code);
            codes.add(code);

            int v = Integer.parseInt(m.group(2));
            String content = m.group(3);
            String enCode = "";

            if (v <= 15 && !content.equals("")) {
                enCode = "const-string v" + v + ",\"" + com.ae.utils.Base64Util.encode(content) + "\"\n" +
                        "invoke-static {v" + v + "}, Lcom/ae/utils/Base64Util;->decode(Ljava/lang/String;)Ljava/lang/String;\n" +
                        "move-result-object v" + v + "\n";
            }

            if (v > 15) {
                enCode = code;
            }

            enCodes.add(enCode);


        }

        for (int a = 0; a < codes.size(); a++) {
            smali = smali.replace(codes.get(a), enCodes.get(a));
        }

        return smali;
    }


    public String dealSmali2(String smali) {

        List<String> codes = new ArrayList<>();
        List<String> enCodes = new ArrayList<>();

        Pattern p = Pattern.compile("(const-string\\sp([0-9]*),\\s\"(.*)\"\n)");
        Matcher m = p.matcher(smali);
        while (m.find()) {
            String code = m.group();
            System.out.println(code);
            codes.add(code);

            int v = Integer.parseInt(m.group(2));
            String content = m.group(3);
            String enCode = "";

            if (v <= 15) {
                enCode = "const-string p" + v + ",\"" + Base64Util.encode(content) + "\"\n" +
                        "invoke-static {p" + v + "}, Lcom/ae/utils/Base64Util;->decode(Ljava/lang/String;)Ljava/lang/String;\n" +
                        "move-result-object p" + v + "\n";
            }

            if (v > 15) {

                enCode = code;
            }

            enCodes.add(enCode);


        }

        for (int a = 0; a < codes.size(); a++) {
            smali = smali.replace(codes.get(a), enCodes.get(a));
        }

        return smali;
    }


    public static String getSmaliByType(ClassDef classDef) {
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


    public interface EncryptCallBack {
        void onProgress(int progress, int total);

        void onClassDefName(String Name);
    }


}
