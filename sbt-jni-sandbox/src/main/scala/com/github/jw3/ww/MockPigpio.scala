package com.github.jw3.ww

import ch.jodersky.jni.nativeLoader

@nativeLoader("callback0")
object MockPigpio {
  @native def initialize()
  @native def addCallback(gpio: Int, cb: Callback)
  @native def inject(gpio: Int, level: Long)
}
