package com.mquerytracker.middleware

import com.mquerytracker.models.PDBLogRow
import com.mquerytracker.tables.{CRUDRepository, PDBLogTable}
import com.mquerytracker.utils.SQLiteDBConnector
import slick.lifted.TableQuery

trait PDBLog extends SQLiteDBConnector{
  object PDBLogRepo extends CRUDRepository[PDBLogTable, PDBLogRow](db){
    override def table: TableQuery[PDBLogTable] = TableQuery[PDBLogTable]
  }
}

