import java.sql.Timestamp

import models.User
import play.api.inject.guice.{GuiceApplicationBuilder, GuiceApplicationLoader}
import repos.UserRepo
import shapeless.{HList, LabelledGeneric}
import shapeless.ops.record.ToMap
import shapeless.record._
import common.migrate._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

case class UpdateForm(name: Option[String], age: Option[Int], password: Option[String])

object Foo extends App {
  val form = UpdateForm(Some("hello"), None, Some("18100001111"))
  val repr = LabelledGeneric[UpdateForm].to(form)

  println(form)
  println(repr)

  val m = repr.toMap
  println(m)

  def notEmptyFields[T, Repr <: HList, K <: Symbol, V <: Option[_]]
  (t: T)
  (implicit gen: LabelledGeneric.Aux[T, Repr],
   toMap: ToMap.Aux[Repr, K, V]): List[(String, V)] // (List[String], List[V])
  = {

    val repr = LabelledGeneric[T].to(t)
    val map: Map[K, V] = repr.toMap
    map.toList.filter(_._2.isDefined).map(x => (x._1.name, x._2)) //.unzip
  }

  val f = notEmptyFields(form).map{ case (name, value) => s""""$name" = "${value.get}""""}.mkString(",")
  println(f)
  println(s"update USER set $f where id = 1")


  def injectTest(): Unit = {
    val injector = new GuiceApplicationBuilder().injector()
    val userRepo = injector.instanceOf[UserRepo]
    val users = Await.result(userRepo.all, Duration.Inf)
    users.foreach(println)
    val now = new Timestamp(System.currentTimeMillis())
    val u1 = User("tom1", "pwd1", "salt1", Some("18100001111"), 1, now, now)
    val r = userRepo.update(u1)
    println(r)

//    val u2 = Await.result(userRepo.get(1), 1 second)
//    println(u2)
  }

  injectTest()


  def migrateTest(): Unit = {
    case class User(name: String, age: Int, mobile: Option[String], createTime: Option[java.sql.Timestamp])
    case class UserV1(name: String, age: Int, mobile: Option[String], createTime: java.sql.Timestamp)

    val now = new Timestamp(System.currentTimeMillis())
    val u1 = User("tom", 18, Some("18110010001"), Some(now))
    val v1 = u1.migrateTo[UserV1]
    println(v1)
  }

//  migrateTest()

}