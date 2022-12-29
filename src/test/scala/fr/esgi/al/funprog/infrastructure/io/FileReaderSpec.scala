package fr.esgi.al.funprog.infrastructure.io

import better.files.File
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funsuite.AnyFunSuite

class FileReaderSpec extends AnyFunSuite with BeforeAndAfterEach {

  private val filePath = "test-input.txt"
  private val fileContent = "Hello\nWorld\n"

  override def beforeEach(): Unit = {
    // Create the test file before each test
    File(filePath).write(fileContent)
    super.beforeEach()
  }

  override def afterEach(): Unit = {
    // Delete the test file after each test
    File(filePath).delete()
    super.afterEach()
  }

  test("readAll should return the content of the file as a string") {
    // Arrange
    val expectedContent = "Hello\nWorld\n"
    val fileReader = FileReader(filePath)

    // Act
    val content = fileReader.readAll

    // Assert
    assert(content == expectedContent)
  }

  test(
    "readLines should return a list of strings, one for each line in the file"
  ) {
    // Arrange
    val filePath = "test-input.txt"
    val expectedLines = List("Hello", "World")

    // Act
    val fileReader = FileReader(filePath)

    // Assert
    assert(fileReader.readLines == expectedLines)
  }
}
