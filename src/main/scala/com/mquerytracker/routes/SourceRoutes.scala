package com.mquerytracker.routes

import akka.http.scaladsl.server.{Directives, Route}
import com.mquerytracker.middleware.PDBAccess
import com.mquerytracker.tables._
import com.mquerytracker.models.PDBAccessRow
import com.mquerytracker.utils.{RouteHelper, RowToJSON}
import slick.lifted.TableQuery
import spray.json.RootJsonFormat

import scala.util.{Failure, Success}

trait SourceRoutes extends RouteHelper with PDBAccess{

  implicit val PDBAccessRowFormat: RootJsonFormat[PDBAccessRow] = jsonFormat7(PDBAccessRow)

  def source_routes: Route = addSource ~ getSources ~ deleteSources ~ updateSource

  val addSource: Route = path(pm = "insert" / "source") {
    post {
      entity(as[PDBAccessRow]) { row =>
        onComplete(PDBAccessRepo.insert(row)) {
          case Success(insertedID) => complete(row.copy(id=insertedID))
          case Failure(e) => complete(e)
        }
      }
    }
  }

  val deleteSources: Route = path(pm = "delete" / "source") {
    post {
      entity(as[PDBAccessRow]) { row =>
        onComplete(PDBAccessRepo.delete(row)) {
          case Success(_) => complete(row)
          case Failure(e) => complete(e)
        }
      }
    }
  }

  val updateSource: Route = path(pm = "update" / "source") {
    post {
      entity(as[PDBAccessRow]) { row =>
        onComplete(PDBAccessRepo.update(row)) {
          case Success(_) => complete(row)
          case Failure(e) => complete(e)
        }
      }
    }
  }

  val getSources: Route = path(pm = "get" / "sources") {
    get {
      onComplete(PDBAccessRepo.get){
        case Success(rows) => complete(rows)
        case Failure(e) => complete(e)
      }
    }
  }

}

