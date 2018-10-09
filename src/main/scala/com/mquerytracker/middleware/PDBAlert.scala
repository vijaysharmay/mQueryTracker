package com.mquerytracker.middleware

import com.mquerytracker.models.PDBAlertRow
import com.mquerytracker.tables.{CRUDRepository, PDBAlertTable}
import com.mquerytracker.utils.SQLiteDBConnector
import slick.lifted.TableQuery

trait PDBAlert extends SQLiteDBConnector{
  object PDBAlertRepo extends CRUDRepository[PDBAlertTable, PDBAlertRow](db){
    override def table: TableQuery[PDBAlertTable] = TableQuery[PDBAlertTable]
  }
}

