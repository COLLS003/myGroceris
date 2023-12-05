package com.mygroceries

import  slick.jdbc.PostgresProfile.api._

object Connection {

  val database = Database.forConfig("postgres")

}
