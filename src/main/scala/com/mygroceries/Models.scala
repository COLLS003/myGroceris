package com.mygroceries



//login structure
case class LoginRequest(email: String, password: String)


//defining our new
case class User(id: Long, name: String, mail: String, location: Int, userType: Int, phone: String, password: String, status: String)

case class Flights(id: Long, name: String, origin: String, Destination: String, capacity: Int, pilot: String, departure: String, arrival: String)
//bookings
case class Bookings(id: Long, flight_id: Long, user_id: Long)
//object Models {
//
//}
object  SlickTables{//all our table will come to this point..
  import slick.jdbc.PostgresProfile.api._
//  schema name is products and the table name from the databas will be products

  class BookingsTable(tag: Tag) extends  Table[Bookings](tag, Some("groceries"), "Bookings"){
    def id = column[Long]("id")
    def userId = column[Long]("user_id")
    def flightId = column[Long]("flight_id")

    override  def * = (id, userId, flightId) <> (Bookings.tupled, Bookings.unapply)
  }

  /**
   * 1. with the class created above product table slick will know how to map the actualdatabase table to the movie class
   * 2. the Table type is from slick and it is typed genetically to prosduct.
   * 3. Table moivie has some contrustor tags, a. tag, 2 schema name, 3, actual tabel name.
   * 4. the overiden method willl map the products table to the product class
   *
   */
class FlightsTable(tag: Tag) extends  Table[Flights](tag, Some("groceries"), /*<- schema name  */ "Flights"){
    //case class Flights(id: Long, name: String, origin: String, Destination: String, capacity: Integer, pilot: String, date: Strin
    def id = column[Long]("id")
    def name = column[String]("name")
    def origin = column[String]("origin")
    def destination = column[String]("destination")
    def pilot = column[String]("pilot")
    def capacity = column[Int]("capacity")
    def departure = column[String]("departure")
    def arrival  = column[String]("arrival")
    //constructor and a destructor for the table
    //Flights(id: Long, name: String, origin: String, Destination: String, capacity: Integer, pilot: String, departure: String, arrival: String)
    override def * = (id, name, origin, destination,capacity, pilot, departure, arrival) <> (Flights.tupled, Flights.unapply)

//    override  def * = (id, name, origin, destination, arrival, departure, capacity, pilot)<> (Flights.tupled, Flights.unapply)
  }

  class UserTable(tag: Tag) extends Table[User](tag, Some("groceries") /*schema name */ , "User") {
    // create some methods which align with the fields in the database
    // case class Users(id: Long, name: String, mail: String, location: Int, userType: Int, phone: String, password: String, status: String)
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def mail = column[String]("mail")

    def location = column[Int]("location")

    def userType = column[Int]("userType")

    def phone = column[String]("phone")

    def password = column[String]("password")

    def status = column[String]("status") // Corrected to match the type in the case class

    // create the constructor and the destructor for the table
    override def * = (id, name, mail, location, userType, phone, password, status) <> (User.tupled, User.unapply)
  }

  /*
  | entry point to our application
  |
  |
   */
  // products entry point
  //users entry point
  lazy val usersTable = TableQuery[UserTable]
  //flights entry point
  lazy val flightsTable = TableQuery[FlightsTable]
  //bookings entrypoint
  lazy val bookingTable = TableQuery[BookingsTable]
  //with the above function we can perform all the operation on table with ease..


}