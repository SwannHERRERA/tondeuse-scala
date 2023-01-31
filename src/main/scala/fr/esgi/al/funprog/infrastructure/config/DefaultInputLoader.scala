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
    val upperRightTry: Try[Position] = extractUpperRight(firstLine)

    upperRightTry match {
      case Success(upperRight) =>
        // Extract instructions from lines
        val instructionsTry: Try[List[List[Instruction]]] = extractInstructions(
          lines
        )

        instructionsTry match {
          case Success(instructions) =>
            // Extract lawns from lines
            val lawnsTry: Try[List[Lawn]] = extractLawns(lines, upperRight)

            lawnsTry match {
              case Success(lawns) if lawns.length != instructions.length =>
                Failure(
                  DonneesIncorectesException(
                    "Le nombre de tondeuses est différent du nombre d'instructions."
                  )
                )
              case Success(lawns)     => Success((upperRight, lawns, instructions))
              case Failure(exception) => Failure(exception)
            }
          case Failure(exception) => Failure(exception)
        }
      case Failure(exception) => Failure(exception)
    }
  }

  /**
   * Extracts the instructions from the input lines.
   *
   * @param lines the input lines
   * @return a list of lists of Instruction objects
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
                assertAuthorizedInstruction(instruction) match {
                  case Success(_)         => Success(Instruction(instruction))
                  case Failure(exception) => Failure(exception)
                }
            )
            .foldRight(Success(List.empty[Instruction]): Try[List[Instruction]]) {
              case (Success(instruction), Success(instructions)) =>
                Success(instruction :: instructions)
              case (Success(_), Failure(exception)) => Failure(exception)
              case (Failure(exception), _)          => Failure(exception)
            }
      }
      .foldRight(
        Success(List.empty[List[Instruction]]): Try[List[List[Instruction]]]
      ) {
        case (Success(instruction), Success(instructions)) =>
          Success(instruction :: instructions)
        case (Success(_), Failure(exception)) => Failure(exception)
        case (Failure(exception), _)          => Failure(exception)
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
              val position = Position(x.toInt, y.toInt)
              assertPositionInLawn(position, upperRight) match {
                case Success(_) =>
                  assertAuthorizedOrientation(orientation) match {
                    case Success(_) =>
                      Success(
                        Lawn(
                          upperRight,
                          Orientation(orientation),
                          Position(x.toInt, y.toInt)
                        )
                      )
                    case Failure(exception) => Failure(exception)
                  }
                case Failure(exception) => Failure(exception)
              }
            case _ =>
              Failure(
                DonneesIncorectesException("Données de position incorrectes")
              )
          }
      }
      .foldRight(Success(List.empty[Lawn]): Try[List[Lawn]]) {
        case (Success(lawn), Success(lawns))  => Success(lawn :: lawns)
        case (Success(_), Failure(exception)) => Failure(exception)
        case (Failure(exception), _)          => Failure(exception)
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
        line.split(' ').toList match {
          case x :: y :: Nil =>
            if (x.toInt < 0 || y.toInt < 0)
              Failure(
                DonneesIncorectesException(
                  "Les coordonnées du coin supérieur droit doivent être positives."
                )
              )
            else
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
    if (List("N", "S", "E", "W").contains(orientation)) Success(())
    else
      Failure(
        DonneesIncorectesException(s"Orientation incorrecte: $orientation")
      )

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
