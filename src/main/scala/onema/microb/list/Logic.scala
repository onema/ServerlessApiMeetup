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

package onema.microb.list

import java.util

import com.amazonaws.serverless.proxy.model.AwsProxyResponse
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync
import com.amazonaws.services.dynamodbv2.model.{AttributeValue, ScanRequest}
import onema.microb.model.Microb.{Item, ItemList}
import onema.serverlessbase.function.ApiGatewayResponse
import org.apache.http.HttpStatus

import scala.collection.JavaConverters._
import scala.collection.mutable

class Logic(tableName: String, dynamodbClient: AmazonDynamoDBAsync) extends ApiGatewayResponse {

  def handleRequest(): AwsProxyResponse = {
    val scanRequest = new ScanRequest().withTableName(tableName)
    val items: mutable.Seq[util.Map[String, AttributeValue]] = dynamodbClient.scan(scanRequest).getItems.asScala
    val listOfMicrobItem = items.map(x => {
      Item(
        id = x.get("Id").getS,
        title = x.get("Title").getS,
        content = x.get("Content").getS,
        date = x.get("PublishDate").getS
      )
    })

    buildResponse(HttpStatus.SC_OK, ItemList(listOfMicrobItem, listOfMicrobItem.size))
  }
}
