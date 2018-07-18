package com.github.jw3

import ch.jodersky.jni.nativeLoader

@nativeLoader("simpleone0")
object TestPigpio {
  @native def init(): Long
  @native def listen(pin: Int)
  @native def publish(pin: Int)
}
