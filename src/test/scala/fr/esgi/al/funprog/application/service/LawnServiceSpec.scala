package fr.esgi.al.funprog.application.service

import fr.esgi.al.funprog.application.model.{Lawn, LawnMower}
import fr.esgi.al.funprog.domain.model.{Instruction, Orientation, Position}
import org.scalatest.funsuite.AnyFunSuite

class LawnServiceSpec extends AnyFunSuite {
  // Define some test fixtures (test data) that we can reuse in multiple tests
  val position1: Position = Position(1, 1)
  val position2: Position = Position(5, 5)
  val position3: Position = Position(5, 0)
  val orientation1: Orientation = Orientation("N")
  val orientation2: Orientation = Orientation("E")
  val lawn1: Lawn = Lawn(position1, orientation1, position1)
  val lawn2: Lawn = Lawn(position2, orientation2, position2)
  val lawn3: Lawn = Lawn(position3, orientation1, position3)
  val instruction1: List[Instruction] = List[Instruction](
    Instruction('A'),
    Instruction('G'),
    Instruction('A'),
    Instruction('D')
  )
  val instruction2: List[Instruction] = List[Instruction](
    Instruction('A'),
    Instruction('A'),
    Instruction('D'),
    Instruction('A')
  )
  val instruction3: List[Instruction] = List[Instruction](
    Instruction('A'),
    Instruction('A'),
    Instruction('G'),
    Instruction('A')
  )
  val lawns: List[Lawn] = List[Lawn](lawn1, lawn2, lawn3)
  val instructions: List[List[Instruction]] =
    List[List[Instruction]](instruction1, instruction2, instruction3)
  val lawnService: LawnService = LawnService(position2, lawns, instructions)

  test("initLawnMowers should return a list of LawnMower objects") {
    val lawnMowers = lawnService.initLawnMowers

    assert(lawnMowers.forall(_.isInstanceOf[LawnMower]))
  }

  test(
    "initLawnMowers should return a list with the correct number of LawnMower objects"
  ) {
    val lawnMowers = lawnService.initLawnMowers

    assert(lawnMowers.length == lawns.length)
  }

  test(
    "initLawnMowers should return a list of LawnMower objects with the correct lawn and instructions"
  ) {
    val lawnMowers = lawnService.initLawnMowers

    assert(lawnMowers.zip(lawns).forall { case (lm, l) => lm.lawn == l })
    assert(lawnMowers.zip(instructions).forall {
      case (lm, inst) => lm.instructions == inst
    })
  }
}
