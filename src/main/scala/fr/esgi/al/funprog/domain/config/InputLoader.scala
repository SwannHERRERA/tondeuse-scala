package fr.esgi.al.funprog.domain.config

import fr.esgi.al.funprog.application.model.Lawn
import fr.esgi.al.funprog.domain.model.{Instruction, Position}

import scala.util.Try

trait InputLoader {

  /**
   * Loads the input data (lawn coordinates and lawnmower instructions) from
   * a file or a database.
   *
   * @return a tuple containing the upperRight position, the list of lawns and the list of instructions
   *         for each lawnmower wrapped in a Try
   */
  def loadData(
      data: String
  ): Try[(Position, List[Lawn], List[List[Instruction]])]
}
