package fr.esgi.al.funprog.domain.model

/**
A case class representing a position on a 2D plane.
@param x the x coordinate of the position
@param y the y coordinate of the position
 */
case class Position(x: Int, y: Int) {

  /**
      Returns a new position by moving from the current position in the specified direction.
      @param orientation the orientation to move in
      @return the new [[Position]] after moving in the specified direction
   */
  def move(orientation: Orientation): Position = {
    Position(x + orientation.forward.x, y + orientation.forward.y)
  }
}
