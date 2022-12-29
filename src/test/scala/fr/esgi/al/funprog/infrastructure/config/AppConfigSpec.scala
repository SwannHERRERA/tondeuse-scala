package fr.esgi.al.funprog.infrastructure.config

import org.scalatest.funsuite.AnyFunSuite

class AppConfigSpec extends AnyFunSuite {
  test(
    "AppConfig should be initialized with correct values from the application.conf file"
  ) {
    val appConfig = AppConfig()
    assert(appConfig.name === "lawn-mower")
    assert(appConfig.inputMode === "console")
    assert(appConfig.inputFile === "input.txt")
    assert(appConfig.outputMode === "console")
    assert(appConfig.outputJsonFile === "output.json")
    assert(appConfig.outputCsvFile === "output.csv")
    assert(appConfig.outputYamlFile === "output.yaml")
  }
}
