package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.application.model.{FunProgLawn, Lawn, LawnMower}
import fr.esgi.al.funprog.domain.model.{Instruction, Orientation, Position}
import org.scalatest.funsuite.AnyFunSuite

class CsvExporterSpec extends AnyFunSuite {
  test("CsvExporter should output the expected CSV string") {
    val writer = new TestWriter
    val exporter = new CsvExporter(writer)
    val lawn = FunProgLawn(
      Position(5, 5),
      List(
        LawnMower(
          Lawn(Position(5, 5), Orientation("N"), Position(1, 2)),
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
          )
        ),
        LawnMower(
          Lawn(Position(5, 5), Orientation("E"), Position(3, 3)),
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
    )
    exporter.export(lawn)
    val expectedOutput =
      """1;1;2;N;1;3;N;GAGAGAGAA
        |2;3;3;E;5;1;E;AADAADADDA""".stripMargin
    assert(writer.content == expectedOutput)
  }
}
