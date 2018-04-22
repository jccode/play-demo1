package repos

import dao._
import javax.inject.{Inject, Singleton}
import models.UserQuery
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}


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
    models.User(userRow.id, userRow.name, userRow.password, userRow.salt, userRow.mobile, userRow.createTime, userRow.updateTime)
  }

  override def close: Future[Unit] = Future.successful(db.close())

  override def get(id: Int): Future[Option[models.User]] = {
    val f = db.run(User.filter(_.id === id).result)
    f.map(_.map(userRowToUser).headOption)
  }

  override def query(query: UserQuery): Future[Seq[models.User]] = {
    val f = db.run(User.filter(t => {
      query.name.fold(true.bind)(t.name === _)
//      &&
//      query.mobile.fold(true.bind){mobile => t.mobile.isDefined && (t.mobile === Some(mobile))}
    }).result)
    f.map(_.map(userRowToUser))
  }
}