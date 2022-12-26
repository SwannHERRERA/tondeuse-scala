package fr.esgi.al.funprog.application.model

import fr.esgi.al.funprog.domain.model.Position

case class FunProgLawn(
    upperRight: Position,
    lawnMowers: List[LawnMower]
)
