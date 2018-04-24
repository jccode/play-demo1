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
