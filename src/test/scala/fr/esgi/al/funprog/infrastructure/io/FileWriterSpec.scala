package fr.esgi.al.funprog.infrastructure.io

import better.files.File
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source

class FileWriterSpec extends AnyFunSuite with BeforeAndAfterEach {

  private val filePath = "test.txt"
  private val fileContent = "This is a test"

  override def afterEach(): Unit = {
    // Delete the test file after each test
    File(filePath).delete()
    super.afterEach()
  }

  test("FileWriter should create a new file if it doesn't exist") {
    val fileWriter = FileWriter(filePath)
    fileWriter.write(fileContent)

    assert(File(filePath).exists())
  }

  test("FileWriter should overwrite the file if it already exists") {
    val fileWriter = FileWriter(filePath)
    fileWriter.write("Initial content")
    fileWriter.write(fileContent)

    val file = Source.fromFile(filePath)
    val writtenContent = file.getLines().mkString("\n")
    file.close()

    assert(writtenContent == fileContent)
  }
}
