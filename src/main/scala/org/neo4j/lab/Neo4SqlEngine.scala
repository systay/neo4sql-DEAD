package org.neo4j.lab

import java.io.StringReader
import net.sf.jsqlparser.parser.CCJSqlParserManager
import net.sf.jsqlparser.statement.select.{PlainSelect, Select}
import net.sf.jsqlparser.schema.Table
import org.neo4j.kernel.EmbeddedGraphDatabase
import org.neo4j.graphdb.GraphDatabaseService

class Neo4SqlEngine(graph: GraphDatabaseService) {
  def this(location: String) = this (new EmbeddedGraphDatabase(location))

  def run(sql: String): Iterable[ Map[ String, Object ] ] = {
    val parser = new CCJSqlParserManager
    val select = parser.parse(new StringReader(sql)).asInstanceOf[ Select ].getSelectBody().asInstanceOf[PlainSelect]
    val table: Table = select.getFromItem.asInstanceOf[ Table ]


    val sqlDatabase = new SqlDatabase(graph.getReferenceNode)
    val sqlTable : SqlTable = sqlDatabase.getTable(table.getName)
    sqlTable.rows
  }
}