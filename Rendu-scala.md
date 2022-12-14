# Rendu Scala

Swann HERRERA

## Exercice 1

```scala
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

@main def hello: Unit =
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
```

## Exercice 2

```scala
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
    case Discounted(_, _, price, discount) => article.price * (1 - discount)
  }
}

def cartAmount(articles: List[Article]): Double = {
  articles match {
    case Nil => 0
    case head :: tail => price(head) + cartAmount(tail)
  }
}

@main def hello: Unit =
  val articles: List[Article] = List(
    Regular(name = "Biscuits", category = "food", price = 2.0),
    Discounted(name = "Monitor", category = "tech", price = 119.99, discount = 0.1),
    Discounted(name = "Mouse", category = "tech", price = 25.50, discount = 0.2),
    Regular(name = "dress", category = "clothes", price = 49.90)
  )
  print(cartAmount(articles))
```

resutltat : 180.29099744319916
resultat calculé : `2 + 119.99 * 0.9 + 25.50 * 0.8 + 49.90 = 180.291`

### Question 6

resutlat: 195.57099681377412

## Exercice 2

```scala
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

  // Cette partie du code ne fonctionne pas mais j'ai l'idée du yield
  def flatMap[B](f: A => Liste[B]): Liste[B] = {

    this match {
      case NonVide(head, tail) => {
        tail.map(x => yield innerFlatMap(x))
      }
      case Vide => Vide
    }
  }

  // Cette fonction est liée a la precedente
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

```
