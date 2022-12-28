package fr.esgi.al.funprog.infrastructure.config

import fr.esgi.al.funprog.application.exception.DonneesIncorectesException
import fr.esgi.al.funprog.application.model.Lawn
import fr.esgi.al.funprog.domain.config.InputLoader
import fr.esgi.al.funprog.domain.model.{Instruction, Orientation, Position}

case class DefaultInputLoader() extends InputLoader {

  /**
   * Loads the data from the given string and returns a tuple containing the upper right corner position,
   * a list of lawns, and a list of instructions for each lawn.
   *
   * @param data the input data string
   * @return a tuple containing the upper right corner position, a list of lawns, and a list of instructions for each lawn
   * @throws DonneesIncorectesException if the data is in the wrong format
   */
  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  @throws[DonneesIncorectesException]
  override def loadData(
      data: String
  ): (Position, List[Lawn], List[List[Instruction]]) =
    try {
      // Extract lines from data and get the first line
      val lines: List[String] = extractLines(data)
      val firstLine = lines.headOption

      // Extract upper right corner position from first line
      val upperRight = extractUpperRight(firstLine)

      // Extract lawns from lines
      val lawns: List[Lawn] = extractLawns(lines, upperRight)

      // Extract instructions from lines
      val instructions: List[List[Instruction]] = extractInstructions(lines)

      // Check that the number of lawns is equal to the number of instructions
      if (lawns.length != instructions.length)
        throw DonneesIncorectesException(
          "Le nombre de tondeuses est différent du nombre d'instructions."
        )
      else
        // Return tuple with upper right corner position, lawns, and instructions
        (upperRight, lawns, instructions)
    } catch {
      case error: Throwable =>
        throw DonneesIncorectesException(
          "Données au mauvais format : " + error.getMessage
        )
    }

  /**
   * Extracts the instructions from the input lines.
   *
   * @param lines the input lines
   * @return a list of lists of Instruction objects
   */
  private def extractInstructions(
      lines: List[String]
  ): List[List[Instruction]] = {
    lines.drop(1).zipWithIndex.filter(_._2 % 2 == 1).map {
      case (line, _) =>
        line.toList.map { instruction =>
          {
            assertAuthorizedInstruction(instruction)
            Instruction(instruction)
          }
        }
    }
  }

  /**
   * Extracts lawns from the given input string.
   *
   * @param lines      a list of strings representing the lines of the input string
   * @param upperRight a [[Position]] object representing the upper right corner of the lawn
   * @return a list of [[Lawn]] objects representing the lawnmowers on the lawn
   * @throws DonneesIncorectesException if the input string is invalid or contains invalid data
   */
  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  @throws[DonneesIncorectesException]
  private def extractLawns(
      lines: List[String],
      upperRight: Position
  ): List[Lawn] = {
    lines.drop(1).zipWithIndex.filter(_._2 % 2 == 0).map {
      case (line, _) =>
        line.split(' ').toList match {
          case x :: y :: orientation :: Nil =>
            val position = Position(x.toInt, y.toInt)
            assertPositionInLawn(position, upperRight)
            assertAuthorizedOrientation(orientation)
            Lawn(
              upperRight,
              Orientation(orientation),
              Position(x.toInt, y.toInt)
            )
          case _ =>
            throw DonneesIncorectesException("Données de position incorrectes")
        }
    }
  }

  /**
   * Extracts the upper right corner position from the given input string.
   *
   * @param firstLine an optional string representing the first line of the input string
   * @return a [[Position]] object representing the upper right corner of the lawn
   * @throws DonneesIncorectesException if the input string is invalid or if the first line does not contain the expected data
   */
  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  @throws[DonneesIncorectesException]
  private def extractUpperRight(firstLine: Option[String]): Position = {
    firstLine match {
      case Some(line) =>
        line.split(' ').toList match {
          case x :: y :: Nil =>
            if (x.toInt < 0 || y.toInt < 0)
              throw DonneesIncorectesException(
                "Les coordonnées du coin supérieur droit doivent être positives."
              )
            else
              Position(x.toInt, y.toInt)
          case _ =>
            throw DonneesIncorectesException("Données de limite incorrectes")
        }
      case None => throw DonneesIncorectesException("Invalid input file")
    }
  }

  /**
   * Extracts the lines from the given input string.
   *
   * @param data a string representing the entire input string
   * @return a list of strings, where each string represents a line in the input string
   */
  private def extractLines(data: String): List[String] = {
    val lines = data
      .split('\n')
      .map { str =>
        str.trim
      }
      .filter(str => str.nonEmpty)
      .toList
    lines
  }

  /**
   * Asserts that the given instruction is authorized.
   *
   * @param instruction a character representing the instruction to be checked
   * @throws DonneesIncorectesException if the instruction is not authorized
   */
  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  @throws[DonneesIncorectesException]
  private def assertAuthorizedInstruction(instruction: Char): Unit = {
    instruction match {
      case 'A' | 'D' | 'G' => ()
      case _ =>
        throw DonneesIncorectesException(
          "Instruction incorrecte : " + instruction.toString
        )
    }
  }

  /**
   * Asserts that the given orientation is authorized.
   *
   * @param orientation a string representing the orientation to be checked
   * @throws DonneesIncorectesException if the orientation is not authorized
   */
  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  @throws[DonneesIncorectesException]
  private def assertAuthorizedOrientation(orientation: String): Unit = {
    orientation match {
      case "N" | "S" | "E" | "W" => ()
      case _ =>
        throw DonneesIncorectesException(
          "Orientation incorrecte : " + orientation
        )
    }
  }

  /**
   * Assert position is in the lawn.
   *
   * @param position   a [[Position]] object representing the position to be checked
   * @param upperRight a [[Position]] object representing the upper right corner of the lawn
   * @throws DonneesIncorectesException if the position is not in the lawn
   */
  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  @throws[DonneesIncorectesException]
  private def assertPositionInLawn(
      position: Position,
      upperRight: Position
  ): Unit = {
    if (position.x < 0 || position.y < 0 || position.x > upperRight.x || position.y > upperRight.y)
      throw DonneesIncorectesException(
        "Position incorrecte : " + position.toString
      )
  }
}

object DefaultInputLoader {
  def apply(): DefaultInputLoader = new DefaultInputLoader()
}
