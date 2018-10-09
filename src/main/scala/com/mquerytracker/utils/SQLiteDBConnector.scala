package com.mquerytracker.utils

import slick.jdbc.JdbcBackend.Database

trait SQLiteDBConnector {
  val db = Database.forConfig(path = "sqlite")
}

