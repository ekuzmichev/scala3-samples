package ru.ekuzmichev

object TypeLambdas:
  def main(args: Array[String]): Unit =
    /*
      - kinds = types of types
      - Int, String, case classes (non-generic), etc. = value-level kind (level-0 kind) => attach to values
      - List, Option, etc. = level-1 kind ("generics")
      - Higher-kinded types: Monad, Functor, etc. = level-2 kind ("generics of generics")
     */
    // List itself is similar to function of types like T => List[T] => type constructor
    // type Lambda is a type that turns type T into a List[T]
    type MyList = [T] =>> List[T] // This is kind of the same as the List itself

    type MapWithStringKey = [T] =>> Map[String, T]
    val addressBook: MapWithStringKey[String] = Map.empty
    type MapWithStringKey2[T] = Map[String, T] // the same thing

    type MySpecialEither = [T, E] =>> Either[E, Option[T]]
    val either: MySpecialEither[Int, String] /* ========= Either[String, Option[Int]] */ = Right(Some(2))
    println(either)

    // Why we need type lambdas?
    // monads
    trait Monad[M[_]]:
      def pure[A](a: A): M[A]
      def flatMap[A, B](ma: M[A])(fn: A => M[B]): M[B]

    // Monad takes type constructor with single type
    // Either has 2 types. How to write monad for either?
    class EitherMonad[E] extends Monad[[T] =>> Either[E, T]]: // smth like Either[E, ?] - for any right type
      override def pure[A](a: A): Either[E, A]                                          = Right(a)
      override def flatMap[A, B](ma: Either[E, A])(fn: A => Either[E, B]): Either[E, B] = ma.flatMap(fn)

    // kind projector - compiler plugin to make Either[E, ?] resolve
    // scala 3 does it out of the box with type lambdas
