package com.mquerytracker.utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

trait RowToJSON extends SprayJsonSupport with DefaultJsonProtocol{}
