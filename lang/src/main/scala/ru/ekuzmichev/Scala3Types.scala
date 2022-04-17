package ru.ekuzmichev

import java.io.File

@main
def main(): Unit =
  // ================= //
  // 1. Literal types
  // ================= //

  val aNumber  = 3
  val three: 3 = 3 // 3 <: Int
  println(three)

  def passNumber(i: Int): Unit = println(i)
  passNumber(42)
  passNumber(three) // ok

  def passStrict(three: 3): Unit = println(three)
  passStrict(three)
  // passStrict(42) // does not compile

  def pi: 3.14       = 3.14
  val truth: true    = true
  val scala: "scala" = "scala"

  // literal types enforce compile type checks to definitions

  def doSmth(x: Option[42]): Unit = x.foreach(println)
  doSmth(None)
  doSmth(Some(42))
  // doSmth(Some(21)) // does not compile

  // ================= //
  // 2. Union types != Either type
  // ================= //
  def ambivalentMethod(arg: Int | String): Unit =
    arg match
      case s: String => println(s"String: $s")
      case i: Int    => println(s"Int: $i")

  ambivalentMethod(1)
  ambivalentMethod("s")

  type ErrorOr[T] = T | String
  def handleResource(file: ErrorOr[File]): Unit = ???

  // Inferred type is Any by default as common ancestor of Int and String
  val stringOrInt                    = if (true) "string" else 43
  // but you can use union type explicitly
  val typedStringOrInt: String | Int = if (true) "string" else 43

  // ================= //
  // 3. Intersection types
  // ================= //

  trait Camera:
    def takePhoto(): Unit = println("snap")
    def use(): Unit       = println("Camera use")

  trait Phone:
    def makeCall(): Unit = println("ring")
    def use(): Unit      = println("Photo use") // method with the same signature

  def useSmartDevice(device: Camera & Phone): Unit =
    device.takePhoto()
    device.makeCall()
    device.use() // which is actually called?

  class Smartphone extends Camera with Phone {
    // if no override - compile error
    override def use(): Unit = println("Smartphone use") // this is called
  }

  useSmartDevice(new Smartphone)

  trait HostConfig
  trait HostController {
    def get(): Option[HostConfig]
  }
  trait PortConfig
  trait PortController {
    def get(): Option[PortConfig]
  }

  def getConfigs(controller: HostController & PortController): Option[HostConfig] & Option[PortConfig] =
    controller.get() // compiles

  // Option[HostConfig] & Option[PortConfig] == Option[HostConfig & PortConfig]

  def getConfigs2(controller: HostController & PortController): Option[HostConfig & PortConfig] =
    controller.get()

  class FinalController extends HostController with PortController:
    override def get(): Option[HostConfig & PortConfig] =
      Some(
        new HostConfig with PortConfig:
          override def toString: String = "FINAL CONFIG"
      )

  println(getConfigs(new FinalController))
  println(getConfigs2(new FinalController))
