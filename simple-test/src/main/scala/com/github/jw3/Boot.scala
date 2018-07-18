package com.github.jw3

import akka.actor.ActorSystem

import scala.concurrent.duration.DurationInt


object Boot extends App {
  val system = ActorSystem()
  import system.dispatcher

  val res =
    TestPigpio.init() match {
      case 1 ⇒
        println(s"Initialized pigpio")

        TestPigpio.listen(4)
        system.scheduler.schedule(5.seconds, 10.millis) {
          TestPigpio.publish(3);
        }

        system.scheduler.scheduleOnce(1.hour) { system.terminate }
      case v ⇒
        println(s"Failed to init pigpio [$v]")
        system.terminate()
    }
}
