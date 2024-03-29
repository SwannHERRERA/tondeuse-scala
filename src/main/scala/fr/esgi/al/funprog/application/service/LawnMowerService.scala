package fr.esgi.al.funprog.application.service

import fr.esgi.al.funprog.application.model.Lawn
import fr.esgi.al.funprog.domain.model.{Instruction, Orientation, Position}

/**
 * Secondary service of the application, which manages the execution of
 * instructions sent to a lawnmower and its movement on the lawn.
 */
class LawnMowerService(lawn: Lawn, instructions: List[Instruction]) {

  /**
   * Executes the instructions sent to the lawnmower and updates its position
   * accordingly.
   *
   * @return the final [[Orientation]] and [[Position]] of the lawnmower
   */
  def run: (Orientation, Position) =
    instructions.foldLeft((lawn.initialOrientation, lawn.initialPosition)) {
      case ((orientation, position), instruction) =>
        val (newOrientation, positionMove) = instruction.move(orientation)
        val newPosition =
          Position(position.x + positionMove.x, position.y + positionMove.y)
        if (lawn.isInside(newPosition)) (newOrientation, newPosition)
        else (orientation, position)
    }
}

object LawnMowerService {
  def apply(lawn: Lawn, instructions: List[Instruction]): LawnMowerService =
    new LawnMowerService(lawn, instructions)
}
