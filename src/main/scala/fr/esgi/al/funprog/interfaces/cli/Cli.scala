package fr.esgi.al.funprog.interfaces.cli

import fr.esgi.al.funprog.application.exception.DonneesIncorectesException
import fr.esgi.al.funprog.application.model.FunProgLawn
import fr.esgi.al.funprog.application.service.LawnService
import fr.esgi.al.funprog.domain.config.InputLoader
import fr.esgi.al.funprog.domain.io.{FunProgLawnExporter, Reader, Writer}
import fr.esgi.al.funprog.infrastructure.config.{AppConfig, DefaultInputLoader}
import fr.esgi.al.funprog.infrastructure.io._

import scala.util.{Failure, Success, Try}

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
   * @return a [[Reader]] for reading input wrapped in a [[Try]]
   */
  private def getReader(config: AppConfig): Try[Reader] =
    config.inputMode match {
      case "file"    => Success(FileReader(config.inputFile))
      case "console" => Success(ConsoleReader())
      case _         => Failure(DonneesIncorectesException("Invalid input mode"))
    }

  /**
   * Creates a [[Writer]] for writing JSON output based on the output mode specified in the AppConfig.
   *
   * @param config the AppConfig containing the output mode
   * @return a [[Writer]] for writing JSON output wrapped in a [[Try]]
   */
  private def getJsonWriter(config: AppConfig): Try[Writer] =
    config.outputMode match {
      case "file"    => Success(FileWriter(config.outputJsonFile))
      case "console" => Success(ConsoleWriter())
      case "both"    => Success(BothWriter(config.outputJsonFile))
      case _         => Failure(DonneesIncorectesException("Invalid output mode"))
    }

  /**
   * Creates a [[Writer]] for writing YAML output based on the output mode specified in the AppConfig.
   *
   * @param config the AppConfig containing the output mode
   * @return a [[Writer]] for writing YAML output wrapped in a [[Try]]
   */
  private def getYamlWriter(config: AppConfig): Try[Writer] =
    config.outputMode match {
      case "file"    => Success(FileWriter(config.outputYamlFile))
      case "console" => Success(ConsoleWriter())
      case "both"    => Success(BothWriter(config.outputYamlFile))
      case _         => Failure(DonneesIncorectesException("Invalid output mode"))
    }

  /**
   * Creates a [[Writer]] for writing CSV output based on the output mode specified in the AppConfig.
   *
   * @param config the AppConfig containing the output mode
   * @return a [[Writer]] for writing CSV output wrapped in a [[Try]]
   */
  private def getCsvWriter(config: AppConfig): Try[Writer] =
    config.outputMode match {
      case "file"    => Success(FileWriter(config.outputCsvFile))
      case "console" => Success(ConsoleWriter())
      case "both"    => Success(BothWriter(config.outputCsvFile))
      case _         => Failure(DonneesIncorectesException("Invalid output mode"))
    }

  /**
   * Runs the application.
   * Reads input, processes it, and exports the output in various formats.
   * @throws DonneesIncorectesException if something goes wrong while reading inputs
   */
  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  @throws[DonneesIncorectesException]
  def run(): Unit = {
    println(appConfig.name + "\n" + "-" * appConfig.name.length)

    val reader = getReader(appConfig) match {
      case Success(r) => r
      case Failure(e) => throw e
    }

    val exporters = List[FunProgLawnExporter](
      JsonExporter(getJsonWriter(appConfig) match {
        case Success(r) => r
        case Failure(e) => throw e
      }),
      YamlExporter(getYamlWriter(appConfig) match {
        case Success(r) => r
        case Failure(e) => throw e
      }),
      CsvExporter(getCsvWriter(appConfig) match {
        case Success(r) => r
        case Failure(e) => throw e
      })
    )

    val inputLoader: InputLoader = DefaultInputLoader()

    val (upperRight, lawns, instructions) =
      inputLoader.loadData(reader.readAll) match {
        case Success(data) => data
        case Failure(e)    => throw e
      }

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
