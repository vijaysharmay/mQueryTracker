package com.mquerytracker.utils

import slick.lifted.Rep

trait IdentifiableTable { def id: Rep[Int] }
