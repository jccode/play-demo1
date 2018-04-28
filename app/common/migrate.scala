package common

import cats.Monoid
import shapeless._
import shapeless.ops.hlist


/**
  * Migrate Ops
  */
trait MigrateOps {

  trait Migration[A, B] {
    def apply(a: A): B
  }

  implicit class MigrationOps[A](a: A) {
    def migrateTo[B](implicit migration: Migration[A, B]): B = migration.apply(a)
  }

  implicit def genericMigration[A, B, ARepr <: HList, BRepr <: HList, Common <: HList, Added <: HList, Unaligned <: HList]
  (implicit
   aGen: LabelledGeneric.Aux[A, ARepr],
   bGen: LabelledGeneric.Aux[B, BRepr],
   inter: hlist.Intersection.Aux[ARepr, BRepr, Common],
   diff: hlist.Diff.Aux[BRepr, Common, Added],
   monoid: Monoid[Added],
   prepend: hlist.Prepend.Aux[Added, Common, Unaligned],
   align: hlist.Align[Unaligned, BRepr]
  ): Migration[A, B] =
    (a: A) => bGen.from(align( prepend(monoid.empty, inter(aGen.to(a))) ))
}


/**
  * object migrate for import.
  *
  * Be aware import "cats.instances.all._" as well.
  */
object migrate extends MigrateOps with ShapelessCatsInstance
