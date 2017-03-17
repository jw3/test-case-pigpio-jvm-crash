package com.github.jw3.pigpio

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import org.bytedeco.javacpp.pigpio
import org.bytedeco.javacpp.pigpio.gpioAlertFunc_t

import scala.concurrent.Await
import scala.concurrent.duration.Duration


object Boot extends App {
  implicit val system = ActorSystem("crash-test")

  pigpio.gpioInitialise()

  val a = system.actorOf(Props[Receiver])
  val l = new foo(a)

  Await.ready(system.whenTerminated, Duration.Inf)
}

class foo(ref: ActorRef) extends gpioAlertFunc_t {
  override def call(gpio: Int, level: Int, tick: Int) = ref ! s"TICK $tick"
}

class Receiver extends Actor {
  def receive = {
    case s ⇒ println(s)
  }
}
