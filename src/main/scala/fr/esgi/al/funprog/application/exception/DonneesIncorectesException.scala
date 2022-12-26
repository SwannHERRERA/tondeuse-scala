package fr.esgi.al.funprog.application.exception

/**
 * Exception thrown when the input data is incorrect.
 *
 * @param message the detail message
 * @param cause the cause of the exception
 */
final case class DonneesIncorectesException(
    private val message: String = "",
    private val cause: Throwable = None.orNull
) extends Exception(message, cause)
