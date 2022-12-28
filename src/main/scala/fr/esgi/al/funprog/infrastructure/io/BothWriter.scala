package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.domain.io.Writer

class BothWriter(fileWriter: FileWriter, consoleWriter: ConsoleWriter)
    extends Writer {

  override def write(content: String): Unit = {
    fileWriter.write(content)
    consoleWriter.write(content)
  }
}

object BothWriter {
  def apply(filePath: String): BothWriter =
    new BothWriter(FileWriter(filePath), ConsoleWriter())
}
