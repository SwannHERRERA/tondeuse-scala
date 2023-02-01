package fr.esgi.al.funprog.infrastructure.config

import fr.esgi.al.funprog.application.exception.DonneesIncorectesException
import fr.esgi.al.funprog.application.model.Lawn
import fr.esgi.al.funprog.domain.config.InputLoader
import fr.esgi.al.funprog.domain.model.{Instruction, Orientation, Position}

import scala.util.{Failure, Success, Try}

case class DefaultInputLoader() extends InputLoader {

  /**
   * Loads the data from the given string and returns a tuple containing the upper right corner position,
   * a list of lawns, and a list of instructions for each lawn.
   *
   * @param data the input data string
   * @return a tuple containing the upper right corner position, a list of lawns, and a list of instructions for each lawn wrapped in a Try
   */
  override def loadData(
      data: String
  ): Try[(Position, List[Lawn], List[List[Instruction]])] = {
    // Extract lines from data and get the first line
    val lines: List[String] = extractLines(data)
    val firstLine = lines.headOption

    // Extract upper right corner position from first line
    val upperRightTry = extractUpperRight(firstLine)

    (for {
      upperRight   <- upperRightTry
      instructions <- extractInstructions(lines)
      lawns        <- extractLawns(lines, upperRight)
      if lawns.length == instructions.length
    } yield (upperRight, lawns, instructions))
      .recoverWith {
        case err: DonneesIncorectesException => Failure(err)
        case _ =>
          Failure(
            DonneesIncorectesException(
              "Le nombre de tondeuses est différent du nombre d'instructions."
            )
          )
      }
  }

  /**
   * Extracts the instructions from the input lines.
   *
   * @param lines the input lines
   * @return a list of lists of Instruction objects wrapped in a Try
   */
  private def extractInstructions(
      lines: List[String]
  ): Try[List[List[Instruction]]] = {
    lines
      .drop(1)
      .zipWithIndex
      .filter(_._2 % 2 == 1)
      .map {
        case (line, _) =>
          line.toList
            .map(
              instruction =>
                for {
                  _ <- assertAuthorizedInstruction(instruction)
                } yield Instruction(instruction)
            )
            .foldLeft(Success(List.empty[Instruction]): Try[List[Instruction]]) {
              case (tryInstructions, tryInstruction) =>
                for {
                  instructions <- tryInstructions
                  instruction  <- tryInstruction
                } yield instructions :+ instruction
            }
      }
      .foldLeft(
        Success(List.empty[List[Instruction]]): Try[List[List[Instruction]]]
      ) {
        case (tryInstructions, tryInstruction) =>
          for {
            instructions <- tryInstructions
            instruction  <- tryInstruction
          } yield instructions :+ instruction
      }
  }

  /**
   * Extracts lawns from the given input string.
   *
   * @param lines      a list of strings representing the lines of the input string
   * @param upperRight a [[Position]] object representing the upper right corner of the lawn
   * @return a list of [[Lawn]] objects representing the lawnmowers on the lawn wrapped in a Try
   */
  private def extractLawns(
      lines: List[String],
      upperRight: Position
  ): Try[List[Lawn]] = {
    lines
      .drop(1)
      .zipWithIndex
      .filter(_._2 % 2 == 0)
      .map {
        case (line, _) =>
          line.split(' ').toList match {
            case x :: y :: orientation :: Nil =>
              for {
                _ <- assertPositionInLawn(
                  Position(x.toInt, y.toInt),
                  upperRight
                )
                _ <- assertAuthorizedOrientation(orientation)
              } yield Lawn(
                upperRight,
                Orientation(orientation),
                Position(x.toInt, y.toInt)
              )
            case _ =>
              Failure(
                DonneesIncorectesException("Données de position incorrectes")
              )
          }
      }
      .foldLeft(Success(List.empty[Lawn]): Try[List[Lawn]]) {
        case (tryLawns, tryLawn) =>
          for {
            lawns <- tryLawns
            lawn  <- tryLawn
          } yield lawns :+ lawn
      }
  }

  /**
   * Extracts the upper right corner position from the given input string.
   *
   * @param firstLine an optional string representing the first line of the input string
   * @return a [[Position]] object representing the upper right corner of the lawn wrapped in a Try
   */
  private def extractUpperRight(firstLine: Option[String]): Try[Position] = {
    firstLine match {
      case Some(line) =>
        line.split(' ') match {
          case Array(x, y) if x.toInt >= 0 && y.toInt >= 0 =>
            Success(Position(x.toInt, y.toInt))
          case _ =>
            Failure(DonneesIncorectesException("Données de limite incorrectes"))
        }
      case None => Failure(DonneesIncorectesException("Invalid input file"))
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
        str.stripTrailing()
      }
      .filter(_.nonEmpty)
      .toList
    lines
  }

  /**
   * Asserts that the given instruction is authorized.
   *
   * @param instruction a character representing the instruction to be checked
   */
  private def assertAuthorizedInstruction(instruction: Char): Try[Unit] = {
    instruction match {
      case 'A' | 'D' | 'G' => Success(())
      case _ =>
        Failure(
          DonneesIncorectesException(
            "Instruction incorrecte : " + instruction.toString
          )
        )
    }
  }

  /**
   * Asserts that the given orientation is authorized.
   *
   * @param orientation a string representing the orientation to be checked
   */
  private def assertAuthorizedOrientation(orientation: String): Try[Unit] =
    orientation match {
      case "N" | "S" | "E" | "W" => Success(())
      case _ =>
        Failure(
          DonneesIncorectesException(s"Orientation incorrecte: $orientation")
        )
    }

  /**
   * Assert position is in the lawn.
   *
   * @param position   a [[Position]] object representing the position to be checked
   * @param upperRight a [[Position]] object representing the upper right corner of the lawn
   */
  private def assertPositionInLawn(
      position: Position,
      upperRight: Position
  ): Try[Unit] =
    (
      position.x < 0,
      position.y < 0,
      position.x > upperRight.x,
      position.y > upperRight.y
    ) match {
      case (true, _, _, _) | (_, true, _, _) | (_, _, true, _) |
          (_, _, _, true) =>
        Failure(
          DonneesIncorectesException(
            "Position incorrecte : " + position.toString
          )
        )
      case (_, _, _, _) => Success(())
    }
}

object DefaultInputLoader {
  def apply(): DefaultInputLoader = new DefaultInputLoader()
}
