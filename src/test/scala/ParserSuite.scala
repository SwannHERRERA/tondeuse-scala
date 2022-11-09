class ParserSuite extends munit.FunSuite {
  test("1. should parse the map") {
    val obtained = parse("ParserSuite1.txt")
    val expected = List(List('c'))
    assertEquals(obtained, expected)
  }
  test("2 should parse the map") {
    val obtained = parse("ParserSuite2.txt")
    val expected = List(List(' ', ' '), List(' ', ' '))
    assertEquals(obtained, expected)
  }
}
