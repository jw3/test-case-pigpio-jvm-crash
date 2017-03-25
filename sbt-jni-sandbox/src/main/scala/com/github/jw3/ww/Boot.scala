package com.github.jw3.ww


object Boot extends App {
  // initialize the library
  MockPigpio.initialize()

  // add a callback
  MockPigpio.addCallback(1, new Callback {
    def call(gpio: Int, level: Int, tick: Long) = println(s"Called $gpio $level $tick")
  })

  // simulate a gpio event
  MockPigpio.inject(1, 1)
}
