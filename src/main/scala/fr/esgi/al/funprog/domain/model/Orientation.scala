package fr.esgi.al.funprog.domain.model

sealed trait Orientation {
  def turnRight: Orientation
  def turnLeft: Orientation
  def forward: Position
  def toString: String
}

case object North extends Orientation {
  override def turnRight: Orientation = East
  override def turnLeft: Orientation = West
  override def forward: Position = Position(0, 1)
  override def toString: String = "N"
}

case object East extends Orientation {
  override def turnRight: Orientation = South
  override def turnLeft: Orientation = North
  override def forward: Position = Position(1, 0)
  override def toString: String = "E"
}

case object South extends Orientation {
  override def turnRight: Orientation = West
  override def turnLeft: Orientation = East
  override def forward: Position = Position(0, -1)
  override def toString: String = "S"
}

case object West extends Orientation {
  override def turnRight: Orientation = North
  override def turnLeft: Orientation = South
  override def forward: Position = Position(-1, 0)
  override def toString: String = "W"
}
