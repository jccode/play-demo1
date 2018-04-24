package common

import slick.lifted.{CanBeQueryCondition, Query, Rep}

object Slick {

  /**
    * Conditional Query Filter
    *
    * Supposed following definition
    * <code>
    * case class UserRow(id: Int, name: String, mobile: Option[String])
    *
    * class User(_tableTag: Tag) extends profile.api.Table[UserRow](_tableTag, "USER") {
    *   def * = (id, name, mobile) <> (UserRow.tupled, UserRow.unapply)
    *
    *   def ? = (Rep.Some(id), Rep.Some(name), mobile).shaped.<>({r=>import r._; _1.map(_=> UserRow.tupled((_1.get, _2.get, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    *
    *   val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    *
    *   val name: Rep[String] = column[String]("NAME", O.Length(50,varying=true))
    *
    *   val mobile: Rep[Option[String]] = column[Option[String]]("MOBILE", O.Length(20,varying=true))
    * }
    *
    * lazy val User = new TableQuery(tag => new User(tag))
    * </code>
    *
    * Usage code as below:
    *
    * <code>
    *   case class UserQuery(name: Option[String], mobile: Option[String])
    *   val query = UserQuery(Some("some_name"), Some("some_number"))
    * </code>
    *
    * @param q
    * @tparam M
    * @tparam U
    * @tparam C
    */
  implicit class ConditionalQueryFilter[M, U, C[_]](q: Query[M, U, C]) {

    def filterIf[T <: Rep[_]](p: Boolean)(f: M => T)(implicit wt: CanBeQueryCondition[T]): Query[M, U, C] = if (p) q.filter(f) else q

    def filterOpt[D, T <: Rep[_]](option: Option[D])(f: (M, D) => T)(implicit wt: CanBeQueryCondition[T]): Query[M, U, C] = option.map(d => q.filter(a => f(a, d))).getOrElse(q)

  }

}

