package models

import play.api.libs.json._
import Implics._

case class Address(number: Int, street: String)
case class Person(name: String, address: Address)

object Address {
  implicit val addressFormat: OFormat[Address] = Json.format[Address]
}

object Person {
  implicit val personFormat: OFormat[Person] = Json.format[Person]
}

case class User(id: Int, name: String, password: String, salt: String, mobile: Option[String], createTime: java.sql.Timestamp, updateTime: java.sql.Timestamp)

object User {
  implicit val userFormat: OFormat[User] = Json.format[User]
}

case class UserQuery(name: Option[String], mobile: Option[String])
