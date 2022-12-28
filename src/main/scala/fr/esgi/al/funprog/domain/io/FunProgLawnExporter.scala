package fr.esgi.al.funprog.domain.io

import fr.esgi.al.funprog.application.model.FunProgLawn

/**
 * The [[FunProgLawnExporter]] trait represents a type that can export a [[FunProgLawn]].
 */
trait FunProgLawnExporter {

  /**
   * Exports the given [[FunProgLawn]].
   * @param funProgLawn the [[FunProgLawn]] to be exported
   */
  def export(funProgLawn: FunProgLawn): Unit
}
