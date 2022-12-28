package fr.esgi.al.funprog.domain.model

sealed trait Instruction {
  def move: (Orientation) => (Orientation, Position)

  def toString: String
}

case object TurnRight extends Instruction {
  override def move: (Orientation) => (Orientation, Position) =
    (orientation: Orientation) => (orientation.turnRight, Position(0, 0))

  override def toString: String = "D"
}

case object TurnLeft extends Instruction {
  override def move: (Orientation) => (Orientation, Position) =
    (orientation: Orientation) => (orientation.turnLeft, Position(0, 0))

  override def toString: String = "G"
}

case object Forward extends Instruction {
  override def move: (Orientation) => (Orientation, Position) =
    (orientation: Orientation) => (orientation, orientation.forward)

  override def toString: String = "A"
}

object Instruction {
  def apply(instruction: Char): Instruction = instruction match {
    case 'D' => TurnRight
    case 'G' => TurnLeft
    case 'A' => Forward
    case _   => ??? // throw new IllegalArgumentException("Instruction inconnue")
  }
}
