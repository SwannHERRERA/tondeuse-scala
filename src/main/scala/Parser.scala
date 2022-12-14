import java.io.BufferedReader
import java.io.FileReader
import scala.util.Using

object Parser {
  def parse: (filename: String) => Map = (filename: String) => {
    val path = "src/main/resources/" + filename
    var list: Map = List()
    val buffer = new BufferedReader(new FileReader(path))
    Using(buffer) { reader =>
      val line: String = reader.readLine()
    }
    list
  }
}