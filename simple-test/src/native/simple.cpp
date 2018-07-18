#include <iostream>
#include <stdint.h>
#include <jni.h>
#include <pigpio.h>

#include <com_github_jw3_TestPigpio__.h>

// from: https://github.com/joan2937/pigpio/issues/138#issue-237028730

static long t = 0;
static bool v = false;

const int K = 1000;
const int tK = 10 * K;

void interrupt_s(int gpio, int level, uint32_t tick)
{
   if(!(t % K))
     std::cout << "." << std::flush;

   ++t;

   if(!(t % tK))
     std::cout << t / tK << std::flush;
}


JNIEXPORT jlong JNICALL Java_com_github_jw3_TestPigpio_00024_test
  (JNIEnv *env, jobject obj, jint pin)
{
	//Initialize PIGPIO library
	if (gpioInitialise() < 0) {
		return 0;
	}
        // Set a single pin as input
	gpioSetMode(pin, PI_INPUT);
	gpioSetPullUpDown(pin, PI_PUD_DOWN);
	   /* monitor pin level changes */
	gpioSetAlertFunc(pin, interrupt_s);
	return 1;
}


JNIEXPORT jlong JNICALL Java_com_github_jw3_TestPigpio_00024_init
  (JNIEnv *env, jobject obj)
{
  return gpioInitialise() < 0 ? 0 : 1;
}

JNIEXPORT void JNICALL Java_com_github_jw3_TestPigpio_00024_listen
  (JNIEnv *env, jobject obj, jint pin)
{
  gpioSetMode(pin, PI_INPUT);
  gpioSetPullUpDown(pin, PI_PUD_DOWN);
  /* monitor pin level changes */
  gpioSetAlertFunc(pin, interrupt_s);
}

JNIEXPORT void JNICALL Java_com_github_jw3_TestPigpio_00024_publish
  (JNIEnv *env, jobject obj, jint pin)
{
  gpioSetMode(pin, PI_OUTPUT);
  gpioWrite(pin, v ? 1 : 0);
  v = !v;
}
