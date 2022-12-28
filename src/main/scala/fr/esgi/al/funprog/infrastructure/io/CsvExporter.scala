package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.application.model.FunProgLawn
import fr.esgi.al.funprog.domain.io.{FunProgLawnExporter, Writer}

/**
 * A [[FunProgLawnExporter]] that writes the output to a CSV file or prints it to the console.
 *
 * @param writer the Writer to use for output
 */
class CsvExporter(writer: Writer) extends FunProgLawnExporter {

  /**
   * Exports the given [[FunProgLawn]] to CSV format.
   *
   * @param funProgLawn the [[FunProgLawn]] to export
   */
  override def export(funProgLawn: FunProgLawn): Unit = {
    // Generate a list of strings representing the CSV lines to write
    val csvLines = funProgLawn.lawnMowers.zipWithIndex.map {
      case (lawnMower, index) =>
        // Get the final position and orientation of the lawn mower after executing its instructions
        val (finalOrientation, finalPosition) = lawnMower.run
        // Construct a string with the data for this lawn mower
        s"${(index + 1).toString};" +
          s"${lawnMower.lawn.initialPosition.x.toString};" +
          s"${lawnMower.lawn.initialPosition.y.toString};" +
          s"${lawnMower.lawn.initialOrientation.toString};" +
          s"${finalPosition.x.toString};" +
          s"${finalPosition.y.toString};" +
          s"${finalOrientation.toString};" +
          s"${lawnMower.instructions.mkString}"
    }
    // Write the CSV lines to the output
    writer.write(csvLines.mkString("\n"))
  }
}

/**
 * A companion object for the [[CsvExporter]] class.
 */
object CsvExporter {

  /**
   * Creates a new [[CsvExporter]] with the given Writer.
   *
   * @param writer the [[Writer]] to use for output
   * @return a new [[CsvExporter]]
   */
  def apply(writer: Writer): CsvExporter = new CsvExporter(writer)
}
