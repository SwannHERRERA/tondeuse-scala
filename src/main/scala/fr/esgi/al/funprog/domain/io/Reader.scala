package fr.esgi.al.funprog.domain.io

/**
 * [[Reader]] is a trait that defines the behavior of reading data.
 */
trait Reader {

  /**
   * Reads all the data in the reader and returns it as a single string.
   *
   * @return the data in the reader as a string
   */
  def readAll: String

  /**
   * Reads all the data in the reader and returns it as a list of strings, where each string represents a line of data.
   *
   * @return the data in the reader as a list of strings
   */
  def readLines: List[String]
}
