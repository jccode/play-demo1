import org.scalatestplus.play.PlaySpec

class SampleSpec extends PlaySpec {

  case class User(name: String, age: Int, password: String)

  "Case class tupled function should works like a charm" must {

    "tupled function turn normal function arguments to tuple style argument" in {
      val args = ("tom", 12, "cat")
      val f = (User.apply _).tupled
      val u = f(args)
      u.name must be ("tom")
      u.age must be (12)
      u.password must be ("cat")
    }

    "unapply function on case class return tuple back" in {
      val u = User("tom", 12, "cat")
      val t = User.unapply(u).get
      t must be (("tom", 12, "cat"))
    }
  }

  "shapeless spec" must {
    "tupled syntax" in {
      import shapeless.syntax.std.tuple._
      val t1 = ("tom", 12)
      val t2 = t1 :+ 1d :+ "cat"
      t2 must be (("tom", 12, 1d, "cat"))
    }

    "Generic transform" in {
      import shapeless.Generic
      val args = ("tom", 12, "cat")

      // Generic of User, Generic of Tuple
      val uGen = Generic[User]
      val tGen = Generic[(String, Int, String)]

      // build uesr
      val u = (User.apply _).tupled(args)
      u.name must be ("tom")

      // transform user to Generic Repr
      val repr = uGen.to(u)

      // transform Generic Repr to Tuple
      val t = tGen.from(repr)
      t must be (args)
    }
  }

}
