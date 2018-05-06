package repos

import java.sql.Timestamp

import com.google.inject.ImplementedBy
import common.Slick._
import dao.Tables
import javax.inject.{Inject, Singleton}
import models.{User, UserQuery}
import common.migrate._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}


@ImplementedBy(classOf[SlickUserRepo])
trait UserRepo {

  def all: Future[Seq[User]]

  def query(userQuery: UserQuery): Future[Seq[User]]

  def get(id: Int): Future[Option[models.User]]

  def insert(user: User): Future[Int]

  def update(user: User): Future[Int]

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

  private def userRowToUser(userRow: UserRow) = userRow.migrateTo[models.User]

  private def userToUserRow(user: models.User): UserRow = user.migrateTo[UserRow]

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
    val now = new Timestamp(System.currentTimeMillis())
    val u = user.copy(createTime = now, updateTime = now)
    db.run(User returning User.map(_.id) += userToUserRow(u))
  }

  override def update(user: models.User): Future[Int] = {
    val now = new Timestamp(System.currentTimeMillis())
    val u = user.copy(updateTime = now)
    db.run(User.filter(_.id === user.id).update(userToUserRow(u)))
  }

}
