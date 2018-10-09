package com.mquerytracker.routes

import akka.http.scaladsl.server.{Directives, Route}

trait FrontendRoutes extends Directives {

  val frontend_routes: Route = renderFrontend

  def renderFrontend: Route = get {
    pathSingleSlash {
      getFromResource("frontend/dist/index.html")
    } ~ getFromResourceDirectory("frontend/dist/")
  }

}
