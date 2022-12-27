package fr.esgi.al.funprog.application.exception

/**
 * Exception thrown when the input data is incorrect.
 *
 * @param message the detail message
 */
final case class DonneesIncorectesException(
    private val message: String
) extends Exception(message)
