
#include <jni.h>


static jstring fromJAR(JNIEnv *env,jobject context){

    jmethodID getPackageResourcePath = (env)->GetMethodID(contextClass, "getPackageResourcePath", "()Ljava/lang/String;");
    jstring path=(jstring) (env)->CallObjectMethod(context,getPackageResourcePath);

    jclass JarFileClass = env->FindClass("java/util/jar/JarFile");
    if(JarFileClass==NULL){
        return env->NewStringUTF("no class found for JarFile!");
    }else{

        return env->NewStringUTF(" found  JarFile!");
    }

    /**
    jmethodID JarFile=env->GetMethodID(JarFileClass, "<init>", "(Ljava/lang/String)V");
    if(JarFile==NULL){
        return env->NewStringUTF("no method found for JarFile!");
    }**/


    return path;

}