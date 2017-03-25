package com.github.jw3.ww

import akka.actor.{Actor, ActorSystem, Props}
import com.github.jw3.ww.Callback.Called


object Boot extends App {
  val system = ActorSystem()

  // initialize the library
  MockPigpio.initialize()

  // add a callback to actor
  val ref = system.actorOf(CallbackActor.props)
  MockPigpio.addCallback(1, Callback.withActor(ref))

  // simulate a gpio event
  MockPigpio.inject(1, 1)
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
