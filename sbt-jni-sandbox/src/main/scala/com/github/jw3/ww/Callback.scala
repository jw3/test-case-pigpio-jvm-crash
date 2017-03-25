package com.github.jw3.ww


abstract class Callback {
  def call(gpio: Int, level: Int, tick: Int)
}
