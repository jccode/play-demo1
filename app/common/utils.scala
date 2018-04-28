package common

import shapeless.Witness
import shapeless.labelled.FieldType

/**
  * Label Utils
  */
object LabelUtils {

  def getFieldName[K, V](value: FieldType[K, V])(implicit witness: Witness.Aux[K]) = witness.value

  def getFieldValue[K, V](value: FieldType[K, V]): V = value

}