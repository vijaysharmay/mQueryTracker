package com.mquerytracker.models

case class PDBLogRow(id: Int, db_name: String, query: String, ts: String, runtime: Int) extends PDBRow
