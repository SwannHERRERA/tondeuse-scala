package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.application.model.FunProgLawn
import fr.esgi.al.funprog.domain.io.{FunProgLawnExporter, Writer}

class CsvExporter(writer: Writer) extends FunProgLawnExporter {
  override def export(
      funProgLawn: FunProgLawn
  ): Unit = writer.write("Not implemented yet")
}

object CsvExporter {
  def apply(writer: Writer): CsvExporter = new CsvExporter(writer)
}
