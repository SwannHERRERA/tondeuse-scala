package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.application.model.FunProgLawn
import fr.esgi.al.funprog.domain.io.{FunProgLawnExporter, Writer}
import fr.esgi.al.funprog.infrastructure.io.serializer.{
  JsonArray,
  JsonNumber,
  JsonObject,
  JsonObjectArray,
  JsonString,
  JsonValue
}

import scala.collection.immutable.ListMap

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
