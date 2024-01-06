package com.mygroceries

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.mygroceries.Marshalls.bookingsFormatter
import com.mygroceries.Queries.{createBooking, createFlight, createUser, listBookings, listFlights, loginUser, readAllUsers}
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

// Assuming `User` and `createUser` are defined in the same package

object Api {



 def main(args: Array[String]): Unit = {
  implicit val system: ActorSystem[_] = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  // Import JSON format for User
  implicit val userFormat: RootJsonFormat[User] = jsonFormat8(User)
  //login formatter
  implicit val loginFormat: RootJsonFormat[LoginRequest] = jsonFormat2(LoginRequest)
  //flight formatter
  implicit  val flightFormatter: RootJsonFormat[Flights] = jsonFormat8(Flights)

  var route: Route =
   concat(
    get {
     path("users" / "list") {
      val result: Future[Seq[User]] = readAllUsers()

      onComplete(result) {
       case Success(users) =>
        complete(StatusCodes.OK, users)
       case Failure(ex) =>
        complete(StatusCodes.InternalServerError, s"Error retrieving users: ${ex.getMessage}")
      }
     }
    },
    post {
     path("users" / "create") {
      entity(as[User]) { createUserRequest =>
       val result: Future[Int] =
        createUser(createUserRequest)

       onComplete(result) {
        case Success(userID) =>
         complete(StatusCodes.Created, s"User created successfully. User ID: $userID")
        case Failure(ex) =>
         complete(StatusCodes.InternalServerError, s"Error creating user: ${ex.getMessage}")
       }
      }
     }
    },
    //login route
      post {
     path("users" / "login") {
      entity(as[LoginRequest]) { loginRequest =>
       val result: Future[Option[User]] = loginUser(loginRequest.email, loginRequest.password)

       onComplete(result) {
        case Success(userOpt) =>
         userOpt match {
          case Some(user) => complete(StatusCodes.OK, user)
          case None => complete(StatusCodes.NotFound, "User not found or password incorrect")
         }
        case Failure(ex) =>
         complete(StatusCodes.InternalServerError, s"Error during login: ${ex.getMessage}")
       }
      }
     }
    },
    //flight endpoints
    post{
     pathPrefix("flight" / "create"){
      entity(as[Flights]){ flightRequest=>{
       val createFlightResponse: Future[Int] = createFlight(flightRequest)
       onComplete(createFlightResponse){
        case Success(flightId)  => complete(StatusCodes.Created, flightRequest)
        case Failure(exception) => complete(StatusCodes.InternalServerError, exception)
       }
      }
      }
     }
    },
    //list All flights
    get{
     pathPrefix("flight" / "list"){
      //define a variable to hold all the flight from the previously created function
      val allFlights: Future[Option[Flights]] = listFlights()
      onComplete(allFlights){
       case Success(flights) => complete(StatusCodes.OK, flights)
       case Failure(exception) => complete(StatusCodes.NotFound)
      }

     }

    },
    //bookings endpoint
    //create a bokings
    post{
     path("bookings" /"create"){
      entity(as[Bookings]){ bookingsRequest =>{
       val createBookingResponse = createBooking(bookingsRequest)
       onComplete(createBookingResponse){
        case Success(bookingID) => complete(StatusCodes.Created, "booking created successfully")
        case Failure(exception) => complete(StatusCodes.NotFound, exception)

       }
      }
      }
     }
    },
    get{
     path("bookings" / "list"){
      val allBookings: Future[Option[Bookings]] = listBookings()
      onComplete(allBookings){
       case Success(bookings) => complete(StatusCodes.OK, bookings)
       case Failure(error) => complete(StatusCodes.InternalServerError, error)
      }
     }
    }
   )

  val bindingFuture = Http().newServerAt("localhost", 8081).bind(route)
  println(s"App now online. Please navigate to http://localhost:8081/users/list\nPress RETURN to stop...")
  scala.io.StdIn.readLine()
  bindingFuture.flatMap { binding =>
   println(s"Stopping server at http://${binding.localAddress.getHostString}:${binding.localAddress.getPort}")
   binding.unbind()
  }.onComplete(_ => system.terminate())
 }

 def getSquare(num: Long): Long = {
  val square = num * num
  square
 }
}
