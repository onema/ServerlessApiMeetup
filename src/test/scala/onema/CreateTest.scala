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

package onema

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder
import onema.microb.create.Logic
import onema.microb.model.Microb.Item
import onema.serverlessbase.configuration.cors.Extensions._
import onema.serverlessbase.configuration.cors.DynamodbCorsConfiguration
import org.apache.http.HttpStatus
import org.scalatest.{FlatSpec, Matchers}

class CreateTest extends FlatSpec with Matchers {


  "An Post" should "create a new item" in {

  // Arrange
    val messageId = "430a160f-67d0-44e1-b25f-103da9548694"
//    val messageId = "4"
    val corsDomain = "http://localhost:63342"
    val dynamodbClient = AmazonDynamoDBAsyncClientBuilder.defaultClient()
    val tableName = "MicrobItems"
    val logic = new Logic(tableName, dynamodbClient)
    val item = Item("Some Title", "Some Content 2")

    // Act
    val response = logic.handleRequest(item, "/path/to/post")
      .withCors(new DynamodbCorsConfiguration(Some(corsDomain), "CorsOrigins", dynamodbClient))

    // Assert
    response.getHeaders.get("location") should fullyMatch regex ("""/path/to/post/[\w\d-]+""")
    response.getStatusCode should be(HttpStatus.SC_ACCEPTED)
  }
}
