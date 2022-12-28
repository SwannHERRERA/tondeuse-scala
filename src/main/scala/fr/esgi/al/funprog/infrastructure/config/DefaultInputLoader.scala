package fr.esgi.al.funprog.infrastructure.config

import fr.esgi.al.funprog.application.model.Lawn
import fr.esgi.al.funprog.domain.config.InputLoader
import fr.esgi.al.funprog.domain.model.{Instruction, Orientation, Position}

case class DefaultInputLoader() extends InputLoader {
  override def loadData(
      data: String
  ): (Position, List[Lawn], List[List[Instruction]]) =
    try {
      val lines = data.split('\n').map { case (str) => str.trim }.toList
      val firstLine = lines.headOption

      val upperRight = firstLine match {
        case Some(line) =>
          line.split(' ').toList match {
            case x :: y :: Nil =>
              assert(x.toInt >= 0 && y.toInt >= 0)
              Position(x.toInt, y.toInt)
            case _ =>
              ??? //throw DonneesIncorectesException("Données de limite incorrectes")
          }
        case None =>
          ??? //throw DonneesIncorectesException("Invalid input file")
      }

      val lawns: List[Lawn] =
        lines.drop(1).zipWithIndex.filter(_._2 % 2 == 0).map {
          case (line, _) =>
            line.split(' ').toList match {
              case x :: y :: orientation :: Nil =>
                assert(
                  x.toInt >= 0 && y.toInt >= 0 && x.toInt <= upperRight.x && y.toInt <= upperRight.y
                )
                Lawn(
                  upperRight,
                  Orientation(orientation),
                  Position(x.toInt, y.toInt)
                )
              case _ =>
                ??? // throw DonneesIncorectesException("Données de position incorrectes")
            }
        }

      val instructions: List[List[Instruction]] =
        lines.drop(1).zipWithIndex.filter(_._2 % 2 == 1).map {
          case (line, _) =>
            line.toList.map {
              Instruction(_)
            }
        }

      assert(lawns.length == instructions.length)
      (upperRight, lawns, instructions)

    } catch {
      case _: Throwable =>
        ??? //throw DonneesIncorectesException("Données au mauvais format")
    }
}

object DefaultInputLoader {
  def apply(): DefaultInputLoader = new DefaultInputLoader()
}
