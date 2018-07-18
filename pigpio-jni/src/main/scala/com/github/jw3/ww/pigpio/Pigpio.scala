package com.github.jw3.ww.pigpio

import ch.jodersky.jni.nativeLoader


@nativeLoader("pigpiojni")
object Pigpio {
  @native def initialize()
  @native def addCallback(gpio: Int, cb: Callback)
}
