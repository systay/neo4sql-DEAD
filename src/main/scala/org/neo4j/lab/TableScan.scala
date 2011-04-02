package org.neo4j.lab

import org.neo4j.kernel.Traversal
import org.neo4j.graphdb.Node
import scala.collection.JavaConversions._
import org.neo4j.graphdb.traversal.Traverser


// 30
class TableScan(firstNode: Node) extends Iterable[ SqlRow ] {
  def iterator = {
    val traverser: Traverser = Traversal.description.
      relationships(RelationshipTypes.NEXT).
      traverse(firstNode)

    traverser.head.nodes.map((n) => new SqlRow(n)).iterator
  }
}