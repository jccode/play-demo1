package models


/**
  * Base model
  */
trait BaseModel {
  def id: Int
  def createTime: java.sql.Timestamp
  def updateTime: java.sql.Timestamp
}

