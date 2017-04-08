package com.github.jw3.ww.pigpio

import akka.actor.{Actor, ActorSystem, Props}
import com.github.jw3.ww.pigpio.Callback.Called


object Boot extends App {
  val system = ActorSystem()

  // initialize the library
  Pigpio.initialize()

  // add a callback to actor
  val ref = system.actorOf(CallbackActor.props)
  Pigpio.addCallback(1, Callback.withActor(ref))
}


object CallbackActor {
  def props = Props(new CallbackActor)
}

class CallbackActor extends Actor {
  var cnt = 0

  def receive = {
    case Called(gpio, level, tick) ⇒
      if (cnt % 100000 == 0) printf("%d12\t%d %d %d\n", cnt, gpio, level, tick)
      cnt += 1
  }
}
