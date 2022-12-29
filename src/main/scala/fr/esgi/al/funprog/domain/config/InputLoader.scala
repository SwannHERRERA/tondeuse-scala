package fr.esgi.al.funprog.domain.config

import fr.esgi.al.funprog.application.exception.DonneesIncorectesException
import fr.esgi.al.funprog.application.model.Lawn
import fr.esgi.al.funprog.domain.model.{Instruction, Position}

trait InputLoader {

  /**
   * Loads the input data (lawn coordinates and lawnmower instructions) from
   * a file or a database.
   *
   * @throws DonneesIncorectesException if there is an error while loading the data
   * @return a tuple containing the upperRight position, the list of lawns and the list of instructions
   *         for each lawnmower
   */
  @throws[DonneesIncorectesException]
  def loadData(
      data: String
  ): (Position, List[Lawn], List[List[Instruction]])
}
