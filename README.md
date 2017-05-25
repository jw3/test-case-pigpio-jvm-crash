debugging my pi jni pigpio
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
- with jni globals added to pigpio (link)
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

