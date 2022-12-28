package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.application.model.FunProgLawn
import fr.esgi.al.funprog.domain.io.{FunProgLawnExporter, Writer}

import scala.collection.immutable.ListMap

/**
 * The YmlValue trait represents a value in a YAML document.
 */
sealed trait YmlValue {

  /**
   * Generates a string of indentation spaces with the given number of indentation levels.
   * @param indent the number of indentation levels
   * @return a string of indentation spaces
   */
  def indentString(indent: Int): String = "  " * indent

  /**
   * Converts the YmlValue to a YAML string with the given indentation level.
   * @param indent the indentation level
   * @return a YAML string representation of the YmlValue
   */
  def toYml(indent: Int): String
}

/**
 * The YmlObject case class represents a YAML object in a YAML document.
 * @param fields the fields of the YAML object, mapped from field names to YmlValues
 */
case class YmlObject(fields: Map[String, YmlValue]) extends YmlValue {

  /**
   * Converts the YmlObject to a YAML string with the given indentation level.
   * @param indent the indentation level
   *  @return a YAML string representation of the YmlValue
   */
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

/**
 * The YmlArray case class represents a YAML array in a YAML document.
 * @param values the values in the YAML array
 */
case class YmlArray(values: List[YmlValue]) extends YmlValue {

  /**
   * Converts the YmlArray to a YAML string with the given indentation level.
   * @param indent the indentation level
   * @return a YAML string representation of the YmlArray
   */
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

/**
 * The YmlString case class represents a YAML string in a YAML document.
 * @param value the string value
 */
case class YmlString(value: String) extends YmlValue {

  /**
   * Converts the YmlString to a YAML string with the given indentation level.
   * @param indent the indentation level
   * @return a YAML string representation of the YmlString
   */
  override def toYml(indent: Int): String = {
    value
  }
}

/**
 * The YmlNumber case class represents a YAML number in a YAML document.
 * @param value the number value
 */
case class YmlNumber(value: Int) extends YmlValue {

  /**
   * Converts the YmlNumber to a YAML string with the given indentation level.
   * @param indent the indentation level
   * @return a YAML string representation of the YmlNumber
   */
  override def toYml(indent: Int): String = {
    value.toString
  }
}

/**
 * The YamlExporter class represents a type that can export a FunProgLawn to a YAML string.
 * @param writer the Writer where the YAML string will be written
 */
class YamlExporter(writer: Writer) extends FunProgLawnExporter {

  /**
   * Exports the given FunProgLawn to a YAML string and writes it to the writer.
   *
   * @param funProgLawn the FunProgLawn to be exported
   */
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

/**
 * The YamlExporter object provides a factory method for creating [[YamlExporter]] instances.
 */
object YamlExporter {

  /**
   * Creates a new [[YamlExporter]] instance with the given writer.
   * @param writer the Writer where the YAML string will be written
   * @return a new YamlExporter instance
   */
  def apply(writer: Writer): YamlExporter = new YamlExporter(writer)
}
