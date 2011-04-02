package org.neo4j.lab

import scala.collection.JavaConversions._
import org.neo4j.graphdb.{Relationship, NotFoundException, DynamicRelationshipType, Node}

class SqlRow(node: Node) extends Map[ String, Object ] {
  private val NEXT = DynamicRelationshipType.withName("next_row")

  private def getNextNode: Option[ Relationship ] = {
    node.getRelationships(NEXT).headOption
  }

  def hasNext: Boolean = !getNextNode.isEmpty

  def next: SqlRow = {
    val nextNode: Node = getNextNode.getOrElse(throw new NotFoundException).getOtherNode(node)
    new SqlRow(nextNode)
  }

  def +[ B1 >: Object ](kv: (String, B1)) = throw new IllegalAccessException

  def -(key: String) = throw new IllegalAccessException

  def get(key: String) = Option(node.getProperty(key))

  def iterator = {
    node.getPropertyKeys.map((key) => ( key.toString -> node.getProperty(key) )).iterator
  }

}