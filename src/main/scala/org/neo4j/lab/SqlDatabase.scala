/*
 * Created by IntelliJ IDEA.
 * User: ata
 * Date: 4/1/11
 * Time: 11:20 PM
 */
package org.neo4j.lab

import scala.collection.JavaConversions._
import java.lang.Iterable
import org.neo4j.graphdb._

class SqlDatabase(startNode: Node) {
  private val graph = startNode.getGraphDatabase
  private val TABLE = DynamicRelationshipType.withName("table")

  private def getTableNode(name: String): Node = {
    val iterable: Iterable[ Relationship ] = startNode.getRelationships(TABLE, Direction.OUTGOING)

    val tableNode: Node = iterable.filter((rel) => {
      rel.getOtherNode(startNode).getProperty("name") == name
    }).headOption.getOrElse(throw new NotFoundException).getOtherNode(startNode)
    tableNode
  }

  def getTable(name: String) = {
    val tableNode: Node = getTableNode(name)
    new SqlTable(tableNode)
  }

}