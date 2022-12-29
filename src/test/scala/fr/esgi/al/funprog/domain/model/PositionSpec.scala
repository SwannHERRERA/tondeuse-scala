package fr.esgi.al.funprog.domain.model

import org.scalatest.funsuite.AnyFunSuite

class PositionSpec extends AnyFunSuite {

  test("A Position should move in the specified direction when move is called") {
    // Create a new position at (0, 0)
    val pos = Position(0, 0)

    // Create an Orientation that moves in the positive x direction
    val orient = Orientation("E")

    // Move the position in the specified direction
    val newPos = pos.move(orient)

    // Assert that the position has moved in the correct direction
    assert(newPos == Position(1, 0))
  }

  test(
    "A Position should move in the negative x direction when given an Orientation that moves in the negative x direction"
  ) {
    // Create a new position at (0, 0)
    val pos = Position(0, 0)

    // Create an Orientation that moves in the negative x direction
    val orient = Orientation("W")

    // Move the position in the specified direction
    val newPos = pos.move(orient)

    // Assert that the position has moved in the correct direction
    assert(newPos == Position(-1, 0))
  }

  test(
    "A Position should move in the positive y direction when given an Orientation that moves in the positive y direction"
  ) {
    // Create a new position at (0, 0)
    val pos = Position(0, 0)

    // Create an Orientation that moves in the positive y direction
    val orient = Orientation("N")

    // Move the position in the specified direction
    val newPos = pos.move(orient)

    // Assert that the position has moved in the correct direction
    assert(newPos == Position(0, 1))
  }

  test(
    "A Position should move in the negative y direction when given an Orientation that moves in the negative y direction"
  ) {
    // Create a new position at (0, 0)
    val pos = Position(0, 0)

    // Create an Orientation that moves in the negative y direction
    val orient = Orientation("S")

    // Move the position in the specified direction
    val newPos = pos.move(orient)

    // Assert that the position has moved in the correct direction
    assert(newPos == Position(0, -1))
  }

  test("A Position should not move when given an invalid Orientation") {
    // Create a new position at (0, 0)
    val pos = Position(0, 0)

    // Create an Orientation that is not a valid representation
    val orient = Orientation("X")

    // Move the position in the specified direction
    val newPos = pos.move(orient)

    // Assert that the position has not moved
    assert(newPos == pos)
  }
}
