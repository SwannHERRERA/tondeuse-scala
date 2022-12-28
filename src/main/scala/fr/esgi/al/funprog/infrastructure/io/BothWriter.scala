package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.domain.io.Writer

/**
 * The [[BothWriter]] class is a writer that writes to both the console and a file.
 * It takes a [[FileWriter]] instance and a [[ConsoleWriter]] instance in its constructor.
 * When its write method is called, it writes the given content to both the file and the console.
 */
class BothWriter(fileWriter: FileWriter, consoleWriter: ConsoleWriter) extends Writer {

  /**
   * Overrides the write method of the [[Writer]] trait.
   * It writes the given content to both the file and the console.
   *
   * @param content the content to write
   */
  override def write(content: String): Unit = {
    fileWriter.write(content)
    consoleWriter.write(content)
  }
}

/**
 * The [[BothWriter]] object contains a factory method to create a [[BothWriter]] instance.
 * It takes a file path as input and creates a [[FileWriter]] instance with it.
 * It then creates a [[ConsoleWriter]] instance and uses both to create a [[BothWriter]] instance.
 */
object BothWriter {

  /**
   * Creates a [[BothWriter]] instance.
   * It creates a [[FileWriter]] instance with the given file path and a [[ConsoleWriter]] instance.
   * It then uses both to create a [[BothWriter]] instance.
   *
   * @param filePath the file path to use to create a [[FileWriter]] instance
   * @return a new [[BothWriter]] instance
   */
  def apply(filePath: String): BothWriter =
    new BothWriter(FileWriter(filePath), ConsoleWriter())
}

