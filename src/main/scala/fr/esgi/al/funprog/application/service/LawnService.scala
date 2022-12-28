package fr.esgi.al.funprog.application.service

import fr.esgi.al.funprog.application.model.{Lawn, LawnMower}
import fr.esgi.al.funprog.domain.model.{Instruction, Position}

/**
 * Main service of the application, which is responsible for running instructions the lawns.
 */
case class LawnService(
    upperRight: Position,
    lawns: List[Lawn],
    instructions: List[List[Instruction]]
) {

  /**
   * Initializes the lawnmowers and places them on the lawn according to their
   * initial positions.
   */
  def initMowers: List[LawnMower] =
    lawns.zip(instructions).map { case (lawn, inst) => LawnMower(lawn, inst) }

}

object LawnService {

  def apply(
      upperRight: Position,
      lawns: List[Lawn],
      instructions: List[List[Instruction]]
  ): LawnService = {
    new LawnService(upperRight, lawns, instructions)
  }
}
