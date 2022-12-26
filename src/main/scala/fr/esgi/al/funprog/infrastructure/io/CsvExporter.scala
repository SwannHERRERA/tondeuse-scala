package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.application.model.FunProgLawn
import fr.esgi.al.funprog.domain.io.FunProgLawnExporter

class CsvExporter extends FunProgLawnExporter {
  override def export(
      funProgLawn: FunProgLawn
  ): String = ???
}
