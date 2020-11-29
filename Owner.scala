/*
* Owner.scala
* This file contains the class Owner.
* */

package sakordekar_mangesh

import scala.io.Source
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import java.io.PrintWriter
import scala.xml._
import scala.collection.mutable.ListBuffer
import sakordekar_mangesh.XMLExamples.XMLHelper


/*
* class Car
* This class uses the interface info and store the data of the owner.
* It contains three class variable.
* name : stores the name of the owner
* cars : list of cars owned by the owner
* accidents : list of all the accidents
* */
class Owner extends Info{

  private var name : String = ""
  private var cars = new ListBuffer[Car]()
  private var accidents = new ListBuffer[Accident]()

  /*
  * read(xml: Node)
  * this function takes in the xml node containing the data
  * of the owner and stores it into the class variables.
  * It returns the current instance of the Owner.
  *
  * parameters :
  * xml: Node - xml node containing the data
  *
  * returns :
  * this - current instance of Owner
  * */
  override def read(xml: Node): this.type = {

    var owners = xml \ "@name"
    this.name = owners.mkString("")

    val children = xml.child
    children.foreach(child => {

      var tag = child.label

      //if car tag, load the car
      if (tag == "car") {
        var tempcar = new Car
        tempcar = tempcar.read(child)
        cars.append(tempcar)

      } else if (tag == "accident") {
        //if accident tag, load accident
        var tempacc = new Accident
        tempacc = tempacc.read(child)
        accidents.append(tempacc)
      }
    })

    this
  }


  /*
  * print_info
  * This function displays owner information to the screen.
  * It returns the info in form of a string
  *
  * returns
  * information of the string
  * */
  override def print_info : String ={

    var output:String = ""
    output += s"---Name: ${this.name}\n"

    cars.foreach(child => {
      output += child.print_info
    })

    if(!accidents.isEmpty){

      output += "------Accidents:\n"
      accidents.foreach(child => {
        output += child.print_info
      })
    }

    output
  }


  /*
  * write
  * Creates a XML tag of the owner info
  *
  * returns
  * the element created
  */
  override def write() : Elem={

    val attr : mutable.HashMap[String,String] = mutable.HashMap(("name",name))
    val Cars = cars.map(x=>x.write)
    val Accidents = accidents.map(x=>x.write)

    XMLExamples.XMLHelper.makeNode("owner",attr,Cars ++ Accidents)

  }


  /*
  * add
  * This functions prompts the user to
  * input data and adds a car or accident
  * to the owner accordingly.
  */
  override def add : Unit ={

    print("Car or accident(0 or 1): ")
    var in : Int = scala.io.StdIn.readInt()

    if(in == 0){

      print("Make: ")
      var make : String = scala.io.StdIn.readLine()

      print("Model: ")
      val mod : String = scala.io.StdIn.readLine()

      print("Year: ")
      var year : Int = scala.io.StdIn.readInt()

      print("Value: ")
      var value : String = scala.io.StdIn.readLine()

      var tempcar = new Car
      tempcar.add_car(make, year, mod, value)
      cars.append(tempcar)
    }
    else if(in == 1){

      print("Date: ")
      var date:String = scala.io.StdIn.readLine()

      var tempAccident = new Accident
      tempAccident.set_date(date)
      accidents.append(tempAccident)

    }
    else{
      println("Invalid")
    }

  }


  /*
  * is_name (name : String)
  * This function checks if the name passed in
  * and the owners names are same.
  * Returns true if the match and false otherwise
  */
  def is_name (name : String): Boolean ={

    var flag : Boolean = false
    var temp = this.name

    temp = temp.toLowerCase()

    if( name == temp) {
      flag = true
    }
    flag
  }


  /*
  * set_name(name : String)
  * Sets the class variable name to the name passed in
  *
  * parameters
  * name - holds the name to be set
  */
  def set_name(name : String) : Unit ={
    this.name = name
  }



  /*
  * get_sum
  * This function calculates and returns the sum of
  * all the cars owned by the owner
  */
  def get_sum : Double = {

    var sum : Double = 0

    cars.foreach(car=>{
      sum += car.get_value
    })

    sum
  }


  /*
  * get_sum
  * This function calculates and returns
  * the payment due by the owner
  */
  def get_payment : Double = {

    var sum:Double = 0

    cars.foreach(car=>{
      sum += car.get_value
    })

    var cost : Double = sum * 0.01 + sum * 0.01 * accidents.size
    cost
  }
}
