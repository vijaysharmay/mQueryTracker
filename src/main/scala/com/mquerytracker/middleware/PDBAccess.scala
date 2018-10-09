package com.mquerytracker.middleware

import com.mquerytracker.models.PDBAccessRow
import com.mquerytracker.tables.{CRUDRepository, PDBAccessTable}
import com.mquerytracker.utils.SQLiteDBConnector
import slick.lifted.TableQuery

trait PDBAccess extends SQLiteDBConnector{
  object PDBAccessRepo extends CRUDRepository[PDBAccessTable, PDBAccessRow](db){
    override def table: TableQuery[PDBAccessTable] = TableQuery[PDBAccessTable]
  }
}
