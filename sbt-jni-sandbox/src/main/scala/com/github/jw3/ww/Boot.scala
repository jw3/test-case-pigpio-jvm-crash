package com.github.jw3.ww


object Boot extends App {
  // initialize the library
  MockPigpio.initialize()

  // add a callback
  MockPigpio.addCallback(1, new Callback {
    var cnt = 0
    def call(gpio: Int, level: Int, tick: Long) = {
      if (cnt % 100000 == 0) printf("%d12\t%d %d %d\n", cnt, gpio, level, tick)
      cnt += 1
    }
  })

  // simulate a gpio event
  MockPigpio.inject(1, 1)

  Thread.sleep(5000)
}
