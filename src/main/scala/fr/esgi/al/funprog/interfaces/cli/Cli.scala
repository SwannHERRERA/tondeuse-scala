package fr.esgi.al.funprog.interfaces.cli

import fr.esgi.al.funprog.application.model.FunProgLawn
import fr.esgi.al.funprog.application.service.LawnService
import fr.esgi.al.funprog.domain.config.InputLoader
import fr.esgi.al.funprog.domain.io.{FunProgLawnExporter, Reader, Writer}
import fr.esgi.al.funprog.infrastructure.config.{AppConfig, DefaultInputLoader}
import fr.esgi.al.funprog.infrastructure.io._

case class Cli(appConfig: AppConfig) {

  private def getReader(config: AppConfig): Reader = config.inputMode match {
    case "file"    => FileReader(config.inputFile)
    case "console" => ConsoleReader()
    case _         => ??? //throw DonneesIncorectesException("Invalid input mode")
  }

  private def getJsonWriter(config: AppConfig): Writer =
    config.outputMode match {
      case "file"    => FileWriter(config.outputJsonFile)
      case "console" => ConsoleWriter()
      case "both"    => BothWriter(config.outputJsonFile)
      case _         => ??? //throw DonneesIncorectesException("Invalid output mode")
    }

  private def getYamlWriter(config: AppConfig): Writer =
    config.outputMode match {
      case "file"    => FileWriter(config.outputYamlFile)
      case "console" => ConsoleWriter()
      case "both"    => BothWriter(config.outputYamlFile)
      case _         => ??? //throw DonneesIncorectesException("Invalid output mode")
    }

  private def getCsvWriter(config: AppConfig): Writer =
    config.outputMode match {
      case "file"    => FileWriter(config.outputCsvFile)
      case "console" => ConsoleWriter()
      case "both"    => BothWriter(config.outputCsvFile)
      case _         => ??? //throw DonneesIncorectesException("Invalid output mode")
    }
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
      lawnService.initMowers
    )

    exporters.foreach(_.export(funProgLawn))
  }
}

object Cli {
  def apply(appConfig: AppConfig): Cli = new Cli(appConfig)
}
