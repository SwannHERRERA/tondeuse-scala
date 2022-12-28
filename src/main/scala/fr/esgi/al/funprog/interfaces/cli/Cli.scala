package fr.esgi.al.funprog.interfaces.cli

import fr.esgi.al.funprog.application.exception.DonneesIncorectesException
import fr.esgi.al.funprog.application.model.FunProgLawn
import fr.esgi.al.funprog.application.service.LawnService
import fr.esgi.al.funprog.domain.config.InputLoader
import fr.esgi.al.funprog.domain.io.{FunProgLawnExporter, Reader, Writer}
import fr.esgi.al.funprog.infrastructure.config.{AppConfig, DefaultInputLoader}
import fr.esgi.al.funprog.infrastructure.io._

/**
 * The [[Cli]] class is responsible for running the application.
 * It reads input, processes it, and exports the output in various formats.
 * @param appConfig the AppConfig to use for the application
 */
class Cli(appConfig: AppConfig) {

  /**
   * Creates a [[Reader]] based on the input mode specified in the AppConfig.
   *
   * @param config the AppConfig containing the input mode
   * @throws DonneesIncorectesException if the input mode is invalid
   * @return a [[Reader]] for reading input
   */
  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  @throws[DonneesIncorectesException]
  private def getReader(config: AppConfig): Reader = config.inputMode match {
    case "file"    => FileReader(config.inputFile)
    case "console" => ConsoleReader()
    case _         => throw DonneesIncorectesException("Invalid input mode")
  }

  /**
   * Creates a [[Writer]] for writing JSON output based on the output mode specified in the AppConfig.
   *
   * @param config the AppConfig containing the output mode
   * @throws DonneesIncorectesException if the output mode is invalid
   * @return a [[Writer]] for writing JSON output
   */
  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  @throws[DonneesIncorectesException]
  private def getJsonWriter(config: AppConfig): Writer =
    config.outputMode match {
      case "file"    => FileWriter(config.outputJsonFile)
      case "console" => ConsoleWriter()
      case "both"    => BothWriter(config.outputJsonFile)
      case _         => throw DonneesIncorectesException("Invalid output mode")
    }

  /**
   * Creates a [[Writer]] for writing YAML output based on the output mode specified in the AppConfig.
   *
   * @param config the AppConfig containing the output mode
   * @throws DonneesIncorectesException if the output mode is invalid
   * @return a [[Writer]] for writing YAML output
   */
  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  @throws[DonneesIncorectesException]
  private def getYamlWriter(config: AppConfig): Writer =
    config.outputMode match {
      case "file"    => FileWriter(config.outputYamlFile)
      case "console" => ConsoleWriter()
      case "both"    => BothWriter(config.outputYamlFile)
      case _         => throw DonneesIncorectesException("Invalid output mode")
    }

  /**
   * Creates a [[Writer]] for writing CSV output based on the output mode specified in the AppConfig.
   *
   * @param config the AppConfig containing the output mode
   * @throws DonneesIncorectesException if the output mode is invalid
   * @return a [[Writer]] for writing CSV output
   */
  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  @throws[DonneesIncorectesException]
  private def getCsvWriter(config: AppConfig): Writer =
    config.outputMode match {
      case "file"    => FileWriter(config.outputCsvFile)
      case "console" => ConsoleWriter()
      case "both"    => BothWriter(config.outputCsvFile)
      case _         => throw DonneesIncorectesException("Invalid output mode")
    }

  /**
   * Runs the application.
   * Reads input, processes it, and exports the output in various formats.
   */
  def run(): Unit = {
    println(appConfig.name + "\n" + "-" * appConfig.name.length)

    val reader = getReader(appConfig)

    val exporters = List[FunProgLawnExporter](
      JsonExporter(getJsonWriter(appConfig)),
      YamlExporter(getYamlWriter(appConfig)),
      CsvExporter(getCsvWriter(appConfig))
    )

    val inputLoader: InputLoader = DefaultInputLoader()

    val (upperRight, lawns, instructions) = inputLoader.loadData(reader.readAll)

    val lawnService = LawnService(upperRight, lawns, instructions)

    val funProgLawn = FunProgLawn(
      lawnService.upperRight,
      lawnService.initLawnMowers
    )

    exporters.foreach(_.export(funProgLawn))
  }
}

object Cli {

  /**
   * Creates a new [[Cli]] instance with the given AppConfig.
   *
   * @param appConfig the AppConfig to use for the Cli instance
   * @return a new [[Cli]] instance
   */
  def apply(appConfig: AppConfig): Cli = new Cli(appConfig)
}
