package fr.esgi.al.funprog.domain.model

sealed trait Instruction {
  def move: (Orientation) => (Orientation, Position)
}

case object TurnRight extends Instruction {
  override def move: (Orientation) => (Orientation, Position) =
    (orientation: Orientation) => (orientation.turnRight, Position(0, 0))
}

case object TurnLeft extends Instruction {
  override def move: (Orientation) => (Orientation, Position) =
    (orientation: Orientation) => (orientation.turnLeft, Position(0, 0))
}

case object Forward extends Instruction {
  override def move: (Orientation) => (Orientation, Position) =
    (orientation: Orientation) => (orientation, orientation.forward)
}

object Instruction {
  def apply(instruction: String): Instruction = instruction match {
    case 'D' => TurnRight
    case 'G' => TurnLeft
    case 'A' => Forward
    case _ =>
      throw new IllegalArgumentException(s"Unknown instruction: $instruction")
  }
}
