package models

import java.sql.Timestamp
import java.text.SimpleDateFormat

import play.api.libs.json.{Format, JsString, JsSuccess, JsValue}


/**
  * Base model
  */
trait BaseModel {
  def id: Int
  def createTime: java.sql.Timestamp
  def updateTime: java.sql.Timestamp
}


/**
  * Implics
  *
  * @author 01372461
  */
object Implics {

  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Timestamp(format.parse(str).getTime))
    }
    def writes(ts: Timestamp) = JsString(format.format(ts))
  }

}
