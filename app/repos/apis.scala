package repos

import models.User

import scala.concurrent.Future

trait UserRepo {

  def all: Future[Seq[User]]

}