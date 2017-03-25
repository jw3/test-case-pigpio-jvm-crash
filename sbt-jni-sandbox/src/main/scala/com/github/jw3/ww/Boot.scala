package com.github.jw3.ww


object Boot extends App {
  MockPigpio.initialize()

  //  val cb = new Callback {
  //    def call(gpio: Int, level: Int, tick: Long) = println("Called")
  //  }
  val foo = new Foo

  MockPigpio.addCallback(1, foo)
  MockPigpio.inject(1, 1)
}

class Foo extends Callback {
  def call(gpio: Int, level: Int, tick: Int) = println("Called")
}
