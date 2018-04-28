import shapeless.Witness
import shapeless.syntax.singleton._
import shapeless.labelled.{FieldType, field}
import shapeless.{HNil, :: }

val i = "hello" ->> 12
i.self
i.witness

"hello".narrow

def getFieldName[K, V](value: FieldType[K, V])
                      (implicit witness: Witness.Aux[K]): K = witness.value

def getFieldValue[K, V](value: FieldType[K, V]): V = value

getFieldName(i)
getFieldValue(i)

val garfield = ("cat" ->> "Garfield") :: ("orange" ->> true) :: HNil

val j = field[String](123)

//getFieldName(j)
//getFieldValue(j)


