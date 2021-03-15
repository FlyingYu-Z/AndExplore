package com.beingyi.app.AE.utils.openFile;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.beingyi.app.AE.dialog.enDexString;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.beingyi.app.AE.R;
import com.beingyi.app.AE.activity.DexEditor;
import com.beingyi.app.AE.dialog.getSavePath;
import com.beingyi.app.AE.dialog.selectFile;
import com.beingyi.app.AE.interfaces.GetSavePathCallBack;
import com.beingyi.app.AE.interfaces.SelectFileCallBack;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.utils.FileUtils;
import com.googlecode.dex2jar.Method;
import com.googlecode.dex2jar.ir.IrMethod;
import com.googlecode.dex2jar.v3.Dex2jar;
import com.googlecode.dex2jar.v3.DexExceptionHandler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedClassDef;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;

import org.jf.baksmali.Adaptors.ClassDefinition;
import org.jf.dexlib2.analysis.ClassPath;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.writer.builder.DexBuilder;
import org.jf.dexlib2.writer.io.MemoryDataStore;
import org.jf.util.IndentingWriter;

import java.io.StringWriter;

import org.jf.dexlib2.analysis.DexClassProvider;
import org.jf.baksmali.BaksmaliOptions;
import org.objectweb.asm.tree.MethodNode;

import jadx.api.JadxArgs;
import jadx.api.JadxDecompiler;
import jadx.api.JavaClass;


public class s_dex extends s_base {
    String Path;
    File file;

    public s_dex(Context mContext, int mWindow, String mPath, View view, boolean mIsInZip) {
        super(mContext, mWindow, view, mIsInZip);
        this.Path = mPath;
        this.file = new File(Path);

        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.file_dex, popupMenu.getMenu());
        popupMenu.show();

        //MenuItem item=popupMenu.getMenu().getItem(R.id.action_dex_tojar);


        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_dex_edit:
                        //ClassList.input=FileUtils.getByte(file.getAbsolutePath());
                        Intent intent = new Intent(context, DexEditor.class);
                        intent.putExtra("isInZip", isInZip);
                        intent.putExtra("Path", file.getAbsolutePath());
                        context.startActivity(intent);
                        break;

                    case R.id.action_dex_tojar: {
                        final File outFile = new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + "_java.jar");

