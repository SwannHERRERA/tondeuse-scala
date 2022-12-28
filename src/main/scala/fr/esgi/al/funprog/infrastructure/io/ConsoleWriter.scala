package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.domain.io.Writer

case class ConsoleWriter() extends Writer {
  override def write(content: String): Unit = {
    println("--------------------\n" + content + "\n--------------------")
  }
}

object ConsoleWriter {
  def apply(): ConsoleWriter = new ConsoleWriter()
}
