package com.mquerytracker.models

case class PDBAccessRow(id: Int, name: String, user: String, passwd: String, host: String, port: Int, authSource: String) extends PDBRow
