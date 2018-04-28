package common

import play.api.libs.json._

/**
  * Label Utils
  */
object LabelUtils {

  import shapeless.Witness
  import shapeless.labelled.FieldType

  def getFieldName[K, V](value: FieldType[K, V])(implicit witness: Witness.Aux[K]) = witness.value

  def getFieldValue[K, V](value: FieldType[K, V]): V = value

}

object utils {

  import play.api.libs.json.Reads
  import play.api.mvc.{AnyContent, Request, Result}
  import play.api.mvc.Results._
  import scala.concurrent.{ExecutionContext, Future}

  def errorJson(error: JsError): JsObject = {
    val fields = error.errors.map { case(path, errors) =>
      val name = path.toJsonString
      val value = JsString(errors.map(e => e.message).mkString(","))
      (name, value)
    }
    JsObject(fields)
  }

  def withRequestJson[A](request: Request[AnyContent])(fn: A => Future[Result])(implicit reads: Reads[A], ec: ExecutionContext): Future[Result] = {
    request.body.asJson match {
      case Some(body) =>
        Json.fromJson(body) match {
          case success: JsSuccess[A] => fn(success.value)
          case error: JsError => Future {BadRequest(errorJson(error))}
        }
      case None =>
        Future{BadRequest(Json.obj("error" -> "Request body was not json"))}
    }
  }
}