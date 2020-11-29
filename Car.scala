/*
* Car.scala
* This file contains the class Car.
* */

package sakordekar_mangesh

import sakordekar_mangesh.XMLExamples.XMLHelper
import scala.collection.mutable
import scala.xml._


/*
* class Car
* This class uses the interface info and store the data of the car.
* It contains four class variable.
* make : stores the make of the car
* year : stores the year of the car
* model : stores the model of the car
* value : stores the value of the car in dollars
* */
class Car extends Info {

  private var make : String = ""
  private var year : Int = 0
  private var model : String = ""
  private var value : String = ""

  /*
  * read(xml: Node)
  * this function takes in the xml node containing the data
  * of the car and stores it into the class variables.
  * It returns the current instance of the car.
  *
  * parameters :
  * xml: Node - xml node containing the data
  *
  * returns :
  * this - current instance of Car
  * */
  override def read(xml: Node): this.type = {
    var temp = xml \ "@make"
    this.make = temp.mkString("")
    temp = xml \ "@year"
    this.year = temp.mkString("").toInt
    temp = xml \ "@model"
    this.model = temp.mkString("")
    temp = xml \ "@value"
    this.value = temp.mkString("")
    this
  }


  /*
  * print_info
  * This function displays accident information to the screen.
  * It returns the info in form of a string
  *
  * returns
  * information of the string
  * */
  override def print_info: String = {
    var output:String = ""
    output += "------Cars:\n"
    output += s"---------Make: ${this.make}           Model: ${this.model}      Year: ${this.year}      Value: $$${this.value}\n"
    return output
  }


  /*
  * write
  * Creates a XML tag of the car info
  *
  * returns
  * the element created
  */
  override def write : Elem={
    val attr: mutable.HashMap[String,String] = mutable.HashMap(("make",make ),("model",model),("year",year.toString),("value",value))
    XMLExamples.XMLHelper.makeNode("car",attr)
  }


  /*
  * add_car(make : String, year : Int, model : String, value : String)
  * Sets the class variables to the values passed in
  *
  * parameters
  * make - holds the make of the car
  * year - holds the year of the car
  * model - holds the model of the car
  * value - holds the value of the car
  */
  def add_car(make : String, year : Int, model : String, value : String): Unit ={
    this.make = make
    this.year = year
    this.model = model
    this.value = value
  }

  /*
  * get_value
  * Returns the value of the car
  */
  def get_value : Double ={
    value.toDouble
  }
}
