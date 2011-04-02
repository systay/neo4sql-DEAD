/*
 * Created by IntelliJ IDEA.
 * User: ata
 * Date: 4/2/11
 * Time: 11:43 AM
 */
package org.neo4j.lab

import org.neo4j.graphdb.DynamicRelationshipType
;
object RelationshipTypes {
  val FIRST = DynamicRelationshipType.withName("first_row")
  val NEXT = DynamicRelationshipType.withName("next_row")

}