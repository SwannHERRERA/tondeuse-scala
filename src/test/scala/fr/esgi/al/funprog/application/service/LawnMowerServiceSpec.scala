package fr.esgi.al.funprog.application.service

import fr.esgi.al.funprog.application.model.Lawn
import fr.esgi.al.funprog.domain.model.{
  Forward,
  Instruction,
  North,
  Position,
  TurnRight
}
import org.scalatest.funsuite.AnyFunSuite

class LawnMowerServiceSpec extends AnyFunSuite {

  test(
    "A LawnMowerService should correctly execute instructions and update the position of the lawnmower"
  ) {
    // Test the run method of the LawnMowerService class
    val lawn = Lawn(Position(5, 5), North, Position(1, 2))
    val instructions = List[Instruction](
      TurnRight,
      Forward,
      TurnRight,
      Forward,
      TurnRight,
      Forward,
      TurnRight,
      Forward,
      Forward
    )
    val service = LawnMowerService(lawn, instructions)
    val (finalOrientation, finalPosition) = service.run
    assert(finalOrientation == North)
    assert(finalPosition == Position(1, 3))
  }
}
