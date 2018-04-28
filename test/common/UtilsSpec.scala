package common

import org.scalatestplus.play.PlaySpec

/**
  * UtilsSpec
  *
  * @author 01372461
  */
class UtilsSpec extends PlaySpec {

  "Label utils" in {
    import shapeless.syntax.singleton._

    val x = "hello" ->> 20
    LabelUtils.getFieldName(x) must be ("hello")
    LabelUtils.getFieldValue(x) must be (20)
  }
}
