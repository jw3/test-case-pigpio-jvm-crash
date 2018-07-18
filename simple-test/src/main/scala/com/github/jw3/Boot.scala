package com.github.jw3

import akka.actor.ActorSystem

import scala.concurrent.duration.DurationInt

object Boot extends App {
  val system = ActorSystem()
  import system.dispatcher

  TestPigpio.init() match {
    case 1 ⇒
      println(s"Initialized pigpio")

      TestPigpio.listen(4)
      system.scheduler.schedule(1.second, 1.milli) {
        TestPigpio.publish(3);
      }
    case v ⇒
      println(s"Failed to init pigpio [$v]")
      system.terminate()
      System.exit(1)
  }
}
