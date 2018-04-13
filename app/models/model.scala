package models

import play.api.libs.json.Json


case class Address(number: Int, street: String)
case class Person(name: String, address: Address)

object Implics {
  implicit val addressWriter = Json.writes[Address]
  implicit val personWriter = Json.writes[Person]
}
