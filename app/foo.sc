import shapeless._
import cats.Monoid
import cats.instances.all._

case class User(name: String, age: Int, mobile: Option[String])
case class UserV2(name: String, age: Int, password: String)

val u1 = User("tom", 18, Some("18138438111"))
val u1_2 = UserV2("tom", 18, "111111")

val genUser = Generic[User]
val uRepr = genUser.to(u1)
genUser.from("tom"::18::None::HNil)

// case class -> tuple
// How to? by Generic?
// Basic principles:
//  1. Generic[A].to(instanceA)      =>  Turn instanceA into genericRepr
//  2. Generic[B].from(genericRepr)  =>  Turn genericRepr into Type B.

val t1 = ("tom", 18, Some("18138438111"))
val genUserTuple = Generic[(String, Int, Option[String])]
genUserTuple.from(genUser.to(u1))

Monoid[String].combine("Hello", "world")

//import common.Cats._
//val m = Monoid[String::Int::Option[String]::HNil]


