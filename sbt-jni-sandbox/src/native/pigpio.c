#include <strings.h>
#include <jni.h>
#include <sys/time.h>
#include <pthread.h>
#include <stdio.h>

#include "com_github_jw3_ww_MockPigpio__.h"

#define PI_MAX_GPIO 10

static pthread_t pthAlert;
static JavaVM* pthread_jvm = NULL;

jobject callbacks[PI_MAX_GPIO];

long getMicrotime();
void call(int, int, long, jobject, JNIEnv*);
static void* pthAlertThread(void*);

JNIEXPORT void JNICALL Java_com_github_jw3_ww_MockPigpio_00024_initialize
  (JNIEnv* env, jobject o)
{
  bzero(callbacks, sizeof(callbacks));

  int sz = 0;
  JNI_GetCreatedJavaVMs(&pthread_jvm, 1, &sz);

  int i=0;
  pthread_create(&pthAlert, NULL, pthAlertThread, &i);
}

JNIEXPORT void JNICALL Java_com_github_jw3_ww_MockPigpio_00024_addCallback
  (JNIEnv* env, jobject o, jint gpio, jobject cb)
{
  if (callbacks[gpio]) (*env)->DeleteGlobalRef(env, callbacks[gpio]);
  callbacks[gpio] = (*env)->NewGlobalRef(env, cb);
}

JNIEXPORT void JNICALL Java_com_github_jw3_ww_MockPigpio_00024_inject
  (JNIEnv* env, jobject o, jint gpio, jlong level)
{
  call(gpio, level, getMicrotime(), callbacks[gpio], env);
}

void call(int p, int l, long t, jobject cb, JNIEnv* env)
{
  jclass cls = (*env)->GetObjectClass(env, cb);
  jmethodID mid = (*env)->GetMethodID(env, cls, "call", "(IIJ)V");
  (*env)->CallVoidMethod(env, cb, mid, p, l, t);
}

long getMicrotime() {
	struct timeval currentTime;
	gettimeofday(&currentTime, NULL);
	return currentTime.tv_sec * (int)1e6 + currentTime.tv_usec;
}

static void* pthAlertThread(void* x)
{
  JNIEnv* env = 0;
  (*pthread_jvm)->AttachCurrentThread(pthread_jvm, (void**) &env, NULL);

  while(1) {
    int i = 0;
    long t = getMicrotime();
    for(; i<PI_MAX_GPIO; ++i) {
      if(0 != callbacks[i])
        call(i, 0, t, callbacks[i], env);
     }
  }
}
