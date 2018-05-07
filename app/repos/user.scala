package repos

import java.sql.Timestamp

import com.google.inject.ImplementedBy
import common.Slick._
import common.migrate._
import common.utils._
import dao.Tables
import javax.inject.{Inject, Singleton}
import models.{User, UserQuery, UserUpdate}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.collection.immutable
import scala.concurrent.{ExecutionContext, Future}


@ImplementedBy(classOf[SlickUserRepo])
trait UserRepo {

  def all: Future[Seq[User]]

  def query(userQuery: UserQuery): Future[Seq[User]]

  def get(id: Int): Future[Option[models.User]]

  def insert(user: User): Future[Int]

  def update(user: User): Future[Int]

  def patch(id: Int, user: UserUpdate): Future[Int]

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

//  override def patch(id: Int, user: UserUpdate): Future[Int] = {
//    val fields: immutable.Seq[(String, Option[Any])] = definedFields(user)
//    val updateFields = fields.map{ case (name, value) => s"""$name = '${value.get}'"""}.mkString(",")
//    val action = sqlu"""update #${User.baseTableRow.tableName} set #$updateFields where id = $id"""
//    db.run(action)
//  }

  override def patch(id: Int, user: UserUpdate): Future[Int] = {
    get(id).flatMap { userOpt =>
      userOpt.map { u0 =>
        val u1 = u0.copy(name = user.name.getOrElse(u0.name), password = user.password.getOrElse(u0.password), mobile = user.mobile.orElse(u0.mobile))
        update(u1)
      }.getOrElse(Future{0})
    }
  }
}
