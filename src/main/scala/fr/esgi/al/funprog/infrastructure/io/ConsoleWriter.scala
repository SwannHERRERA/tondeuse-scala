package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.domain.io.Writer

/**
 * A [[Writer]] that prints the content to the console.
 */
case class ConsoleWriter() extends Writer {

  /**
   * Writes the given content to the console.
   *
   * @param content the content to write to the console
   */
  override def write(content: String): Unit = {
    println("--------------------\n" + content + "\n--------------------")
  }
}

/**
 * Companion object for [[ConsoleWriter]].
 */
object ConsoleWriter {

  /**
   * Creates a new instance of [[ConsoleWriter]].
   *
   * @return a new instance of [[ConsoleWriter]]
   */
  def apply(): ConsoleWriter = new ConsoleWriter()
}
