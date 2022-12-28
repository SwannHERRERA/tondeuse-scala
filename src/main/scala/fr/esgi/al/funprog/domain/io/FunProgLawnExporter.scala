package fr.esgi.al.funprog.domain.io

import fr.esgi.al.funprog.application.model.FunProgLawn

/**
 * An interface for exporting the state of a lawn and its mowers.
 *
 */
trait FunProgLawnExporter {

  def export(
      funProgLawn: FunProgLawn
  ): Unit
}
