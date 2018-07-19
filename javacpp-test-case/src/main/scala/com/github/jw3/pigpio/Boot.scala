package com.github.jw3.pigpio

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import org.bytedeco.javacpp.pigpio
import org.bytedeco.javacpp.pigpio.gpioAlertFunc_t

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Boot extends App {
  implicit val system = ActorSystem("crash-test")

  val listenOn = 4
  val publishFrom = 3

  pigpio.gpioInitialise()

  val s = system.actorOf(Sender.props(publishFrom))
  val r = system.actorOf(Props[Receiver])
  val l = F(r)

  println(s"adding listener, pin $listenOn")
  pigpio.gpioSetAlertFunc(listenOn, l)

  println(s"adding publisher, pin $publishFrom")
  system.scheduler.schedule(1.second, 1.milli, s, Sender.DoIt)

  Await.ready(system.whenTerminated, Duration.Inf)
}

object F {
  def apply(ref: ActorRef) = new F(ref)
}

class F(ref: ActorRef) extends gpioAlertFunc_t {
  override def call(gpio: Int, level: Int, tick: Int): Unit =
    ref ! s"gpio $gpio received $level at $tick"
}

object Sender {
  def props(pin: Int) = Props(new Sender(pin))

  case object DoIt
}

class Sender(pin: Int) extends Actor {
  def low: Receive = {
    case Sender.DoIt ⇒
      pigpio.gpioWrite(pin, 0)
      context.become(low)
  }

  def high: Receive = {
    case Sender.DoIt ⇒
      pigpio.gpioWrite(pin, 1)
      context.become(high)
  }

  def receive: Receive = low
}

class Receiver extends Actor {
  def receive: Receive = {
    case s ⇒ println(s)
  }
}
