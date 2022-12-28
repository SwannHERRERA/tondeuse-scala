package fr.esgi.al.funprog.application.model

import fr.esgi.al.funprog.domain.model.Position

/**
 * Represents a rectangular lawn with several lawnmowers on it.
 *
 * @param upperRight a [[Position]] object representing the upper right corner of the lawn
 * @param lawnMowers a list of [[LawnMower]] objects representing the lawnmowers on the lawn
 */
case class FunProgLawn(
    upperRight: Position,
    lawnMowers: List[LawnMower]
)
