package com.mquerytracker.models

case class PDBAlertRow(id: Int, name: String, toAddressList: String, database: Int, queryThreshold: Float, mailSubjectTemplate: String, mailBodyTemplate: String) extends PDBRow
