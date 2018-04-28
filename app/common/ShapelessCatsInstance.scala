package common

import cats.Monoid
import shapeless._
import shapeless.labelled.{FieldType, field}


/**
  * Shapeless Cats Instance -- adapter
  *
  * @author 01372461
  */
trait ShapelessCatsInstance {

  /**
    * Helper function create Monoid
    */
  def createMonoid[A](zero: A)(add: (A, A) => A): Monoid[A] =
    new Monoid[A] {
      override def empty: A = zero
      override def combine(x: A, y: A): A = add(x, y)
    }

  implicit val hnilMonoid: Monoid[HNil] = createMonoid[HNil](HNil)((x, y) => HNil)

  implicit def hlistMonoid[H, T <: HList]
  (implicit
   hMonoid: Lazy[Monoid[H]],
   tMonoid: Monoid[T]
  ): Monoid[H :: T] = createMonoid(hMonoid.value.empty :: tMonoid.empty){
    (x, y) => hMonoid.value.combine(x.head, y.head) :: tMonoid.combine(x.tail, y.tail)
  }

  implicit def labeledHlistMonoid[K <: Symbol, V , T <: HList]
  (implicit
   hMonoid: Lazy[Monoid[V]],
   tMonoid: Monoid[T]
  ): Monoid[FieldType[K, V] :: T] = createMonoid(field[K](hMonoid.value.empty) :: tMonoid.empty) {
    (x, y) => field[K](hMonoid.value.combine(x.head, y.head)) :: tMonoid.combine(x.tail, y.tail)
  }

}

object ShapelessCatsInstance extends ShapelessCatsInstance
