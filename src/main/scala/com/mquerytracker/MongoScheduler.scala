package com.mquerytracker

import akka.actor.Actor
import MongoScheduler._

class MongoScheduler extends Actor with MongoUtils {

  def receive: PartialFunction[Any, Unit] = {
    case PDBSyncCycle => get_profiled_databases()
  }

}

object MongoScheduler {

  case object PDBSyncCycle

}
