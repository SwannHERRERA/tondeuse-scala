package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.domain.io.Reader

import scala.io.StdIn

/**
 * Utility class for reading a file and returning its content as a string.
 */
case class ConsoleReader() extends Reader {

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
  override def readAll: String = {
    readFromConsole()
  }

  override def readLines: List[String] = {
    readFromConsole().split('\n').toList
  }
}

object ConsoleReader {
  def apply(): ConsoleReader = new ConsoleReader()
}
