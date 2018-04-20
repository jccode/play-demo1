package repos

import com.google.inject.ImplementedBy
import models.User

import scala.concurrent.Future

@ImplementedBy(classOf[SlickUserRepo])
trait UserRepo {

  def all: Future[Seq[User]]

  def close: Future[Unit]
}