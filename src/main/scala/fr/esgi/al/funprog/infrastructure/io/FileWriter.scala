package fr.esgi.al.funprog.infrastructure.io

import better.files.File
import scala.language.existentials
import fr.esgi.al.funprog.domain.io.Writer

case class FileWriter(filePath: String) extends Writer {
  override def write(content: String): Unit = {
    val file = File(filePath).createFileIfNotExists().overwrite(content)
    println(s"File ${file.name} has been written.")
  }
}

object FileWriter {
  def apply(filePath: String): FileWriter = new FileWriter(filePath)
}
