package fr.esgi.al.funprog.domain.model

import org.scalatest.funsuite.AnyFunSuite

class OrientationSpec extends AnyFunSuite {

  test("An Orientation should have the correct turnRight and turnLeft values") {
    // Test the turnRight and turnLeft values for each Orientation value
    assert(North.turnRight == East)
    assert(North.turnLeft == West)
    assert(East.turnRight == South)
    assert(East.turnLeft == North)
    assert(South.turnRight == West)
    assert(South.turnLeft == East)
    assert(West.turnRight == North)
    assert(West.turnLeft == South)
  }

  test("An Orientation should have the correct forward value") {
    // Test the forward value for each Orientation value
    assert(North.forward == Position(0, 1))
    assert(East.forward == Position(1, 0))
    assert(South.forward == Position(0, -1))
    assert(West.forward == Position(-1, 0))
  }

  test("An Orientation should have the correct string representation") {
    // Test the string representation for each Orientation value
    assert(North.toString == "N")
    assert(East.toString == "E")
    assert(South.toString == "S")
    assert(West.toString == "W")
  }

  test(
    "The Orientation companion object should be able to create an Orientation from a string representation"
  ) {
    // Test the apply method of the Orientation companion object
    assert(Orientation("N") == North)
    assert(Orientation("E") == East)
    assert(Orientation("S") == South)
    assert(Orientation("W") == West)
    assert(Orientation("Invalid") == OrientationNone)
  }
}
