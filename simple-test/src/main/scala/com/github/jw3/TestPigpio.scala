package com.github.jw3

import ch.jodersky.jni.nativeLoader

@nativeLoader("simpleone0")
object TestPigpio {
  @native def test(pin: Int): Long
}
