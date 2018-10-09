package com.mquerytracker.tables

import com.mquerytracker.models.PDBRow
import com.mquerytracker.utils.IdentifiableTable
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.Future

abstract class CRUDRepository[T <: Table[E] with IdentifiableTable, E <: PDBRow](db: Database) {
  def table: TableQuery[T]

  def insert(row: E): Future[Int] = db.run(table returning table.map(_.id) += row)
  def update(row: E): Future[Int] = db.run(table.filter(_.id === row.id).update(row))
  def delete(row: E): Future[Int] = db.run(table.filter(_.id === row.id).delete)
  def get: Future[Seq[E]] = db.run(table.result)
}
