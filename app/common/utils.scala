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

trait PlayUtils {

  import play.api.libs.json.Reads
  import play.api.mvc.{AnyContent, Request, Result}
  import play.api.mvc.Results._
  import scala.concurrent.{ExecutionContext, Future}


  def success[T](t: T)(implicit fmt: Writes[T]): Result =
    Ok(Json.toJson(RestResult.success(t)))

  def fail(msg: String)(implicit fmt: Writes[Error]): Result =
    BadRequest(Json.toJson(RestResult.fail(msg)))

  def fail(code: Int, msg: String)(implicit fmt: Writes[Error]): Result =
    BadRequest(Json.toJson(RestResult.fail(code, msg)))


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
          case error: JsError => Future { fail(errorJson(error).toString()) }
        }
      case None =>
        Future{ fail("Request body was not json") }
    }
  }

  /**
    * Result Opts
    * @param value
    * @tparam T
    */
  implicit class ResultOps[T](value: T) {

    def success(implicit fmt: Writes[T]): Result =
      Ok(Json.toJson(RestResult.success(value)))

    def fail(implicit fmt: Writes[Error]): Result =
      BadRequest(Json.toJson(RestResult.fail(value.toString)))
  }
}

trait CommonUtils {

  import shapeless.{HList, LabelledGeneric}
  import shapeless.ops.record.ToMap
  import shapeless.record._


  def definedFields[T, Repr <: HList, K <: Symbol]
  (t: T)
  (implicit gen: LabelledGeneric.Aux[T, Repr],
   toMap: ToMap.Aux[Repr, K, Option[Any]]): List[(String, Option[Any])] = {
    val repr = LabelledGeneric[T].to(t)
    val map: Map[K, Option[Any]] = repr.toMap
    map.toList.filter(_._2.isDefined).map(x => (x._1.name, x._2))
  }

}