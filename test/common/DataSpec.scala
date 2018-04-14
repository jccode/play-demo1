package common

import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{JsObject, JsValue, Json}

/**
  * DataSpec
  *
  * @author 01372461
  */
class DataSpec extends PlaySpec {

  "RestResult" must {
    "with one instance of RestSuccess" in {
      val ret = new RestSuccess("Hello")
      ret.isError must be(false)
      ret.payload must be(Some("Hello"))
      ret.error must be (None)
    }

    "with on instance of RestFailed" in {
      val ret = new RestFailed(msg = "error message")
      ret.isError must be (true)
      ret.payload must be (None)
      ret.error must not be (None)
      val err = ret.error.get
      err.code must be (0)
      err.msg must be ("error message")
    }

    "also with success/fail shortcuts" in {
      val ret = RestResult.success("Hello")
      ret.isError must be (false)
      ret.payload must be(Some("Hello"))
      ret.error must be (None)

      val ret2 = RestResult.fail("error message")
      ret2.isError must be (true)
      ret2.payload must be (None)
      ret2.error.get.msg must be ("error message")
    }

    "support play json serialize" in {
      val s = Json.toJson(Error(0, "err")).toString()
      s must be ("""{"code":0,"msg":"err"}""")

      val r1 = Json.toJson(RestResult.success("Hello")).toString()
      r1 must be ("""{"isError":false,"payload":"Hello","error":{}}""")

      val r2 = Json.toJson(RestResult.fail("err")).toString()
      r2 must be ("""{"isError":true,"payload":"","error":{"code":0,"msg":"err"}}""")
    }
  }
}
