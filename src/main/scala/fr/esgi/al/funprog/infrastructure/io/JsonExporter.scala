package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.application.model.FunProgLawn
import fr.esgi.al.funprog.domain.io.{FunProgLawnExporter, Writer}

import scala.collection.immutable.ListMap

/**
 * The JsonValue trait represents a JSON value that can be converted to a JSON string.
 */
sealed trait JsonValue {

  /**
   * Returns a string of spaces with a length equal to the given indent level.
   * @param indent the indent level
   * @return a string of spaces with a length equal to the given indent level
   */
  def indentString(indent: Int): String = "  " * indent

  /**
   * Returns a JSON string representation of this value with the given indent level.
   * @param indent the indent level
   * @return a JSON string representation of this value with the given indent level
   */
  def toJson(indent: Int): String
}

/**
 * The JsonObject class represents a JSON object.
 * @param fields the fields of the JSON object, where each field is a key-value pair
 */
case class JsonObject(fields: Map[String, JsonValue]) extends JsonValue {

  /**
   * Returns a JSON string representation of this object with the given indent level.
   * @param indent the indent level
   * @return a JSON string representation of this object with the given indent level
   */
  override def toJson(indent: Int): String = {
    fields
      .map {
        case (key, value) =>
          s""""$key": ${value.toJson(indent + 1)}"""
      }
      .mkString(
        "{\n" + indentString(indent + 1),
        ",\n" + indentString(indent + 1),
        "\n" + indentString(indent) + "}"
      )
  }
}

/**
 * The JsonArray class represents a JSON array.
 * @param values the values of the JSON array
 */
case class JsonArray(values: List[JsonPrimitive]) extends JsonValue {

  /**
   * Returns a JSON string representation of this array with the given indent level.
   * @param indent the indent level
   *  @return a JSON string representation of this value with the given indent level
   */
  override def toJson(indent: Int): String = {
    values
      .map(_.toJson(indent + 1))
      .mkString(
        "[",
        ", ",
        "]"
      )
  }
}

/**
 * The JsonObjectArray class represents a JSON array of objects.
 * @param values the values of the JSON array
 */
case class JsonObjectArray(values: List[JsonObject]) extends JsonValue {

  /**
   * Returns a JSON string representation of this array with the given indent level.
   * @param indent the indent level
   * @return a JSON string representation of this array with the given indent level
   */
  override def toJson(indent: Int): String = {
    values
      .map(_.toJson(indent + 1))
      .mkString(
        "[\n" + indentString(indent + 1),
        ",\n" + indentString(indent + 1),
        "\n" + indentString(indent) + "]"
      )
  }
}

/**
 * The JsonPrimitive trait represents a JSON primitive value (string, number, etc.).
 */
trait JsonPrimitive extends JsonValue

/**
 * The JsonString class represents a JSON string.
 * @param value the string value
 */
case class JsonString(value: String) extends JsonPrimitive {

  /**
   * Returns a JSON string representation of this string with the given indent level.
   * @param indent the indent level
   * @return a JSON string representation of this string with the given indent level
   */
  override def toJson(indent: Int): String = {
    s""""$value""""
  }
}

/**
 * The JsonNumber class represents a JSON number.
 * @param value the number value
 */
case class JsonNumber(value: Int) extends JsonPrimitive {

  /**
   * Returns a JSON string representation of this number with the given indent level.
   * @param indent the indent level
   * @return a JSON string representation of this number with the given indent level
   */
  override def toJson(indent: Int): String = {
    value.toString
  }
}

/**
 * Class for exporting a `FunProgLawn` object to a JSON string and writing it to a output stream.
 *
 * @param writer the `Writer` to use for outputting the JSON representation of the `FunProgLawn` object
 */
class JsonExporter(writer: Writer) extends FunProgLawnExporter {

  /**
   * Exports the given `funProgLawn` object to a JSON file or the console, depending on the type of `writer` passed to the
   * `JsonExporter` constructor.
   *
   * @param funProgLawn the `FunProgLawn` object to be exported
   */
  override def export(
      funProgLawn: FunProgLawn
  ): Unit =
    writer.write(
      JsonObject(
        ListMap[String, JsonValue](
          "limite" -> JsonObject(
            ListMap(
              "x" -> JsonNumber(funProgLawn.upperRight.x),
              "y" -> JsonNumber(funProgLawn.upperRight.y)
            )
          ),
          "tondeuses" -> JsonObjectArray(
            funProgLawn.lawnMowers.map { lawnMower =>
              JsonObject(
                ListMap[String, JsonValue](
                  "debut" -> JsonObject(
                    ListMap[String, JsonValue](
                      "point" -> JsonObject(
                        ListMap(
                          "x" -> JsonNumber(lawnMower.lawn.initialPosition.x),
                          "y" -> JsonNumber(lawnMower.lawn.initialPosition.y)
                        )
                      ),
                      "direction" -> JsonString(
                        lawnMower.lawn.initialOrientation.toString
                      )
                    )
                  ),
                  "instructions" -> JsonArray(
                    lawnMower.instructions.map { instruction =>
                      JsonString(instruction.toString)
                    }
                  ),
                  "fin" -> {
                    val (finalOrientation, finalPosition) = lawnMower.run
                    JsonObject(
                      ListMap[String, JsonValue](
                        "point" -> JsonObject(
                          ListMap(
                            "x" -> JsonNumber(finalPosition.x),
                            "y" -> JsonNumber(finalPosition.y)
                          )
                        ),
                        "direction" -> JsonString(finalOrientation.toString)
                      )
                    )
                  }
                )
              )
            }
          )
        )
      ).toJson(0)
    )
}

/**
 * Companion object for the [[JsonExporter]] class. Provides a convenient way to create [[JsonExporter]] instances.
 */
object JsonExporter {

  /**
   * Creates a new [[JsonExporter]] instance with the given `writer`.
   *
   * @param writer the [[Writer]] to use for outputting the JSON representation of the [[FunProgLawn]] object
   * @return a new [[JsonExporter]] instance
   */
  def apply(writer: Writer): JsonExporter = new JsonExporter(writer)
}
