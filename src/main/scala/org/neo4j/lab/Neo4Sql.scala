package org.neo4j.lab

import java.io.StringReader
import net.sf.jsqlparser.parser.CCJSqlParserManager
import net.sf.jsqlparser.statement.select.{PlainSelect, Select}
import net.sf.jsqlparser.schema.Table
import org.neo4j.kernel.{Traversal, EmbeddedGraphDatabase}
import org.neo4j.graphdb.{Direction, DynamicRelationshipType, GraphDatabaseService}
import org.neo4j.graphdb.traversal.Traverser

class Neo4Sql(graph: GraphDatabaseService) {
  def this(location: String) = this (new EmbeddedGraphDatabase(location))

  def run(sql: String): Iterable[ Map[ String, Object ] ] = {
    val parser = new CCJSqlParserManager
    val select = parser.parse(new StringReader(sql)).asInstanceOf[ Select ].getSelectBody().asInstanceOf[PlainSelect]
    val table: Table = select.getFromItem.asInstanceOf[ Table ]
    val TABLES = DynamicRelationshipType.withName("tables")
    val TABLE = DynamicRelationshipType.withName("table")

    val traverse: Traverser = Traversal.description().
      depthFirst.
      relationships(TABLES, Direction.OUTGOING).
      relationships(TABLE, Direction.OUTGOING).
      traverse(graph.getReferenceNode)

    for((path)<-traverse) yield {
      null
    }



    graph.getReferenceNode
  }
}