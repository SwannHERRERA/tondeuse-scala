package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.application.model.FunProgLawn
import fr.esgi.al.funprog.domain.io.{FunProgLawnExporter, Writer}

class CsvExporter(writer: Writer) extends FunProgLawnExporter {
  override def export(funProgLawn: FunProgLawn): Unit = {
    val csvLines = funProgLawn.lawnMowers.zipWithIndex.map {
      case (lawnMower, index) =>
        val (finalOrientation, finalPosition) = lawnMower.run
        s"${(index + 1).toString};" +
          s"${lawnMower.lawn.initialPosition.x.toString};" +
          s"${lawnMower.lawn.initialPosition.y.toString};" +
          s"${lawnMower.lawn.initialOrientation.toString};" +
          s"${finalPosition.x.toString};" +
          s"${finalPosition.y.toString};" +
          s"${finalOrientation.toString};" +
          s"${lawnMower.instructions.mkString}"
    }
    writer.write(csvLines.mkString("\n"))
  }
}

object CsvExporter {
  def apply(writer: Writer): CsvExporter = new CsvExporter(writer)
}
