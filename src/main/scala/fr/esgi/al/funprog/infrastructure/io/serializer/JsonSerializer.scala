package fr.esgi.al.funprog.infrastructure.io.serializer

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
   *
   * @param indent the indent level
   * @return a JSON string representation of this number with the given indent level
   */
  override def toJson(indent: Int): String = {
    value.toString
  }
}
