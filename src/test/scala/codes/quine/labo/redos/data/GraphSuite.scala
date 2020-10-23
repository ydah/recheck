package codes.quine.labo.redos.data

class GraphSuite extends munit.FunSuite {
  test("Graph.from") {
    val g = Graph.from(Seq((1, 'a', 2), (1, 'a', 3), (2, 'b', 3)))
    assertEquals(g.neighbors, Map(1 -> Seq(('a', 2), ('a', 3)), 2 -> Seq(('b', 3))))
    assertEquals(g.neighbors(4), Seq.empty) // `withDefaultValue(Seq.empty)` is used
  }

  test("Graph#edges") {
    val g = Graph.from(Seq((1, 'a', 2), (1, 'a', 3), (2, 'b', 3)))
    assertEquals(g.edges, Seq((1, 'a', 2), (1, 'a', 3), (2, 'b', 3)))
  }

  test("Graph#vertices") {
    val g = Graph.from(Seq((1, 'a', 2), (1, 'a', 3), (2, 'b', 3)))
    assertEquals(g.vertices, Set(1, 2, 3))
  }

  test("Graph#reverse") {
    val g1 = Graph.from(Seq((1, 'a', 2), (1, 'a', 3), (2, 'b', 3)))
    val g2 = Graph.from(Seq((2, 'a', 1), (3, 'a', 1), (3, 'b', 2)))
    assertEquals(g1.reverse, g2)
  }

  test("Graph#scc") {
    val g = Graph.from(
      Seq(
        (1, (), 2),
        (2, (), 3),
        (3, (), 4),
        (4, (), 2),
        (3, (), 5),
        (5, (), 6),
        (6, (), 5),
        (6, (), 7)
      )
    )
    assertEquals(g.scc, Seq(Seq(7), Seq(6, 5), Seq(4, 3, 2), Seq(1)))
  }

  test("Graph#path") {
    val g = Graph.from(
      Seq(
        (1, 'a', 2),
        (2, 'b', 3),
        (3, 'c', 2),
        (3, 'd', 4),
        (3, 'e', 5)
      )
    )
    assertEquals(g.path(Set(1), 4), Some(Seq('a', 'b', 'd')))
    assertEquals(g.path(Set(1), 5), Some(Seq('a', 'b', 'e')))
    assertEquals(g.path(Set(4), 5), None)
    assertEquals(g.path(Set(4, 3), 5), Some(Seq('e')))
  }

  test("Graph#reachable") {
    val g = Graph.from(
      Seq(
        (1, (), 2),
        (2, (), 3),
        (3, (), 1),
        (4, (), 5),
        (6, (), 7)
      )
    )
    assertEquals(g.reachable(Set(1)), Graph.from(Seq((1, (), 2), (2, (), 3), (3, (), 1))))
    assertEquals(g.reachable(Set(4, 6)), Graph.from(Seq((4, (), 5), (6, (), 7))))
    assertEquals(g.reachable(Set(1)).neighbors(4), Seq.empty) // `withDefaultValue(Seq.empty)` is used
  }

  test("Graph#reachableMap") {
    val g = Graph.from(
      Seq(
        (1, (), 2),
        (2, (), 3),
        (4, (), 5),
        (6, (), 7)
      )
    )
    assertEquals(
      g.reachableMap,
      Map(
        1 -> Set(1, 2, 3),
        2 -> Set(2, 3),
        3 -> Set(3),
        4 -> Set(4, 5),
        5 -> Set(5),
        6 -> Set(6, 7),
        7 -> Set(7)
      )
    )
  }
}
