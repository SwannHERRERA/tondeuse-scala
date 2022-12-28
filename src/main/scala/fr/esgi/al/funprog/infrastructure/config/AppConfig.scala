package fr.esgi.al.funprog.infrastructure.config

import com.typesafe.config.{Config, ConfigFactory}

/**
 * Case class for storing application configuration values.
 *
 * @param name           the name of the application
 * @param inputMode      the input mode (file or console)
 * @param inputFile      the path to the input file
 * @param outputMode     the output mode (file or console or both)
 * @param outputJsonFile the path to the output JSON file
 * @param outputCsvFile  the path to the output CSV file
 * @param outputYamlFile the path to the output YAML file
 */
case class AppConfig(
    name: String,
    inputMode: String,
    inputFile: String,
    outputMode: String,
    outputJsonFile: String,
    outputCsvFile: String,
    outputYamlFile: String
) {

  /**
   * Constructs a new AppConfig case class from a Config object.
   *
   * @param config the Config object containing the configuration values
   */
  def this(config: Config) = {
    this(
      config.getString("application.name"),
      config.getString("application.input-mode"),
      config.getString("application.input-file"),
      config.getString("application.output-mode"),
      config.getString("application.output-json-file"),
      config.getString("application.output-csv-file"),
      config.getString("application.output-yaml-file")
    )
  }
}

/**
 * Object for loading and storing application configuration values.
 */
object AppConfig {
  private val config = ConfigFactory.load()

  /**
   * Loads the configuration values from the application.conf file
   * and returns them as an AppConfig case class.
   *
   * @return the configuration values as an AppConfig case class
   */
  def apply(): AppConfig = new AppConfig(config)
}
