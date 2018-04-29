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

package onema.microb.create

import java.text.SimpleDateFormat
import java.time.{ZoneId, ZonedDateTime}
import java.util.UUID.randomUUID

import com.amazonaws.serverless.proxy.model.AwsProxyResponse
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync
import com.amazonaws.services.dynamodbv2.document.{DynamoDB, Item}
import com.typesafe.scalalogging.Logger
import onema.microb.model.Microb.{Item => MicrobItem}
import onema.serverlessbase.function.ApiGatewayResponse
import org.apache.http.HttpStatus

class Logic(tableName: String, dynamodbClient: AmazonDynamoDBAsync) extends ApiGatewayResponse {

  //--- Fields ---
  private val dynamoDb = new DynamoDB(dynamodbClient)
  private val table = dynamoDb.getTable(tableName)
  protected val log = Logger("apigateway-handler")

  //--- Methods ---
  def handleRequest(item: MicrobItem, path: String): AwsProxyResponse = {
    val id = randomUUID().toString
    val date = now
    val dynamoItem = new Item()
      .withPrimaryKey("Id", id)
      .withString("Title", item.title)
      .withString("Content", item.content)
      .withString("PublishDate", date)

    table.putItem(dynamoItem)
    buildResponse(HttpStatus.SC_ACCEPTED, item.copy(id = id, date = date), Map("location" -> s"$path/$id"))
  }

  private def now = {
    val utcZoneId = ZoneId.of("UTC")
    val zonedDateTime = ZonedDateTime.now
    zonedDateTime.withZoneSameInstant(utcZoneId).toString
  }
}
