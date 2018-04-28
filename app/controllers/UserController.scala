package controllers

import common.migrate._
import common.utils._
import javax.inject.{Inject, Singleton}
import models.{User, UserCreateForm, UserQuery}
import play.api.mvc.{AbstractController, ControllerComponents}
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
    repo.all.map(success(_))
  }

  def query(name: Option[String], mobile: Option[String]) = Action.async {
    repo.query(UserQuery(name, mobile)).map(_.success)
  }

  def get(id: Int) = Action.async {
    repo.get(id).map(_.success)
  }

  def create() = Action.async { request =>
    withRequestJson[UserCreateForm](request) { userForm =>
      val user = userForm.migrateTo[User]
      repo.insert(user).map(success(_))
    }
  }

}
