package common

import org.scalatestplus.play.PlaySpec
import play.api.libs.json.Json

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

  "PageResult" must {
    "it's property should calculate correctly" in {
      val p = PageResult(List("H", "e", "l", "l", "o"), 1, 34)
      p.list.size must be (5)
      p.pageSize must be (20)
      p.page must be (1)
      p.total must be (34)
      p.totalPage must be (2)
    }

    "default pageSize can be change by implicit value" in {
      implicit val pageSize: Int = 10
      val p = PageResult(List("H", "e", "l", "l", "o"), 1, 34)
      p.pageSize must be (10)
      p.totalPage must be (4)
    }

    "support play json serialize" in {
      implicit val pageSize: Int = 10
      val p = PageResult(List("Hello"), 1, 34)
      val s = Json.toJson(p).toString()
      s must be ("""{"list":["Hello"],"page":1,"total":34,"pageSize":10,"totalPage":4}""")
    }
  }
}
