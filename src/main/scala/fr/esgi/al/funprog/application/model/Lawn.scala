package fr.esgi.al.funprog.application.model

import fr.esgi.al.funprog.domain.model.{Orientation, Position}

case class Lawn(
                 upperRight: Position,
                 initialOrientation: Orientation,
                 initialPosition: Position
               ) {
  def isInside(position: Position): Boolean =
    position.x >= 0 && position.x <= upperRight.x && position.y >= 0 && position.y <= upperRight.y
}
