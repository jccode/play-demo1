package controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import repos.UserRepo

import scala.concurrent.ExecutionContext

/**
  * UserController
  *
  * @author 01372461
  */
@Singleton
class UserController @Inject() (cc: ControllerComponents, repo: UserRepo)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def userList() = Action.async {
    repo.list.map { users =>
      Ok(Json.toJson(users))
    }
  }
}
