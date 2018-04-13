package common

final case class Error(code: Int, message: String)

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

final class RestFailed(private val code: Int = 0, private val message: String = "") extends RestResult[Nothing] {
  override def isError: Boolean = true

  override def payload: Option[Nothing] = None

  override def error: Option[Error] = Some(Error(code, message))
}

