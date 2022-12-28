package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.domain.io.Reader

import scala.io.StdIn

/**
 * [[ConsoleReader]] is a class that implements the [[Reader]] trait and reads
 * content from the console. It reads input line by line from the user
 * until the user enters 'q', at which point it stops reading and returns
 * the input as a String.
 */
class ConsoleReader() extends Reader {

  /**
   * This method reads input from the console line by line until the user enters 'q',
   * at which point it returns the input as a String.
   */
  private def readFromConsole(): String = {
    println(
      "Please enter your content line by line. " +
        "Enter 'q' to validate content."
    )
    val input = StdIn.readLine()
    if (input == "q") {
      ""
    } else {
      input + "\n" + readFromConsole()
    }
  }

  /**
   * readAll reads input from the console until the user enters 'q',
   * at which point it returns the input as a String.
   */
  override def readAll: String = {
    readFromConsole()
  }

  /**
   * readLines reads input from the console until the user enters 'q',
   * at which point it returns the input as a List of Strings, with each
   * line of input as an element in the List.
   */
  override def readLines: List[String] = {
    readFromConsole().split('\n').toList
  }
}

/**
 * Companion object provides an instance of [[ConsoleReader]].
 */
object ConsoleReader {
  def apply(): ConsoleReader = new ConsoleReader()
}
