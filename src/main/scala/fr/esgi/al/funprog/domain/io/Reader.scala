package fr.esgi.al.funprog.domain.io

trait Reader {
  def readAll: String
  def readLines: List[String]
}
