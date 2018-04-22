package repos

import com.google.inject.ImplementedBy
import models.{User, UserQuery}

import scala.concurrent.Future

@ImplementedBy(classOf[SlickUserRepo])
trait UserRepo {

  def all: Future[Seq[User]]

  def query(userQuery: UserQuery): Future[Seq[User]]

  def get(id: Int): Future[Option[models.User]]

  def close: Future[Unit]
}