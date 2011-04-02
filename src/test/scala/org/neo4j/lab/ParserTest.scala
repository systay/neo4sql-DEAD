package org.neo4j.lab

import org.junit.{Before, Test}
import org.neo4j.kernel.ImpermanentGraphDatabase
import org.neo4j.graphdb.{RelationshipType, DynamicRelationshipType, Node, Transaction}
import junit.framework.Assert._

class ParserTest {
  var graph : ImpermanentGraphDatabase = null

  def relType(typeName:String): RelationshipType = DynamicRelationshipType.withName(typeName)

  @Before
  def setup = {
    graph = new ImpermanentGraphDatabase()
    val refNode: Node = graph.getReferenceNode
    val tx: Transaction = graph.beginTx

    val aTable = graph.createNode
    refNode.createRelationshipTo(aTable, relType("table"))
    aTable.setProperty("name", "a")

    val aRow = graph.createNode
    aTable.createRelationshipTo(aRow, relType("first_row"))
    aRow.setProperty("hello", "world")

    tx.success
    tx.finish
  }

  @Test
  def shouldParseSimpleSelectStatement = {
    val sqlEngine: Neo4SqlEngine = new Neo4SqlEngine(graph)
    val sql = "SELECT * FROM a"
    val result: Iterable[ Map[ String, Object ] ] = sqlEngine.run(sql)

    assertEquals(1, result.toList.size)
  }

}