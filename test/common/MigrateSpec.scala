package common

import org.scalatestplus.play.PlaySpec
import common.migrate._

class MigrateSpec extends PlaySpec {

  case class User(name: String, age: Int, mobile: Option[String])
  case class UserV1(name: String, age: Int)
  case class UserV2(age: Int, name: String)
  case class UserV3(name: String, age: Int, mobile: Option[String], password: String)

  val (name, age, mobile) = ("tom", 18, Some("18100001111"))
  val u1 = User(name, age, mobile)

  "case class migrate" must {

    "delete field, also keep field orders" in {
      val v1 = u1.migrateTo[UserV1]
      v1 must be (UserV1(name, age))
    }

    "delete field, field order changes" in {
      val v2 = u1.migrateTo[UserV2]
      v2 must be (UserV2(age, name))
    }

    "add field" in {
//      val v3 = u1.migrateTo[UserV3]
//      v3 must be (UserV3(name, age, mobile, ""))
    }
  }
}
