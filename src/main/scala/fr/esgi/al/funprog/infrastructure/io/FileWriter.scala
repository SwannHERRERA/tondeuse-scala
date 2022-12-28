package fr.esgi.al.funprog.infrastructure.io

import better.files.File
import fr.esgi.al.funprog.domain.io.Writer

import scala.language.existentials

/**
 * A class that writes the given content to a file.
 * @param filePath the path of the file to which the content will be written.
 */
class FileWriter(filePath: String) extends Writer {

  /**
   * Writes the given content to the file specified in the constructor.
   * If the file doesn't exist, it will be created. If it already exists, it will be overwritten.
   * @param content the content to write to the file.
   */
  override def write(content: String): Unit = {
    val file = File(filePath)
      .createFileIfNotExists(createParents = true)
      .overwrite(content)
    println(s"File ${file.name} has been written.")
  }
}

/**
 * An object that creates a new [[FileWriter]] instance with the given file path.
 */
object FileWriter {
  def apply(filePath: String): FileWriter = new FileWriter(filePath)
}
