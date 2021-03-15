


#include <jni.h>


/**
 * 获取上下文
 * @param env
 * @return
 */
jobject getApplication(JNIEnv *env) {
    jobject application = NULL;
    jclass activity_thread_clz = env->FindClass("android/app/ActivityThread");
    if (activity_thread_clz != NULL) {
        jmethodID get_Application = env->GetStaticMethodID(activity_thread_clz,
                                                           "currentActivityThread",
                                                           "()Landroid/app/ActivityThread;");
        if (get_Application != NULL) {
            jobject currentActivityThread = env->CallStaticObjectMethod(activity_thread_clz,
                                                                        get_Application);
            jmethodID getal = env->GetMethodID(activity_thread_clz, "getApplication",
                                               "()Landroid/app/Application;");
            application = env->CallObjectMethod(currentActivityThread, getal);
        }
        return application;
    }
    return application;
}


/**
 * 获取包名
 * @param env
 * @return
 */
jstring getPackageName(JNIEnv *env) {
    jobject context = getApplication(env);
    if (context == NULL) {
        //LOGE("context is null!");
        return NULL;
    }
    jclass activity = env->GetObjectClass(context);
    jmethodID methodId_pack = env->GetMethodID(activity, "getPackageName", "()Ljava/lang/String;");
    jstring name_str = static_cast<jstring >( env->CallObjectMethod(context, methodId_pack));
    return name_str;
}


/**
 * 获取安装包路径
 * @param env
 * @return
 */
jstring getSourcePath(JNIEnv *env) {
    jobject context = getApplication(env);
    if (context == NULL) {
        //LOGE("context is null!");
        return NULL;
    }
    jmethodID getPackageResourcePath = (env)->GetMethodID(contextClass, "getPackageResourcePath",
                                                          "()Ljava/lang/String;");
    jstring path = (jstring) (env)->CallObjectMethod(context, getPackageResourcePath);

    return path;
}


jboolean checkHook(JNIEnv *env) {

    jboolean isExists = false;

    jclass jclass1 = env->FindClass("bin/mt/apksignaturekillerplus/HookApplication");
    if (jclass1 == NULL) {
        isExists = false;
    } else {
        isExists = true;
    }

    if (env->ExceptionCheck()) {
        env->ExceptionDescribe();
        env->ExceptionClear();
    }

    return isExists;


}
