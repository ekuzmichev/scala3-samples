package ru.ekuzmichev

object Enums:
  def main(args: Array[String]): Unit =
    enum Permissions:
      case READ, WRITE, EXEC, NONE

    /*
      Compiler automatically creates:
        sealed class Permissions
        + READ, WRITE, EXEC, NONE values that extend Permissions and are constants
        companion object Permissions generated
        these values are in companion object
     */
    val read: Permissions = Permissions.READ // READ is field of companion object Permissions
    println(read)

    // enums can have arguments
    enum PermissionsWithBits(val bits: Int):
      case READ  extends PermissionsWithBits(4) // 100
      case WRITE extends PermissionsWithBits(2) // 010
      case EXEC  extends PermissionsWithBits(1) // 001
      case NONE  extends PermissionsWithBits(0) // 000

      def toHex: String = bits.toHexString // can create defs/vals inside enum

    // You can define custom companion object with defs/vals that will be merged with auto-generated one
    object PermissionsWithBits:
      def fromBits(bits: Int): PermissionsWithBits =
        PermissionsWithBits.NONE // dummy implementation without actual bit checking

    val readWithBits: PermissionsWithBits = PermissionsWithBits.READ

    println(readWithBits)
    println(s"Bits: ${readWithBits.bits}")
    println(s"HEX: ${readWithBits.toHex}")
    println(PermissionsWithBits.fromBits(4))

    // Standard API
    val first = Permissions.READ.ordinal
    println(first)

    val allPermissions: Array[Permissions] = Permissions.values // Array with all possible values
    allPermissions.foreach(println)

    val readFromString: Permissions = Permissions.valueOf("READ")
    println(readFromString)
