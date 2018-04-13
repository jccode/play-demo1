package controllers

import javax.inject.{Inject, Singleton}
import models.{Address, Person}
import models.Implics._
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

/**
  * JsonController
  *
  * @author 01372461
  */
@Singleton
class JsonController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def demo1() = Action {
    Ok(Json.toJson(Person("tom", Address(19, "LianHuaRoad"))))
  }
}