                        new getSavePath(context, window, outFile.getAbsolutePath(), new GetSavePathCallBack() {

                            @Override
                            public void onSuccess(String filePath) {
                                dex2jar(filePath);
                            }

                            @Override
                            public void onCancel() {

                            }

                        });
                    }

                    break;

                    case R.id.action_dex_toSmali: {
                        final File outDir = new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + "_smali");

                        new getSavePath(context, window, outDir.getAbsolutePath(), new GetSavePathCallBack() {

                            @Override
                            public void onSuccess(String filePath) {
                                dex2smali(filePath);
                            }

                            @Override
                            public void onCancel() {

                            }

                        });
                    }
                    break;


                    case R.id.action_dex_toJava: {
                        final File outDir = new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + "_java");

                        new getSavePath(context, window, outDir.getAbsolutePath(), new GetSavePathCallBack() {

                            @Override
                            public void onSuccess(String filePath) {
                                dex2Java(filePath);
                            }

                            @Override
                            public void onCancel() {

                            }

                        });
                    }
                    break;


                    case R.id.action_dex_addDex:
                        new selectFile(context, window, FileUtils.getSDPath(), "dex", new SelectFileCallBack() {

                            @Override
                            public void onSelected(String selectedPath) {
                                final File outFile = new File(new File(Path).getParent(), FileUtils.getPrefix(Path) + "_merger.dex");
                                new getSavePath(context, window, outFile.getAbsolutePath(), new GetSavePathCallBack() {

                                    @Override
                                    public void onSuccess(String filePath) {
                                        final List<String> filePaths = new ArrayList<>();
                                        filePaths.add(Path);
                                        filePaths.add(selectedPath);

                                        mergerDex(filePaths, filePath);
                                    }

                                    @Override
                                    public void onCancel() {

                                    }

                                });


                            }

                            @Override
                            public void onCancel() {

                            }

                        });
                        break;


                    case R.id.action_dex_enString:
                        new enDexString(context, window, Path);
                        break;


                }

                return true;
            }
        });


    }


    public void dex2jar(final String outPath) {


        final AlertProgress progres = new AlertProgress(context);
        new Thread() {
            @Override
            public void run() {
                progres.setLabel(MyApplication.getInstance().getString("f013ea9dcba3f5ca1278aa850931fec8"));
                progres.setNoProgress();
                progres.show();
                try {

                    FileUtils.createFile(outPath);
                    Dex2jar dex2jar=Dex2jar.from(file);
                    dex2jar.reUseReg(true);
                    dex2jar.optimizeSynchronized(false);
                    dex2jar.printIR(false);
                    dex2jar.topoLogicalSort().skipDebug(true);
                    dex2jar.setExceptionHandler(new DexExceptionHandler() {
                        @Override
                        public void handleFileException(Exception e) {

                        }

                        @Override
                        public void handleMethodTranslateException(Method method, IrMethod irMethod, MethodNode methodNode, Exception e) {

                        }
                    });
                    dex2jar.to(new File(outPath));

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.showToast(MyApplication.getInstance().getString("6bfe16584f867f8128f075b6f43e8a8c"));
                            adapter.refresh();
                            adapter.setItemHighLight(outPath);
                        }
                    });

                } catch (Exception e) {
                    activity.showMessage(context, MyApplication.getInstance().getString("22af5f9e0c3f5aaba469cb807298386e"), e.toString());
                }

                progres.dismiss();
            }
        }.start();


    }


    public void dex2smali(final String outDir) {

        final AlertProgress progres = new AlertProgress(context);
        new Thread() {
            @Override
            public void run() {
                progres.setLabel(MyApplication.getInstance().getString("f013ea9dcba3f5ca1278aa850931fec8"));
                progres.show();
                try {
                    FileUtils.mkdir(outDir);


                    DexBackedDexFile dex = DexBackedDexFile.fromInputStream(Opcodes.getDefault(), new BufferedInputStream(new FileInputStream(file)));
                    List<DexBackedClassDef> dexBackedClassDef = Lists.newArrayList(dex.getClasses());
                    ClassPath classpath = new ClassPath(Lists.newArrayList(new DexClassProvider(dex)), false, dex.getClasses().size());

                    for (int i = 0; i < dexBackedClassDef.size(); i++) {

                        progres.setProgress(i, dexBackedClassDef.size());
                        String type = dexBackedClassDef.get(i).getType();
                        String smali = getSmaliByType(classpath, type);

                        File smaliFile = new File(utils.DesPath(outDir) + (type.substring(1, type.length() - 1)) + ".smali");
                        FileUtils.writeFile(smaliFile.getAbsolutePath(), smali);

                    }

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.showToast(MyApplication.getInstance().getString("6bfe16584f867f8128f075b6f43e8a8c"));
                            adapter.refresh();
                            adapter.setItemHighLight(outDir);
                        }
                    });

                } catch (Exception e) {
                    activity.showMessage(context, MyApplication.getInstance().getString("22af5f9e0c3f5aaba469cb807298386e"), e.toString());
                }

                progres.dismiss();
            }
        }.start();


    }


    public void dex2Java(final String outDir) {

        final AlertProgress progres = new AlertProgress(context);
        new Thread() {
            @Override
            public void run() {
                progres.setLabel(MyApplication.getInstance().getString("f013ea9dcba3f5ca1278aa850931fec8"));
                progres.show();
                try {
                    FileUtils.mkdir(outDir);


                    JadxArgs args = new JadxArgs();
                    args.setSkipResources(true);
                    args.setShowInconsistentCode(true);
                    args.setInputFiles(ImmutableList.of(new File(Path)));
                    JadxDecompiler decompiler = new JadxDecompiler(args);
                    decompiler.load();

                    List<JavaClass> javaClasses=decompiler.getClasses();
                    for(int i=0;i<javaClasses.size();i++){
                        JavaClass jcls = javaClasses.get(i);
                        jcls.decompile();

                        progres.setProgress(i, javaClasses.size());
                        String fullName = jcls.getFullName();
                        String code = jcls.getCode();

                        File javaFile = new File(utils.DesPath(outDir) + (fullName.replace(".","/")) + ".java");
                        FileUtils.writeFile(javaFile.getAbsolutePath(), code);

                    }

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.showToast(MyApplication.getInstance().getString("6bfe16584f867f8128f075b6f43e8a8c"));
                            adapter.refresh();
                            adapter.setItemHighLight(outDir);
                        }
                    });

                } catch (Exception e) {
                    activity.showMessage(context, MyApplication.getInstance().getString("22af5f9e0c3f5aaba469cb807298386e"), e.toString());
                }

                progres.dismiss();
            }
        }.start();


    }


    public void mergerDex(final List<String> filePaths, final String outPath) {

        final AlertProgress progres = new AlertProgress(context);
        new Thread() {
            @Override
            public void run() {
                progres.setLabel(MyApplication.getInstance().getString("f013ea9dcba3f5ca1278aa850931fec8"));
                progres.show();
                try {

                    DexBuilder dexBuilder = new DexBuilder(Opcodes.getDefault());
                    dexBuilder.setIgnoreMethodAndFieldError(true);


                    for (int i = 0; i < filePaths.size(); i++) {
                        DexBackedDexFile dex = DexBackedDexFile.fromInputStream(Opcodes.getDefault(), new BufferedInputStream(new FileInputStream(filePaths.get(i))));
                        List<ClassDef> classDefs = Lists.newArrayList(dex.getClasses());

                        for (int d = 0; d < classDefs.size(); d++) {
                            progres.setLabel(new File(filePaths.get(i)).getName());
                            progres.setProgress(d, classDefs.size());

                            dexBuilder.internClassDef(classDefs.get(d));

                        }


                    }


                    MemoryDataStore memoryDataStore = new MemoryDataStore();
                    dexBuilder.writeTo(memoryDataStore);
                    byte[] result = Arrays.copyOf(memoryDataStore.getBufferData(), memoryDataStore.getSize());
                    FileUtils.saveFile(result, outPath);


                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.showToast(MyApplication.getInstance().getString("8828c6d9b89f6cd3dd0d33f15c35f5b4"));
                            adapter.refresh();
                            adapter.setItemHighLight(outPath);
                        }
                    });

                } catch (Exception e) {
                    activity.showMessage(context, MyApplication.getInstance().getString("22af5f9e0c3f5aaba469cb807298386e"), e.toString());
                }

                progres.dismiss();
            }
        }.start();


    }


    public static String getSmaliByType(ClassPath classpath, String Type) {
        String code = null;
        try {
            DexBackedClassDef dexBackedClassDef = (DexBackedClassDef) classpath.getClassDef(Type);

            StringWriter stringWriter = new StringWriter();
            IndentingWriter writer = new IndentingWriter(stringWriter);
            ClassDefinition classDefinition1 = new ClassDefinition(new BaksmaliOptions(), dexBackedClassDef);
            classDefinition1.writeTo(writer);
            writer.close();
            code = stringWriter.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return code;
    }


}
