package fr.esgi.al.funprog.domain.model

/**
 * The [[Instruction]] trait represents an action that a lawn mower can execute.
 * It defines a move method which takes an [[Orientation]] and returns a tuple
 * containing the new [[Orientation]] after the instruction has been executed and
 * the [[Position]] change caused by the instruction.
 * It also defines a [[toString]] method which returns the string representation
 * of the instruction.
 */
sealed trait Instruction {
  def move: (Orientation) => (Orientation, Position)

  def toString: String
}

/**
 * The [[TurnRight]] case object extends the [[Instruction]] trait and represents the instruction to turn right.
 */
case object TurnRight extends Instruction {
  override def move: (Orientation) => (Orientation, Position) =
    (orientation: Orientation) => (orientation.turnRight, Position(0, 0))

  override def toString: String = "D"
}

/**
 * The [[TurnLeft]] case object extends the [[Instruction]] trait and represents the instruction to turn left.
 */
case object TurnLeft extends Instruction {
  override def move: (Orientation) => (Orientation, Position) =
    (orientation: Orientation) => (orientation.turnLeft, Position(0, 0))

  override def toString: String = "G"
}

/**
 * The [[Forward]] case object extends the [[Instruction]] trait and represents the instruction to move forward.
 */
case object Forward extends Instruction {
  override def move: (Orientation) => (Orientation, Position) =
    (orientation: Orientation) => (orientation, orientation.forward)

  override def toString: String = "A"
}

/**
 * The [[Instruction]] companion object contains a method to parse a string into an [[Instruction]].
 */
object Instruction {
  def apply(instruction: Char): Instruction = instruction match {
    case 'D' => TurnRight
    case 'G' => TurnLeft
    case 'A' => Forward
    case _   => println("Ooops, unknown instruction"); Forward
  }
}
