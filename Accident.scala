/*
* Accident.scala
* This file contains the class Accident.
* */

package sakordekar_mangesh

import sakordekar_mangesh.XMLExamples.XMLHelper
import scala.xml._


/*
* class Accident
* This class uses the interface info and store the data of the accident.
* It contains four class variable.
* date : stores the date of the accident
* */
class Accident extends Info{

  private var date : String = ""

  //Overrides add
  override def add: Unit = super.add


  /*
  * print_info
  * This function displays accident information to the screen.
  * It returns the info in form of a string
  *
  * returns
  * information of the string
  */
  override def print_info: String ={
    var output:String = ""
    output += s"---------Accident date: " + this.date + "\n"
    return output
  }

  /*
  * read(xml: Node)
  * this function takes in the xml node containing the data
  * of the accident and stores it into the class variables.
  * It returns the current instance of the accident.
  *
  * parameters :
  * xml: Node - xml node containing the data
  *
  * returns :
  * this - current instance of accident
  * */
  override def read(xml : Node): this.type = {
    var temp = xml.child
    temp.foreach(i =>{
      if(i.label == "date") {
        this.date = i.child.mkString("")
      }
    })
    this
  }

  
  /*
  * write
  * Creates a XML tag of the accident info
  *
  * returns
  * the element created
  */
  override def write : Elem ={

    val child : Node = Text(date)
    val dateNode = XMLExamples.XMLHelper.makeNode("date",null,child)
    XMLExamples.XMLHelper.makeNode("accident",null,dateNode)
  }


  /*
  * set_date(new_date:String)
  * Sets the class variable date to the date passed in
  *
  * parameters
  * new_date - holds the date to be set
  */
  def set_date(new_date:String): Unit ={
    this.date = new_date
  }

}
