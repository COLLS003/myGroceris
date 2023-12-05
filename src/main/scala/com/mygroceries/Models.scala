package com.mygroceries
import java.time.LocalDate


case class User(id: Long, name: String, mail: String, location: Int, userType: Int, phone: String, password: String, status: String)
case class Products(id: Long, name: String, price: Float, category: Int, imageUrl: String)
case class OrderItems(id: Long, productID: Int, quantity: Float, subtotal: Float,  orderID: Int)
case class Orders(id: Long, dataCreated: LocalDate, status: String, total: Float, userID: Int)


//object Models {
//
//}
object  SlickTables{//all our table will come to this point..
  import slick.jdbc.PostgresProfile.api._
  class ProductTable(tag: Tag) extends Table[Products](tag, Some("product")/*<this is the schema name>*/, "Products"){
    def id       = column[Long]("product_id", O.PrimaryKey, O.AutoInc)
    def name     = column[String]("name")
    def price    = column[Float]("price")
    def category = column[Int]("category")
    def imageUrl = column[String]("image_url")
    //the start fucntion maps the column objects to the movie constructor and destructor..
    //the <> is an extension method provided by slick ..
    //the tupled is the constructor while he uaapply is the destructor..
    override def * = (id, name, price, category, imageUrl) <>(Products.tupled, Products.unapply)
  }

  /**
   * 1. with the class created above product table slick will know how to map the actualdatabase table to the movie class
   * 2. the Table type is from slick and it is typed genetically to product.
   * 3. Table moivie has some contrustor tags, a. tag, 2 schema name, 3, actual tabel name.
   * 4. the overiden method willl map the products table to the product class
   *
   */
  // creating  the entry point into our applicatiom
  lazy val productTable = TableQuery[ProductTable]
  //with the above function we can perform all the operation on table with ease..


}