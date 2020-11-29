/*
* CarShop.scala
* This file contains the class CarShop.
* */

package sakordekar_mangesh

import scala.collection.mutable
import scala.io.StdIn
import scala.xml._
import scala.collection.mutable.ListBuffer

import sakordekar_mangesh.XMLExamples.XMLHelper


/*
* class CarShop
* This class uses the interface info and store the data of the car shop.
* It contains three class variables.
* name : stores the name of the car shop
* services : list of services the store offers
* nextShop : link to the next shop.
* */
class CarShop extends Info {

  private var name : String = ""
  private var services = new ListBuffer[Service]()
  private var nextShop : CarShop = null


  /*
 * findShop(code : Int)
 * this function takes in the code of the service
 * which is to be searched and checks the services list
 * for the service. it returns true if it found the service
 * and passes on to the nest car shop if it doesnt find it.
 * it uses the chain of responsibility pattern to achieve
 * this task.
 *
 * parameters :
 * code: Int - code of the service to be searched
 *
 * returns :
 * true - if service is found
 * */
  def find_shop(code : Int) : Boolean = {

    var found = false
    var i = 0
    while(!found && i < services.length){
      found = services(i).is_present(code)
      if(found){
        this.print_info
        services(i).print_info
        return true
      }
      else{
        i += 1
      }
    }

    //Check is the current shop is last
    if (nextShop == null){
      return false
    }

    found = nextShop.find_shop(code)
    found
  }


  /*
  * read(xml: Node)
  * this function takes in the xml node containing the data
  * of the car and stores it into the class variables.
  * It returns the current instance of the Car Shop
  *
  * parameters :
  * xml: Node - xml node containing the data
  *
  * returns :
  * this - current instance of Car Shop
  * */
  override def read(xml: Node): this.type = {

    var name = xml \ "@name"
    this.name = name.mkString("")

    val children = xml.child
    children.foreach(child => {
      var tag = child.label
      if (tag == "service") {
        var tempservice = new Service
        tempservice = tempservice.read(child)
        services.append(tempservice)
      }
    })
    this
  }


  /*
  * append(new_node : CarShop)
  * this function takes in a newly created carshop
  * and appends it to the end of the linked list of
  * carshops
  *
  * parameters :
  * new_node: CarShop - car shop to be appended
  *
  * */
  def append(new_node : CarShop) : Unit = {

    //If current shop is last, append
    if (nextShop == null){
      nextShop = new_node
    }
    //Else pass to the nest class
    else{
      nextShop.append(new_node)
    }
  }


  /*
  * write
  * Creates a XML tag of the car shop info
  *
  * returns
  * the element created
  */
  override def write : Elem ={

    val attr : mutable.HashMap[String,String] = mutable.HashMap(("name",name))
    var serviceXML = services.map(service => service.write)
    XMLHelper.makeNode("carShop",attr,serviceXML)
  }

  /*
  * get_next
  * Returns the link to the next car shop
  * */
  def get_next : CarShop={
    nextShop
  }

  /*
  * print_info
  * This function displays car shop information to the screen.
  * */
  override def print_info : Unit ={
    print('"' + name + '"' + " handles Code: " )
  }
}
