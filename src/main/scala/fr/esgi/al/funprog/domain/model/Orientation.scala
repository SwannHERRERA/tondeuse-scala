package fr.esgi.al.funprog.domain.model

/**
[[Orientation]] represents the direction that an object is facing.
It has four possible values: North, East, South, and West.
Each Orientation value has methods to turn right or left,
and to move forward in the current direction.
 */
sealed trait Orientation {

  /**
  Returns the [[Orientation]] to the right of this one.
  @return the [[Orientation]] to the right of this one.
   */
  def turnRight: Orientation

  /**
   * Returns the [[Orientation]] to the left of this one.
   * @return the [[Orientation]] to the left of this one.
   */
  def turnLeft: Orientation

  /**
   * Returns the [[Position]] that results from moving forward in this direction.
   * @return the [[Position]] that results from moving forward in this direction.
   */
  def forward: Position

  /**
   * Returns a string representation of this [[Orientation]].
   * @return a string representation of this [[Orientation]].
   */
  def toString: String
}

/**
[[Orientation]] representing the direction North.
 */
case object North extends Orientation {
  override def turnRight: Orientation = East
  override def turnLeft: Orientation = West
  override def forward: Position = Position(0, 1)
  override def toString: String = "N"
}

/**
[[Orientation]] representing the direction East.
 */
case object East extends Orientation {
  override def turnRight: Orientation = South
  override def turnLeft: Orientation = North
  override def forward: Position = Position(1, 0)
  override def toString: String = "E"
}

/**
[[Orientation]] representing the direction South.
 */
case object South extends Orientation {
  override def turnRight: Orientation = West
  override def turnLeft: Orientation = East
  override def forward: Position = Position(0, -1)
  override def toString: String = "S"
}

/**
[[Orientation]] representing the direction West.
 */
case object West extends Orientation {
  override def turnRight: Orientation = North
  override def turnLeft: Orientation = South
  override def forward: Position = Position(-1, 0)
  override def toString: String = "W"
}

/**
Companion object for the [[Orientation]] class, providing a method to create an [[Orientation]]
from a string representation.
 */
object Orientation {

  /**
      Creates an [[Orientation]] from a string representation.
      @param orientation a string representation of the desired [[Orientation]].
      @return an [[Orientation]] corresponding to the provided string representation.
      If the string is not a valid representation, the method returns [[North]].
   */
  def apply(orientation: String): Orientation = orientation match {
    case "N" => North
    case "E" => East
    case "S" => South
    case "W" => West
    case _   => println("Ooops ! Invalid orientation"); North
  }
}
