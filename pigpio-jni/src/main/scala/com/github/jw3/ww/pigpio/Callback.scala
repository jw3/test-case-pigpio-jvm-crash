package com.github.jw3.ww.pigpio

import akka.actor.ActorRef


trait Callback {
  def call(gpio: Int, level: Int, tick: Long)
}

object Callback {
  case class Called(gpio: Int, level: Int, tick: Long)

  def withActor(ref: ActorRef) = new Callback {
    def call(gpio: Int, level: Int, tick: Long) = ref ! Called(gpio, level, tick)
  }
}
