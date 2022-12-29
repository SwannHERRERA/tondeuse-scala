package fr.esgi.al.funprog.application.model

import fr.esgi.al.funprog.application.service.LawnMowerService
import fr.esgi.al.funprog.domain.model.{Instruction, Orientation, Position}

/**
 * Represents a lawnmower on a rectangular lawn.
 *
 * @param lawn         a [[Lawn]] object representing the lawn on which the lawnmower is placed
 * @param instructions a list of [[Instruction]] objects representing the instructions to be executed by the lawnmower
 */
case class LawnMower(lawn: Lawn, instructions: List[Instruction]) {

  /**
   * Executes the instructions on the lawn.
   *
   * @return a tuple containing the final [[Orientation]] and [[Position]] of the lawnmower
   */
  def run: (Orientation, Position) = LawnMowerService(lawn, instructions).run
}
