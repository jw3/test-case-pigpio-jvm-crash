package com.github.jw3.ww


trait Callback {
  def call(gpio: Int, level: Int, tick: Long)
}
