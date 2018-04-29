/**
  * This file is part of the ONEMA Default (Template) Project Package.
  * For the full copyright and license information,
  * please view the LICENSE file that was distributed
  * with this source code.
  *
  * copyright (c) 2018, Juan Manuel Torres (http://onema.io)
  *
  * @author Juan Manuel Torres <kinojman@gmail.com>
  */

package onema.microb.update

import java.text.SimpleDateFormat
import java.time.{ZoneId, ZonedDateTime}

import com.amazonaws.serverless.proxy.model.AwsProxyResponse
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap
import com.amazonaws.services.dynamodbv2.model.ReturnValue
import com.typesafe.scalalogging.Logger
import onema.microb.model.Microb.Item
import onema.serverlessbase.function.ApiGatewayResponse
import org.apache.http.HttpStatus

class Logic(tableName: String, dynamodbClient: AmazonDynamoDBAsync) extends ApiGatewayResponse {

  //--- Fields ---
  private val dynamoDb = new DynamoDB(dynamodbClient)
  private val table = dynamoDb.getTable(tableName)
  protected val log = Logger("apigateway-handler")

  //--- Methods ---
  def handleRequest(item: Item, path: String): AwsProxyResponse = {
    val updateItem = new UpdateItemSpec().withPrimaryKey("Id", item.id)
    if(itemExists(item.id)) {
      updateItem.withUpdateExpression("set Title = :t, Content = :c")
        .withValueMap(new ValueMap().withString(":t", item.title).withString(":c", item.content))
    } else {
      updateItem.withUpdateExpression("set Title = :t, Content = :c, PublishDate = :d")
        .withValueMap(new ValueMap().withString(":t", item.title).withString(":c", item.content).withString(":d", now))
    }

    updateItem.withReturnValues(ReturnValue.ALL_NEW)
    val outcome = table.updateItem(updateItem)
    val newItem = Item(
      title = outcome.getItem.getString("Title"),
      content = outcome.getItem.getString("Content"),
      date = outcome.getItem.getString("PublishDate"),
      id = outcome.getItem.getString("Id")
    )
    buildResponse(HttpStatus.SC_OK, newItem)
  }

  private def itemExists(id: String) = {
    Option(table.getItem("Id", id)) match {
      case Some(_) => true
      case None => false
    }
  }

  private def now = {
    val utcZoneId = ZoneId.of("UTC")
    val zonedDateTime = ZonedDateTime.now
    zonedDateTime.withZoneSameInstant(utcZoneId).toString
  }
}
