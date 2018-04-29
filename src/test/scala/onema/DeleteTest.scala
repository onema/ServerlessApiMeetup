///**
//  * This file is part of the ONEMA ServerlessApiMeetup Package.
//  * For the full copyright and license information,
//  * please view the LICENSE file that was distributed
//  * with this source code.
//  *
//  * copyright (c) 2018, Juan Manuel Torres (http://onema.io)
//  *
//  * @author Juan Manuel Torres <kinojman@gmail.com>
//  */
//
//package onema
//
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder
//import onema.microb.model.Microb.Item
//import onema.microb.delete.Logic
//import org.apache.http.HttpStatus
//import org.scalatest.{FlatSpec, Matchers}
//
//class DeleteTest extends FlatSpec with Matchers {
//
//
//  "An Delete" should "delete an existing item" in {
//
//  // Arrange
//    val messageId = "a7404658-4df9-4cee-86ef-c51f572e683e"
//    val corsDomain = "https://foo.com"
//    val dynamodbClient = AmazonDynamoDBAsyncClientBuilder.defaultClient()
//    val tableName = "MicrobItems"
//    val logic = new Logic(tableName, dynamodbClient)
//
//    // Act
//    val response = logic.handleRequest(messageId)
////      .withCors(new DynamodbCorsConfiguration(corsDomain, "CorsOrigins", dynamodbClient))
//
//    // Assert
//    response.getStatusCode should be(HttpStatus.SC_OK)
//  }
//}
