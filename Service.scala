/*
* Service.scala
* This file contains the class Service.
* */

package sakordekar_mangesh

import scala.collection.mutable
import scala.io.StdIn
import scala.xml.{Elem, Node, Text}
import sakordekar_mangesh.XMLExamples.XMLHelper


/*
* class Service
* This class uses the interface info and store the data of the service.
* It contains two class variable.
* code : stores the code of the service
* description : stores the description of the service
* */
class Service extends Info{
  private var code : Int = -1
  private var description : String = ""

  /*
  * read(xml: Node)
  * this function takes in the xml node containing the data
  * of the service and stores it into the class variables.
  * It returns the current instance of the service.
  *
  * parameters :
  * xml: Node - xml node containing the data
  *
  * returns :
  * this - current instance of service.
  * */
  override def read(xml: Node): this.type = {

    var code = xml \ "@code"
    this.code = code.mkString("").toInt
    this.description = xml.child.mkString("")

    this
  }


  /*
  * print_info
  * This function displays service information to the screen.
  * */
  override def print_info : Unit = {
    println(code + " Description: " + description)
  }


  /*
  * is_present(code : Int)
  * this function takes in the code which is being searched
  * and checks if it is same as the service code.
  * If it is same, it returns true and false otherwise
  *
  * parameters :
  * code : Int - code of service in search
  *
  * returns :
  * true if code matches and false otherwise
  * */
  def is_present(code : Int) : Boolean = {

    if ( code == this.code){
      return true
    }
    false
  }

  /*
  * write
  * Creates a XML tag of the service info
  *
  * returns
  * the element created
  */
  override def write : Elem ={

    val child : Node = Text(description)
    val attr: mutable.HashMap[String,String] = mutable.HashMap(("code",code.toString))
    XMLExamples.XMLHelper.makeNode("service",attr,child)
  }
}
