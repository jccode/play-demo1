package common

import cats.kernel.Monoid
import cats.instances.all._
import common.cat._
import org.scalatestplus.play.PlaySpec
import shapeless.{HNil, ::}

class CatSpec extends PlaySpec {

  "HList monoid" must {
    "cat's monoid should test" in {
      Monoid[String].empty must be ("")
    }

    "HNil monoid" in {
      Monoid[HNil].empty must be (HNil)
    }

    "HList monoid" in {
      val m = Monoid[String :: Int :: HNil]
      m.empty must be ("" :: 0 :: HNil)
      m.combine("Hello" :: 10 :: HNil, "world" :: 11 :: HNil) must be ("Helloworld" :: 21 :: HNil)
    }
  }

}
