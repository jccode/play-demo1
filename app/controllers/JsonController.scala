package controllers

import common.RestResult
import javax.inject.{Inject, Singleton}
import models.{Address, Person}
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
    val p = Person("tom", Address(19, "LianHuaRoad"))
    Ok(Json.toJson(RestResult.success(p)))
  }
}
