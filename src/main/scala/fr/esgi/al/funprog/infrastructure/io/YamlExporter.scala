package fr.esgi.al.funprog.infrastructure.io

import fr.esgi.al.funprog.application.model.FunProgLawn
import fr.esgi.al.funprog.domain.io.{FunProgLawnExporter, Writer}
import fr.esgi.al.funprog.infrastructure.io.serializer.{
  YmlArray,
  YmlNumber,
  YmlObject,
  YmlString,
  YmlValue
}

import scala.collection.immutable.ListMap

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
