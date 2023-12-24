package com.mygroceries

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object PrivateExecutionContext {
 var executor = Executors.newFixedThreadPool(4)
 implicit var ec: ExecutionContext = ExecutionContext.fromExecutorService(executor)
}

object Main {
 import slick.jdbc.PostgresProfile.api._
 import PrivateExecutionContext._

 val tomatoes = Products(1, "Tomato", 200, 1, "mytomatoimage.com")

 def insertProduct(): Unit = {
  val queryResponse = SlickTables.productTable += tomatoes
  val futureID: Future[Int] = Connection.database.run(queryResponse)

  futureID.onComplete {
   case Success(newProductID) =>
    println(s"Product added successfully. New product ID is $newProductID")
   case Failure(ex) =>
    println(s"Could not complete the query: $ex")
  }

  // No need to sleep here; let the Future complete asynchronously
 }

 def main(args: Array[String]): Unit = {
  insertProduct()

  // Make sure the application does not exit immediately; you might want to keep it running
  Thread.sleep(5000) // Sleep for 5 seconds or use a different mechanism to keep the application alive
 }
}
