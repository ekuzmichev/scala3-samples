package ru.ekuzmichev

object OpaqueTypes:
  def main(args: Array[String]): Unit =
    // This is plain value wrapper
    // This adds memory overhead
    // case class Name(value: String)

    // opaque types
    // they have no overhead in comparison with wrappers
    object SocialNetwork: // domain scope
      opaque type Name = String

      // 1 - companion object
      object Name:
        def fromString(s: String): Option[Name] =
          if (s.isEmpty || s.charAt(0).isLower) None else Some(s)

      // 2 - extension methods
      extension (n: Name) {
        def length: Int = n.length // called on the String type inside the scope
      }

    import SocialNetwork._

    // val name: Name = "bob" // compile error // left: Name, right: String
    // Outside of the scope the opaque type is defined it has no connection with the type with which it is defined
    // Name is not related to String outside the scope of its declaration
    // These types can not be used interchangeably
    // Outside we have access only to its own API

    val maxOpt: Option[Name] = Name.fromString("Max")

    // maxOpt.map(_.charAt(0)) // does not compile // Name does not have String methods outside of the scope

    println(Name.fromString("Bob"))
    println(Name.fromString("bob"))

    println(maxOpt.map(_.length)) // Got via extension method

    // opaque type bounds
    object Graphics:
      opaque type Color                = Int // in HEX
      opaque type ColorFilter <: Color = Int

      val Red: Color                    = 0xff000000
      val Green: Color                  = 0x00ff0000
      val Blue: Color                   = 0x0000ff00
      val HalfTransparency: ColorFilter = 0x88 // 50% transparency

    import Graphics._

    case class OverlayFilter(color: Color)

    // Here we can use ColorFilter due to class hierarchy (extends Color) and possible substitution of types
    val fadeLayer = OverlayFilter(HalfTransparency)
