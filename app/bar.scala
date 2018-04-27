import cats.kernel.Monoid
import shapeless.HNil

/**
  * bar
  *
  * @author 01372461
  */
object bar extends App {

  case class User(name: String, age: Int, mobile: Option[String])
  case class UserV1(name: String, age: Int)
  case class UserV2(age: Int, name: String)
  case class UserV3(name: String, age: Int, gender: Boolean)

  def migrate(): Unit = {
    import common.migrate._
    import scala.reflect.runtime.universe._

    val u1 = User("tom", 18, Some("18138438111"))

    val v1 = u1.migrateTo[UserV1]
    println(v1)

    val v2 = u1.migrateTo[UserV2]
    println(v2)

//    val v3 = u1.migrateTo[UserV3]
//    println(v3)
  }

  def debug(): Unit = {
    //  implicitly[Migration[User, UserV2]]
    //  println(reify(implicitly[Migration[User, UserV1]]))
  }

  def kitty(): Unit = {
    import cats.Monoid
    import cats.instances.all._
    import common.cat._
    import shapeless._

    println(Monoid[String].combine("Hello", " world"))
    val m = Monoid[String::Int::HNil]
    println(m.empty)
    println(m.combine("Hello"::10::HNil, "world"::11::HNil))
  }

  kitty()
}
