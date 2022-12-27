package fr.esgi.al.funprog

import fr.esgi.al.funprog.application.model.{FunProgLawn, Lawn, LawnMower}
import fr.esgi.al.funprog.domain.io.FunProgLawnExporter
import fr.esgi.al.funprog.domain.model._
import fr.esgi.al.funprog.infrastructure.io.JsonExporter

object Main extends App {
  println("Ici le programme principal")
  println("Hello World")

  private val upperRight = Position(5, 5)
  private val lawn1 = Lawn(upperRight, North, Position(1, 2))
  private val lawn1Instructions = List[Instruction](
    TurnLeft,
    Forward,
    TurnLeft,
    Forward,
    TurnLeft,
    Forward,
    TurnLeft,
    Forward,
    Forward
  )
  private val lawn2 = Lawn(upperRight, East, Position(3, 3))
  private val lawn2Instructions = List[Instruction](
    Forward,
    Forward,
    TurnRight,
    Forward,
    Forward,
    TurnRight,
    Forward,
    TurnRight,
    TurnRight,
    Forward
  )
  private val lawnMower1 = LawnMower(lawn1, lawn1Instructions)
  private val lawnMower2 = LawnMower(lawn2, lawn2Instructions)

  private val funProgLawn = FunProgLawn(upperRight, List(lawnMower1, lawnMower2))

  private val exporter: FunProgLawnExporter = new JsonExporter()

  println(exporter.export(funProgLawn))
}
