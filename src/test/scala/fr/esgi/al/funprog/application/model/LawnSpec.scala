package fr.esgi.al.funprog.application.model

import fr.esgi.al.funprog.domain.model.{North, Position}
import org.scalatest.funsuite.AnyFunSuite

class LawnSpec extends AnyFunSuite {

  test("A Lawn should correctly determine if a position is inside its bounds") {
    // Test the isInside method of the Lawn class
    val lawn = Lawn(Position(5, 5), North, Position(0, 0))
    assert(lawn.isInside(Position(0, 0)))
    assert(lawn.isInside(Position(5, 5)))
    assert(!lawn.isInside(Position(-1, 0)))
    assert(!lawn.isInside(Position(0, -1)))
    assert(!lawn.isInside(Position(6, 0)))
    assert(!lawn.isInside(Position(0, 6)))
  }
}
