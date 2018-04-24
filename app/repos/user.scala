package repos

import com.google.inject.ImplementedBy
import dao.Tables
import javax.inject.{Inject, Singleton}
import models.{User, UserQuery}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import common.Slick._

import scala.concurrent.{ExecutionContext, Future}


@ImplementedBy(classOf[SlickUserRepo])
trait UserRepo {

  def all: Future[Seq[User]]

  def query(userQuery: UserQuery): Future[Seq[User]]

  def get(id: Int): Future[Option[models.User]]

  def insert(user: User): Future[Int]

  def close: Future[Unit]
}


@Singleton
class SlickUserRepo @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends UserRepo with Tables {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  override val profile = dbConfig.profile

  override def all: Future[Seq[models.User]] = {
    val f = db.run(User.result)
    f.map(_.map(userRowToUser))
  }

  private def userToUserRow(user: models.User): UserRow = {
    UserRow(user.id, user.name, user.password, user.salt, user.mobile, user.createTime, user.updateTime)
  }

  private def userRowToUser(userRow: UserRow): models.User = {
    models.User(userRow.name, userRow.password, userRow.salt, userRow.mobile, userRow.id, userRow.createTime, userRow.updateTime)
  }

  override def close: Future[Unit] = Future.successful(db.close())

  override def get(id: Int): Future[Option[models.User]] = {
    val f = db.run(User.filter(_.id === id).result)
    f.map(_.map(userRowToUser).headOption)
  }

  override def query(query: UserQuery): Future[Seq[models.User]] = {
    val f = db.run(User
      .filterOpt(query.name)(_.name === _)
      .filterOpt(query.mobile)(_.mobile === _)
      .result)
    f.map(_.map(userRowToUser))
  }

  override def insert(user: models.User): Future[Int] = {
    db.run(User returning User.map(_.id) += userToUserRow(user))
  }
}