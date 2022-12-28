package fr.esgi.al.funprog.domain.io

/**
[[Writer]] is a trait that defines the behavior of writing data.
 */
trait Writer {

  /**
  [[write]] writes the given data to some output.
  @param data the data to write
   */
  def write(data: String): Unit
}
