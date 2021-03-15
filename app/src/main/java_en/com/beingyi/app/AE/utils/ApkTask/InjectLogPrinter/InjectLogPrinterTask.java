package com.beingyi.app.AE.ApkTask.InjectLogPrinter;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import com.beingyi.app.AE.ApkTask.baseTask;
import com.beingyi.app.AE.utils.FileUtils;
import com.beingyi.app.AE.utils.ZipOutUtil;
import com.google.common.collect.Lists;

import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.writer.builder.DexBuilder;
import org.jf.dexlib2.writer.io.MemoryDataStore;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


public class InjectLogPrinterTask extends baseTask {


    DealCallBack dealCallBack;

    public InjectLogPrinterTask(String mInput, String mOutput, DealCallBack mDealCallBack) throws Exception {
        super(mInput, mOutput);

        this.dealCallBack = mDealCallBack;


    }


    public void start() throws Exception {


        for (String dexName : dexList) {
            dealCallBack.onStep(BASE128.decode("mO/uhGU3mBSUuxIF0pA+Gg==") + dexName);

            InputStream inputStream = zipFile.getInputStream(zipFile.getEntry(dexName));
            ByteArrayOutputStream baos = FileUtils.cloneInputStream(inputStream);

            ZipOutUtil.AddFile(zos, dexName, dealDex(baos.toByteArray()));

        }


        ZipOutUtil.Sava(zipFile, zos, dexList, new ZipOutUtil.ZipSavsCallback(){

            @Override
            public void onStep(ZipOutUtil.Step step)
            {

            }

            @Override
            public void onProgress(int progress, int total)
            {
                dealCallBack.onStep(BASE128.decode("X8F6cB5TYm4wFvctF3NfWLv6cLknUhCfGZrecy3+cio="));
                dealCallBack.onSaveProgress(progress,total);
            }
        });


    }


    private byte[] dealDex(byte[] input) throws Exception {
        DexBuilder dexBuilder = new DexBuilder(Opcodes.getDefault());
        dexBuilder.setIgnoreMethodAndFieldError(true);

        DexBackedDexFile dex = DexBackedDexFile.fromInputStream(Opcodes.getDefault(), new ByteArrayInputStream(input));

        List<ClassDef> classDefs = Lists.newArrayList(dex.getClasses());

        for (int i = 0; i < classDefs.size(); i++) {

            ClassDef classDef = dealClass(classDefs.get(i));

            dealCallBack.onProgress(i, classDefs.size());
        }


        MemoryDataStore memoryDataStore = new MemoryDataStore();
        dexBuilder.writeTo(memoryDataStore);

        return Arrays.copyOf(memoryDataStore.getBufferData(), memoryDataStore.getSize());
    }

    private ClassDef dealClass(ClassDef classDef) {

        for (Method method : classDef.getMethods()) {

            dealCallBack.onLabel(classDef.getType()+BASE128.decode("OojM/qIOvm2OsYbohXlJ7A==")+method.getName());

        }

        return classDef;
    }


}
