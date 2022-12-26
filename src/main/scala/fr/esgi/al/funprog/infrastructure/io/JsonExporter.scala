package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.application.model.FunProgLawn
import fr.esgi.al.funprog.domain.io.FunProgLawnExporter

import scala.collection.immutable.ListMap

sealed trait JsonValue {
  def indentString(indent: Int): String = "\t" * indent
  def toJson(indent: Int = 0): String
}

case class JsonObject(fields: Map[String, JsonValue]) extends JsonValue {
  override def toJson(indent: Int = 0): String = {
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

case class JsonArray(values: List[JsonValue]) extends JsonValue {
  override def toJson(indent: Int = 0): String = {
    values
      .map(_.toJson(indent + 1))
      .mkString(
        "[",
        ", ",
        "]"
      )
  }
}

case class JsonString(value: String) extends JsonValue {
  override def toJson(indent: Int = 0): String = {
    s""""$value""""
  }
}

case class JsonNumber(value: Int) extends JsonValue {
  override def toJson(indent: Int = 0): String = {
    value.toString
  }
}

class JsonExporter extends FunProgLawnExporter {
  override def export(
      funProgLawn: FunProgLawn
  ): String =
    JsonObject(
      ListMap(
        "limite" -> JsonObject(
          ListMap(
            "x" -> JsonNumber(funProgLawn.upperRight.x),
            "y" -> JsonNumber(funProgLawn.upperRight.y)
          )
        ),
        "tondeuses" -> JsonArray(
          funProgLawn.lawnMowers.map { lawnMower =>
            JsonObject(
              ListMap(
                "position" -> JsonObject(
                  ListMap(
                    "x" -> JsonNumber(lawnMower.lawn.initialPosition.x),
                    "y" -> JsonNumber(lawnMower.lawn.initialPosition.y)
                  )
                ),
                "orientation"  -> JsonString(lawnMower.lawn.initialOrientation.toString),
                "instructions" -> JsonArray() // suite ...
              )
            )
          }
        )
      )
    ).toJson()
}
