package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.domain.io.Writer

@SuppressWarnings(Array("org.wartremover.warts.Var"))
case class TestWriter() extends Writer() {
  var content: String = "";

  override def write(data: String): Unit = content += data
}
