package com.mygroceries

import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

object  Marshalls {


  //formateres


  implicit  val flightFormatter: RootJsonFormat[Flights] = jsonFormat8(Flights)

}
