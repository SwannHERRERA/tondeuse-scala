package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.application.model.FunProgLawn
import fr.esgi.al.funprog.domain.io.{FunProgLawnExporter, Writer}

class YamlExporter(writer: Writer) extends FunProgLawnExporter {
  override def export(funProgLawn: FunProgLawn): Unit = writer.write("Not implemented yet")
}

object YamlExporter {
  def apply(writer: Writer): YamlExporter = new YamlExporter(writer)
}
