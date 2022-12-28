package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.application.model.FunProgLawn
import fr.esgi.al.funprog.domain.io.{FunProgLawnExporter, Writer}

import scala.collection.immutable.ListMap

sealed trait JsonValue {
  def indentString(indent: Int): String = "  " * indent
  def toJson(indent: Int): String
}

case class JsonObject(fields: Map[String, JsonValue]) extends JsonValue {
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

case class JsonArray(values: List[JsonPrimitive]) extends JsonValue {
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

case class JsonObjectArray(values: List[JsonObject]) extends JsonValue {
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

trait JsonPrimitive extends JsonValue

case class JsonString(value: String) extends JsonPrimitive {
  override def toJson(indent: Int): String = {
    s""""$value""""
  }
}

case class JsonNumber(value: Int) extends JsonPrimitive {
  override def toJson(indent: Int): String = {
    value.toString
  }
}

class JsonExporter(writer: Writer) extends FunProgLawnExporter {
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

object JsonExporter {
  def apply(writer: Writer): JsonExporter = new JsonExporter(writer)
}
