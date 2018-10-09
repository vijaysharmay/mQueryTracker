package com.mquerytracker.routes

import akka.http.scaladsl.server.Route
import com.mquerytracker.middleware.PDBAlert
import com.mquerytracker.models.PDBAlertRow
import com.mquerytracker.utils.RouteHelper
import spray.json.RootJsonFormat

import scala.util.{Failure, Success}

trait AlertRoutes extends RouteHelper with PDBAlert{

  implicit val PDBAlertRowFormat: RootJsonFormat[PDBAlertRow] = jsonFormat7(PDBAlertRow)

  def alert_routes: Route = addAlert ~ getAlerts ~ deleteAlerts ~ updateAlert

  val addAlert: Route = path(pm = "insert" / "alert") {
    post {
      entity(as[PDBAlertRow]) { row =>
        onComplete(PDBAlertRepo.insert(row)) {
          case Success(insertedID) => complete(row.copy(id=insertedID))
          case Failure(e) => complete(e)
        }
      }
    }
  }

  val deleteAlerts: Route = path(pm = "delete" / "alert") {
    post {
      entity(as[PDBAlertRow]) { row =>
        onComplete(PDBAlertRepo.delete(row)) {
          case Success(_) => complete(row)
          case Failure(e) => complete(e)
        }
      }
    }
  }

  val updateAlert: Route = path(pm = "update" / "alert") {
    post {
      entity(as[PDBAlertRow]) { row =>
        onComplete(PDBAlertRepo.update(row)) {
          case Success(_) => complete(row)
          case Failure(e) => complete(e)
        }
      }
    }
  }

  val getAlerts: Route = path(pm = "get" / "alerts") {
    get {
      onComplete(PDBAlertRepo.get){
        case Success(rows) => complete(rows)
        case Failure(e) => complete(e)
      }
    }
  }

}

