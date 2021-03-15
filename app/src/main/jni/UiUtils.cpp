
#include <jni.h>


jobject showToast(JNIEnv *env, jobject thiz, jobject context, jstring str) {

    jclass tclss = env->FindClass("android/widget/Toast");
    jmethodID mid = env->GetStaticMethodID(tclss,"makeText","(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;");
    jobject job = env->CallStaticObjectMethod(tclss,mid,context,str);
    jmethodID showId = env->GetMethodID(tclss,"show","()V");
    env->CallVoidMethod(job,showId,context,str);

}