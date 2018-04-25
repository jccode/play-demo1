package common

import shapeless._
import shapeless.ops.hlist

object migrate {

  trait Migration[A, B] {
    def apply(a: A): B
  }

  implicit class MigrationOps[A](a: A) {
    def migrateTo[B](implicit migration: Migration[A, B]): B = migration.apply(a)
  }

  implicit def genericMigration[A, B](implicit aGen: Generic[A],
                                      bGen: Generic[B]
                                     ): Migration[A, B] =
    new Migration[A, B] {
      override def apply(a: A): B = ???
    }

}



