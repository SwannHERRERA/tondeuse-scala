package fr.esgi.al.funprog.application.model

import fr.esgi.al.funprog.domain.model.{Orientation, Position}

/**
 * Represents a rectangular lawn.
 *
 * @param upperRight         a [[Position]] object representing the upper right corner of the lawn
 * @param initialOrientation an [[Orientation]] object representing the initial orientation of a lawnmower on the lawn
 * @param initialPosition    a [[Position]] object representing the initial position of a lawnmower on the lawn
 */
case class Lawn(
    upperRight: Position,
    initialOrientation: Orientation,
    initialPosition: Position
) {

  /**
   * Determines whether the given position is inside the bounds of the lawn.
   *
   * @param position a [[Position]] object representing the position to be checked
   * @return `true` if the position is inside the bounds of the lawn, `false` otherwise
   */
  def isInside(position: Position): Boolean =
    position.x >= 0 && position.x <= upperRight.x && position.y >= 0 && position.y <= upperRight.y
}
