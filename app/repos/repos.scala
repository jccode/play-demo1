package repos

import javax.inject.{Inject, Singleton}
import models.User
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRepo @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class UserTable(_tableTag: Tag) extends profile.api.Table[User](_tableTag, "USER") {
    def * = (id, name, password, salt, mobile, createTime, updateTime) <> ((User.apply _).tupled, User.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), Rep.Some(password), Rep.Some(salt), mobile, Rep.Some(createTime), Rep.Some(updateTime)).shaped.<>({r=>import r._; _1.map(_=> (User.apply _).tupled((_1.get, _2.get, _3.get, _4.get, _5, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INTEGER), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column NAME SqlType(VARCHAR), Length(50,true) */
    val name: Rep[String] = column[String]("NAME", O.Length(50,varying=true))
    /** Database column PASSWORD SqlType(VARCHAR), Length(128,true) */
    val password: Rep[String] = column[String]("PASSWORD", O.Length(128,varying=true))
    /** Database column SALT SqlType(VARCHAR), Length(128,true) */
    val salt: Rep[String] = column[String]("SALT", O.Length(128,varying=true))
    /** Database column MOBILE SqlType(VARCHAR), Length(20,true) */
    val mobile: Rep[Option[String]] = column[Option[String]]("MOBILE", O.Length(20,varying=true))
    /** Database column CREATE_TIME SqlType(TIMESTAMP) */
    val createTime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("CREATE_TIME")
    /** Database column UPDATE_TIME SqlType(TIMESTAMP) */
    val updateTime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("UPDATE_TIME")

    /** Index over (name) (database name USER_NAME) */
    val index1 = index("USER_NAME", name)
  }
  /** Collection-like TableQuery object for table User */
  private lazy val users = TableQuery[UserTable]

  def list: Future[Seq[User]] = db.run(users.result)
}