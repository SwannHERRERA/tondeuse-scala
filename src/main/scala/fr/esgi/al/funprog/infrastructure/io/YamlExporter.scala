package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.application.model.FunProgLawn
import fr.esgi.al.funprog.domain.io.{FunProgLawnExporter, Writer}

import scala.collection.immutable.ListMap

sealed trait YmlValue {
  def indentString(indent: Int): String = "  " * indent
  def toYml(indent: Int): String
}

case class YmlObject(fields: Map[String, YmlValue]) extends YmlValue {
  override def toYml(indent: Int): String = {
    fields
      .map {
        case (key, value) =>
          s"$key: ${value.toYml(indent + 1)}"
      }
      .mkString(
        "\n" + indentString(indent),
        "\n" + indentString(indent),
        ""
      )
  }
}

case class YmlArray(values: List[YmlValue]) extends YmlValue {
  override def toYml(indent: Int): String = {
    values
      .map(_.toYml(indent + 1))
      .mkString(
        "\n" + indentString(indent) + "- ",
        "\n" + indentString(indent) + "- ",
        ""
      )
  }
}

case class YmlString(value: String) extends YmlValue {
  override def toYml(indent: Int): String = {
    value
  }
}

case class YmlNumber(value: Int) extends YmlValue {
  override def toYml(indent: Int): String = {
    value.toString
  }
}

class YamlExporter(writer: Writer) extends FunProgLawnExporter {
  override def export(funProgLawn: FunProgLawn): Unit =
    writer.write(
      YmlObject(
        ListMap[String, YmlValue](
          "limite" -> YmlObject(
            ListMap[String, YmlValue](
              "x" -> YmlNumber(funProgLawn.upperRight.x),
              "y" -> YmlNumber(funProgLawn.upperRight.y)
            )
          ),
          "tondeuses" -> YmlArray(
            funProgLawn.lawnMowers.map { lawnMower =>
              YmlObject(
                ListMap[String, YmlValue](
                  "debut" -> YmlObject(
                    ListMap[String, YmlValue](
                      "point" -> YmlObject(
                        ListMap[String, YmlValue](
                          "x" -> YmlNumber(lawnMower.lawn.initialPosition.x),
                          "y" -> YmlNumber(lawnMower.lawn.initialPosition.y)
                        )
                      ),
                      "direction" -> YmlString(
                        lawnMower.lawn.initialOrientation.toString
                      )
                    )
                  ),
                  "instructions" -> YmlArray(
                    lawnMower.instructions.map { instruction =>
                      YmlString(instruction.toString)
                    }
                  ),
                  "fin" -> {
                    val (finalOrientation, finalPosition) = lawnMower.run
                    YmlObject(
                      ListMap[String, YmlValue](
                        "point" -> YmlObject(
                          ListMap[String, YmlValue](
                            "x" -> YmlNumber(finalPosition.x),
                            "y" -> YmlNumber(finalPosition.y)
                          )
                        ),
                        "direction" -> YmlString(finalOrientation.toString)
                      )
                    )
                  }
                )
              )
            }
          )
        )
      ).toYml(0)
    )
}

object YamlExporter {
  def apply(writer: Writer): YamlExporter = new YamlExporter(writer)
}
