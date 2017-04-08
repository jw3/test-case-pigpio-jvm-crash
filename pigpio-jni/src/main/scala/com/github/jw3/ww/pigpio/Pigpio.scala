package com.github.jw3.ww.pigpio

import ch.jodersky.jni.nativeLoader


@nativeLoader("pigpio-jni")
object Pigpio {
  @native def initialize()
  @native def addCallback(gpio: Int, cb: Callback)
}
