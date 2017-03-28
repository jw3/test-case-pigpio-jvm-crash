package com.github.jw3.ww.pigpio

import ch.jodersky.jni.nativeLoader


@nativeLoader("akkapigpio")
object Pigpio {
  @native def initialize()
  @native def addCallback(gpio: Int, cb: Callback)
}
