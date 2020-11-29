/*
* Insurance.scala
* This file contains the class insurance.
* */


package sakordekar_mangesh

import sakordekar_mangesh.XMLExamples.XMLHelper

import scala.io.StdIn
import scala.xml._
import scala.collection.mutable
import scala.collection.mutable.ListBuffer



/*
* class Car
* This class store the data of the insurance.
* It contains two class variable.
* list_start : link to the first car shop
* zips : list of all zip codes
* */
class Insurance {

  private var list_start : CarShop = null
  private var zips = new ListBuffer[Zip]()



  /*
  * load_xml
  * this function loads the xml file and checks if
  * it is opened correctly. It reads in the data
  * and passes on to other classes to store the data
  *
  * */
  def load_xml : Unit = {

    var path : String = ""
    print("File name: ")
    path = StdIn.readLine()

    if(new java.io.File(path).exists) {
      val xml = XML.loadFile(path)

      if (xml.label == "insurance") {
        val children = xml.child
        children.foreach(child => {
          var tag = child.label

          //if owner tag, load the owen
          if (tag == "zip") {
            var tempzip = new Zip
            tempzip = tempzip.read(child)
            zips.append(tempzip)

          } else if (tag == "carShop") {
            //if pet tag, make a new pet and have it load the info it wants, then add it to the list
            var tempshop = new CarShop
            tempshop = tempshop.read(child)
            if (list_start == null) {
              list_start = tempshop
            }
            else {
              list_start.append(tempshop)
            }
          }
        })
      }
      else{
        println("Invalid xml file. Needs to be an insurance xml file")
      }
    }
    else{
      println(s"Could not open file: ${path} (The system cannot find the file specified)")
    }
  }


  /*
  * print_data
  * This function displays insurance information to the screen.
  * It returns the info in form of a string
  *
  * returns
  * information of the string
  * */
  def print_data : String = {

    var output: String = ""
    zips.foreach(zip => {
      output += zip.print_info()
    })

    output
  }


  /*
  * add
  * This functions prompts the user to
  * input data and adds a new zip
  * to the insurance accordingly.
  */
  def add : Unit ={

    print("What zip code: ")
    var code : Int = scala.io.StdIn.readInt()

    var flag : Boolean = false
    var found : Boolean = false

    zips.foreach(zip => {
      flag = zip.is_zip(code)
      if(flag){
        zip.add
        found = true
      }
    })

    if(!found){
      var tempZip = new Zip
      tempZip.set_code(code)
      zips.append(tempZip)
      println("Added zip code")
    }
  }


  /*
  * write_xml
  * Creates a XML tag of the insurance info
  *
  * returns
  * the element created
  */
  def write_xml : Elem ={

    val attr : mutable.HashMap[String,String]  = mutable.HashMap(("insurance",null))
    var write_zips = zips.map(x => x.write())

    var curr : CarShop = list_start

    while(curr != null){
      write_zips += curr.write
      curr = curr.get_next
    }
    XMLHelper.makeNode("insurance", attr, write_zips)
  }



  /*
  * remove_zip
  * Prompts the user to enter a zip code
  * and deletes it from the database.
  * */
  def remove_zip : Unit ={

    print("What zip code: ")
    var code : Int = scala.io.StdIn.readInt()

    var flag : Boolean = false
    var i : Int = 0

    zips.foreach(zip => {
      flag = zip.is_zip(code)
      if(flag){
        zips.remove(i)
      }
      i += 1
    })
    println(s"Removed ${code}")
  }


  /*
  * find_shop
  * Prompts the user to type in the code
  * and goes through each shop to find the service.
  * */
  def find_shop : Unit = {

    print ("Code: ")
    var choice = StdIn.readInt()

    if(list_start == null){
      println("Service not found.")
      return
    }
    var found = list_start.find_shop(choice)

    if(!found){
      println("Service not found.")
    }
  }


  /*
  * total_insurance
  * This function calculates and returns the sum of
  * all the cars in the database.
  */
  def total_insurance : String={

    var sum : Double = 0
    print("What zip code:")
    var code: Int = scala.io.StdIn.readInt()

    var flag:Boolean = false
    zips.foreach(zip => {
      flag = zip.is_zip(code)
      if(flag){
        sum += zip.get_sum
      }
    })

    var out : String = "Value: "
    val formatter = java.text.NumberFormat.getCurrencyInstance()
    var sum_str : String = f"$sum%1.2f"
    var num = formatter.format(sum_str.toDouble)
    out += num
    out
  }


  /*
  * total_payment
  * This function calculates and returns the total
  * amount due by all the owners in the database.
  */
  def total_payment : String ={

    var sum : Double = 0
    print("What zip code:")
    var code: Int = scala.io.StdIn.readInt()

    var flag : Boolean = false
    zips.foreach(zip => {
      flag = zip.is_zip(code)
      if(flag){
        sum = zip.insurance_for
      }
    })
    var out : String = "Monthly Payment: $"
    out += f"$sum%1.2f"
    out
  }
}
