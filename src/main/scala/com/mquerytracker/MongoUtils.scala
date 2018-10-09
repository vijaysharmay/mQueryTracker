package com.mquerytracker

import java.text.SimpleDateFormat

import com.mquerytracker.middleware.{PDBAccess, PDBLog}
import com.mquerytracker.models.{PDBAccessRow, PDBLogRow}
import com.typesafe.scalalogging.LazyLogging
import org.mongodb.scala.MongoClient
import slick.dbio.DBIO

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

trait MongoUtilsFramework
  extends LazyLogging
  with PDBAccess
  with PDBLog

trait MongoUtils extends MongoUtilsFramework{

  def test_connection(row: PDBAccessRow): Future[Long] = {
    val client: MongoClient = MongoClient(get_connection_string(row))
    val rowsFuture = client.getDatabase(row.name).getCollection("system.profile").count().toFuture()
    rowsFuture
  }

  def get_connection_string(row: PDBAccessRow): String = {
//    val options = s"?authSource=${row.authSource}&connectTimeoutMS=5&socketTimeoutMS=5&maxTimeMS=5"
    val options = s"?authSource=${row.authSource}"
    s"mongodb://${row.user}:${row.passwd}@${row.host}:${row.port}/$options"
  }

  def get_profiled_databases(): Unit = {
    PDBAccessRepo.get.onComplete({
      case Success(rows) => {
        for (row <- rows){
          get_slow_queries(get_connection_string(row), row.name)
        }
      }
      case Failure(e) => logger.info(s"Failed with error - $e")
    })
  }

  def get_slow_queries(mongoConnection: String, db_name: String): Unit = {
    try{
      val client: MongoClient = MongoClient(mongoConnection)
      val rowsFuture = client.getDatabase(db_name).getCollection("system.profile").find().toFuture()

      rowsFuture.onComplete({
        case Success(result) => {
          for (row <- result){
            val runtime = row("millis").asInt32().getValue
            val dateFormat: SimpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS")
            val timeStampString: String = dateFormat.format(row("ts").asDateTime().getValue.toLong)

            val insertFuture = PDBLogRepo.insert(
              PDBLogRow(0, db_name, row.toJson(), timeStampString, runtime)
            )

            insertFuture.onComplete({
              case Success(n) => logger.info(s"$n rows affected by upsert query")
              case Failure(_) => {}
            })
          }
          client.close()
        }
        case Failure(e) => logger.info(s"Something went wrong - $e")
      })

    }catch {
      case e: Exception => logger.info(s"Something went wrong - $e")
    }
  }
}

