import java.io.BufferedReader
import java.io.FileReader
import scala.util.Using
import scala.annotation.tailrec

def nbOccurrences(chain: String, ch: Char): Int  = {
  if (chain.isEmpty) 0
  else if (chain.head == ch) 1 + nbOccurrences(chain.tail, ch)
  else nbOccurrences(chain.tail, ch)
}

def commencePar(chain: String, start: String): Boolean = {
  if (start.isEmpty) true
  else if (chain.isEmpty) false
  else if (chain.charAt(0) == start.charAt(0)) commencePar(chain.tail, start.tail)
  else false
}

def compteMots(chain: String): Int = {
  if (chain.isBlank()) 0
  else compteMotsRecu(chain, 1)
}

def compteMotsRecu(chain: String, acc: Int): Int = {
  if (chain.isEmpty) acc
  else if (chain.charAt(0) == ' ') compteMotsRecu(chain.tail, acc + 1)
  else compteMotsRecu(chain.tail, acc)
}

sealed trait Article

final case class Regular(name: String, category: String, price: Double) extends Article

// discount est compris entre 0 et 1
final case class Discounted(name: String, category: String, price: Double, discount: Float) extends Article

def applyDiscount(article: Discounted): Double = {
  article.price * (1 - article.discount)
}

def price(article: Article): Double = {
  article match {
    case Regular(_, _, price) => price
    case v@Discounted(_, _, _, _) => applyDiscount(v)
  }
}

def applyCoupon(coupon: Float, category: String) = {
  (article: Article) => {
    article match {
      case Regular(_, cat, price) => if (cat == category) price * (1 - coupon) else price
      case v@Discounted(_, cat, _, _) => if (cat == category) applyDiscount(v) * (1 - coupon) else applyDiscount(v)
    }
  }
}

def cartAmount(articles: List[Article], coupon: Article => Double): Double = {
  articles match {
    case Nil => 0
    case head :: tail => coupon(head) + cartAmount(tail, coupon)
  }
}

def exo_1 = {
  val l_in_nb_occurence = nbOccurrences("Hello World", 'l')
  println(l_in_nb_occurence)
  val l_in_empty = nbOccurrences("", 'l')
  println(l_in_empty)
  println(commencePar("Hello World!", "Hello"))
  println(commencePar("Hello World!", "World"))
  println(compteMots("Hello world !"))
  println(compteMots(""))
  println(compteMots(" "))
  println(compteMots("Hello!"))
}

def exo_2 = {
  val articles: List[Article] = List(
    Regular(name = "Biscuits", category = "food", price = 2.0),
    Discounted(name = "Monitor", category = "tech", price = 119.99, discount = 0.1),
    Discounted(name = "Mouse", category = "tech", price = 25.50, discount = 0.2),
    Regular(name = "dress", category = "clothes", price = 49.90)
  )
}

def exo_2_q_6 = {
  val articles: List[Article] = List(
    Regular(name = "Rice", category = "food", price = 10.0),
    Discounted(name = "Chocolate", category = "food", price = 8.0, discount = 0.1),
    Regular(name = "Biscuits", category = "food", price = 2.0),
    Discounted(name = "Monitor", category = "tech", price = 119.99, discount = 0.1),
    Discounted(name = "Mouse", category = "tech", price = 25.50, discount = 0.2),
    Regular(name = "dress", category = "clothes", price = 49.90)
  )
  println(cartAmount(articles, applyCoupon(0.1f, "food")))
}

sealed trait Liste[+A] {
  def map[B](f: A => B): List[B] = {
    this match {
      case NonVide(head, tail) => f(head) :: tail.map(f)
      case Vide => Nil
    }
  }

  def asList(): List[A] = {
    this match {
      case NonVide(head, tail) => head :: tail.asList()
      case Vide => Nil
    }
  }

  //(Vide: Liste[Int]).flatMap[Int](x => Liste(x + 1)) ==  Vide
  // Liste(1, 2, 3, 4).flatMap(x => Liste(x + 1)) == Liste(2, 3, 4, 5)
  def flatMap[B](f: A => Liste[B]): Liste[B] = {
    
    this match {
      case NonVide(head, tail) => {
        tail.map(x => yield innerFlatMap(x))
      }
      case Vide => Vide
    }
  }

  def innerFlatMap[B](list: List[A]): A {
    list match {
      case Nil => Vide
      case head :: tail => head :: innerFlatMap(tail)
    }
  }

  def fold[B >: A](default: B, f: (B, B) => B): B = {
    this match {
      case NonVide(head, tail) => f(head, tail.fold(default, f))
      case Vide => default
    }
  }
}

case class NonVide[+A](tete: A, queue: Liste[A]) extends Liste[A]

object Vide extends Liste[Nothing]

object Liste {
  def apply[A](first: A, others: A*): Liste[A] = {
    val o = others.toList

    if (others.isEmpty) NonVide(first, Vide)
    else NonVide(first, apply(o.head, o.tail: _*))
  }
}

@main def hello: Unit =
  println(Liste(1, 2, 3, 4, 5, 6))
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

