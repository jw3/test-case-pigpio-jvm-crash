package com.github.jw3

import akka.actor.ActorSystem

import scala.concurrent.duration.DurationInt


object Boot extends App {
  val system = ActorSystem()
  import system.dispatcher

  val res = TestPigpio.test(4)
  println(s"Called test methog, result [$res]")

  system.scheduler.scheduleOnce(1.hour) {
    system.terminate
  }
}
