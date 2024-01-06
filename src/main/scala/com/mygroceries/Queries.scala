package com.mygroceries



//package com.mygroceries



import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object PrivateExecutionContext {
  var executor = Executors.newFixedThreadPool(4)
  implicit var ec: ExecutionContext = ExecutionContext.fromExecutorService(executor)
}


  import com.mygroceries.PrivateExecutionContext._
  import slick.jdbc.PostgresProfile.api._

//  case class userStructure()

object Queries{


//  def readAllProducts(): Unit = {
//    val readAllQuery = SlickTables.productTable.result
//    val allProducts: Future[Seq[Products]] = Connection.database.run(readAllQuery)
//    allProducts.onComplete {
//      case Success(products) => println(s"products present ${products.mkString(",")}")
//      case Failure(ex) => println(s"failed to retrieve data ${ex}")
//    }
//    Thread.sleep(5000) // Sleep for 5 seconds or use a different mechanism to keep the application alive
//
//
//  }

//  def readSomeProducts(): Unit = {
//    //adding soem regex in our search functionality using the % symbole to mathh anuthing befour or after ..
//    val readAllQuery = SlickTables.productTable.filter(_.name.like("%Onions%")).result
//    val allProducts: Future[Seq[Products]] = Connection.database.run(readAllQuery)
//    allProducts.onComplete {
//      case Success(products) => println(s"products present ${products.mkString(",")}")
//      case Failure(ex) => println(s"failed to retrieve data ${ex}")
//    }
//    Thread.sleep(5000) // Sleep for 5 seconds or use a different mechanism to keep the application alive
//  }


//  def deleteProduct(): Unit = {
//    val deleteQuery = SlickTables.productTable.filter(_.id === 1L).delete
//    //run the query
//    Connection.database.run(deleteQuery)
//  }
  //  def updateProductBasedOnID(): Unit = {
  //    val unpdateQuery = SlickTables.productTable.filter(_.id === 2L).update(tomatoes.copy(category = 3))
  //    val futureID: Future[Int] = Connection.database.run(unpdateQuery )
  //
  //    futureID.onComplete {
  //      case Success(newProductID) =>
  //        println(s"Product updated  successfully. New product ID is $newProductID")
  //      case Failure(ex) =>
  //        println(s"Could not complete the query: $ex")
  //    }
  //    Thread.sleep(5000) // Sleep for 5 seconds or use a different mechanism to keep the application alive
  //  }

  // users crude operation


  def createUser(newUser: User): Future[Int] = {
    val createQuery = SlickTables.usersTable += newUser
    val creationResponse = Connection.database.run(createQuery)
    creationResponse.map { userID =>
      println(s"User added successfully. New user id is $userID")
      userID
    }
  }

  //get all users

  // Inside your query file
  def readAllUsers(): Future[Seq[User]] = {
    val readAllQuery = SlickTables.usersTable.result
    Connection.database.run(readAllQuery)
  }
  //loging User
  def loginUser(email: String, password: String): Future[Option[User]] = {
    val loginQuery = SlickTables.usersTable.filter(user => user.mail === email && user.password === password).result.headOption
    val loginResponse: Future[Option[User]] = Connection.database.run(loginQuery)

    loginResponse.onComplete {
      case Success(userOpt) => println(userOpt)
      case Failure(ex) => println(s"Could not complete the query: $ex")
    }

    loginResponse
  }
  /*
  Flight Queries

   */

  def createFlight(flight: Flights): Future[Int] = {
    val createFlightQuery = SlickTables.flightsTable += flight
    val queryResponse = Connection.database.run(createFlightQuery)
    queryResponse.map{ flightID =>
      println(s"Flight created successfuly ${flightID}")
      flightID
    }
  }

  //list flight
  def listFlights(): Future[Option[Flights]] = {
    val listFlightQuery = SlickTables.flightsTable.result
    val queryResponse = Connection.database.run(listFlightQuery)
    val futureResponse = queryResponse.map{
      case flightData if flightData.nonEmpty  => Some(flightData.head)
      case _ => None
    }
    futureResponse

  }
  //bookings
  def createBooking(newBooking: Bookings): Future[Int] = {
    val createBookingsQuery = SlickTables.bookingTable += newBooking
    val createBookingsResposen = Connection.database.run(createBookingsQuery)
    createBookingsResposen.map{ bookingID => bookingID }
  }

  def listBookings(): Future[Option[Bookings]] = {
    val allBookings = SlickTables.bookingTable.result
    val queryRespone = Connection.database.run(allBookings)
    val futureResponse = queryRespone.map{
      case bookingSeq if bookingSeq.nonEmpty => Some(bookingSeq.head)
      case _ => None
    }
    futureResponse
  }

}
