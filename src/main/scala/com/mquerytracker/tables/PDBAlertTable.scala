package com.mquerytracker.tables

import com.mquerytracker.models.PDBAlertRow
import com.mquerytracker.utils.IdentifiableTable
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.{Index, ProvenShape}

import scala.concurrent.ExecutionContext.Implicits.global

class PDBAlertTable(tag: Tag) extends Table[PDBAlertRow](tag, _tableName = "pdb_alert") with IdentifiableTable {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name: Rep[String] = column[String]("name")
  def toAddressList: Rep[String] = column[String]("toAddressList")
  def database: Rep[Int] = column[Int]("database")
  def queryThreshold: Rep[Float] = column[Float]("queryThreshold")
  def mailSubjectTemplate: Rep[String] = column[String]("mailSubjectTemplate")
  def mailBodyTemplate: Rep[String] = column[String]("mailBodyTemplate")

  def * : ProvenShape[PDBAlertRow] = {
    (id, name, toAddressList, database, queryThreshold, mailSubjectTemplate, mailBodyTemplate).mapTo[PDBAlertRow]
  }

  def uniqueConstraint: Index = index(
    name = "idx_uniq_alert_db",
    (name, toAddressList, database, queryThreshold),
    unique = true
  )

}
