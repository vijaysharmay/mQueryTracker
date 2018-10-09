package com.mquerytracker.tables

import com.mquerytracker.models.PDBAccessRow
import com.mquerytracker.utils.IdentifiableTable
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.{Index, ProvenShape}

import scala.concurrent.ExecutionContext.Implicits.global

class PDBAccessTable(tag: Tag) extends Table[PDBAccessRow](tag, _tableName = "pdb_access") with IdentifiableTable {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name: Rep[String] = column[String]("name")
  def user: Rep[String] = column[String]("user")
  def passwd: Rep[String] = column[String]("passwd")
  def host: Rep[String] = column[String]("host")
  def port: Rep[Int] = column[Int]("port")
  def authSource: Rep[String] = column[String]("authSource")

  def * : ProvenShape[PDBAccessRow] = {
    (id, name, user, passwd, host, port, authSource).mapTo[PDBAccessRow]
  }

  def uniqueConstraint: Index = index(
    name = "idx_uniq_access_db",
    (name, user, passwd, host, port, authSource),
    unique = true
  )

}


