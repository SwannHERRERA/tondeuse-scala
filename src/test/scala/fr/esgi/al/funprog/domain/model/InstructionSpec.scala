package fr.esgi.al.funprog.domain.model

import org.scalatest.funsuite.AnyFunSuite

class InstructionSpec extends AnyFunSuite {

  test(
    "A TurnRight instruction should turn the Orientation to the right and not change the Position"
  ) {
    // Test the move method of the TurnRight instruction
    val orientation = North
    val (newOrientation, positionChange) = TurnRight.move(orientation)
    assert(newOrientation == East)
    assert(positionChange == Position(0, 0))
  }

  test(
    "A TurnLeft instruction should turn the Orientation to the left and not change the Position"
  ) {
    // Test the move method of the TurnLeft instruction
    val orientation = North
    val (newOrientation, positionChange) = TurnLeft.move(orientation)
    assert(newOrientation == West)
    assert(positionChange == Position(0, 0))
  }

  test(
    "A Forward instruction should not change the Orientation and move the Position in the current direction"
  ) {
    // Test the move method of the Forward instruction
    val orientation = North
    val (newOrientation, positionChange) = Forward.move(orientation)
    assert(newOrientation == North)
    assert(positionChange == orientation.forward)
  }

  test("An Instruction should have the correct string representation") {
    // Test the string representation of each Instruction value
    assert(TurnRight.toString == "D")
    assert(TurnLeft.toString == "G")
    assert(Forward.toString == "A")
  }

  test(
    "The Instruction companion object should be able to parse a string into an Instruction"
  ) {
    // Test the apply method of the Instruction companion object
    assert(Instruction('D') == TurnRight)
    assert(Instruction('G') == TurnLeft)
    assert(Instruction('A') == Forward)
    assert(Instruction('X') == InstructionNone)
  }
}
