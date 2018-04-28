package controllers

import java.sql.Timestamp

import cats.Monoid
import javax.inject.{Inject, Singleton}
import models.{User, UserCreateForm, UserQuery}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import common.utils._
import common.migrate._
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

  def create() = Action.async { request =>
    withRequestJson[UserCreateForm](request) { userForm =>
      val user = userForm.migrateTo[User]
      repo.insert(user).map(id => Ok(id.toString()))
    }
  }

}
