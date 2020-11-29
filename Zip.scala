/*
* Zip.scala
* This file contains the class Zip
* */

package sakordekar_mangesh


import scala.io.StdIn
import scala.xml._
import scala.collection.mutable.ListBuffer
import sakordekar_mangesh.XMLExamples.XMLHelper
import scala.collection.mutable


/*
* class Zip
* This class uses the interface info and store the data of the zip.
* It contains two class variable.
* code : stores the zip code
* owners : list of owners
* */
class Zip extends Info{

  private var code : Int = -1
  private var owners = new ListBuffer[Owner]()


  /*
  * read(xml: Node)
  * this function takes in the xml node containing the data
  * of the Zip and stores it into the class variables.
  * It returns the current instance of the service.
  *
  * parameters :
  * xml: Node - xml node containing the data
  *
  * returns :
  * this - current instance of zip.
  * */
  override def read(xml : Node): this.type = {

    var zips = xml \ "@code"
    var temp = zips.mkString("")
    this.code = temp.toInt

    val children = xml.child
    children.foreach(child => {
      var tag = child.label

      //if owner tag, load the owner
      if (tag == "owner") {
        var tempowner = new Owner
        tempowner = tempowner.read(child)
        owners.append(tempowner)
      }
    })

    this
  }


  /*
  * add
  * This functions prompts the user to
  * input data and adds an owner
  * to the zip accordingly.
  */
  override def add : Unit ={

    print("What owner: ")
    var new_name: String = scala.io.StdIn.readLine()

    var flag:Boolean = false
    var found:Boolean = false
    owners.foreach(owner => {
      flag = owner.is_name(new_name.toLowerCase)
      if(flag){
        owner.add
        found = true
      }
    })

    if(!found){
      var tempOwner = new Owner()
      tempOwner.set_name(new_name)
      owners.append(tempOwner)
      println("Added owner")
    }
  }


  /*
  * print_info
  * This function displays zip information to the screen.
  * It returns the info in form of a string
  *
  * returns
  * information of the string
  * */
  override def print_info(): String ={
    var output : String = ""
    output += s"Zip Code: ${this.code}\n"
    owners.foreach(owner => {
      output += owner.print_info
    })
    output
  }


  /*
  * write
  * Creates a XML tag of the zip info
  *
  * returns
  * the element created
  */
  override def write() : Elem ={

    val attr: mutable.HashMap[String,String] = mutable.HashMap(("code",code.toString))
    var Owners = owners.map(x => x.write())
    XMLHelper.makeNode("zip",attr,Owners)
  }



  /*
  * is_zip(new_code : Int)
  * This function checks if the zip passed in
  * and the zip code are same.
  * Returns true if the match and false otherwise
  */
  def is_zip(new_code : Int) : Boolean ={

    var flag:Boolean = false
    if(new_code == this.code) {
      flag = true
    }
    flag
  }


  /*
  * set_code(new_code : Int)
  * Sets the class variable code to the code passed in
  *
  * parameters
  * code - holds the code to be set
  */
  def set_code(code : Int): Unit ={
    this.code  = code
  }


  /*
  * get_sum
  * This function calculates and returns the sum of
  * all the cars owned by the owners in the zip.
  * Uses parallelization
  */
  def get_sum : Double ={

    var sum : Double = 0
    owners.par.foreach(owner =>{
      synchronized(sum += owner.get_sum)
    })
    sum
  }


  /*
  * insurance_for
  * This function calculates and returns the total
  * amount due by all the owners in the zip.
  * Uses parallelization
  */
  def insurance_for : Double = {

    print("What owner: ")
    var new_name: String = scala.io.StdIn.readLine()
    var flag : Boolean = false
    var payment : Double = 0

    owners.par.foreach(owner => {
      flag = synchronized(owner.is_name(new_name.toLowerCase))

      if(flag){
        payment = synchronized(owner.get_payment)
      }
    })
    payment
  }
}
