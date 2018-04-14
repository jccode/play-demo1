package common

import play.api.libs.json._

final case class Error(code: Int, msg: String)

object Error {
  implicit val errorWrites: OWrites[Error] = Json.writes[Error]
}

sealed trait RestResult[+T] {
  def isError: Boolean
  def payload: Option[T]
  def error: Option[Error]
}

final class RestSuccess[T](private val data: T) extends RestResult[T] {
  override def isError: Boolean = false

  override def payload: Option[T] = Option(data)

  override def error: Option[Error] = None
}

final class RestFailed(private val code: Int = 0, private val msg: String = "") extends RestResult[Nothing] {
  override def isError: Boolean = true

  override def payload: Option[Nothing] = None

  override def error: Option[Error] = Some(Error(code, msg))
}


object RestResult {
  def success[T](data: T) = new RestSuccess[T](data)

  def fail(code: Int = 0, msg: String = "") = new RestFailed(code, msg)

  def fail(msg: String): RestFailed = fail(0, msg)

  implicit def restResultWrites[T](implicit fmt: Writes[T]): Writes[RestResult[T]] =
    (r: RestResult[T]) =>
      Json.obj("isError" -> r.isError) +
      ("payload" -> r.payload.map(x => fmt.writes(x)).getOrElse(JsString(""))) +
      ("error" -> r.error.map(Json.toJson(_)).getOrElse(JsObject.empty))
}
