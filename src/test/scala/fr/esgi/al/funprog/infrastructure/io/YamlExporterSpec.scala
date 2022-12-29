package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.application.model.{FunProgLawn, Lawn, LawnMower}
import fr.esgi.al.funprog.domain.model.{Instruction, Orientation, Position}
import org.scalatest.funsuite.AnyFunSuite

class YamlExporterSpec extends AnyFunSuite {
  test("YamlExporter should output the expected YAML string") {
    val writer = new TestWriter
    val exporter = new YamlExporter(writer)
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
      """
        |limite:
        |  x: 5
        |  y: 5
        |tondeuses:
        |  -
        |    debut:
        |      point:
        |        x: 1
        |        y: 2
        |      direction: N
        |    instructions:
        |      - G
        |      - A
        |      - G
        |      - A
        |      - G
        |      - A
        |      - G
        |      - A
        |      - A
        |    fin:
        |      point:
        |        x: 1
        |        y: 3
        |      direction: N
        |  -
        |    debut:
        |      point:
        |        x: 3
        |        y: 3
        |      direction: E
        |    instructions:
        |      - A
        |      - A
        |      - D
        |      - A
        |      - A
        |      - D
        |      - A
        |      - D
        |      - D
        |      - A
        |    fin:
        |      point:
        |        x: 5
        |        y: 1
        |      direction: E""".stripMargin
    assert(writer.content.replaceAll(" \n", "\n") == expectedOutput)
  }
}
