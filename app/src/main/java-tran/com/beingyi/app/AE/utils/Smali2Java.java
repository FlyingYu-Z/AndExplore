package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.application.MyApplication;
import com.google.common.collect.ImmutableList;

import jadx.api.JadxArgs;
import jadx.api.JadxDecompiler;
import jadx.api.JavaClass;

import java.io.File;
import java.io.IOException;

import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.writer.builder.DexBuilder;
import org.jf.dexlib2.writer.io.FileDataStore;
import org.jf.dexlib2.writer.pool.DexPool;
import org.jf.smali.Smali;
import org.jf.smali.SmaliOptions;

public class Smali2Java {


    public static String translate(ClassDef cls) throws Exception {
        File tmp = File.createTempFile("test", ".dex");
        try {
            DexPool pool = new DexPool(Opcodes.getDefault());
            pool.internClass(cls);
            pool.writeTo(new FileDataStore(tmp));
            JadxArgs args = new JadxArgs();
            args.setSkipResources(true);
            args.setShowInconsistentCode(true);
            args.setInputFiles(ImmutableList.of(tmp));
            JadxDecompiler decompiler = new JadxDecompiler(args);
            decompiler.load();
            JavaClass jcls = decompiler.getClasses().iterator().next();
            jcls.decompile();
            return jcls.getCode();
        } finally {
            tmp.delete();
        }
    }




    public static String translate(String smali) throws Exception {
        File tmp = File.createTempFile("test", ".dex");
        try {
            DexBuilder dexBuilder = new DexBuilder(Opcodes.getDefault());
            dexBuilder.setIgnoreMethodAndFieldError(true);

            ClassDef classDef = Smali.assembleSmaliFile(smali, dexBuilder, new SmaliOptions());

            DexPool pool = new DexPool(Opcodes.getDefault());
            pool.internClass(classDef);
            pool.writeTo(new FileDataStore(tmp));
            JadxArgs args = new JadxArgs();
            args.setSkipResources(true);
            args.setShowInconsistentCode(true);
            args.setInputFiles(ImmutableList.of(tmp));
            JadxDecompiler decompiler = new JadxDecompiler(args);
            decompiler.load();
            JavaClass jcls = decompiler.getClasses().iterator().next();
            jcls.decompile();
            return jcls.getCode();
        } catch (Exception e) {

        } finally {
            tmp.delete();
        }

        return null;

    }


    public static void translate(File file, File outPath) throws Exception {
        File tmp = File.createTempFile("test", ".dex");

        DexBuilder dexBuilder = new DexBuilder(Opcodes.getDefault());
        dexBuilder.setIgnoreMethodAndFieldError(true);

        ClassDef classDef = Smali.assembleSmaliFile(FileUtils.readFile(file.getAbsolutePath()), dexBuilder, new SmaliOptions());

        DexPool pool = new DexPool(Opcodes.getDefault());
        pool.internClass(classDef);
        pool.writeTo(new FileDataStore(tmp));
        JadxArgs args = new JadxArgs();
        args.setSkipResources(true);
        args.setShowInconsistentCode(true);
        args.setInputFiles(ImmutableList.of(tmp));
        JadxDecompiler decompiler = new JadxDecompiler(args);
        decompiler.load();
        JavaClass jcls = decompiler.getClasses().iterator().next();
        jcls.decompile();
        FileUtils.writeFile(outPath.getAbsolutePath(), jcls.getCode());

        tmp.delete();

    }


}
