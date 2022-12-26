package fr.esgi.al.funprog.application.model

import fr.esgi.al.funprog.application.service.LawnMowerService
import fr.esgi.al.funprog.domain.model.{Instruction, Orientation, Position}

case class LawnMower(lawn: Lawn, instructions: List[Instruction]) {
  def run: (Orientation, Position) = LawnMowerService(lawn, instructions).run
}
