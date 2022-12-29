package fr.esgi.al.funprog.infrastructure.config

import fr.esgi.al.funprog.application.exception.DonneesIncorectesException
import fr.esgi.al.funprog.application.model.Lawn
import fr.esgi.al.funprog.domain.model.{Instruction, Orientation, Position}
import org.scalatest.funsuite.AnyFunSuite

class DefaultInputLoaderSpec extends AnyFunSuite {
  test(
    "loadData should extract upper right corner position, lawns and instructions from input string"
  ) {
    val input =
      """5 5
        |1 2 N
        |GAGAGAGAA
        |3 3 E
        |AADAADADDA""".stripMargin
    val expectedOutput = (
      Position(5, 5),
      List(
        Lawn(Position(5, 5), Orientation("N"), Position(1, 2)),
        Lawn(Position(5, 5), Orientation("E"), Position(3, 3))
      ),
      List(
        List(
          Instruction('G'),
          Instruction('A'),
          Instruction('G'),
          Instruction('A'),
          Instruction('G'),
          Instruction('A'),
          Instruction('G'),
          Instruction('A'),
          Instruction('A')
        ),
        List(
          Instruction('A'),
          Instruction('A'),
          Instruction('D'),
          Instruction('A'),
          Instruction('A'),
          Instruction('D'),
          Instruction('A'),
          Instruction('D'),
          Instruction('D'),
          Instruction('A')
        )
      )
    )
    assert(DefaultInputLoader().loadData(input) == expectedOutput)
  }

  test(
    "loadData should throw DonneesIncorectesException if input string is in wrong format"
  ) {
    val input =
      """5
        |1 2 N
        |GAGAGAGAA
        |3 3 E
        |AADAADADDA""".stripMargin
    assertThrows[DonneesIncorectesException] {
      DefaultInputLoader().loadData(input)
    }
  }

  test(
    "loadData should extract upper right corner position, lawns and instructions from input string, even if upperRight is 0,0"
  ) {
    val input =
      """0 0
        |0 0 N
        |GAGAGAGAA
        |0 0 E
        |AADAADADDA""".stripMargin

    val expectedOutput = (
      Position(0, 0),
      List(
        Lawn(Position(0, 0), Orientation("N"), Position(0, 0)),
        Lawn(Position(0, 0), Orientation("E"), Position(0, 0))
      ),
      List(
        List(
          Instruction('G'),
          Instruction('A'),
          Instruction('G'),
          Instruction('A'),
          Instruction('G'),
          Instruction('A'),
          Instruction('G'),
          Instruction('A'),
          Instruction('A')
        ),
        List(
          Instruction('A'),
          Instruction('A'),
          Instruction('D'),
          Instruction('A'),
          Instruction('A'),
          Instruction('D'),
          Instruction('A'),
          Instruction('D'),
          Instruction('D'),
          Instruction('A')
        )
      )
    )

    assert(DefaultInputLoader().loadData(input) == expectedOutput)
  }

  test(
    "loadData should throw DonneesIncorectesException if input string contains invalid data"
  ) {
    val input =
      """5 5
        |1 2 Z
        |GAGAGAGAA
        |3 3 E
        |AADAADADDA""".stripMargin
    assertThrows[DonneesIncorectesException] {
      DefaultInputLoader().loadData(input)
    }
  }

  test(
    "loadData should throw DonneesIncorectesException if there are more instructions than lawns"
  ) {
    val input =
      """5 5
        |1 2 N
        |GAGAGAGAA
        |3 3 E
        |AADAADADDA
        |0 0 N
        |AA
        |ADGGAAAGA""".stripMargin
    assertThrows[DonneesIncorectesException] {
      DefaultInputLoader().loadData(input)
    }
  }

  test(
    "loadData should throw DonneesIncorectesException if there are more lawns than instructions"
  ) {
    val input =
      """5 5
        |1 2 N
        |GAGAGAGAA
        |3 3 E
        |AADAADADDA
        |0 0 N""".stripMargin
    assertThrows[DonneesIncorectesException] {
      DefaultInputLoader().loadData(input)
    }
  }

  test(
    "loadData should throw DonneesIncorectesException if there upperRight position is not valid"
  ) {
    val input =
      """-1 5
        |1 2 N
        |GAGAGAGAA
        |3 3 E
        |AADAADADDA""".stripMargin
    assertThrows[DonneesIncorectesException] {
      DefaultInputLoader().loadData(input)
    }
  }

  test(
    "loadData should throw DonneesIncorectesException for position out of bounds"
  ) {
    val input =
      """5 5
        |6 2 N
        |GAGAGAGAA
        |3 3 E
        |AADAADADDA
      """.stripMargin

    assertThrows[DonneesIncorectesException] {
      DefaultInputLoader().loadData(input)
    }
  }
}
