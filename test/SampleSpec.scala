import org.scalatestplus.play.PlaySpec

class SampleSpec extends PlaySpec {

  "Case class tupled function should works like a charm" must {
    case class User(name: String, age: Int, password: String)

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

}
