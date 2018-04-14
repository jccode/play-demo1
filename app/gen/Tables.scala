package gen
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.H2Profile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Account.schema ++ Product.schema ++ User.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Account
   *  @param id Database column ID SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param userId Database column USER_ID SqlType(INTEGER)
   *  @param name Database column NAME SqlType(VARCHAR), Length(50,true)
   *  @param balance Database column BALANCE SqlType(INTEGER)
   *  @param createTime Database column CREATE_TIME SqlType(TIMESTAMP)
   *  @param updateTime Database column UPDATE_TIME SqlType(TIMESTAMP) */
  case class AccountRow(id: Long, userId: Int, name: String, balance: Option[Int], createTime: java.sql.Timestamp, updateTime: java.sql.Timestamp)
  /** GetResult implicit for fetching AccountRow objects using plain SQL queries */
  implicit def GetResultAccountRow(implicit e0: GR[Long], e1: GR[Int], e2: GR[String], e3: GR[Option[Int]], e4: GR[java.sql.Timestamp]): GR[AccountRow] = GR{
    prs => import prs._
    AccountRow.tupled((<<[Long], <<[Int], <<[String], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table ACCOUNT. Objects of this class serve as prototypes for rows in queries. */
  class Account(_tableTag: Tag) extends profile.api.Table[AccountRow](_tableTag, "ACCOUNT") {
    def * = (id, userId, name, balance, createTime, updateTime) <> (AccountRow.tupled, AccountRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userId), Rep.Some(name), balance, Rep.Some(createTime), Rep.Some(updateTime)).shaped.<>({r=>import r._; _1.map(_=> AccountRow.tupled((_1.get, _2.get, _3.get, _4, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column USER_ID SqlType(INTEGER) */
    val userId: Rep[Int] = column[Int]("USER_ID")
    /** Database column NAME SqlType(VARCHAR), Length(50,true) */
    val name: Rep[String] = column[String]("NAME", O.Length(50,varying=true))
    /** Database column BALANCE SqlType(INTEGER) */
    val balance: Rep[Option[Int]] = column[Option[Int]]("BALANCE")
    /** Database column CREATE_TIME SqlType(TIMESTAMP) */
    val createTime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("CREATE_TIME")
    /** Database column UPDATE_TIME SqlType(TIMESTAMP) */
    val updateTime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("UPDATE_TIME")

    /** Index over (name) (database name ACCOUNT_NAME) */
    val index1 = index("ACCOUNT_NAME", name)
  }
  /** Collection-like TableQuery object for table Account */
  lazy val Account = new TableQuery(tag => new Account(tag))

  /** Entity class storing rows of table Product
   *  @param id Database column ID SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param name Database column NAME SqlType(VARCHAR), Length(50,true)
   *  @param price Database column PRICE SqlType(INTEGER)
   *  @param stock Database column STOCK SqlType(INTEGER)
   *  @param createTime Database column CREATE_TIME SqlType(TIMESTAMP)
   *  @param updateTime Database column UPDATE_TIME SqlType(TIMESTAMP) */
  case class ProductRow(id: Int, name: String, price: Option[Int], stock: Option[Int], createTime: java.sql.Timestamp, updateTime: java.sql.Timestamp)
  /** GetResult implicit for fetching ProductRow objects using plain SQL queries */
  implicit def GetResultProductRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[Int]], e3: GR[java.sql.Timestamp]): GR[ProductRow] = GR{
    prs => import prs._
    ProductRow.tupled((<<[Int], <<[String], <<?[Int], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table PRODUCT. Objects of this class serve as prototypes for rows in queries. */
  class Product(_tableTag: Tag) extends profile.api.Table[ProductRow](_tableTag, "PRODUCT") {
    def * = (id, name, price, stock, createTime, updateTime) <> (ProductRow.tupled, ProductRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), price, stock, Rep.Some(createTime), Rep.Some(updateTime)).shaped.<>({r=>import r._; _1.map(_=> ProductRow.tupled((_1.get, _2.get, _3, _4, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INTEGER), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column NAME SqlType(VARCHAR), Length(50,true) */
    val name: Rep[String] = column[String]("NAME", O.Length(50,varying=true))
    /** Database column PRICE SqlType(INTEGER) */
    val price: Rep[Option[Int]] = column[Option[Int]]("PRICE")
    /** Database column STOCK SqlType(INTEGER) */
    val stock: Rep[Option[Int]] = column[Option[Int]]("STOCK")
    /** Database column CREATE_TIME SqlType(TIMESTAMP) */
    val createTime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("CREATE_TIME")
    /** Database column UPDATE_TIME SqlType(TIMESTAMP) */
    val updateTime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("UPDATE_TIME")

    /** Index over (name) (database name PRODUCT_NAME) */
    val index1 = index("PRODUCT_NAME", name)
  }
  /** Collection-like TableQuery object for table Product */
  lazy val Product = new TableQuery(tag => new Product(tag))

  /** Entity class storing rows of table User
   *  @param id Database column ID SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param name Database column NAME SqlType(VARCHAR), Length(50,true)
   *  @param password Database column PASSWORD SqlType(VARCHAR), Length(128,true)
   *  @param salt Database column SALT SqlType(VARCHAR), Length(128,true)
   *  @param mobile Database column MOBILE SqlType(VARCHAR), Length(20,true)
   *  @param createTime Database column CREATE_TIME SqlType(TIMESTAMP)
   *  @param updateTime Database column UPDATE_TIME SqlType(TIMESTAMP) */
  case class UserRow(id: Int, name: String, password: String, salt: String, mobile: Option[String], createTime: java.sql.Timestamp, updateTime: java.sql.Timestamp)
  /** GetResult implicit for fetching UserRow objects using plain SQL queries */
  implicit def GetResultUserRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[java.sql.Timestamp]): GR[UserRow] = GR{
    prs => import prs._
    UserRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table USER. Objects of this class serve as prototypes for rows in queries. */
  class User(_tableTag: Tag) extends profile.api.Table[UserRow](_tableTag, "USER") {
    def * = (id, name, password, salt, mobile, createTime, updateTime) <> (UserRow.tupled, UserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), Rep.Some(password), Rep.Some(salt), mobile, Rep.Some(createTime), Rep.Some(updateTime)).shaped.<>({r=>import r._; _1.map(_=> UserRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

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
  lazy val User = new TableQuery(tag => new User(tag))
}
