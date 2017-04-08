my white whale
===

on raspberry with pigpio sig 11 randomly on alert callbacks from native to jvm

- runtime of days or runtime of seconds

### tested
- oracle jvm 8.~90-8.121
- azul jvm 8.112
- various compiler args both generic and tuned for pi
- pi 2 and 3
- javacpp, jna
- with jni globals added to pigpio (link)
- akka, rxscala, (also plain old callback, i think)
- pigpio as java or scala lib

### next step 
- attempt to duplicate the failure on x86 the round trip to from the pi, or dev on the pi, is too slow
- use jni directly (instead of jna, javacpp) to try to debug
- add another layer
  - jni to pigpio
  - c to application (most likely)

### sbt-jni for great things

- sbt sbt-jni-sandbox/javah
- sbt sbt-jni-sandbox/nativeCompile
- sbt sbt-jni-sandbox/run
