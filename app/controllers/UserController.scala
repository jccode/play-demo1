package controllers

import javax.inject.{Inject, Singleton}
import models.UserQuery
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import repos.UserRepo

import scala.concurrent.{ExecutionContext, Future}

/**
  * UserController
  *
  * @author 01372461
  */
@Singleton
class UserController @Inject() (cc: ControllerComponents, repo: UserRepo)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def userList() = Action.async {
    repo.all.map { users =>
      Ok(Json.toJson(users))
    }
  }

  def query(name: Option[String], mobile: Option[String]) = Action.async {
    repo.query(UserQuery(name, mobile)).map { users =>
      Ok(Json.toJson(users))
    }
  }

  def get(id: Int) = Action.async {
    repo.get(id).map { userOpt =>
      Ok(Json.toJson(userOpt))
    }
  }

  def create() = Action.async {
    Future { Ok("Hello") }
  }
}
