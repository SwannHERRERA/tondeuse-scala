package fr.esgi.al.funprog.infrastructure.io

import better.files.File
import fr.esgi.al.funprog.domain.io.Reader

/**
 * Utility class for reading a file and returning its content as a string.
 */
case class FileReader(filePath: String) extends Reader {

  /**
   * Reads the specified file and returns its content as a string.
   *
   * @return the content of the file as a string
   */
  override def readAll: String = {
    File(filePath).contentAsString
  }

  /**
   * Reads the specified file and returns a list of strings, one for each line in the file.
   *
   * @return a list of strings, one for each line in the file
   */
  override def readLines: List[String] = {
    File(filePath).lines.toList
  }
}
