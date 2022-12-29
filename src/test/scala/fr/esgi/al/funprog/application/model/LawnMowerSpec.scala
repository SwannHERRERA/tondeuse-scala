package fr.esgi.al.funprog.application.model

import fr.esgi.al.funprog.domain.model.{
  Forward,
  Instruction,
  North,
  Position,
  TurnLeft,
  West
}
import org.scalatest.funsuite.AnyFunSuite

class LawnMowerSpec extends AnyFunSuite {
  test("LawnMower should move forward in the correct direction") {
    // Set up test conditions
    val lawn = Lawn(Position(5, 5), North, Position(0, 0))
    val instructions = List(Forward)
    val expectedOrientation = North
    val expectedPosition = Position(0, 1)

    // Create LawnMower and run instructions
    val lawnMower = LawnMower(lawn, instructions)
    val (orientation, position) = lawnMower.run

    // Assert that the LawnMower has the expected final Orientation and Position
    assert(orientation == expectedOrientation)
    assert(position == expectedPosition)
  }

  test("LawnMower should turn left and move forward in the correct direction") {
    // Set up test conditions
    val lawn = Lawn(Position(5, 5), North, Position(0, 0))
    val instructions: List[Instruction] = List[Instruction](TurnLeft, Forward)
    val expectedOrientation = West
    val expectedPosition = Position(0, 0)

    // Create LawnMower and run instructions
    val lawnMower = LawnMower(lawn, instructions)
    val (orientation, position) = lawnMower.run

    // Assert that the LawnMower has the expected final Orientation and Position
    assert(orientation == expectedOrientation)
    assert(position == expectedPosition)
  }
}
