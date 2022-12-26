package fr.esgi.al.funprog.application.service

import fr.esgi.al.funprog.application.exception.DonneesIncorectesException
import fr.esgi.al.funprog.application.model.{Lawn, LawnMower}
import fr.esgi.al.funprog.domain.io.Writer
import fr.esgi.al.funprog.domain.model.{Instruction, Orientation, Position}

/**
 * Main service of the application, which is responsible for running instructions the lawns.
 */
class LawnService(lawns: List[Lawn], instructions: List[List[Instruction]]) {

  /**
   * Loads the input data (lawn coordinates and lawnmower coordinates, orientation and instructions) from
   * a file or a database.
   */
  def loadData(dataFile: String): Unit throws DonneesIncorectesException = ???

  /**
   * Initializes the lawnmowers and places them on the lawn according to their
   * initial positions.
   */
  def initMowers: List[LawnMower] =
    lawns.zip(instructions).map { case (lawn, inst) => LawnMower(lawn, inst) }

object LawnService {

  /**
   * Loads the input data (lawn coordinates and lawnmower instructions) from
   * a file or a database.
   *
   * @throws DonneesIncorectesException if there is an error while loading the data
   * @return a tuple containing the list of lawns and the list of instructions
   *         for each lawnmower
   */
  @throws[DonneesIncorectesException]
  private def loadData(
      dataFile: String
  ): (List[Lawn], List[List[Instruction]]) = ???

  /**
   * Creates an instance of the LawnService class using the input data
   * loaded from a file or a database.
   *
   * @throws DonneesIncorectesException if there is an error while loading the data
   */
  @throws[DonneesIncorectesException]
  def apply(dataFile: String): LawnService = {
    val (lawns, instructions) = loadData(dataFile)
    new LawnService(lawns, instructions)
  }
}
}
