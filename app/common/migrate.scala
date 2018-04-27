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

  implicit def genericMigration[A, B, ARepr <: HList, BRepr <: HList, Unaligned <: HList]
  (implicit
   aGen: LabelledGeneric.Aux[A, ARepr],
   bGen: LabelledGeneric.Aux[B, BRepr],
   inter: hlist.Intersection.Aux[ARepr, BRepr, Unaligned],
   align: hlist.Align[Unaligned, BRepr]
  ): Migration[A, B] =
    (a: A) => bGen.from(align.apply(inter.apply(aGen.to(a))))
}



