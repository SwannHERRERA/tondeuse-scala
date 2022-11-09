import java.io.BufferedReader
import java.io.FileReader
import scala.util.Using

@main def hello: Unit =
  println("Hello world!")
  println(msg)
  val map: Map = Parser.parse("ParserSuite1.txt")
  println(map)


// Map : [][]
// Orientation: enum(N, E, W, S)
// état de la tondeuse a un instant T: (0, 0, N)
// Fonctionne avec une pile d'instruction :
// A -> Avancer
// D -> rotation Droit
// G -> rotation Gauche
// On ne peut pas sortir de la carte mais pas d'erreur juste aucun changement d'êtat

// On Utilise un fichier de configuration
def msg = "I was compiled by Scala 3. :)"

