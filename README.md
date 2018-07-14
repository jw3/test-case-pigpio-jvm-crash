pigpio signal handling test case
===

on raspberry with pigpio sig 11 randomly on alert callbacks from native to jvm

common behavior is that initial run of application can progress for long (infinite?) amount of time without issue
 - have processed billions of events successfully

ensuing executions result in sigsev within shorter time period
  - eg.  1000 events, 100, 50, 50, 100, 50.. 
  - never again achieves the "stability" of the first execution

### tested
- oracle jvm 8.~90-8.121
- azul jvm 8.112
- various compiler args both generic and tuned for pi
- pi 2 and 3
- javacpp, jna
- with jni globals added to pigpio [link](https://github.com/jw3/pigpio/tree/jni-pigpio)
- akka, rxscala, (also plain old callback, i think)
- pigpio as java or scala lib

### thoughts
- gc related issue?
- jni related issue
  - have used jna and java-cpp to generate proxies to pigpio
  - have injected jni code (global ref handling) into pigpio ([branch](https://github.com/jw3/pigpio/tree/jni-pigpio))

### next steps
- attempt to duplicate the failure on x86 the round trip to from the pi, or dev on the pi, is too slow
- determine if jni code 

### sbt-jni

- sbt sbt-jni-sandbox/javah
- sbt sbt-jni-sandbox/nativeCompile
- sbt sbt-jni-sandbox/run

expected 

`Called 1 1 1490413300085547`


### Generated

Code generated by java-cpp below.  No global reference is created for the callback.

```cpp
JNIEXPORT jint JNICALL Java_org_bytedeco_javacpp_pigpio_gpioSetAlertFunc(JNIEnv* env, jclass cls, jint arg0, jobject arg1) {
    JavaCPP_org_bytedeco_javacpp_pigpio_00024gpioAlertFunc_1t* ptr1 = arg1 == NULL ? NULL : (JavaCPP_org_bytedeco_javacpp_pigpio_00024gpioAlertFunc_1t*)jlong_to_ptr(env->GetLongField(arg1, JavaCPP_addressFID));
    jlong position1 = arg1 == NULL ? 0 : env->GetLongField(arg1, JavaCPP_positionFID);
    ptr1 += position1;
    jint rarg = 0;
    jthrowable exc = NULL;
    try {
        int rvalue = gpioSetAlertFunc((unsigned)arg0, (ptr1 == NULL ? NULL : ptr1->ptr));
        rarg = (jint)rvalue;
    } catch (...) {
        exc = JavaCPP_handleException(env, 18);
    }

    if (exc != NULL) {
        env->Throw(exc);
    }
    return rarg;
}
JNIEXPORT jint JNICALL Java_org_bytedeco_javacpp_pigpio_gpioSetAlertFuncEx(JNIEnv* env, jclass cls, jint arg0, jobject arg1, jobject arg2) {
    JavaCPP_org_bytedeco_javacpp_pigpio_00024gpioAlertFuncEx_1t* ptr1 = arg1 == NULL ? NULL : (JavaCPP_org_bytedeco_javacpp_pigpio_00024gpioAlertFuncEx_1t*)jlong_to_ptr(env->GetLongField(arg1, JavaCPP_addressFID));
    jlong position1 = arg1 == NULL ? 0 : env->GetLongField(arg1, JavaCPP_positionFID);
    ptr1 += position1;
    char* ptr2 = arg2 == NULL ? NULL : (char*)jlong_to_ptr(env->GetLongField(arg2, JavaCPP_addressFID));
    jlong position2 = arg2 == NULL ? 0 : env->GetLongField(arg2, JavaCPP_positionFID);
    ptr2 += position2;
    jint rarg = 0;
    jthrowable exc = NULL;
    try {
        int rvalue = gpioSetAlertFuncEx((unsigned)arg0, (ptr1 == NULL ? NULL : ptr1->ptr), ptr2);
        rarg = (jint)rvalue;
    } catch (...) {
        exc = JavaCPP_handleException(env, 18);
    }

    if (exc != NULL) {
        env->Throw(exc);
    }
    return rarg;
}
```
/tmp/javacpp-presets/pigpio/target/classes/org/bytedeco/javacpp/jnipigpio.cpp

### again
- 07/13/2018; have some work-work coming up on the pi, so going to revisit this again :/
