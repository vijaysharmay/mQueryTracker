package com.mquerytracker

import java.nio.file.{Files, Paths}

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.mquerytracker.routes.{AlertRoutes, FrontendRoutes, SourceRoutes}
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import slick.dbio.DBIO
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

trait Framework
  extends App
    with SourceRoutes
    with AlertRoutes
    with FrontendRoutes

object Server extends Framework with LazyLogging{

  implicit val system: ActorSystem = ActorSystem("mQueryTracker")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  val schedulerActor = system.actorOf(Props(classOf[MongoScheduler]), "Actor")
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

//  system.scheduler.schedule(5.seconds, 10.seconds, schedulerActor, MongoScheduler.PDBSyncCycle)

  val config = ConfigFactory.load()
  val host = config.getString("http.interface")
  val port = config.getInt("http.port")

  val routes = source_routes ~ frontend_routes ~ alert_routes

  if(Files.exists(Paths.get("mQueryTracker.db"))){
    logger.info("Database has already been initialized")
  }else{
    val DDLSchemas = (PDBAccessRepo.table.schema ++ PDBAlertRepo.table.schema).create
    val initializeFuture = db.run(DBIO.seq(DDLSchemas))
    initializeFuture.onComplete({
      case Success(_) => logger.info("Database has been initialized")
      case Failure(e) => logger.info(s"Failed with error - $e")
    })
  }

  Http().bindAndHandle(handler = routes, interface = host, port = port) map { binding =>
    logger.info(s"REST interface bound to ${binding.localAddress}") } recover { case ex =>
    logger.info(s"REST interface could not bind to $host:$port", ex.getMessage)
  }

}

