package com.mygroceries

import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

object  Marshalls {


  //formatters


  implicit  val flightFormatter: RootJsonFormat[Flights] = jsonFormat8(Flights)
  implicit val bookingsFormatter: RootJsonFormat[Bookings] = jsonFormat3(Bookings)

}
