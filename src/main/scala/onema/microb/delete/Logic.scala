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

package onema.microb.delete

import java.text.SimpleDateFormat
import java.time.{ZoneId, ZonedDateTime}

import com.amazonaws.serverless.proxy.model.AwsProxyResponse
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.spec.{DeleteItemSpec, UpdateItemSpec}
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
  def handleRequest(id: String): AwsProxyResponse = {
    val deletedItem = new DeleteItemSpec()
      .withPrimaryKey("Id", id)
      .withReturnValues(ReturnValue.ALL_OLD)

    Option(table.deleteItem(deletedItem).getItem) match {
      case Some(item) =>
        val newItem = Item(
          title = item.getString("Title"),
          content = item.getString("Content"),
          date = item.getString("PublishDate"),
          id = item.getString("Id")
        )
        buildResponse(HttpStatus.SC_OK, newItem)
      case None =>
        buildResponse(HttpStatus.SC_GONE)
    }
  }
}
