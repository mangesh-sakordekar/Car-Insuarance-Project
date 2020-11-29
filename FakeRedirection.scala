package sakordekar_mangesh

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.Scanner


object FakeRedirection {
    @throws[IOException]
    def main(args: Array[String]): Unit = {
        try //no argument try once to get it from the user
        if (args.length < 1) {
            System.out.println("No input file")
            System.out.print("Enter a file (n to skip):")
            var scanner = new Scanner(System.in)

            val file = scanner.next()

            //if it is a file, try to open in and set to console in
            if (!(file == "n" || file == "N")) {
                var is = new FileInputStream(new File(file))
                System.setIn(is)
            }
        }
        else { //try to open the file using the first command argument
            var is = new FileInputStream(new File(args(0)))
            System.setIn(is)
        }
        catch {
            case e: Exception =>
                //Any error, use normal console input
                System.out.println("Error redirecting input. Continue with console input")
        }
        InsuranceProgram.main(args) //TODO change this to be your entry class (the one with main())

    }
}

