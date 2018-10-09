package com.mquerytracker.utils

import akka.http.scaladsl.server.Directives

trait RouteHelper extends Directives with RowToJSON{}
