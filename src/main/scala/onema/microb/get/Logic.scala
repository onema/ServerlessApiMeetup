/**
  * This file is part of the ONEMA ServerlessApiMeetup Package.
  * For the full copyright and license information,
  * please view the LICENSE file that was distributed
  * with this source code.
  *
  * copyright (c) 2018, Juan Manuel Torres (http://onema.io)
  *
  * @author Juan Manuel Torres <kinojman@gmail.com>
  */

package onema.microb.get

import com.amazonaws.serverless.proxy.model.AwsProxyResponse
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync
import com.amazonaws.services.dynamodbv2.document.DynamoDB
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
    log.info(s"ID: $id")
    Option(table.getItem("Id", id)) match {
      case Some(item) => buildResponse(HttpStatus.SC_OK, Item(
        id = item.getString("Id"),
        title = item.getString("Title"),
        content = item.getString("Content"),
        date = item.getString("PublishDate"))
      )
      case None => buildError(HttpStatus.SC_NOT_FOUND, "Item not found")
    }
  }
}
