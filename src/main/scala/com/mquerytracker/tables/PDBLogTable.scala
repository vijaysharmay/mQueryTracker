package com.mquerytracker.tables

import com.mquerytracker.models.PDBLogRow
import com.mquerytracker.utils.IdentifiableTable
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.{Index, ProvenShape}

class PDBLogTable(tag: Tag) extends Table[PDBLogRow](tag, _tableName = "pdb_log") with IdentifiableTable {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def db_name: Rep[String] = column[String]("db_name")
  def query: Rep[String] = column[String]("query")
  def time_stamp: Rep[String] = column[String]("time_stamp")
  def runtime: Rep[Int] = column[Int]("runtime")

  def * : ProvenShape[PDBLogRow] = {
    (id, db_name, query, time_stamp, runtime).mapTo[PDBLogRow]
  }

  def uniqueConstraint: Index = index(
    name = "idx_uniq_log_db",
    (db_name, query, time_stamp),
    unique = true
  )

}
