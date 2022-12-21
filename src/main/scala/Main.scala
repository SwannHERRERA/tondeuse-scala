import java.io.BufferedReader
import java.io.FileReader
import scala.util.Using
import scala.annotation.tailrec

@main def hello: Unit =
  println(msg())
// On Utilise un fichier de configuration
def msg = "I was compiled by Scala 3. :)"

