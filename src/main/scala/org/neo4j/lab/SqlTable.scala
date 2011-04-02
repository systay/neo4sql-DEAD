package org.neo4j.lab

import scala.collection.JavaConversions._
import org.neo4j.graphdb.Node

class SqlTable(node: Node) {

  def rows: Iterable[ SqlRow ] = {
    val firstRel = node.
      getRelationships(RelationshipTypes.FIRST).
      headOption

    firstRel match {
      case None => new Array[ SqlRow ](0)
      case Some(x) => {
        val firstNode: Node = x.getOtherNode(node)
        new TableScan(firstNode)
      }
    }
  }
}