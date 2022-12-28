package fr.esgi.al.funprog

import fr.esgi.al.funprog.infrastructure.config.AppConfig
import fr.esgi.al.funprog.interfaces.cli.Cli

object Main extends App {
  val cli = Cli(AppConfig())

  cli.run()
}
