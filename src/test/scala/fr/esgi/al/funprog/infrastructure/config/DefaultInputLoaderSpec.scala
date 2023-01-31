package fr.esgi.al.funprog.infrastructure.config

import fr.esgi.al.funprog.application.model.Lawn
import fr.esgi.al.funprog.domain.model.{Instruction, Orientation, Position}
import org.scalatest.funsuite.AnyFunSuite

import scala.util.{Failure, Success}

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
    DefaultInputLoader().loadData(input) match {
      case Success(output) => assert(output == expectedOutput)
      case _               => fail()
    }
  }

  test(
    "loadData should fail with DonneesIncorectesException if input string is in wrong format"
  ) {
    val input =
      """5
        |1 2 N
        |GAGAGAGAA
        |3 3 E
        |AADAADADDA""".stripMargin
    DefaultInputLoader().loadData(input) match {
      case Success(_) => fail()
      case Failure(exception) =>
        assert(exception.getMessage == "Données de limite incorrectes")
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

    DefaultInputLoader().loadData(input) match {
      case Success(output) => assert(output == expectedOutput)
      case _               => fail()
    }
  }

  test(
    "loadData should fail with DonneesIncorectesException if input string contains invalid data"
  ) {
    val input =
      """5 5
        |1 2 Z
        |GAGAGAGAA
        |3 3 E
        |AADAADADDA""".stripMargin
    DefaultInputLoader().loadData(input) match {
      case Success(_) => fail()
      case Failure(exception) =>
        assert(exception.getMessage == "Orientation incorrecte : Z")
    }
  }

  test(
    "loadData should fail with DonneesIncorectesException if there are more instructions than lawns"
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
    DefaultInputLoader().loadData(input) match {
      case Success(_) => fail()
      case Failure(exception) =>
        assert(exception.getMessage == "Données de position incorrectes")
    }
  }

  test(
    "loadData should fail with DonneesIncorectesException if there are more lawns than instructions"
  ) {
    val input =
      """5 5
        |1 2 N
        |GAGAGAGAA
        |3 3 E
        |AADAADADDA
        |0 0 N""".stripMargin
    DefaultInputLoader().loadData(input) match {
      case Success(_) => fail()
      case Failure(exception) =>
        assert(exception.getMessage == "Le nombre de tondeuses est différent du nombre d'instructions.")
    }
  }

  test(
    "loadData should fail with DonneesIncorectesException if there upperRight position is not valid"
  ) {
    val input =
      """-1 5
        |1 2 N
        |GAGAGAGAA
        |3 3 E
        |AADAADADDA""".stripMargin
    DefaultInputLoader().loadData(input) match {
      case Success(_) => fail()
      case Failure(exception) =>
        assert(exception.getMessage == "Les coordonnées du coin supérieur droit doivent être positives.")
    }
  }

  test(
    "loadData should fail with DonneesIncorectesException for position out of bounds"
  ) {
    val input =
      """5 5
        |6 2 N
        |GAGAGAGAA
        |3 3 E
        |AADAADADDA
      """.stripMargin

    DefaultInputLoader().loadData(input) match {
      case Success(_) => fail()
      case Failure(exception) =>
        assert(exception.getMessage == "Position incorrecte : Position(6,2)")
    }
  }
}
