package fr.esgi.al.funprog.domain.model

case class Position(x: Int, y: Int) {
  def move(orientation: Orientation): Position = {
    Position(x + orientation.forward.x, y + orientation.forward.y)
  }
}
