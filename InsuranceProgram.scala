/*
* InsuranceProgram.scala
* This file contains the class InsuranceProgram.
* */

package sakordekar_mangesh

/**
  * name:      Insurance Program
  *
  * author:    Mangesh Sakordekar
  *
  * course:    CSC 461 Programming Languages
  * section:   M001
  * date:      12/05/2018
  *
  * version:  Scala 2.7.1
  *
  * Description :
  * The Insurance program interprets xml files containing insurance data.
  * It uses inbuilt support for xml provided by scala.
  * It goes through the given xml file and reads the data into local variables
  * after checking for the correctness of the file.
  * It uses the chain of responsibility pattern to to parse
  * the file and store data.
  *
  * Chain of Responsibility Use in each file:
  * InsuranceProgram : None  //Main function
  * Insurance :
  *   line 39) load_xml
  *   line 139) write_xml
  *   line 90) print_data
  *   line  107) add
  * Zip :
  *   line 41) read(Node)
  *   line 118) write
  *   line 101) print_info
  *   line  69) add
  * Owner :
  *   line 43) read(Node)
  *   line 107) write
  *   line 75) print_info
  *   line 124) add
  * Car : None  //End class - nothing to chain forward
  * Accident : None  //End class - nothing to chain forward
  * CarShop :
  *   line 84) read(Node)
  *   line 132) write
  *   line 46) find_shop
  * Service : None //End class - nothing to chain forward
  * Info : None //Trait
  *
  *
  * Parallelization :
  * Zip:
  *    line 161) get_sum
  *    line 177) insurance_for
  *
  * Last tier reached : Tier 8
  *
  * Bugs: None
  *
  */



import scala.io.StdIn
import sakordekar_mangesh.XMLExamples.XMLHelper

import scala.xml.XML


/*
* class InsuranceProgram
* This class contains the main. It prints the menu
* and controls the program by calling functions.
* */
object InsuranceProgram {


  def main(args: Array[String]): Unit = {
    var choice: String = ""
    var flag: Boolean = true
    var ins = new Insurance
    while (flag) {

      println("0) Quit\n1) Load XML\n2) Write XML\n3) Display data\n" +
        "4) Add data\n5) Remove zip code\n6) Find service\n" +
        "7) Total value insured\n8) Insurance for")
      print("Choice :")

      choice = StdIn.readLine()

      choice match {

        case "0" => flag = false

        case "1" => ins.load_xml

        case "2" => var fileName: String = ""
          print("File name:")
          fileName = scala.io.StdIn.readLine()
          val newNode = ins.write_xml
          XML.save(fileName, newNode, "UTF-8", true, null)

        case "3" => println(ins.print_data)

        case "4" => ins.add

        case "5" => ins.remove_zip

        case "6" => ins.find_shop

        case "7" => println(ins.total_insurance)

        case "8" => println(ins.total_payment)

        case _ => println(s"Invalid Input")

      }
    }
  }
}
