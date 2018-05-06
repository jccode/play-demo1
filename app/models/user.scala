package models

import play.api.libs.json.OFormat
import play.api.libs.json._
import common.Implics._


//case class User(id: Int, name: String, password: String, salt: String, mobile: Option[String], createTime: java.sql.Timestamp, updateTime: java.sql.Timestamp)
// TODO createTime, updateTime should not provided by user.
case class User(name: String, password: String, salt: String, mobile: Option[String], id: Int, createTime: java.sql.Timestamp, updateTime: java.sql.Timestamp) extends BaseModel

object User {
  implicit val userFormat: OFormat[User] = Json.format[User]
}

case class UserQuery(name: Option[String], mobile: Option[String])

case class UserCreateForm(name: String, password: String, salt: String, mobile: Option[String])

object UserCreateForm {
  implicit val userCreateFormat: OFormat[UserCreateForm] = Json.format[UserCreateForm]
}

case class UserUpdateForm(name: Option[String], password: Option[String], mobile: Option[String])

object UserUpdateForm {
  implicit val userUpdateFormFormat: OFormat[UserUpdateForm] = Json.format[UserUpdateForm]
}
