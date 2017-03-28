#include <strings.h>
#include <stdint.h>
#include <jni.h>
#include <pigpio.h>

#include "com_github_jw3_ww_pigpio_Pigpio__.h"


static JavaVM* jvm = 0;
jobject callbacks[PI_MAX_GPIO];

void alertFn(int, int, uint32_t);

JNIEXPORT void JNICALL Java_com_github_jw3_ww_pigpio_Pigpio_00024_initialize
  (JNIEnv* env, jobject o)
{
  bzero(callbacks, sizeof(callbacks));

  int sz = 0;
  JNI_GetCreatedJavaVMs(&jvm, 1, &sz);
}

JNIEXPORT void JNICALL Java_com_github_jw3_ww_pigpio_Pigpio_00024_addCallback
  (JNIEnv* env, jobject o, jint gpio, jobject cb)
{
  if (callbacks[gpio]) env->DeleteGlobalRef(callbacks[gpio]);
  callbacks[gpio] = env->NewGlobalRef(cb);
  gpioSetAlertFunc(gpio, alertFn);
}

void alertFn(int p, int l, uint32_t t)
{
  JNIEnv* env = 0;
  jvm->AttachCurrentThread((void**)&env, 0);

  jclass cls = env->GetObjectClass(callbacks[p]);
  jmethodID mid = env->GetMethodID(cls, "call", "(IIJ)V");
  env->CallVoidMethod(callbacks[p], mid, p, l, t);

  jvm->DetachCurrentThread();
}
