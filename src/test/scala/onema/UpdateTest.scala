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
//import onema.microb.update.Logic
//import onema.microb.model.Microb.Item
//import org.apache.http.HttpStatus
//import org.scalatest.{FlatSpec, Matchers}
//
//class UpdateTest extends FlatSpec with Matchers {
//
//
//  "An Post" should "update an existing item" in {
//
//  // Arrange
//    val messageId = "be5788db-8211-403a-a230-aa359ea8ce5d"
//    val corsDomain = "https://foo.com"
//    val dynamodbClient = AmazonDynamoDBAsyncClientBuilder.defaultClient()
//    val tableName = "MicrobItems"
//    val logic = new Logic(tableName, dynamodbClient)
//    val item = Item("Update Title", "UPDATE Content 2", id = messageId)
//
//    // Act
//    val response = logic.handleRequest(item, "/path/to/post")
////      .withCors(new DynamodbCorsConfiguration(corsDomain, "CorsOrigins", dynamodbClient))
//
//    // Assert
//    response.getStatusCode should be(HttpStatus.SC_OK)
//    response.getBody should be ("{\"title\":\"Update Title\",\"content\":\"UPDATE Content 2\",\"date\":null,\"id\":null}")
//  }
//}
